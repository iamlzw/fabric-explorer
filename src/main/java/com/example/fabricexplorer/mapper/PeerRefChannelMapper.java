package com.example.fabricexplorer.mapper;

import com.example.fabricexplorer.entity.PeerRefChannel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PeerRefChannelMapper {
    int validCount(PeerRefChannel peerRefChannel);
    void savePeerRefChannel(PeerRefChannel peerRefChannel);
}
