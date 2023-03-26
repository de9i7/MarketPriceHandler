package com.task.price.service;

import com.task.price.IPriceQuoteListener;
import com.task.price.service.model.PriceQuote;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PriceTicker {
    private List<IPriceQuoteListener> subscriberLists = new ArrayList<>();

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    List<PriceQuote> quotes = new ArrayList<>();

    String[] pairs = {"EUR/USD", "EUR/JPY", "GBP/USD"};
    {
        quotes = Arrays.stream(pairs).map((pair) -> {
            Double randomBidPrice = 1 + Math.random() * 10;
            Double randomAskPrice = randomBidPrice * 1.1;
            return new PriceQuote(pair, randomBidPrice, randomAskPrice);
        }).collect(Collectors.toList());
    }

    @PostConstruct
    private void postConstruct() {
        executorService.scheduleAtFixedRate(() -> {
            Double randomFactor = Math.random();
            for (PriceQuote quote : quotes) {
                quote.setBid(quote.getBid() * randomFactor);
                quote.setAsk(quote.getAsk() * randomFactor);
                sendMessage(quote);
            }
        }, 3, 3, TimeUnit.SECONDS);
    }

    /**
     * Sends message to each subscriber when update is generated.
     * @param quote
     */
    public void sendMessage(PriceQuote quote) {
        String response = convertToCsv(quote);
        for (IPriceQuoteListener s : subscriberLists) {
            s.onMessage(response);
        }
    }

    /**
     * Converts price quote model object to CSV output
     * @param quote PriceQuote mocel object
     * @return
     */
    private String convertToCsv(PriceQuote quote) {
        String[] columns = new String[]{
                quote.getPair(),
                quote.getBid().toString(),
                quote.getAsk().toString(),
                quote.getTimestamp().toString()
        };
        return Stream.of(columns).collect(Collectors.joining(","));
    }

    public void subscribe(IPriceQuoteListener s) {
        subscriberLists.add(s);
    }
}
