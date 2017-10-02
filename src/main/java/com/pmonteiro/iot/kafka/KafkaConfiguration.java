package com.pmonteiro.iot.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaConfiguration {

    @JsonProperty("server")
    private String server;
    @JsonProperty("topic")
    private String topic;
    @JsonProperty("group.id")
    private String groupId;
    @JsonProperty("enable.auto.commit")
    private String autoCommit;
    @JsonProperty("auto.commit.interval.ms")
    private String autoCommitInterval;
    @JsonProperty("auto.offset.reset")
    private String autoOffsetReset;

    public KafkaConfiguration() {
        // needed by Jackson for the serialization of the KafkaConfiguration object
    }

    public String getServer() {
        return server;
    }

    public String getTopic() {
        return topic;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getAutoCommit() {
        return autoCommit;
    }

    public String getAutoCommitInterval() {
        return autoCommitInterval;
    }

    public String getAutoOffsetReset() {
        return autoOffsetReset;
    }
}
