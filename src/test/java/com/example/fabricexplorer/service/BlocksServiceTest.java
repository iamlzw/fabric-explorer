package com.example.fabricexplorer.service;

import com.alibaba.fastjson.JSON;
import com.example.fabricexplorer.client.CAClient;
import com.example.fabricexplorer.client.ChannelClient;
import com.example.fabricexplorer.client.FabricClient;
import com.example.fabricexplorer.config.Config;
import com.example.fabricexplorer.entity.*;
import com.example.fabricexplorer.network.CreateChannel;
import com.example.fabricexplorer.user.UserContext;
import com.example.fabricexplorer.util.Util;
import org.bouncycastle.util.encoders.Hex;
import org.hyperledger.fabric.protos.common.Common;
import org.hyperledger.fabric.protos.ledger.rwset.kvrwset.KvRwset;
import org.hyperledger.fabric.protos.peer.Query;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Peer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.logging.Level;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class BlocksServiceTest {
    private static final Logger log = LoggerFactory.getLogger(BlocksServiceTest.class);

    @Autowired
    private BlocksService blocksService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private PeerService peerService;
    @Autowired
    private ChaincodeService chaincodeService;
    @Autowired
    private TransactionService transactionService;

    static String printableString(final String string) {
        int maxLogStringLength = 64;
        if (string == null || string.length() == 0) {
            return string;
        }

        String ret = string.replaceAll("[^\\p{Print}]", "?");

        ret = ret.substring(0, Math.min(ret.length(), maxLogStringLength)) + (ret.length() > maxLogStringLength ? "..." : "");

        return ret;

    }

    static String upperFirstChar(String s){
        char[] cs = s.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }

    private void initPeers(Channel channel,String channelGenesisHash){
        Collection peers = channel.getPeers();
        Iterator peerIter = peers.iterator();
        while (peerIter.hasNext())
        {
            Peer pr = (Peer) peerIter.next();
            com.example.fabricexplorer.entity.Peer peer = new com.example.fabricexplorer.entity.Peer();
            peer.setChannel_genesis_hash(channelGenesisHash);
            peer.setMspId(upperFirstChar(pr.getName().split("\\.")[1].substring(0,4))+"MSP");
            peer.setPeer_type("peer");
            peer.setServer_hostname(pr.getName());
            peer.setRequests(pr.getUrl());
            peerService.insertNewPeers(peer);
            PeerRefChannel peerRefChannel = new PeerRefChannel();
            peerRefChannel.setChannel_id(channelGenesisHash);
            peerRefChannel.setPeer_id(pr.getName());
            peerRefChannel.setPeer_type("peer");
            peerService.savePeerRefChannel(peerRefChannel);
        }
    }

    private void initOrderers(Channel channel,String channelGenesisHash){
        Collection orderers = channel.getOrderers();
        Iterator orderIter = orderers.iterator();
        while (orderIter.hasNext())
        {
            Orderer orderer = (Orderer) orderIter.next();
            com.example.fabricexplorer.entity.Peer peer= new com.example.fabricexplorer.entity.Peer();
            peer.setChannel_genesis_hash(channelGenesisHash);
            peer.setMspId(upperFirstChar(orderer.getName().split("\\.")[0].substring(0,7))+"MSP");
            peer.setPeer_type("orderer");
            peer.setServer_hostname(orderer.getName());
            peer.setRequests(orderer.getUrl());
            peerService.insertNewPeers(peer);
            PeerRefChannel peerRefChannel = new PeerRefChannel();
            peerRefChannel.setChannel_id(channelGenesisHash);
            peerRefChannel.setPeer_id(orderer.getName());
            peerRefChannel.setPeer_type("orderer");
            peerService.savePeerRefChannel(peerRefChannel);
        }
    }

    private void initChaincodeDB(List<Query.ChaincodeInfo> chaincodeInfos,String channelGenesisHash,List<Peer> peers){
        Chaincode chaincode = new Chaincode();
        chaincode.setChannel_genesis_hash(channelGenesisHash);
        chaincode.setName(chaincodeInfos.get(0).getName());
        chaincode.setCreateDt(new Date());
        chaincode.setPath(chaincodeInfos.get(0).getPath());
        chaincode.setVersion(chaincodeInfos.get(0).getVersion());
        chaincode.setTxCount(0);
        chaincodeService.saveChaincode(chaincode);

        for (Peer peer:peers){
            PeerRefChainCode peerRefChainCode = new PeerRefChainCode();
            peerRefChainCode.setCc_version(chaincode.getVersion());
            peerRefChainCode.setChannel_id(channelGenesisHash);
            peerRefChainCode.setPeer_id(peer.getName());
            peerRefChainCode.setChaincode_id(chaincode.getName());
            chaincodeService.savePeerRefChaincode(peerRefChainCode);
        }
    }

    @Test
    void getBlocksCount() throws Exception{
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
        HFClient client = fabClient.getInstance();

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

        List<Peer> peerLists = new LinkedList<>();
        peerLists.add(peer0_org1);
        peerLists.add(peer0_org2);
        peerLists.add(peer1_org1);
        peerLists.add(peer1_org2);

        //通道信息
        BlockchainInfo channelInfo = channel.queryBlockchainInfo();
        BlockInfo genesisBlock = channel.queryBlockByNumber(0);
        String channelGenesisHash = Hex.toHexString(SDKUtils.calculateBlockHash(client,0,genesisBlock.getPreviousHash(),genesisBlock.getDataHash()));
        com.example.fabricexplorer.entity.Channel channel1 = new com.example.fabricexplorer.entity.Channel();
        channel1.setBlocks(0);
        channel1.setTrans(0);
        channel1.setName(genesisBlock.getChannelId());
        channel1.setChannel_genesis_hash(channelGenesisHash);
        channelService.saveChannel(channel1);


//        初始化peer信息
        initPeers(channel,channelGenesisHash);
//        初始化orderer信息
        initOrderers(channel,channelGenesisHash);
//        初始化chaincode数据
        initChaincodeDB(channel.queryInstantiatedChaincodes(peer0_org1),channelGenesisHash,peerLists);

        //获取blocks以及交易信息
        for (long current = channelInfo.getHeight()-1 ; current > -1 ; --current) {
            //通过区块号遍历区块信息
            BlockInfo currentBlock = channel.queryBlockByNumber(current);
//            log.info(Hex.toHexString(currentBlock.getTransActionsMetaData()));
            final long blockNumber = currentBlock.getBlockNumber();
            String dataHash = Hex.toHexString(currentBlock.getDataHash());
            String preBlockHash = Hex.toHexString(currentBlock.getPreviousHash());
            int envelopeCount = currentBlock.getEnvelopeCount();//区块中的交易数量
            String blockHash = Hex.toHexString(SDKUtils.
                    calculateBlockHash(client, blockNumber, currentBlock.getPreviousHash(), currentBlock.getDataHash()));
            Blocks blocks = new Blocks();
            blocks.setBlockHash(blockHash);
            blocks.setBlockNum(blockNumber);
            blocks.setDataHash(dataHash);
            blocks.setPreHash(preBlockHash);
            blocks.setChannel_genesis_hash(channelGenesisHash);
            blocks.setTxCount(envelopeCount);
            Date createDt= currentBlock.getEnvelopeInfo(0).getTimestamp();
            blocks.setCreateDt(createDt);
//            log.info("blocknum : "+current+"has envelopCount"+envelopeCount);
            blocksService.saveBlocks(blocks);
            int i = 0;
            int txCount = 0;
            //遍历区块上的信封
            for (BlockInfo.EnvelopeInfo envelopeInfo : currentBlock.getEnvelopeInfos()) {
                ++i;
                String txId = envelopeInfo.getTransactionID();
//                long epoch = envelopeInfo.getEpoch();
                BlockInfo.EnvelopeType type = envelopeInfo.getType();//信封类型 TRANSACTION_ENVELOPE,ENVELOPE;
                String creator_nonce = Hex.toHexString(envelopeInfo.getNonce());
                String creatorMspId = envelopeInfo.getCreator().getMspid();//创建MspID
                String certificate = envelopeInfo.getCreator().getId();
                long validationCode = envelopeInfo.getValidationCode();
                String channelId = envelopeInfo.getChannelId();
//                log.info("creator :" + creatorMspId);
                long blockId = blockNumber;
                Transaction transaction = new Transaction();
                transaction.setBlockId(blockId);
                transaction.setTxHash(txId);
                transaction.setCreateDt(envelopeInfo.getTimestamp());
                transaction.setChainCode(channelId);
                transaction.setCreatorMspId(creatorMspId);
                transaction.setType(type.name());
                transaction.setChannelGenesisHash(channelGenesisHash);
                transaction.setValidationCode(validationCode);
                transaction.setCreatorNonce(creator_nonce);
                if (type == BlockInfo.EnvelopeType.TRANSACTION_ENVELOPE) {
                    ++txCount;
                    BlockInfo.TransactionEnvelopeInfo transactionEnvelopeInfo = (BlockInfo.TransactionEnvelopeInfo) envelopeInfo;
                    long actionsCount = transactionEnvelopeInfo.getTransactionActionInfoCount();
//                    log.info("actionsCount : "+actionsCount);
                    boolean isValid = transactionEnvelopeInfo.isValid();
                    long validCode = transactionEnvelopeInfo.getValidationCode();
                    String envelope_signature = Hex.toHexString(transactionEnvelopeInfo.getSignature());//交易的签名
                    transaction.setEnvelopeSignature(envelope_signature);
                    int j = 0;
                    for (BlockInfo.TransactionEnvelopeInfo.TransactionActionInfo transactionActionInfo : transactionEnvelopeInfo.getTransactionActionInfos()) {
                        ++j;
                        long responseStatus = transactionActionInfo.getResponseStatus();
                        transactionActionInfo.getProposalResponsePayload();
                        List endorserList = new LinkedList();
                        String endorser_signature = Hex.toHexString(transactionActionInfo.getEndorsementInfo(0).getSignature());
                        transaction.setEndorserSignature(endorser_signature);
                        for (int n = 0; n < transactionActionInfo.getEndorsementsCount(); ++n) {
                            BlockInfo.EndorserInfo endorserInfo = transactionActionInfo.getEndorsementInfo(n);
                            endorserList.add(endorserInfo.getMspid());
                        }

                        transaction.setEndorserMspId(endorserList.toString());

                        String chaincodeName = transactionActionInfo.getChaincodeIDName();
                        TxReadWriteSetInfo rwsetInfo = transactionActionInfo.getTxReadWriteSet();
                        Map readSetMap = new HashMap();
                        Map writeSetMap = new HashMap();
                        if (null != rwsetInfo) {
                            int rwsIndex = -1;
                            for (TxReadWriteSetInfo.NsRwsetInfo nsRwsetInfo : rwsetInfo.getNsRwsetInfos()) {
                                rwsIndex++;
                                final String namespace = nsRwsetInfo.getNamespace();
                                KvRwset.KVRWSet rws = nsRwsetInfo.getRwset();
                                List readSetList = rws.getReadsList();
                                List writeSetList = rws.getWritesList();
                                readSetMap.put(rwsIndex,readSetList);
                                writeSetMap.put(rwsIndex,writeSetList);
                            }
                        }
                        transaction.setReadSet(JSON.toJSONString(readSetMap.toString()));
                        transaction.setReadSet(JSON.toJSONString(writeSetMap.toString()));
                    }

                }
                transactionService.saveTransaction(transaction);
            }

        }

    }
}