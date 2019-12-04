package com.example.fabricexplorer.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class transaction {
    private BigInteger id;
    private int block_id;
    private String txHash;
    private Date createDt;
    private String chanincode;
    private int status;
    private String creator_msp_id;
    private String endorser_msp_id;
    private String chaincode_id;
    private String type;
    private String read_set;
    private String write_set;
    private String channel_genesis_hash;
    private String validation_code;
    private String envelope_signature;
    private String payload_extension;
    private String creator_id_bytes;
    private String creator_nonce;
    private String chaincode_proposal_input;
    private String tx_response;
    private String payload_proposal_hash;
    private String endorser_id_bytes;
    private String endorser_signature;
}
