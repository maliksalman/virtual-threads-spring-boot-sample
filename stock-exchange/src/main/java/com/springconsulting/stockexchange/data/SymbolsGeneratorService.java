package com.springconsulting.stockexchange.data;

import com.springconsulting.stockexchange.StockExchangeService;
import com.springconsulting.stockexchange.beans.Symbol;
import com.springconsulting.stockexchange.config.Configuration;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SymbolsGeneratorService {

    private final Random random = new Random();

    private final Configuration configuration;

    public SymbolsGeneratorService(Configuration configuration) {
        this.configuration = configuration;
    }

    public List<Symbol> generate(List<String> existing, int count) {

        List<Symbol> list = new ArrayList<>();

        // a kill-switch so this doesn't become a runaway process
        long iterationsLeft = 1000000;

        // generating random symbols that aren't already used
        while(list.size() < count && iterationsLeft >= 0) {
            iterationsLeft--;
            String randomSymbol = configuration.getGeneratorPrefix() + RandomStringUtils
                    .random(5, true, false)
                    .toUpperCase();
            if (!existing.contains(randomSymbol)) {
                list.add(new Symbol(randomSymbol, random.nextInt(50000)));
            }
        }

        return list;
    }
}
