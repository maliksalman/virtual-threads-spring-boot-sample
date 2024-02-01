package com.springconsulting.stockexchange.beans;

import java.time.OffsetDateTime;

public record Price(
        String exchange,
        String symbol,
        int priceCents,
        OffsetDateTime time
){}