package com.yieldlab.BidRequest.mappers;

public interface ObjectToOtherFormatMapper<T, R> {
    T map(R inputObject);
}