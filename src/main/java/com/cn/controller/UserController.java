package com.cn.controller;


import com.cn.DTO.DeptDTO;
import com.cn.DTO.UserDTO;
import com.cn.dao.UserDAO;
import com.cn.service.DeptService;
import com.cn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @RequestMapping(value = "/getInfoList")
    public Object getInfoList(HttpServletRequest request, @RequestBody Map<String, String> params){
        return userService.getUserInfo(Integer.valueOf(params.get("id")));
    }

    @RequestMapping(value = "/getUserById/{id}")
    public Object getInfoList(HttpServletRequest request, @PathVariable("id") Integer id){
        return userService.getUserInfo(id);
    }

    @RequestMapping(value = "/updateUserInfo")
    public UserDTO updateUserInfo(UserDTO userDTO){
        return userService.updateUserInfo(userDTO);
    }

    @RequestMapping(value = "/del/{id}")
    public Object del(@PathVariable("id") Integer id){
        return userService.delUser(id);
    }

    @RequestMapping("/getUserByName/{name}")
    public UserDTO getUserByName(@PathVariable("name") String name){
        return userService.getUserInfo(name);
    }

    @RequestMapping("/getDeptById/{id}")
    public DeptDTO getDeptById(@PathVariable("id") Integer id){
        return deptService.getDeptById(id);
    }

    @RequestMapping("/getDeptByCode/{id}")
    public DeptDTO getDeptByCode(@PathVariable("id") Integer id){
        return deptService.getDeptByCode(id);
    }
}
