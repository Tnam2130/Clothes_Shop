package com.main.asm.payload.request;

import lombok.Data;

@Data
public class QueryRequest {
    int page=0;
    int size=12;
    String order;
    String orderBy;
}
