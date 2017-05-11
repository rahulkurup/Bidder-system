package com.yieldlab.ResponseParser;

import com.yieldlab.exception.InvalidResponseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ContentResponseParserTest {

    private static final String CONTENT_FIELD = "content";
    private static final String BID_ID = "bid";
    private ContentResponseParser parser;

    @Before
    public void setUp() {
        parser = new ContentResponseParser();
    }

    @Test
    public void outputShouldBeTheBestBid() {
        List<JSONObject> list = Arrays.asList(getJsonObject("a:$price$", 100),
                getJsonObject("b:$price$", 200),
                getJsonObject("c:$price$", 300));
        assertEquals(parser.parse(list), "c:300");
    }

    @Test
    public void outputShouldBeTheBestBidIgnoringBadResponses() {
        List<JSONObject> list = Arrays.asList(getJsonObject("a:$price$", 100),
                getJsonObject("b:$price$", 300),
                getJsonObject("c:$phsjse$", 200));
        assertEquals(parser.parse(list), "b:300");
    }

    @Test(expected = InvalidResponseException.class)
    public void exceptionThrownIfAllResponsesAreBad() {
        List<JSONObject> list = Arrays.asList(getJsonObject("a:$pricdde$", 100),
                getJsonObject("b:$pricdde$", 300),
                getJsonObject("c:$phsjse$", 400),
                getJsonObject("c:$phsjse$", 500));
        parser.parse(list);
    }

    private JSONObject getJsonObject(String content, int bidAmount) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(CONTENT_FIELD, content);
            jsonObject.put(BID_ID, bidAmount);
        } catch (JSONException e) {
        }
        return jsonObject;
    }
}