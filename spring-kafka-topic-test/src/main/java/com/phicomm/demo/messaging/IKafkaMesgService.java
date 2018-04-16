package com.phicomm.demo.messaging;

public interface IKafkaMesgService {

    public void sendMessage(String topic, Object obj);

    public void sendMessage(String topic, int partition, Object obj);

    public void sendMessage(String topic, String mesg);

}
