package com.yieldlab.BidRequest.mappers;

import com.google.gson.Gson;
import com.yieldlab.BidRequest.BidRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class ObjectToStringMapper implements ObjectToOtherFormatMapper<Optional<String>, BidRequest> {

    @Override
    public Optional<String> map(BidRequest request) {
        if (request == null) {
            return Optional.empty();
        }
        try {
            Gson gson = new Gson();
            return Optional.of(gson.toJson(request));
        } catch (Exception ex) {
            log.fatal("Something went wrong while parsing Request to Json", request);
        }
        return Optional.empty();
    }
}
