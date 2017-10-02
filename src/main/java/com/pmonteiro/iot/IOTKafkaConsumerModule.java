package com.pmonteiro.iot;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.pmonteiro.iot.db.MongoConfiguration;
import io.dropwizard.setup.Environment;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class IOTKafkaConsumerModule extends AbstractModule {

    private IOTKafkaConsumerConfiguration configuration;
    private Environment environment;
    private KafkaConsumer kafkaConsumer;

    public IOTKafkaConsumerModule(final IOTKafkaConsumerConfiguration configuration,
                                  final Environment environment,
                                  final KafkaConsumer kafkaConsumer) {
        this.configuration = configuration;
        this.environment = environment;
        this.kafkaConsumer = kafkaConsumer;
    }

    @Override
    protected void configure() {
        bind(IOTKafkaConsumerConfiguration.class).toInstance(configuration);
        bind(Environment.class).toInstance(environment);
        bind(KafkaConsumer.class).toInstance(kafkaConsumer);

        install(jpaModule(configuration.getDatabaseConfiguration()));
    }

    private Module jpaModule(MongoConfiguration databaseConfiguration) {
        final Properties properties = new Properties();
        properties.put("hibernate.transaction.jta.platform", databaseConfiguration.getJtaPlatform());
        properties.put("hibernate.ogm.datastore.provider", databaseConfiguration.getProvider());
        properties.put("hibernate.ogm.datastore.host", databaseConfiguration.getHost());
        properties.put("hibernate.ogm.datastore.database", databaseConfiguration.getDatabase());
        properties.put("hibernate.ogm.datastore.create_database", databaseConfiguration.isCreateDatabase());

        final JpaPersistModule jpaModule = new JpaPersistModule("DefaultUnit");
        jpaModule.properties(properties);

        return jpaModule;
    }
}
