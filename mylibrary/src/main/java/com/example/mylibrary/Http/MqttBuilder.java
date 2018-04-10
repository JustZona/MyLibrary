package com.example.mylibrary.Http;

import com.example.mylibrary.MyException.MyException;
import com.example.mylibrary.ViewUtil.StringUtil;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;


/**
 * Created by zy on 2017/7/25.
 * Mqttv构造器.
 */
public class MqttBuilder {

    /**
     * 订阅的消息.
     */
    private String[] topic;

    private String url="";

    /**
     * 客户端ID.
     */
    private String client_id="";

    private String userName;

    private String passWord;

    private MqttCallback mqttCallback;

    /**
     * 是否清空session.
     */
    private boolean isCleanSession = false;

    /**设置连接超时时间.*/
    private int timeOut = 10;

    /**设置心跳时间.*/
    private int keepAlive = 20;

    /**消息级别.**/
    private int[] QOS_VALUES;

    /**
     * 当异步操作完成时，将通知此接口的实现者.
     */
    private IMqttActionListener mqttActionListener;

    public IMqttActionListener getMqttActionListener() {
        return mqttActionListener;
    }

    public MqttBuilder setMqttActionListener(IMqttActionListener mqttActionListener) {
        this.mqttActionListener = mqttActionListener;
        return this;
    }

    public MqttBuilder setTopic(String[] topic) {
        this.topic = topic;
        return this;
    }

    public MqttBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public MqttBuilder setClient_id(String client_id) {
        this.client_id = client_id;
        return this;
    }

    public MqttBuilder setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public MqttBuilder setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public MqttBuilder setMqttCallback(MqttCallback mqttCallback) {
        this.mqttCallback = mqttCallback;
        return this;
    }

    public MqttBuilder setCleanSession(boolean cleanSession) {
        isCleanSession = cleanSession;
        return this;
    }

    public MqttBuilder setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public MqttBuilder setKeepAlive(int keepAlive) {
        this.keepAlive = keepAlive;
        return this;
    }

    public MqttBuilder setQOS_VALUES(int[] QOS_VALUES) {
        this.QOS_VALUES = QOS_VALUES;
        return this;
    }

    public Mqttv createMqty() throws MyException {
        if (mqttCallback==null|| topic==null|| StringUtil.isEmpty(client_id)||StringUtil.isEmpty(url)){
            throw new MyException("mqttCallback or topic or or url client_id is null");
        }
        return new Mqttv(topic,QOS_VALUES,url,client_id,userName,passWord,mqttCallback,isCleanSession,timeOut,keepAlive,
                mqttActionListener);
    }
}
