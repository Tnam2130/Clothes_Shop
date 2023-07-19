package com.main.asm.payload.request;

import lombok.Data;

@Data
public class ProductRequest extends QueryRequest{
    Long category;
}
