package com.springconsulting.stockexchange;

import com.springconsulting.stockexchange.beans.Exchange;
import com.springconsulting.stockexchange.beans.Price;
import com.springconsulting.stockexchange.beans.Symbol;
import com.springconsulting.stockexchange.data.SymbolsGeneratorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchange")
public class StockExchangeController {

    private final StockExchangeService exchangeService;
    private final SymbolsGeneratorService generatorService;

    public StockExchangeController(StockExchangeService exchangeService, SymbolsGeneratorService generatorService) {
        this.exchangeService = exchangeService;
        this.generatorService = generatorService;
    }

    @GetMapping("/symbols/{symbol}")
    public Price getPrice(@PathVariable("symbol") String symbol) {
        return exchangeService.generatePrice(symbol);
    }

    @PutMapping("/symbols/{count}")
    public void addSymbols(@PathVariable("count") int count)  {
        List<String> existing = exchangeService.getExchange().symbols();
        List<Symbol> symbols = generatorService.generate(existing, count);
        exchangeService.addSymbols(symbols);
    }

    @GetMapping
    public Exchange getExchange()  {
        return exchangeService.getExchange();
    }

    @ControllerAdvice
    @ResponseBody
    static class ControllerExceptionHandler {

        @ExceptionHandler(SymbolNotFoundException.class)
        @ResponseStatus(value = HttpStatus.NOT_FOUND)
        public String symbolNotFoundException(SymbolNotFoundException ex) {
            return ex.getMessage();
        }
    }
}


