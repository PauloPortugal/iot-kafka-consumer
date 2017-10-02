package com.pmonteiro.iot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pmonteiro.iot.db.PersistInitialiser;
import com.pmonteiro.iot.db.TopicMessageDAO;
import com.pmonteiro.iot.kafka.KafkaConfiguration;
import com.pmonteiro.iot.resources.TopicsResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.knowm.dropwizard.sundial.SundialBundle;
import org.knowm.dropwizard.sundial.SundialConfiguration;

import java.util.Arrays;
import java.util.Properties;

public class IOTKafkaConsumerApplication extends Application<IOTKafkaConsumerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new IOTKafkaConsumerApplication().run(args);
    }

    @Override
    public String getName() {
        return "IOTKafkaConsumer";
    }

    @Override
    public void initialize(final Bootstrap<IOTKafkaConsumerConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<IOTKafkaConsumerConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(IOTKafkaConsumerConfiguration configuration) {
                return configuration.getSwaggerBundleConfiguration();
            }
        });

        bootstrap.addBundle(new SundialBundle<IOTKafkaConsumerConfiguration>() {
            @Override
            public SundialConfiguration getSundialConfiguration(IOTKafkaConsumerConfiguration configuration) {
                return configuration.getSundialConfiguration();
            }
        });
    }

    @Override
    public void run(final IOTKafkaConsumerConfiguration configuration,
                    final Environment environment) {
        KafkaConsumer kafkaConsumer = getKafkaConsumer(configuration.getKafkaConfiguration());
        final Injector injector = Guice.createInjector(
                new IOTKafkaConsumerModule(configuration, environment, kafkaConsumer));
        environment.jersey().register(injector.getInstance(TopicsResource.class));
        environment.getApplicationContext().setAttribute("kafkaConsumer", kafkaConsumer);
        environment.getApplicationContext().setAttribute("topicMessageDAO", injector.getInstance(TopicMessageDAO.class));
        injector.getInstance(PersistInitialiser.class);
    }

    private KafkaConsumer getKafkaConsumer(KafkaConfiguration kafkaConfiguration) {
        KafkaConsumer kafkaConsumer = new KafkaConsumer<>(getKafkaProperties(kafkaConfiguration));
        kafkaConsumer.subscribe(Arrays.asList(kafkaConfiguration.getTopic()));
        return kafkaConsumer;
    }


    private Properties getKafkaProperties(KafkaConfiguration kafkaConfiguration) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", kafkaConfiguration.getServer());
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", kafkaConfiguration.getGroupId());
        properties.setProperty("enable.auto.commit", kafkaConfiguration.getAutoCommit());
        properties.setProperty("auto.commit.interval.ms", kafkaConfiguration.getAutoCommitInterval());
        properties.setProperty("auto.offset.reset", kafkaConfiguration.getAutoOffsetReset());
        return properties;
    }

}
