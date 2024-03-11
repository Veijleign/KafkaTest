package com.example.kafkatest.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class KafkaListeners {

    Map<String, Set<String>> consumedRecords = new ConcurrentHashMap<>();

    @KafkaListener(
            topics = "ignis",
            groupId = "groupId"
    )
    void listener(
            ConsumerRecord<?, ?> consumerRecord,
            @Header(KafkaHeaders.OFFSET) Long offset,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition
    ) throws InterruptedException {

        log.info(
                "Data = " + consumerRecord.value()
                        + ", offset = " + offset
                        + ", partition = " + partition
        );
        trackConsumedPartitionsString(
                "consumer-1",
                consumerRecord.partition()
        );
    }

    private void trackConsumedPartitionsString(
            String consumerName,
            int partitionNUmber
    ) {
        log.info("Was been here");
        consumedRecords.computeIfAbsent(consumerName, k -> new HashSet<>());
        consumedRecords.computeIfPresent(consumerName, (k, v) -> {
            v.add(String.valueOf(partitionNUmber));
            return v;
        });
    }

}