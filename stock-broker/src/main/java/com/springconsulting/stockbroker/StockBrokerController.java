package com.springconsulting.stockbroker;

import com.springconsulting.stockbroker.beans.Price;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/broker")
public class StockBrokerController {

    private final StockExchangeService service;

    public StockBrokerController(StockExchangeService service) {
        this.service = service;
    }

    @GetMapping("/exchanges")
    public Set<String> listExchanges() {
        return service.listExchanges();
    }

    @GetMapping("/exchanges/{exchange}/symbols")
    public List<String> listSymbols(@PathVariable("exchange") String exchange) {
        return service.listSymbols(exchange);
    }

    @GetMapping("/symbols/{symbol}")
    public Price getPrice(@PathVariable("symbol") String symbol) {
        return service.getPrice(symbol);
    }
}
