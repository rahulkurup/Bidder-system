package com.yieldlab.BidRequest;

import lombok.Builder;
import lombok.Value;

import java.util.Map;
import javax.validation.constraints.NotNull;

@Builder
@Value
public class BidRequest {
    @NotNull
    private Long id;
    Map<String, String> attributes;
}
