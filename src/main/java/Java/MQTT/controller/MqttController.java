package Java.MQTT.controller;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {

    @GetMapping("/send")
    public String send(@RequestParam String msg) {
        try {
            // 발송용 클라이언트 생성 (매번 새로 생성하여 간단히 테스트)
            String clientId = "pub-client-" + System.currentTimeMillis();
            MqttClient client = new MqttClient("tcp://broker.emqx.io:1883", clientId);
            client.connect();
            
            MqttMessage message = new MqttMessage(msg.getBytes());
            // Subscriber가 구독 중인 토픽과 동일한 곳으로 발행합니다.
            client.publish("test/topic/123", message);
            
            client.disconnect();
            client.close(); // 자원 해제
            
            return ">>> [MQTT] 발송 성공: " + msg;
        } catch (MqttException e) {
            return ">>> [MQTT] 발송 실패: " + e.getMessage();
        }
    }
}
