package com.example.kafkatest;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("api/v1/test")
public class TestController {

    private KafkaTemplate<String, String> kafkaTemplate;

    public TestController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/kafkaTest1")
    public ResponseEntity<Void> publish(
            @RequestBody TestMessage dto
    ) {
        kafkaTemplate.send(
                "ignis",
                dto.testMessage()
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/kafkaTest2/{id}")
    public ResponseEntity<Void> measureCelsius(
            @PathVariable Long id
    ) {
        new Random()
                .doubles(25, 35)
                .limit(id)
                .forEach(tmp -> {
                    kafkaTemplate.send(
                            "ignis",
                            ((Double)tmp).toString()
                    );
                });
        return ResponseEntity.ok().build();
    }


    @GetMapping("/justTest")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello. I'm working fine");
    }

//    @PostMapping("/transactions")
//    public void generateAndSendMessages(@RequestBody InputParameters inputParameters) {
//        for (long i = 0; i < inputParameters.getNumberOfMessages(); i++) {
//            Order o = new Order(id++, i+1, i+2, 1000, "NEW", groupId);
//            CompletableFuture<SendResult<Long, Order>> result =
//                    kafkaTemplate.send("transactions", o.getId(), o);
//            result.whenComplete((sr, ex) ->
//                    LOG.info("Sent({}): {}", sr.getProducerRecord().key(), sr.getProducerRecord().value()));
//        }
//        groupId++;
//    }

}
