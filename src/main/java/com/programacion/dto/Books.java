package com.programacion.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Books {
    private Integer id;
    private String isbn;
    private String title;
    private Integer author_id;
    private Double price;

    private String author;
}
