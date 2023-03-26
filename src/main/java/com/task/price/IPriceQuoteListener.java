package com.task.price;

import com.task.price.service.model.PriceQuote;

public interface IPriceQuoteListener {
    void onMessage(String quote);
}
