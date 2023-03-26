package com.task.price.fee;


import com.task.price.model.FxRateQuote;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StandardFeeStrategyImplTest {

    @Test
    public void applyStandardFeeTest() {
        FxRateQuote quote = new FxRateQuote(
            "EUR/USD",
                1.5,
                1.7,
                new Timestamp(System.currentTimeMillis())
        );
        StandardFeeStrategyImpl instance = new StandardFeeStrategyImpl();
        instance.apply(quote);

        assertEquals(2.04, quote.getAsk());
        assertEquals(1.25, quote.getBid());
    }
}
