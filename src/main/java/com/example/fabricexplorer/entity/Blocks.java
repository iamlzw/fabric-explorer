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
public class Blocks {
    private BigInteger id;
    private long blockNum;
    private String dataHash;
    private String preHash;
    private String blockHash;
    private long txCount;
    private Date createDt;
    private String pre_blockHash;
    private String channel_genesis_hash;
    private int blksize;

}
