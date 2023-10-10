package com.lofominhili.trendchat.dto.BasicDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SuccessDTO<T> implements Serializable {

    private final Integer statusCode;
    private final String subject;
    private final T data;

}
