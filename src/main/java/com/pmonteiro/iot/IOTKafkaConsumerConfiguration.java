package com.pmonteiro.iot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pmonteiro.iot.db.MongoConfiguration;
import com.pmonteiro.iot.kafka.KafkaConfiguration;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.knowm.dropwizard.sundial.SundialConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class IOTKafkaConsumerConfiguration extends Configuration {

    @Valid
    @JsonProperty("mongo")
    private MongoConfiguration databaseConfiguration;

    @JsonProperty("kafka")
    private KafkaConfiguration kafkaConfiguration;

    @JsonProperty("swagger")
    private SwaggerBundleConfiguration swaggerBundleConfiguration;

    @Valid
    @NotNull
    public SundialConfiguration sundialConfiguration = new SundialConfiguration();

    @JsonProperty("sundial")
    public SundialConfiguration getSundialConfiguration() {

        return sundialConfiguration;
    }

    SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
        return swaggerBundleConfiguration;
    }

    MongoConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    public KafkaConfiguration getKafkaConfiguration() {
        return kafkaConfiguration;
    }
}
