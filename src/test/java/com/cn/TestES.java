package com.cn;


import com.cn.DTO.UserDTO;
import com.cn.repository.UserRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestES {

    @Autowired
    private JestClient jestClient;

    @Autowired
    UserRepository userRepository;
    @Test
    public void test02(){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(2);
        userDTO.setLastName("haha");
        userRepository.index(userDTO);
    }
    @Test
    public void test() throws IOException {
        //保存一个文档
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setLastName("jetfang");
        userDTO.setEmail("111@qq.com");
        //构建一个索引
        Index index = new Index.Builder(userDTO).index("atguigu").type("user").build();
        //client执行以下
        jestClient.execute(index);
    }

    @Test
    public void search() throws IOException {
        String json = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"lastName\" : \"haha\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        Search se = new Search.Builder(json).addIndex("atguigu").addType("user").build();
        SearchResult searchResult = jestClient.execute(se);
        System.out.println(searchResult.getJsonString());
    }
}
