package com.yieldlab.BidRequest;

import com.yieldlab.BidRequest.mappers.ObjectToOtherFormatMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class BidRequestPostHandler implements BidRequestHandler<String, BidRequest, JSONObject> {

    private final ObjectToOtherFormatMapper<Optional<String>, BidRequest> mapper;
    private final PostRequestExecutor postRequestExecutor;

    @Autowired
    public BidRequestPostHandler(ObjectToOtherFormatMapper<Optional<String>, BidRequest> mapper,
                                 PostRequestExecutor postRequestExecutor) {
        this.mapper = mapper;
        this.postRequestExecutor = postRequestExecutor;
    }

    @Override
    public List<JSONObject> requestBid(List<String> urls, BidRequest request) {
        if (urls == null) {
            return new ArrayList<>();
        }
        return urls.stream().map(url -> sendPost(url, request)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private JSONObject sendPost(String url, BidRequest bidRequest) {

        /* Convert Bid Request to JSON String*/
        Optional<String> jsonString = mapper.map(bidRequest);
        if (!jsonString.isPresent()) {
            log.fatal("Unable to parse to JSON. Skipping sending request {} to url {}", bidRequest, url);
            return null;
        }

        /* Do the Post Request */
        try {
            return postRequestExecutor.executePost(composePostRequest(url, jsonString.get()));
        } catch (IOException e) {
            log.fatal("Cannot post request. Skipping sending bid request {} to url {}", bidRequest, url, e);
            return null;
        }
    }

    private HttpPost composePostRequest(String url, String json) throws UnsupportedEncodingException {
        HttpPost postRequest = new HttpPost(url);
        StringEntity postString;
        try {
            postString = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            log.fatal("Unable to parse JSON {} to Post Request", json);
            throw e;
        }
        postRequest.addHeader("content-type", "application/json");
        postRequest.setEntity(postString);
        return postRequest;
    }
}
