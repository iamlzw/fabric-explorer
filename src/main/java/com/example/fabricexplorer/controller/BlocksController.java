package com.example.fabricexplorer.controller;

import com.example.fabricexplorer.chaincode.invocation.QueryChaincode;
import com.example.fabricexplorer.client.CAClient;
import com.example.fabricexplorer.client.ChannelClient;
import com.example.fabricexplorer.client.FabricClient;
import com.example.fabricexplorer.config.Config;
import com.example.fabricexplorer.entity.Blocks;
import com.example.fabricexplorer.service.BlocksService;
import com.example.fabricexplorer.user.UserContext;
import com.example.fabricexplorer.util.Util;
import org.bouncycastle.util.encoders.Hex;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/blocks")
public class BlocksController {

    private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
    private static final String EXPECTED_EVENT_NAME = "event";

    @Autowired
    private BlocksService blocksService;

    @RequestMapping("/validBlock")
    @ResponseBody
    public boolean saveBlock() throws Exception {
        Util.cleanUp();
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
        Peer peer0_org1 = fabClient.getInstance().newPeer(Config.ORG1_PEER_0, Config.ORG1_PEER_0_URL);
        Peer peer1_org1 = fabClient.getInstance().newPeer(Config.ORG1_PEER_1, Config.ORG1_PEER_1_URL);
        Peer peer0_org2 = fabClient.getInstance().newPeer(Config.ORG2_PEER_0, Config.ORG2_PEER_0_URL);
        Peer peer1_org2 = fabClient.getInstance().newPeer(Config.ORG2_PEER_1, Config.ORG2_PEER_1_URL);
        EventHub eventHub = fabClient.getInstance().newEventHub("eventhub01", "grpc://localhost:7053");
        Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER_NAME, Config.ORDERER_URL);
        channel.addPeer(peer0_org1);
        channel.addPeer(peer1_org1);
        channel.addPeer(peer0_org2);
        channel.addPeer(peer1_org2);
        channel.addEventHub(eventHub);
        channel.addOrderer(orderer);
        channel.initialize();

        //通道信息
        BlockchainInfo channelInfo = channel.queryBlockchainInfo();
        String channelGenesisHash = Hex.toHexString(channel.queryBlockchainInfo().getCurrentBlockHash());

        //通过区块号遍历区块信息
        BlockInfo currentBlock = channel.queryBlockByNumber(0);
        final long blockNumber = currentBlock.getBlockNumber();

        String dataHash = Hex.toHexString(currentBlock.getDataHash());
        String preBlockHash = Hex.toHexString(currentBlock.getPreviousHash());
        int envelopeCount = currentBlock.getEnvelopeCount();

        Blocks blocks = new Blocks();
        blocks.setChannel_genesis_hash(channelGenesisHash);
        blocks.setTxCount(envelopeCount);
        blocks.setPreHash(preBlockHash);
        blocks.setDataHash(dataHash);
        blocks.setBlockNum(blockNumber);
        return true;
    }
}
