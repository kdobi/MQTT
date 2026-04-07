package Java.MQTT.controller;

import Java.MQTT.publisher.MqttPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {

    // Publisher를 주입받아서 사용
    private final MqttPublisher mqttPublisher;

    public MqttController(MqttPublisher mqttPublisher) {
        this.mqttPublisher = mqttPublisher;
    }

    @GetMapping("/mqtt/publish")
    public String publish(@RequestParam String topic,
                          @RequestParam String message) {
        // http://localhost:8080/mqtt/publish?topic=test/topic&message=hello
        mqttPublisher.publish(topic, message);
        return "MQTT message published successfully";
    }
}