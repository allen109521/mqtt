package com.allen.subscribers.config;/*
package com.allen.mqttprogram.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@Slf4j
public class MqttInboundConfiguration {
  @Autowired
  private MqttProperties mqttProperties;
  @Bean
  public MessageChannel mqttInputChannel() {
    return new DirectChannel();
  }
  @Bean
  public MqttConnectOptions getMqttConnectOptions(){
    MqttConnectOptions mqttConnectOptions=new MqttConnectOptions();
    mqttConnectOptions.setServerURIs( mqttProperties.getUrl().split(","));
    mqttConnectOptions.setKeepAliveInterval(100);
    mqttConnectOptions.setMaxInflight(1000);
    mqttConnectOptions.setUserName(mqttProperties.getUsername());
    mqttConnectOptions.setPassword(mqttProperties.getPassword().toCharArray());
    mqttConnectOptions.setAutomaticReconnect(true);
    return mqttConnectOptions;
  }

  @Bean
  public MqttPahoClientFactory mqttClientFactory() {
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    factory.setConnectionOptions(getMqttConnectOptions());
    return factory;
  }
  @Bean
  public MessageProducer inbound(MqttPahoClientFactory mqttPahoClientFactory) {
    String[] inboundTopics = mqttProperties.getTopics().split(",");
    MqttPahoMessageDrivenChannelAdapter adapter =
            new MqttPahoMessageDrivenChannelAdapter(mqttProperties.getClientId(),
                    mqttPahoClientFactory,inboundTopics);
    adapter.setCompletionTimeout(5000);
    adapter.setConverter(new DefaultPahoMessageConverter());
    adapter.setQos(1);
    adapter.setOutputChannel(mqttInputChannel());
    return adapter;
  }

  @Bean
  @ServiceActivator(inputChannel = "mqttInputChannel")
  public MessageHandler handler() {

    return message -> log.info("???????????????"+ message.getPayload());
  }

}
*/
