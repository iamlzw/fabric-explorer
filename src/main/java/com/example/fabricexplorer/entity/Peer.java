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
public class Peer {
    private BigInteger id;
    private int org;
    private String channel_genesis_hash;
    private String mspId;
    private String events;
    private String server_hostname;
    private Date createDt;
    private String peer_type;
}
