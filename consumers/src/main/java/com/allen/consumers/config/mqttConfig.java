package com.allen.consumers.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.util.StringUtils;

@Configuration
public class mqttConfig {
  @Bean
  public MessageChannel mqttOutboundChannel() {
    return new DirectChannel();
  }

  @Value("${spring.mqtt.username}")
  private String username;

  @Value("${spring.mqtt.password}")
  private String password;

  @Value("${spring.mqtt.url}")
  private String hostUrl;

  @Value("${spring.mqtt.consumer.defaultTopic}")
  private String consumerDefaultTopic;

  @Value("${spring.mqtt.consumer.clientId}")
  private String consumerClientId;




  /**
   * MQTT消息订阅绑定（消费者）
   *
   * @return {@link org.springframework.integration.core.MessageProducer}
   */

  @Bean
  public MessageProducer inbound() {
    // 可以同时消费（订阅）多个Topic
    MqttPahoMessageDrivenChannelAdapter adapter =
            new MqttPahoMessageDrivenChannelAdapter(
                    consumerClientId, mqttClientFactory(),
                    StringUtils.split(consumerDefaultTopic, ","));
    adapter.setCompletionTimeout(5000);
    adapter.setConverter(new DefaultPahoMessageConverter());
    adapter.setQos(1);
    // 设置订阅通道
    adapter.setOutputChannel(mqttInboundChannel());
    return adapter;
  }

  /**
   * MQTT信息通道（消费者）
   *
   * @return {@link org.springframework.messaging.MessageChannel}
   */
  @Bean(name = "mqttInboundChannel")
  public MessageChannel mqttInboundChannel() {
    return new DirectChannel();
  }

  /**
   * MQTT消息处理器（消费者）
   *
   * @return {@link org.springframework.messaging.MessageHandler}
   */
  @Bean
  @ServiceActivator(inputChannel = "mqttInboundChannel")
  public MessageHandler handler() {
    return new MessageHandler() {
      @Override
      public void handleMessage(Message<?> message) throws MessagingException {
        System.out.println("接收到的消息为："+message.getPayload()+message.toString());
      }
    };
  }



  @Bean
  public MqttPahoClientFactory mqttClientFactory() {
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    factory.setConnectionOptions(getMqttConnectOptions());
    return factory;
  }

  @Bean
  public MqttConnectOptions getMqttConnectOptions(){
    MqttConnectOptions mqttConnectOptions=new MqttConnectOptions();
    mqttConnectOptions.setUserName(username);
    mqttConnectOptions.setPassword(password.toCharArray());
    mqttConnectOptions.setServerURIs(new String[]{hostUrl});
    return mqttConnectOptions;
  }


}
