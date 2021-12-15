package com.data2.easybuild.example.kafka;

import com.data2.easybuild.message.queue.common.rocketmq.AbstractPullConsumerJob;
import com.data2.easybuild.message.queue.common.rocketmq.KafkaConsumer;
import com.data2.easybuild.message.queue.common.rocketmq.RocketMqConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author data2
 * @description
 * @date 2021/3/25 下午4:03
 */
@Component
@KafkaConsumer(bootstrapServers = "", topic = "test_kafka_topic", groupId = "kafkaGroup")
public class TestPullKafkaConsumerJob extends AbstractPullConsumerJob {

    @Override
    public void run(String... args) {
        // DO your business, with kafkaConsumer
        // kafkaConsumer.poll(Duration.ofNanos(100));
        ConsumerRecords<String,String> records = kafkaConsumer.poll(Duration.ofNanos(1000));
        for (ConsumerRecord<String,String> record : records){
            System.out.printf("topic=%s, partition=%s, offset=%d, customer=%s, country=%s",
                    record.topic(), record.partition(), record.offset(),
                    record.key(), record.value());
        }
        consumer.commitSync();
    }
}
