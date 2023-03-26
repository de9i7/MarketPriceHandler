package com.task.price;

import com.opencsv.CSVReader;
import com.task.price.fee.FeeStrategyFactory;
import com.task.price.model.FxRateQuote;
import com.task.price.publisher.PricePublisher;
import com.task.price.service.PriceTicker;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class PriceProcessor implements IPriceQuoteListener{

    private static final Logger LOG = LoggerFactory.getLogger(PriceProcessor.class);

    private ExecutorService subscriptionUpdatesConsumer = new ThreadPoolExecutor(10, 100, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    @Autowired
    private PriceTicker priceService;

    @Autowired
    private PricePublisher pricePublisher;

    @PostConstruct
    private void initialize() {
        priceService.subscribe(this);
    }

    @Override
    public void onMessage(String quote) {

        LOG.info("Received message from price service: {}", quote);
        FxRateQuote quoteModel = parseCsv(quote);

        LOG.info("Applying fee adjustment strategy..");
        FeeStrategyFactory.getStrategy(quoteModel).apply(quoteModel);

        LOG.info("Publishing result.");
        pricePublisher.publish(quoteModel);
    }

    /**
     * Extracts fields values from csv-formatted response and puts it
     * into the quote model object
     * @param quote
     * @return
     */
    private FxRateQuote parseCsv(String quote) {
        CSVReader reader = new CSVReader(new StringReader(quote));
        FxRateQuote quoteModel;
        try {
            String[] quoteFields = reader.readNext();
            quoteModel = new FxRateQuote(
                    quoteFields[0],
                    Double.parseDouble(quoteFields[1]),
                    Double.parseDouble(quoteFields[2]),
                    Timestamp.valueOf(quoteFields[3])
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return quoteModel;
    }
}
