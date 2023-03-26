package com.task.price.service.model;

import java.sql.Timestamp;

public class PriceQuote {
    private final Timestamp timestamp;
    private final String pair;
    private Double bid;
    private Double ask;

    public PriceQuote(String instrument, Double bid, Double ask) {
        this.pair = instrument;
        this.bid = bid;
        this.ask = ask;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String getPair() {
        return pair;
    }

    public Double getBid() {
        return bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
