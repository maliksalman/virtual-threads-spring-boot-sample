package com.springconsulting.stockbroker;

import com.springconsulting.stockbroker.beans.Exchange;
import com.springconsulting.stockbroker.beans.Price;
import com.springconsulting.stockbroker.config.Configuration;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class StockExchangeService {

    private final Logger logger = LoggerFactory.getLogger(StockExchangeService.class);
    private final Map<String, Price> prices = new ConcurrentHashMap<>();
    private final Map<String, Exchange> exchanges = new ConcurrentHashMap<>();

    private final ExecutorService executorService;
    private final Configuration configuration;
    private final MeterRegistry metrics;

    public StockExchangeService(ExecutorService executorService, Configuration configuration, MeterRegistry metrics) {
        this.executorService = executorService;
        this.configuration = configuration;
        this.metrics = metrics;
    }

    @Scheduled(fixedDelay = 15000)
    public void fetchSymbolsFromExchanges() throws Exception {

        // create tasks to get symbols handled by each exchange
        long time = System.currentTimeMillis();
        List<Callable<Exchange>> tasks = configuration.getClients().values().stream()
                .map(client -> getTaskToGetExchange(client))
                .collect(Collectors.toList());

        // run the tasks in parallel
        List<Future<Exchange>> futures = executorService.invokeAll(tasks);
        futures.forEach(f -> {
            try {
                Exchange exchange = f.get();
                exchanges.put(exchange.name(), exchange);
            } catch (Exception e) {
                logger.warn("Couldn't get symbols: Error=" + e.getMessage());
            }
        });

        logger.info("Fetched exchanges: count=" + futures.size());
        metrics.timer("fetch.symbols").record(System.currentTimeMillis()-time, TimeUnit.MILLISECONDS);
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public void fetchPricesFromExchanges() throws Exception {

        // create tasks
        long time = System.currentTimeMillis();
        List<Callable<Price>> tasks = new ArrayList<>();
        for (Exchange exchange : exchanges.values()) {

            // get the client to interact with the exchange
            RestClient client = configuration.getClients().get(exchange.name());

            // create tasks
            exchange.symbols().stream()
                    .map(s -> getTaskToGetPrice(client, s))
                    .forEach(tasks::add);
        }

        // run the tasks in parallel
        List<Future<Price>> futures = executorService.invokeAll(tasks);
        for (Future<Price> f : futures) {
            try {
                Price price = f.get();
                prices.put(price.symbol(), price);
            } catch (Exception e) {
                logger.warn("Couldn't get price: Error=" + e.getMessage());
            }
        }
        logger.info("Fetched prices: count=" + futures.size());
        metrics.timer("fetch.prices").record(System.currentTimeMillis()-time, TimeUnit.MILLISECONDS);
    }

    private Callable<Price> getTaskToGetPrice(RestClient client, String symbol) {
        return () -> client.get()
                .uri("/exchange/symbols/" + symbol)
                .retrieve()
                .body(Price.class);
    }

    private Callable<Exchange> getTaskToGetExchange(RestClient client) {
        return () -> client.get()
                .uri("/exchange")
                .retrieve()
                .body(Exchange.class);
    }

    public Set<String> listExchanges() {
        return exchanges.keySet();
    }

    public List<String> listSymbols(String exchange) {
        return exchanges.get(exchange).symbols();
    }

    public Price getPrice(String symbol) {
        return prices.get(symbol);
    }
}
