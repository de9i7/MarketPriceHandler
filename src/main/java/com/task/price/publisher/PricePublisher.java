package com.task.price.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.task.price.model.FxRateQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PricePublisher {
    private static final Logger LOG = LoggerFactory.getLogger(PricePublisher.class);

    private static final String ENDPOINT_URL = "http://endpoint.net/api/v1/method";

    @Autowired
    RestTemplate restTemplate;

    public void publish(FxRateQuote quoteModel) {
        try {

            String payload = convertToJson(quoteModel);
            LOG.info("Sending adjusted quotes: {}", payload);

//            String restResponse = postResponseToRestEndpoint(payload);
//            LOG.info("Reponse from REST service: {}", restResponse);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String postResponseToRestEndpoint(String payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>(payload, headers);

        String response =
                restTemplate.postForObject(ENDPOINT_URL, request, String.class);
        return response;
    }

    private String convertToJson(FxRateQuote quoteModel) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(quoteModel);
    }
}
