package com.wangxin.app.service;

import com.ibm.mqtt.MqttClient;
import com.ibm.mqtt.MqttException;  
  
//消费者  
public class Consumer {  
    private final static String CONNECTION_STRING = "tcp://172.16.248.90:1883";
    private final static boolean CLEAN_START = true;    
    private final static short KEEP_ALIVE = 30;// 低耗网络，但是又需要及时获取数据，心跳30s    
    private final static String CLIENT_ID = "consumer";// 客户端标识    
    private final static int[] QOS_VALUES = { 0};// 对应主题的消息级别    
    private final static String[] TOPICS = { "consumer/topic"};    
      
    /**  
     * 重新连接服务  
     */    
    public void connect() throws MqttException {    
        System.out.println("connect to mqtt broker.");    
        MqttClient mqttClient = new MqttClient(CONNECTION_STRING);    
        System.out.println("***********register Simple Handler***********");    
        mqttClient.registerSimpleHandler(new SimpleCallbackHandler());// 注册接收消息方法  可以做为消费者
        mqttClient.connect(CLIENT_ID, CLEAN_START, KEEP_ALIVE);    
        System.out.println("***********subscribe receiver topics***********");    
        mqttClient.subscribe(TOPICS, QOS_VALUES);// 订阅接主题    
    
        System.out.println("***********CLIENT_ID:" + CLIENT_ID);    
        /**  
         * 完成订阅后，可以增加心跳，保持网络通畅，也可以发布自己的消息  
         */    
        mqttClient.publish(CLIENT_ID+"/keepalive", "keepalive".getBytes(), QOS_VALUES[0],true);// 增加心跳，保持网络通畅


    }    
      
    public static void main(String[] args) throws MqttException {    
        new Consumer().connect();  
    }    
}  