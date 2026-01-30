package com.nijiahao.llm.controller;

import com.nijiahao.llm.config.RabbitConfig;
import com.nijiahao.common.core.domain.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/llm/mq")
@RequiredArgsConstructor
public class MqTestController {

    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public Result<String> sendTestMessage(@RequestParam("docId") Long docId) {
        
        // 发送消息
        rabbitTemplate.convertAndSend(
            RabbitConfig.EXCHANGE_NAME, 
            RabbitConfig.ROUTING_KEY, 
            docId
        );

        return Result.success("消息已发送，请看控制台日志！ID=" + docId);
    }
}