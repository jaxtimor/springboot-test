package com.cn.dao;

import com.cn.DTO.DeptDTO;
import com.cn.DTO.UserDTO;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDAO {
    @Select("<script>" +
            "select * from employee " +
            "<where>" +
            "<if test=\"id != null and id != '' \">" +
            " id = #{id}" +
            "</if>" +
            "</where>" +
            "</script>")
    public List<Map<String, Object>> getUserInfo(@Param("id") Integer id);

    @Select("<script>" +
            "select * from employee " +
            "<where>" +
            "<if test=\"lastName != null and lastName != '' \">" +
            " lastName = #{lastName}" +
            "</if>" +
            "</where>" +
            "</script>")
    public UserDTO getUserInfoByName(@Param("lastName") String lastName);

    @Update("<script>" +
            "update employee set lastName=#{lastName},email=#{email} " +
            "where id = #{id}" +
            "</script>")
    public int updateUserInfo(UserDTO userDTO);

    @Delete("delete from employee where id = #{id}")
    public int delUser(@Param("id") Integer id);

    @Select("select * from department where id = #{id}")
    public DeptDTO getDeptById(@Param("id") Integer id);
}
