package com.task.price.fee;

import com.task.price.model.FxRateQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fee adjustment logic implementation
 */
public class StandardFeeStrategyImpl implements IFeeStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(StandardFeeStrategyImpl.class);
    private static final Double FACTOR = 1.2;

    /**
     * Applies static factor to both bid and ask values.
     * @param price FX rate quote mode
     */
    public void apply(FxRateQuote price) {
        LOG.info("Applying standard fee factor: {} ", FACTOR);
        price.setBid(price.getBid() / FACTOR);
        price.setAsk(price.getAsk() * FACTOR);
    }
}
