package com.example.fabricexplorer.mapper;

import com.example.fabricexplorer.entity.Blocks;
import com.example.fabricexplorer.entity.Transaction;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BlocksMapper {
    int getBlocksCount(Blocks blocks);
    //插入区块信息
    int saveBlock(Blocks blocks);
    //通过区块号获取交易数量
    int getTxCountByBlockNum(Map map);
    //    通过交易ID获取交易
    //    params channel_genesis_hash,txHash
    Transaction getTransactionByID(Map map);
    //Returns the latest 'n' blocks by channel
    //params channel_genesis_hash
    List<Blocks> getBlockActivityList(String channel_genesis_hash);
    //Returns the list of transactions by channel, organization, date range and greater than a block and transaction id
    // params channel_genesis_hash,blockNum,txid,from,to,orgs
    List<Transaction> getTxList(Map map);

    List<Map> getBlockAndTxList(Map map);

    void updateChannelAfterSaveBlock(String channel_genesis_hash);



}
