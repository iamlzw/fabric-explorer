package com.example.fabricexplorer.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class chaincodes {
    private BigInteger id;
    private String name;
    private String version;
    private String path;
    private String channel_genesis_hash;
    private int txCount;
    private Date createDt;
}
