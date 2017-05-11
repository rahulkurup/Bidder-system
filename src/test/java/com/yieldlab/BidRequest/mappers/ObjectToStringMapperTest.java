package com.yieldlab.BidRequest.mappers;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class ObjectToStringMapperTest {
    private ObjectToStringMapper ObjectToStringMapper;

    @Before
    public void setUp() {
        ObjectToStringMapper = new ObjectToStringMapper();
    }

    @Test
    public void returnEmptyIfInputIsNull() {
        assertEquals(Optional.empty(), ObjectToStringMapper.map(null));
    }
}