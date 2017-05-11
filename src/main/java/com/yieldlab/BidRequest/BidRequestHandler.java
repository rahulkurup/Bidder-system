package com.yieldlab.BidRequest;

import java.util.List;

@FunctionalInterface
public interface BidRequestHandler<T, R, M> {

    List<M> requestBid(List<T> list, R request);
}
