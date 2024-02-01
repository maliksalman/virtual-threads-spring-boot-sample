package com.springconsulting.stockexchange;

import com.springconsulting.stockexchange.beans.Exchange;
import com.springconsulting.stockexchange.beans.Price;
import com.springconsulting.stockexchange.beans.Symbol;
import com.springconsulting.stockexchange.config.Configuration;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class StockExchangeService {

    private final Map<String, Symbol> symbols = new HashMap<>();
    private final Random random = new Random();
    private final Configuration configuration;



    public StockExchangeService(Configuration configuration) {
        this.configuration = configuration;
    }

    public Price generatePrice(String symbol) {

        Price price = Optional.ofNullable(symbols.get(symbol))
                .map(s -> new Price(configuration.getExchangeName(),
                        s.name(),
                        generateRandomPrice(s.priceCents()),
                        Instant.now().atOffset(ZoneOffset.UTC)))
                .orElseThrow(() -> new SymbolNotFoundException(symbol));

        try {
            Thread.sleep(configuration.getDelayDuration());
        } catch (InterruptedException e) {
            throw new RuntimeException("Sleep was interrupted, shouldn't happen");
        }

        return price;
    }

    public void addSymbols(List<Symbol> newSymbols) {
        newSymbols.forEach(s -> symbols.put(s.name(), s));
    }

    private int generateRandomPrice(int basePrice) {

        // get a random percentage between 1 and 5 percents
        double randomPercentage = (random.nextDouble(5d - 1d) + 1d) / 100;
        if ((random.nextInt() % 2) == 0) {
            // randomly increasing by this percentage
            randomPercentage = 1d + randomPercentage;
        } else {
            // randomly decreasing by this percentage
            randomPercentage = 1d - randomPercentage;
        }

        return (int) Math.round(basePrice * randomPercentage);
    }

    public Exchange getExchange() {
        return new Exchange(configuration.getExchangeName(), new ArrayList<>(symbols.keySet()));
    }
}
