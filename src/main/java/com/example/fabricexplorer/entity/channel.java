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
public class channel {
    private BigInteger id;
    private String name;
    private int blocks;
    private int trans;
    private Date createDt;
    private String channel_genesis_hash;
    private String channel_hash;
    private String channel_config;
    private String channel_block;
    private String channel_tx;
    private String channel_version;
}
