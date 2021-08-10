package com.nao.selectshop.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ProductRequestDto {
    private String title;
    private String link;
    private String image;
    private int lprice;

}
