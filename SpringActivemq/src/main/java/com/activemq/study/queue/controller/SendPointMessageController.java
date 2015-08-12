package com.activemq.study.queue.controller;

import com.activemq.study.domain.User;
import com.activemq.study.queue.producer.QueueSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: SendPointMessageController.java
 * @Author: sunfayun
 * @Date: 2015/08/05
 * @Time: 下午6:15
 * @Version 1.0
 */
@Controller
@RequestMapping("/activemq")
public class SendPointMessageController {

    @Autowired
    private QueueSender queueSender;

    @ResponseBody
    @RequestMapping(value = "/queueSender")
    public String sendMessage(User user){
        queueSender.send("queue_info",user);
        return "suc";
    }

}
