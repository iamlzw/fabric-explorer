package com.example.fabricexplorer.service;

import com.example.fabricexplorer.entity.Peer;
import com.example.fabricexplorer.entity.PeerRefChainCode;
import com.example.fabricexplorer.entity.PeerRefChannel;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.List;

public interface PeerService {
    void insertNewPeers(Peer peer);
    List<Peer> getPeers();
    int getNodesCount();
    void savePeerRefChannel(PeerRefChannel peerRefChannel);

}
