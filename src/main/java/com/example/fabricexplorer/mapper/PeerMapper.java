package com.example.fabricexplorer.mapper;

import com.example.fabricexplorer.entity.Peer;
import org.apache.ibatis.annotations.Mapper;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.Map;

@Mapper
public interface PeerMapper {
    int insertNewPeers(Peer peer);
    int validPeer(Peer peer);
    List<Peer> getPeers();
    int getNodesCount();
}
