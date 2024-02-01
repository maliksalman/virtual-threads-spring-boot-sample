package com.springconsulting.stockexchange.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "config")
public class Configuration {

    private Duration delayDuration;
    private String exchangeName;
    private String generatorPrefix;

    public Duration getDelayDuration() {
        return delayDuration;
    }

    public void setDelayDuration(Duration delayDuration) {
        this.delayDuration = delayDuration;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getGeneratorPrefix() {
        return generatorPrefix;
    }

    public void setGeneratorPrefix(String generatorPrefix) {
        this.generatorPrefix = generatorPrefix;
    }
}
