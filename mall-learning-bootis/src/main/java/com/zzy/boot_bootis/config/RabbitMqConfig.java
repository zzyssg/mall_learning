package com.zzy.boot_bootis.config;

import com.zzy.boot_bootis.model.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @ClassName RabbitMqConfig
 * @Author ZZy
 * @Date 2023/9/17 16:51
 * @Description
 * @Version 1.0
 */
@Configuration
public class RabbitMqConfig {


    /**
     * 订单消息实际消费队列绑定到额交换机
     * @return
     */
    @Bean(value = "orderDirect")
    DirectExchange orderDirect() {
        return ExchangeBuilder.directExchange(QueueEnum.QUEUE_ORDER_CANCEL.getExchange())
                .durable(true)
                .build();
    }


    /**
     * 订单延迟队列所绑定的交换机
     * @return
     */
    @Bean(value = "orderTtlDirect")
    @Primary
    DirectExchange orderTtlDirect() {
        return ExchangeBuilder.directExchange(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange())
                .durable(true)
                .build();
    }


    @Bean
    public Queue orderQueue() {
        return new Queue(QueueEnum.QUEUE_ORDER_CANCEL.getName());
    }

    @Bean
    public Queue orderTtlQueue() {
        return QueueBuilder.durable(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getName())
                .withArgument("x-dead-letter-exchange", QueueEnum.QUEUE_ORDER_CANCEL.getExchange())
                .withArgument("x-dead-letter-routing-key", QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey())
                .build();
    }


    /**
     * 将订单队列绑定到交换机
     * @param directExchange
     * @param orderQue
     * @return
     */
    @Bean
    Binding orderBinding(DirectExchange directExchange, @Qualifier("orderQueue") Queue  orderQue) {
        return BindingBuilder.bind(orderQue).to(directExchange)
                .with(QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey());
    }

    /**
     * 将订单延迟队列绑定到交换机
     * @param directTtlExchange
     * @param orderTtlQue
     * @return
     */
    @Bean
    Binding orderTtlBinding(DirectExchange directTtlExchange, @Qualifier("orderTtlQueue") Queue  orderTtlQue) {
        return BindingBuilder.bind(orderTtlQue).to(directTtlExchange)
                .with(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey());
    }



}
