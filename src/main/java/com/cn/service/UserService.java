package com.cn.service;


import com.cn.DTO.DeptDTO;
import com.cn.DTO.UserDTO;
import com.cn.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "emp")
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Cacheable(cacheNames = {"emp"},keyGenerator = "mykeyGenerator",unless = "#result==null")
    public Object getUserInfo(Integer id){
        return userDAO.getUserInfo(id);
    }

    //返回之后缓存返回结果
    @CachePut(keyGenerator = "mykeyGeneratorForUpdate")
    public UserDTO updateUserInfo(UserDTO userDTO){
        userDAO.updateUserInfo(userDTO);
        return userDTO;
    }

    //方法执行完毕后执行，可配置为方法执行前执行 无论方法成功或者失败
    @CacheEvict(key = "#id")
    public String delUser(Integer id){
        System.out.println("del user:" + id);
        return "success";
    }

    //有put方法一定会执行 所以通过lastname 一定会查询数据库
    @Caching(
            cacheable = {
                    @Cacheable(key = "#lastName")
            },
            put = {
                    @CachePut(key = "#result.id"),//按返回结果中的id为key 更新缓存
                    @CachePut(key="#result.email")
            }
    )
    public UserDTO getUserInfo(String lastName){
        return userDAO.getUserInfoByName(lastName);
    }


}
