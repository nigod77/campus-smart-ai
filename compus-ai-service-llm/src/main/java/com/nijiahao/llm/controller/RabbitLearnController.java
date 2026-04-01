package com.nijiahao.llm.controller;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/llm/test")
public class RabbitLearnController {

    // RabbitTemplate 是 Spring Boot 提供的核心操作类，用来发送消息
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // ==========================================
    // 第一阶段：最基础的发送 (点对点直连)
    // 目标：只要把消息丢进一个名叫 "hello_queue" 的队列就行
    // ==========================================
    @GetMapping("/step1/simple")
    public String sendSimpleMessage(@RequestParam(name = "msg") String msg) {
        // 参数1：队列名称 (这里假设我们绕过自定义交换机，直接用队列名作为路由键发送到默认交换机)
        // 参数2：具体要发送的消息内容
        rabbitTemplate.convertAndSend("hello_queue", msg);
        return "第一阶段：基础消息发送成功 -> " + msg;
    }

    // ==========================================
    // 第二阶段：进阶路由发送 (使用交换机)
    // 目标：把消息发给指定的交换机，并附带一个路由键（Routing Key）
    // ==========================================
    @GetMapping("/step2/routing")
    public String sendRoutingMessage(@RequestParam(name = "msg") String msg) {
        String exchangeName = "learn_direct_exchange"; // 交换机名称
        String routingKey = "learn.routing.key";       // 路由键

        // 参数1：交换机名称
        // 参数2：路由键 (交换机靠这个字符串来决定把消息扔给哪个队列)
        // 参数3：消息内容
        rabbitTemplate.convertAndSend(exchangeName, routingKey, msg);
        return "第二阶段：路由消息发送成功 -> 交由 " + exchangeName + " 处理";
    }

    // ==========================================
    // 第三阶段：可靠性投递测试 (结合你之前的 YAML 配置)
    // 目标：发送消息后，我们要知道 RabbitMQ 到底收没收到
    // ==========================================
    @GetMapping("/step3/confirm")
    public String sendMessageWithConfirm(@RequestParam(name = "msg") String msg) {
        // 创建一个唯一的消息ID，用来在回调时核对是哪条消息
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        // 我们故意写错一个交换机名字，测试回调机制会不会告诉我们失败了
        String badExchange = "non_existent_exchange";
        
        System.out.println("准备发送消息，ID: " + correlationData.getId());

        // 发送消息，并将 correlationData 作为第4个参数传入
        rabbitTemplate.convertAndSend(badExchange, "some.key", msg, correlationData);
        
        return "第三阶段：带确认机制的消息已发送，请观察控制台的回调日志！ID: " + correlationData.getId();
    }
}