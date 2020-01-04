package com.cn.repository;

import com.cn.DTO.UserDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserRepository extends ElasticsearchRepository<UserDTO,Integer> {


}
