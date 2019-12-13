package com.example.fabricexplorer.mapper;

import com.example.fabricexplorer.entity.PeerRefChainCode;
import com.example.fabricexplorer.entity.PeerRefChannel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PeerRefChaincodeMapper {
    int validCount(PeerRefChainCode peerRefChainCode);
    void savePeerRefChaincode(PeerRefChainCode peerRefChainCode);
}
