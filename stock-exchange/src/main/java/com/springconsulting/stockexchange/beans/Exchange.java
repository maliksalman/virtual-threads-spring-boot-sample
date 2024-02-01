package com.springconsulting.stockexchange.beans;

import java.util.List;

public record Exchange(
        String name,
        List<String> symbols
) {
}