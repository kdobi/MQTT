package Java.MQTT.subscriber;

import jakarta.annotation.PostConstruct; // Bean 생성 후 자동 실행 메서드 지정  ( 객체 생성 -> 의존성 주입 완료 -> 해당 메서드 실행 )
import jakarta.annotation.PreDestroy; // Bean 소멸 직전 실행 메서드 지정 (Spring 종료시 -> 자원 정리 메서드 실행)
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriber {

    // 상수 정의 ( 브로커 주소, 클라이언트 아이디, 구독할 토픽 )
    private static final String BROKER_URL = "tcp://localhost:1883";
    private static final String CLIENT_ID = "spring-subscriber-client";
    private static final String TOPIC = "test/topic";

    // 외부주입 ( MQTT 연결 설정, MQTT 메시지 처리 로직 )
    private final MqttConnectOptions mqttConnectOptions;
    private final MqttCallbackHandler mqttCallbackHandler;

    private MqttClient client;

    public MqttSubscriber(MqttConnectOptions mqttConnectOptions,
                          MqttCallbackHandler mqttCallbackHandler) {
        this.mqttConnectOptions = mqttConnectOptions;
        this.mqttCallbackHandler = mqttCallbackHandler;
    }

    @PostConstruct
    public void subscribe() {
        try {
            client = new MqttClient(BROKER_URL, CLIENT_ID);
            client.setCallback(mqttCallbackHandler);
            client.connect(mqttConnectOptions); // MQTT 연결 ( 1.TCP생성 / 2.CONNECT 패킷 전송 / 3. CONNACK 응답 수신 )
            client.subscribe(TOPIC, 1);

            System.out.println("[SUBSCRIBE START] topic = " + TOPIC);

        } catch (Exception e) {
            System.out.println("[SUBSCRIBE ERROR] " + e.getMessage());
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void disconnect() {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
            }
            if (client != null) {
                client.close();
            }
            System.out.println("[SUBSCRIBE END] disconnected");
        } catch (Exception e) {
            System.out.println("[SUBSCRIBE CLOSE ERROR] " + e.getMessage());
        }
    }
}