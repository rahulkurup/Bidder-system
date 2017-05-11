package com.yieldlab.controllers;

import com.yieldlab.BidRequest.BidRequest;
import com.yieldlab.BidRequest.BidRequestHandler;
import com.yieldlab.ResponseParser.ResponseParser;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class ResultControllerTest {
    @Mock
    private BidRequestHandler<String, BidRequest, JSONObject> requestHandler;
    @Mock
    private ResponseParser<JSONObject, String> responseParser;

    @Test
    public void checkIfFlowIsGoodWithoutErrors() throws Exception {
        when(responseParser.parse(anyList())).thenReturn("highest");
        ResultController controller = new ResultController(requestHandler, responseParser);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/2/a=5")).andReturn().getResponse();
    }
}