package com.pmonteiro.iot.core;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tasks")
@ApiModel(value="Topic message entity", description = "A description of a topic stream message")
public class TopicMessage extends AbstractEntity {

    @Basic
    @ApiModelProperty(example = "iot-plants")
    private String topic;

    @ApiModelProperty(example = "1")
    private Integer partition;

    @ApiModelProperty(example = "35")
    private Long offset;

    @ApiModelProperty(example = "2017-08-13 14:30:08")
    private Date timestamp;

    @ApiModelProperty(example = "2")
    private String key;

    @ApiModelProperty(example = "{Json object}")
    private String value;

    private TopicMessage() {
        //useful to deserialise the object
    }

    public TopicMessage(String topic, Integer partition, Long offset, Date timestamp, String key, String value) {
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
        this.timestamp = timestamp;
        this.key = key;
        this.value = value;
    }

    public String getTopic() {
        return topic;
    }

    public Integer getPartition() {
        return partition;
    }

    public Long getOffset() {
        return offset;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TopicMessage{" +
                "topic='" + topic + '\'' +
                ", partition=" + partition +
                ", offset=" + offset +
                ", timestamp=" + timestamp +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
