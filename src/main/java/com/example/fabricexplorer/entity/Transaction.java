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
public class Transaction {
    private BigInteger id;
    private long blockId;
    private String txHash;
    private Date createDt;
    private String chainCode;
    private int status;
    private String creatorMspId;
    private String endorserMspId;
    private String chaincodeId;
    private String type;
    private String readSet;
    private String writeSet;
    private String channelGenesisHash;
    private long validationCode;
    private String envelopeSignature;
    private String payloadExtension;
    private String creatorIdBytes;
    private String creatorNonce;
    private String chaincodeProposalInput;
    private String txResponse;
    private String payloadProposalHash;
    private String endorserIdBytes;
    private String endorserSignature;
}
