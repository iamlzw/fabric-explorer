package com.example.fabricexplorer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.hyperledger.fabric.sdk.Peer;

import java.util.Map;

@Mapper
public interface PeerMapper {
    int insertNewPeers(Map map);
}
