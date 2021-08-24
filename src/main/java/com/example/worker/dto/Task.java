package com.example.worker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    private String uuid;

    private Integer taskNumber;

    private Integer number;

    private BigInteger result;


}
