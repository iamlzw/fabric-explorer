package com.example.fabricexplorer.Impl;

import com.example.fabricexplorer.client.CAClient;
import com.example.fabricexplorer.client.ChannelClient;
import com.example.fabricexplorer.client.FabricClient;
import com.example.fabricexplorer.config.Config;
import com.example.fabricexplorer.entity.Peer;
import com.example.fabricexplorer.entity.PeerRefChainCode;
import com.example.fabricexplorer.entity.PeerRefChannel;
import com.example.fabricexplorer.mapper.PeerMapper;
import com.example.fabricexplorer.mapper.PeerRefChaincodeMapper;
import com.example.fabricexplorer.mapper.PeerRefChannelMapper;
import com.example.fabricexplorer.network.CreateChannel;
import com.example.fabricexplorer.service.PeerService;
import com.example.fabricexplorer.user.UserContext;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service("PeerService")
public class PeerServiceImpl implements PeerService {

    @Autowired
    private PeerMapper peerMapper;

    @Autowired
    private PeerRefChannelMapper peerRefChannelMapper;

    @Override
    public void insertNewPeers(Peer peer) {
        if (peerMapper.validPeer(peer) == 0){
            peerMapper.insertNewPeers(peer);
        }
    }

    @Override
    public List<Peer> getPeers() {
        return peerMapper.getPeers();
    }

    @Override
    public int getNodesCount() {
        return peerMapper.getNodesCount();
    }

    @Override
    public void savePeerRefChannel(PeerRefChannel peerRefChannel) {
        if (peerRefChannelMapper.validCount(peerRefChannel) == 0){
            peerRefChannelMapper.savePeerRefChannel(peerRefChannel);
        }
    }
}
