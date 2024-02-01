package com.springconsulting.stockexchange.beans;

public record Symbol(
        String name,
        int priceCents
){}