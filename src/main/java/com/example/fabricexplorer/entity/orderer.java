package com.example.fabricexplorer.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.util.Date;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class orderer {
    private BigInteger id;
    private String requests;
    private String server_hostname;
    private Date createDt;
}
