package Java.MQTT.publisher;

import org.eclipse.paho.client.mqttv3.MqttClient;   // TCP 기반 MQTT 연결,발행,구독
import org.eclipse.paho.client.mqttv3.MqttConnectOptions; // MQTT 연결 설정 객채
import org.eclipse.paho.client.mqttv3.MqttMessage;  // 메시지 객체
import org.springframework.stereotype.Component;    // Spring Bean 등록

@Component
public class MqttPublisher {

    private static final String BROKER_URL = "tcp://localhost:1883"; // MQTT 브로커 주소, 1833은 기본포트
    private static final String CLIENT_ID = "spring-publisher-client"; // 브로커에서 각 클라이언트 구분용

    // DI - 연결 설정 객체
    private final MqttConnectOptions mqttConnectOptions;

    // 	Spring이 자동으로 MqttConnectOptions 주입
    public MqttPublisher(MqttConnectOptions mqttConnectOptions) {
        this.mqttConnectOptions = mqttConnectOptions;
    }

    // topic = 메시지 주제,  payload = 실제 데이터
    public void publish(String topic, String payload) {
        MqttClient client = null; // try 밖에 선언 -> finally에서 사용

        try {
            client = new MqttClient(BROKER_URL, CLIENT_ID);
            client.connect(mqttConnectOptions);

            // 문자열 -> byte (MQTT는 byte 기반 전송)
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(1); // Qos 설정  0=손실가능, 1=중복가능, 2=완전보장

            client.publish(topic, message);

            System.out.println("[PUBLISH] topic = " + topic);
            System.out.println("[PUBLISH] payload = " + payload);

        } catch (Exception e) {
            System.out.println("[PUBLISH ERROR] " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (client != null && client.isConnected()) {
                    client.disconnect();
                }
                if (client != null) {
                    client.close();
                }
            } catch (Exception e) {
                System.out.println("[PUBLISH CLOSE ERROR] " + e.getMessage());
            }
        }
    }
}