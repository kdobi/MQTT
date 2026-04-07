package Java.MQTT.subscriber;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;

@Service
public class MqttSubscriber {
    private final MqttConnectOptions options;
    private final MqttCallbackHandler callbackHandler;

    public MqttSubscriber(MqttConnectOptions options, MqttCallbackHandler callbackHandler) {
        this.options = options;
        this.callbackHandler = callbackHandler;
    }

    @PostConstruct
    public void init() {
        try {
            // 공용 무료 브로커 주소 (broker.emqx.io)
            // 클라이언트 ID가 중복되면 연결이 끊기므로 랜덤한 ID를 생성합니다.
            String clientId = "sub-client-" + System.currentTimeMillis();
            MqttClient client = new MqttClient("tcp://broker.emqx.io:1883", clientId);
            
            client.setCallback(callbackHandler);
            client.connect(options);
            
            // "test/topic/123" 이라는 주소로 오는 메시지를 구독합니다.
            client.subscribe("test/topic/123"); 
            System.out.println(">>> [MQTT] Subscriber가 'test/topic/123' 구독을 시작했습니다! (ID: " + clientId + ")");
        } catch (MqttException e) {
            System.err.println(">>> [MQTT] 연결 실패: " + e.getMessage());
        }
    }
}
