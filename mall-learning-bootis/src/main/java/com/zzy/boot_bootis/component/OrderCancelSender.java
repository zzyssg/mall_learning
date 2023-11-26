package com.zzy.boot_bootis.component;

import com.zzy.boot_bootis.model.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName OrderCancelSender
 * @Author ZZy
 * @Date 2023/9/17 17:58
 * @Description
 * @Version 1.0
 */
@Component
public class OrderCancelSender {

    private static Logger logger = LoggerFactory.getLogger(OrderCancelSender.class);

    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendMessage(Long orderId,final long delayTimes) {

        amqpTemplate.convertAndSend(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(), QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(), orderId, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            }

        });
        logger.info("send delay orderId :{}", orderId);

    }


}

