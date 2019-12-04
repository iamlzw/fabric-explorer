package com.example.fabricexplorer.Impl;

import com.example.fabricexplorer.client.CAClient;
import com.example.fabricexplorer.client.ChannelClient;
import com.example.fabricexplorer.client.FabricClient;
import com.example.fabricexplorer.config.Config;
import com.example.fabricexplorer.network.CreateChannel;
import com.example.fabricexplorer.service.PeerService;
import com.example.fabricexplorer.user.UserContext;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PeerServiceImpl implements PeerService {
    @Override
    public void insertNewPeers() throws Exception {
        String caUrl = Config.CA_ORG1_URL;
        CAClient caClient = new CAClient(caUrl, null);
        // Enroll Admin to Org1MSP
        UserContext adminUserContext = new UserContext();
        adminUserContext.setName(Config.ADMIN);
        adminUserContext.setAffiliation(Config.ORG1);
        adminUserContext.setMspId(Config.ORG1_MSP);
        caClient.setAdminUserContext(adminUserContext);
        adminUserContext = caClient.enrollAdminUser(Config.ADMIN, Config.ADMIN_PASSWORD);

        FabricClient fabClient = new FabricClient(adminUserContext);

        ChannelClient channelClient = fabClient.createChannelClient(Config.CHANNEL_NAME);
        Channel channel = channelClient.getChannel();
        Collection peers = channel.getPeers();
        Iterator peerIter = peers.iterator();
        while (peerIter.hasNext())
        {
            Peer pr = (Peer) peerIter.next();
            Logger.getLogger(CreateChannel.class.getName()).log(Level.INFO,pr.getName()+ " at " + pr.getUrl());
        }
    }
}
