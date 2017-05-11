package com.yieldlab.BidRequest;

import com.yieldlab.BidRequest.mappers.ObjectToOtherFormatMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BidRequestPostHandlerTest {
    @Mock
    private ObjectToOtherFormatMapper<Optional<String>, BidRequest> mapper;
    @Mock
    private PostRequestExecutor postRequestExecutor;
    private BidRequestPostHandler bidRequestPostHandler;

    @Before
    public void setUp() {
        bidRequestPostHandler = new BidRequestPostHandler(mapper, postRequestExecutor);
    }

    @Test
    public void returnEmptyListIFUrlListINull() {
        assertEquals(bidRequestPostHandler.requestBid(null, BidRequest.builder().build()).size(), 0);
    }

    @Test
    public void nullRequestsNeedtoBeFilteredOut() {
        BidRequest request = getMockRequest(1);
        when(mapper.map(request)).thenReturn(Optional.empty());
        assertEquals(bidRequestPostHandler.requestBid(Arrays.asList("url1", "url2", "url3"), request).size(), 0);
    }

    @Test
    public void nullRequestsNeedtoBeFilteredOutAndGoodResultsMustCome() {
        BidRequest request = getMockRequest(1);
        when(mapper.map(request)).thenReturn(Optional.of("json1"));
        HttpPost post1 = composePostRequest("url1", "json1");
        HttpPost post2 = composePostRequest("url2", "json1");
        HttpPost post3 = composePostRequest("url3", "json1");
        try {
            when(postRequestExecutor.executePost(post1)).thenReturn(null);
            when(postRequestExecutor.executePost(post2)).thenReturn(null);
            when(postRequestExecutor.executePost(post3)).thenReturn(null);
        } catch (IOException ex) {
        }
        assertEquals(bidRequestPostHandler.requestBid(Arrays.asList("url1", "url2", "url3"), request).size(), 0);
    }

    private Map<String, String> getMockAttributes() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "aa");
        map.put("b", "bb");
        return map;
    }

    private BidRequest getMockRequest(long id) {
        return BidRequest.builder().id(id).attributes(getMockAttributes()).build();
    }

    private HttpPost composePostRequest(String url, String json) {
        HttpPost postRequest = new HttpPost(url);
        StringEntity postString = null;
        try {
            postString = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
        }
        postRequest.addHeader("content-type", "application/json");
        postRequest.setEntity(postString);
        return postRequest;
    }
}