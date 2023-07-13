package com.store_api.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewGoodRequest {
    private String name;
    private int price;
    private int count;
    private Long categoryId;
}
