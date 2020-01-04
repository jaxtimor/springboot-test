package com.cn;

import com.cn.DTO.BookDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMQ {

    @Autowired
    private RabbitTemplate template;

    @Test
    public void sendMsgDirect(){
        Map<String,Object> map = new HashMap<>();
        map.put("hello","world");
        template.convertAndSend("exchange.direct","atguigu.news",map);
    }

    @Test
    public void receiveMsg(){
        Object o = template.receiveAndConvert("atguigu.news");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    @Test
    public void sendBookMSG(){
        BookDTO dto = new BookDTO();
        dto.setAuthor("jetfang");
        dto.setName("ybhshigewangba");
        template.convertAndSend("exchange.direct","atguigu.news",dto);
    }
}
