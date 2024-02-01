package com.springconsulting.stockbroker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "config")
public class Configuration {

    private Map<String, RestClient> clients = new HashMap<>();


    public Map<String, RestClient> getClients() {
        return clients;
    }

    public void setExchanges(Map<String, String> exchanges) {
        clients.clear();
        exchanges.forEach((name,baseUrl) -> clients.put(name, RestClient.create(baseUrl)));
    }
}
