package com.yieldlab.controllers;

import com.yieldlab.BidRequest.BidRequest;
import com.yieldlab.BidRequest.BidRequestHandler;
import com.yieldlab.ResponseParser.ResponseParser;
import com.yieldlab.exception.InvalidInputException;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
public class ResultController {

    @Value("#{'${bidders.list}'.split(',')}")
    private List<String> myList;
    private final BidRequestHandler<String, BidRequest, JSONObject> requestHandler;
    private final ResponseParser<JSONObject, String> responseParser;

    @Autowired
    public ResultController(BidRequestHandler<String, BidRequest, JSONObject> requestHandler,
                            ResponseParser<JSONObject, String> responseParser) {
        this.requestHandler = requestHandler;
        this.responseParser = responseParser;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String bidRequest(@PathVariable Long id, @RequestParam Map<String, String> attributes) {
        if (id == null || attributes == null) {
            throw new InvalidInputException();
        }
        return responseParser.parse(bidAndGetResponse(id, attributes));
    }

    private List<JSONObject> bidAndGetResponse(long id, Map<String, String> attributes) {
        if (myList == null || myList.isEmpty()) {
            log.error("No bidders configured in proprty file. Check if it is intentional");
        }
        return requestHandler.requestBid(myList, BidRequest.builder().id(id).attributes(attributes).build());
    }
}