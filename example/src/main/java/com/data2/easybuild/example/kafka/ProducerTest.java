package com.data2.easybuild.example.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class ProducerTest {

    public void send() {
        Properties props = new Properties();
        //kafka集群，broker-list
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-1:9092");
        //all相当于-1
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        //重试次数
        props.put(ProducerConfig.RETRIES_CONFIG, 2);
        //批次大小
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        //等待时间
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        //RecordAccumulator缓冲区大小
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer kafkaProducer = new KafkaProducer(props);

        ProducerRecord record1 = new ProducerRecord("topic", "order123131");
        ProducerRecord record2 = new ProducerRecord("topic","order2342232");

        kafkaProducer.beginTransaction();
        kafkaProducer.send(record1);
        kafkaProducer.send(record2);
        kafkaProducer.commitTransaction();


    }

}
