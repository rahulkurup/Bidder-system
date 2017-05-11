package com.yieldlab.BidRequest;

import lombok.extern.log4j.Log4j2;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
public class PostRequestExecutor {

    public JSONObject executePost(HttpPost postRequest) throws IOException {
        if (postRequest == null) {
            return null;
        }
        CloseableHttpResponse response;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            response = httpClient.execute(postRequest);
            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            return new JSONObject(responseString);
        } catch (IOException e) {
            log.fatal("Error occured while executing post Request {}", postRequest, e);
            throw e;
        }
    }
}
