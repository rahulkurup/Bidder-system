package com.yieldlab.ResponseParser;

import java.util.List;

public interface ResponseParser<R, T> {

    T parse(List<R> response);
}
