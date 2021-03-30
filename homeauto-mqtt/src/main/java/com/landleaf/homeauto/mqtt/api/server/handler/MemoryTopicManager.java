package com.landleaf.homeauto.mqtt.api.server.handler;


import com.google.common.collect.Lists;
import com.landleaf.homeauto.mqtt.api.RsocketTopicManager;
import com.landleaf.homeauto.mqtt.api.TransportConnection;
import com.landleaf.homeauto.mqtt.api.server.path.TopicManager;

import java.util.List;

public class MemoryTopicManager implements RsocketTopicManager {


    private TopicManager topicManager = new TopicManager();

    @Override
    public List<TransportConnection> getConnectionsByTopic(String topic) {
        return topicManager.getTopicConnection(topic).orElse(Lists.newArrayList());
    }

    @Override
    public void addTopicConnection(String topic, TransportConnection connection) {
        topicManager.addTopicConnection(topic,connection);
    }

    @Override
    public void deleteTopicConnection(String topic, TransportConnection connection) {
        topicManager.deleteTopicConnection(topic,connection);
    }

}
