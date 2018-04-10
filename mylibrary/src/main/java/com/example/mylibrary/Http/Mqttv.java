package com.example.mylibrary.Http;

import com.example.mylibrary.MyException.MyException;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


/**
 * Created by zy on 2017/7/25.
 */

public class Mqttv {

    /**
     * 阻塞客户端.
     */
    private MqttClient client;

    private MqttConnectOptions opts;

    /**
     * 非阻塞客户端.
     */
    private MqttAsyncClient mqttAsyncClient;

    private MqttConnectOptions asyncOpts;

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
    private boolean isCleanSession;

    /**设置连接超时时间.*/
    private int timeOut;

    /**设置心跳时间.*/
    private int keepAlive;

    /**消息级别.**/
    private int[] QOS_VALUES;

    private Options options;

    /**
     * 当异步操作完成时，将通知此接口的实现者.
     */
    private IMqttActionListener mqttActionListener;

    protected Mqttv(String[] topic,int[] QOS_VALUES,String url,String client_id,String userName,String passWord,MqttCallback mqttCallback,boolean isCleanSession,int timeOut,int keepAlive,
                    IMqttActionListener mqttActionListener){
        this.topic=topic;
        this.QOS_VALUES = QOS_VALUES;
        this.url = url;
        this.client_id = client_id;
        this.userName = userName;
        this.passWord = passWord;
        this.mqttCallback = mqttCallback;
        this.isCleanSession = isCleanSession;
        this.timeOut = timeOut;
        this.keepAlive = keepAlive;
        this.mqttActionListener = mqttActionListener;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    /**
     * 开启服务.
     */
    public void startAsyncConnect() throws MqttException, MyException {
        MemoryPersistence persistence=new MemoryPersistence();
        mqttAsyncClient=new MqttAsyncClient(url,client_id,persistence);
        asyncOpts=new MqttConnectOptions();
        asyncOpts.setCleanSession(isCleanSession);
        asyncOpts.setUserName(userName);
        asyncOpts.setPassword(passWord.toCharArray());
        asyncOpts.setConnectionTimeout(timeOut);
        asyncOpts.setKeepAliveInterval(keepAlive);
        if (options!=null){
            options.OptionsExtend(asyncOpts);
        }
        mqttAsyncClient.setCallback(mqttCallback);
        mqttAsyncClient.connect(asyncOpts);
        if (QOS_VALUES==null){
            throw new MyException("QOS_VALUES is null");
        }else {
            if (mqttActionListener==null){
                mqttAsyncClient.subscribe(topic,QOS_VALUES);
            }else {
                mqttAsyncClient.subscribe(topic,QOS_VALUES,null,mqttActionListener);
            }

        }


    }

    /**
     * 开启服务.
     */
    public void startConnect() throws MqttException {
        MemoryPersistence persistence=new MemoryPersistence();
        client=new MqttClient(url,client_id,persistence);
        opts=new MqttConnectOptions();
        opts.setCleanSession(isCleanSession);
        opts.setUserName(userName);
        opts.setPassword(passWord.toCharArray());
        opts.setConnectionTimeout(timeOut);
        opts.setKeepAliveInterval(keepAlive);
        if (options!=null){
            options.OptionsExtend(opts);
        }
        client.setCallback(mqttCallback);
        client.connect(opts);
        if (QOS_VALUES==null){
            client.subscribe(topic);
        }else {
            client.subscribe(topic,QOS_VALUES);
        }
    }

    /**
     * 断开连接.
     */
    public void disconnect() throws MqttException {
        client.disconnect();
    }

    /**
     * 断开连接.
     */
    public void disAsyncconnect() throws MqttException {
        mqttAsyncClient.disconnect();
    }

    /**
     * 停止订阅主题.
     * @param topic
     */
    public void unSubscribe(String[] topic) throws MqttException {
        client.unsubscribe(topic);
    }

    /**
     * 停止订阅主题.
     * @param topic
     */
    public void unAsyncSubscribe(String[] topic) throws MqttException {
        mqttAsyncClient.unsubscribe(topic);
    }

    /**
     * 传输数据.
     * @param topic
     * 消息需要发送的主题.
     * @param content
     * 内容.
     * @param qos
     * 服务质量:
     * 级别0：尽力而为。消息发送者会想尽办法发送消息，但是遇到意外并不会重试。
     * 级别1：至少一次。消息接收者如果没有知会或者知会本身丢失，消息发送者会再次发送以保证消息接收者至少会收到一次，当然可能造成重复消息。
     * 级别2：恰好一次。保证这种语义肯待会减少并发或者增加延时，不过丢失或者重复消息是不可接受的时候，级别2是最合适的。
     */
    public void publish(String topic, String content,int qos) throws MqttException {
        MqttMessage msg = new MqttMessage();
        msg.setQos(qos);
        msg.setRetained(true);
        msg.setPayload(content.getBytes());
        client.publish(topic, msg);
    }

    public void publishAsync(String topic, String content,int qos) throws MqttException {
        MqttMessage msg = new MqttMessage();
        msg.setQos(qos);
        msg.setRetained(true);
        msg.setPayload(content.getBytes());
        mqttAsyncClient.publish(topic, msg);
    }

    public MqttConnectOptions getOpts(){
        return opts;
    }

    public MqttConnectOptions getAsyncOpts() {
        return asyncOpts;
    }

    public interface Options{
        public void OptionsExtend(MqttConnectOptions options);
    }
}
