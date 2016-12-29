package com.wangxin.app.service;

import com.ibm.mqtt.MqttSimpleCallback;
  
//简单回调函数，处理server接收到的主题消息   
public class SimpleCallbackHandler implements MqttSimpleCallback {  
  
    /**  
     * 当客户机和broker意外断开时触发 可以再此处理重新订阅  
     */  
    public void connectionLost() throws Exception {  
         System.out.println("客户机和broker已经断开");    
    }  
  
    public void publishArrived(String topicName, byte[] payload, int Qos, boolean retained) throws Exception {  
         System.out.println("订阅主题: " + topicName);    
         System.out.println("消息数据: " + new String(payload));    
         System.out.println("消息级别(0,1,2): " + Qos);    
         System.out.println("是否是实时发送的消息(false=实时，true=服务器上保留的最后消息): "    
                 + retained);    
    }    
}  