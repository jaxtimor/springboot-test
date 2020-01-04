package com.cn.config;

import com.cn.DTO.UserDTO;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;


@Configuration
public class CacheConfig {

    @Bean("mykeyGenerator")
    public KeyGenerator keyGenerator(){
        return new KeyGenerator(){

            @Override
            public Object generate(Object o, Method method, Object... objects) {
                return  objects[0];
            }
        };
    }

    @Bean("mykeyGeneratorForUpdate")
    public KeyGenerator keyGeneratorForUpdate(){
        return new KeyGenerator(){

            @Override
            public Object generate(Object o, Method method, Object... objects) {
                return  ((UserDTO)objects[0]).getId();
            }
        };
    }
}
