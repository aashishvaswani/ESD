package com.aashish.ESD.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductRequest (
        @NotNull(message = "Product should be present")
        @NotEmpty(message = "Product should be present")
        @NotBlank(message = "Product should be present")
        @JsonProperty("name")
        String name,

        @JsonProperty("price")
        Double price
){}
