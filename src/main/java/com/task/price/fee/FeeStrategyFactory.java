package com.task.price.fee;

import com.task.price.model.FxRateQuote;

public class FeeStrategyFactory {

    public static IFeeStrategy getStrategy(FxRateQuote quote) {
        // Here custom fee strategy selection logic might be implemented
        // in case of multiple adjustment factors/algorithms applicable
        // for specific ccy pair.
        return new StandardFeeStrategyImpl();
    }
}
