package Java.MQTT.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;   // MQTT 연결 옵션 설정 클래스
import org.springframework.context.annotation.Bean;         // 메서드가 반환한 객체를 스프링 빈에 등록
import org.springframework.context.annotation.Configuration; // 이 클래스가 설정 클래스임을 표시

@Configuration
public class MqttConfig {

    /*
     MQTT 연결할 때 어떤 방식으로 연결할지 적어놓는 설정서

     대표적인 설정들
     - cleanSession = 이전 세션 유지 여부
     - automaticReconnect = 연결 끊기면 자동 재연결 여부
     - connectionTimeout = 연결 시도 최대 대기 시간
     - keepAliveInterval = 연결 유지 heartbeat 간격. 즉 생존 확인용
     - userName = 사용자 이름
     - password = 비밀번호

     MQTT 클라이언트가 connect(options) 할 때, 이 객체의 값들을 읽어서 브로커와 연결 협상을 수행한다.
    */

    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        // 이부분은 내부에서 직접 생성 안하고 yml에서 주입 받아서 사용 가능

        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);

        // 인증 필요시
        options.setUserName("username");  // 브로커에 인증할 사용자 이름
        options.setPassword("password".toCharArray());  // 보안측면에서 String 보다 char이 유리함.

        return options;
    }
}