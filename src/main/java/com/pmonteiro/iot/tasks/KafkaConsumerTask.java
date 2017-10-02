package com.pmonteiro.iot.tasks;

import com.pmonteiro.iot.core.TopicMessage;
import com.pmonteiro.iot.db.BaseDAO;
import com.pmonteiro.iot.db.TopicMessageDAO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.knowm.sundial.Job;
import org.knowm.sundial.SundialJobScheduler;
import org.knowm.sundial.annotations.SimpleTrigger;
import org.knowm.sundial.exceptions.JobInterruptException;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@SimpleTrigger(repeatInterval = 2, timeUnit = TimeUnit.SECONDS)
public class KafkaConsumerTask extends Job {

    @Override
    public void doRun() throws JobInterruptException {
        KafkaConsumer<String, String> kafkaConsumer = (KafkaConsumer) SundialJobScheduler.getServletContext().getAttribute("kafkaConsumer");
        BaseDAO dao = (TopicMessageDAO) SundialJobScheduler.getServletContext().getAttribute("topicMessageDAO");
        for (ConsumerRecord<String, String> record : kafkaConsumer.poll(100)) {
            TopicMessage topicMessage = new TopicMessage(
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    new Date(record.timestamp()),
                    record.key(), record.value());
            dao.persist(topicMessage);
            System.out.println(topicMessage);
        }
        kafkaConsumer.commitSync();
    }
}
