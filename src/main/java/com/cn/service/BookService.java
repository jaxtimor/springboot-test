package com.cn.service;

import com.cn.DTO.BookDTO;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class BookService {
    private static final Logger logger = Logger.getLogger(BookService.class);

    @RabbitListener(queues = "atguigu.news")
    public void getBookMsg(BookDTO book){
        System.out.println("Book MSG:" + book);
    }

    @RabbitListener(queues = "atguigu.emps")
    public void getEmps(Object o){
        System.out.println(o.getClass());
        System.out.println(o);
    }

    @Scheduled(cron = "0 0/5  * * * MON-FRI")
    public void hello(){
        logger.info("hello...");
    }
}
