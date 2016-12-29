package com.wangxin.app.service;

import com.ibm.mqtt.MqttClient;  
import com.ibm.mqtt.MqttException;  
  
//生产者  可以发送与接收消息   
public class Producer {  
    private final static String CONNECTION_STRING = "tcp://172.16.248.90:1883";
    private final static boolean CLEAN_START = true;    
    private final static short KEEP_ALIVE = 30;// 低耗网络，但是又需要及时获取数据，心跳30s    
    private final static String CLIENT_ID = "producer";// 客户端标识    
    private final static int[] QOS_VALUES = { 0, 2};// 对应主题的消息级别    
    private final static String[] TOPICS = { "consumer/topic","tokudu/yzq124"};    
      
    private static Producer instance = new Producer();    
      
    private MqttClient mqttClient;    
    /**  
     * 返回实例对象  
     *   
     * @return  a
     */    
    public static Producer getInstance() {    
        return instance;    
    }    
    /**  
     * 重新连接服务  
     */    
    private void connect() throws MqttException {    
        System.out.println("connect to mqtt broker.");    
        mqttClient = new MqttClient(CONNECTION_STRING);    
        System.out.println("***********register Simple Handler***********");    
        SimpleCallbackHandler simpleCallbackHandler = new SimpleCallbackHandler();    
        mqttClient.registerSimpleHandler(simpleCallbackHandler);// 注册接收消息方法  可以做为消费者  
        mqttClient.connect(CLIENT_ID, CLEAN_START, KEEP_ALIVE);    
        System.out.println("***********subscribe receiver topics***********");    
        mqttClient.subscribe(TOPICS, QOS_VALUES);// 订阅接主题    
    
        System.out.println("***********CLIENT_ID:" + CLIENT_ID);    
        /**  
         * 完成订阅后，可以增加心跳，保持网络通畅，也可以发布自己的消息  
         */    
        mqttClient.publish(CLIENT_ID+"/keepalive", "keepalive".getBytes(), QOS_VALUES[0],    
                true);// 增加心跳，保持网络通畅    
    }    
      
    /**  
     * 发送消息  
     *   
     * @param clientId  
     * @param message
     */    
    public void sendMessage(String clientId, String message) {    
        try {    
            if (mqttClient == null || !mqttClient.isConnected()) {    
                connect();    
            }    
            System.out.println("send message to " + clientId + ", message is "    
                    + message);    
            // 发布自己的消息    
            mqttClient.publish(clientId, message.getBytes(),    
                    0, false);    
        } catch (MqttException e) {    
             System.out.println(e.getCause());    
            e.printStackTrace();    
        }    
    }

    public void getMessage(String clientId, String message) {
        try {
            if (mqttClient == null || !mqttClient.isConnected()) {
                connect();
            }
            System.out.println("send message to " + clientId + ", message is "
                    + message);
            // 发布自己的消息

//            mqttClient.publish(clientId, message.getBytes(),        0, false);
        } catch (MqttException e) {
             System.out.println(e.getCause());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {    
      // new Producer().sendMessage("tokudu/yzq124", "java程序发的消息");    
       new Producer().sendMessage("consumer/topic", "java程序发的消息");    
    }    
} 