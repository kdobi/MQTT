package Java.MQTT.subscriber;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken; // 메시지 전송 완료 상태를 추적하는 객체
import org.eclipse.paho.client.mqttv3.MqttCallback; // MQTT 이벤트 처리 인터페이스 (연결 끊김, 메시지 도착, 전송 완료)
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Component
public class MqttCallbackHandler implements MqttCallback {

    // 브로커와 연결이 끊겼을 때
    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("[SUBSCRIBE] connection lost");
        if (cause != null) {
            cause.printStackTrace();
        }
    }

    // 구독한 topic으로 메시지가 들어왔을 때
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("[SUBSCRIBE] topic = " + topic);
        System.out.println("[SUBSCRIBE] payload = " + new String(message.getPayload()));
    }

    // 메시지 전송 완료시 (publisher 이벤트)
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Subscriber에서는 보통 특별히 사용할 일이 거의 없음
        System.out.println("[SUBSCRIBE] delivery complete");
    }
}