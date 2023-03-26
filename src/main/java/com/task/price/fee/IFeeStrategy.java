package com.task.price.fee;

import com.task.price.model.FxRateQuote;

public interface IFeeStrategy {
    void apply(FxRateQuote price);
}
