package com.example.fabricexplorer.Impl;

import com.example.fabricexplorer.client.CAClient;
import com.example.fabricexplorer.client.FabricClient;
import com.example.fabricexplorer.config.Config;
import com.example.fabricexplorer.service.NetworkService;
import com.example.fabricexplorer.user.UserContext;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Peer;

public class NetworkServiceImpl implements NetworkService {

    @Override
    public boolean synchNetWorkConfigToDB(FabricClient client) throws Exception {
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
        HFClient hfClient = client.getInstance();
        Channel channel = hfClient.getChannel("mychannel");
        Peer peer0_org1 = hfClient.newPeer(Config.ORG1_PEER_0,Config.ORG1_PEER_0_URL);
        hfClient.queryChannels(peer0_org1);
        return false;
    }
}
