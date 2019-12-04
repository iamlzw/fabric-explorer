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
public class peer_ref_chaincode {
    private BigInteger id;
    private String peer_id;
    private String chaincode_id;
    private String cc_version;
    private String channel_id;
    private Date createDt;
}
