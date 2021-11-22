package com.allen.subscribers.controller;

import com.allen.subscribers.service.MqttGateway;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
  @Resource
  private MqttGateway mqttGateway;

  @RequestMapping("/sendMqtt")
  public String sendMqtt(String topic,String  sendData){
    mqttGateway.sendToMqtt(topic,2,sendData);
    return "OK";
  }

}
