package com.task.price.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class FxRateQuote {
    private final Timestamp timestamp;
    private final String pair;
    private Double bid;
    private Double ask;

    public FxRateQuote(String pair, Double bid, Double ask, Timestamp timestamp) {
        this.pair = pair;
        this.bid = bid;
        this.ask = ask;
        this.timestamp = timestamp;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FxRateQuote quote = (FxRateQuote) o;
        return Objects.equals(timestamp, quote.timestamp) && Objects.equals(pair, quote.pair) && Objects.equals(bid, quote.bid) && Objects.equals(ask, quote.ask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, pair, bid, ask);
    }
}
