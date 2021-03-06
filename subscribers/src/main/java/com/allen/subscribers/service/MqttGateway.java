package com.allen.subscribers.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {
  /**
   * 发送信息
   * @param data
   */
  void sendToMqtt(String data);

  /**
   * 指定主题发送信息
   * @param topic
   * @param payload
   */
  void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, String payload);

  /**
   * 指定主题和qos发送信息
   * @param topic
   * @param qos
   * @param payload
   */
  void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);

}
