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
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);

        // 공용 브로커 테스트를 위해 인증 정보는 잠시 주석 처리합니다.
        // options.setUserName("username");
        // options.setPassword("password".toCharArray());

        return options;
    }
}