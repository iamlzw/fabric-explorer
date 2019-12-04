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
public class peer_ref_channel {
    private BigInteger id;
    private Date createDt;
    private String peer_id;
    private String channel_id;
    private String peer_type;
}
