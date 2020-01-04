package com.cn.service;


import com.cn.DTO.DeptDTO;
import com.cn.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DeptService {

    @Resource(name = "redisCacheManager")
    RedisCacheManager redisCacheManager;

    @Autowired
    private UserDAO userDAO;


    //通过注解的方式
    @Cacheable(cacheNames = "dept",cacheManager = "redisCacheManager")
    public DeptDTO getDeptById(Integer id){
        return userDAO.getDeptById(id);
    }

    //编码的方式设置缓存
    public DeptDTO getDeptByCode(Integer id){
        DeptDTO deptDTO = userDAO.getDeptById(id);
        Cache dept = redisCacheManager.getCache("dept");
        dept.put("code:"+id,deptDTO);
        return deptDTO;
    }




}
