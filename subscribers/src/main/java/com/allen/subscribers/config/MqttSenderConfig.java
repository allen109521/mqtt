package com.allen.subscribers.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageHandler;

@Configuration
@IntegrationComponentScan
public class MqttSenderConfig {
  @Value("${spring.mqtt.username}")
  private String username;

  @Value("${spring.mqtt.password}")
  private String password;

  @Value("${spring.mqtt.url}")
  private String hostUrl;

  @Value("${spring.mqtt.client.id}")
  private String clientId;

  @Value("${spring.mqtt.default.topic}")
  private String defaultTopic;

  @Bean
  public MqttConnectOptions getMqttConnectOptions(){
    MqttConnectOptions mqttConnectOptions=new MqttConnectOptions();
    mqttConnectOptions.setUserName(username);
    mqttConnectOptions.setPassword(password.toCharArray());
    mqttConnectOptions.setServerURIs(new String[]{hostUrl});
    return mqttConnectOptions;
  }
  @Bean
  public MqttPahoClientFactory mqttClientFactory() {
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    factory.setConnectionOptions(getMqttConnectOptions());
    return factory;
  }
  @Bean
  @ServiceActivator(inputChannel = "mqttOutboundChannel")
  public MessageHandler mqttOutbound() {
    MqttPahoMessageHandler messageHandler =  new MqttPahoMessageHandler(clientId, mqttClientFactory());
    messageHandler.setAsync(true);
    messageHandler.setDefaultTopic(defaultTopic);
    return messageHandler;
  }

}
