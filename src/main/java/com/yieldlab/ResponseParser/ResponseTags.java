package com.yieldlab.ResponseParser;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum ResponseTags {
    PRICE(":$price$", true);

    public static List<ResponseTags> getActivatedTags() {
        return Arrays.stream(ResponseTags.values()).
                filter(ResponseTags::isEnabled).collect(Collectors.toList());
    }
    @Getter
    private final String tag;
    @Getter
    private final boolean enabled;
}
