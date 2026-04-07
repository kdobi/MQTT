# MQTT 이해

```text
MqttApplication
   ↓ 앱 시작
MqttConfig
   ↓ MQTT 연결 설정 제공
MqttSubscriber
   ↓ 브로커 연결 + 토픽 구독
MqttCallbackHandler
   ↓ 메시지 도착 시 처리
MqttController
   ↓ HTTP 요청 받음
MqttPublisher
   ↓ MQTT 메시지 발행