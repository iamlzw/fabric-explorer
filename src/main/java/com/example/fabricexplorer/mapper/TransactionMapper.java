package com.example.fabricexplorer.mapper;

import com.example.fabricexplorer.entity.Blocks;
import com.example.fabricexplorer.entity.Transaction;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TransactionMapper {
    Blocks getTxCountByBlockNum(Map map);
    int existTransaction(Transaction transaction);
    void saveTransaction(Transaction transaction);
    void updateChainCodeAfterSaveTransaction(Transaction transaction);
    void updateChannelAfterSaveTransaction(Transaction transaction);
    int getTxCount();
    List<Map> getTxList(Map map);
    List<Map> getTxCountList(String channelGenesisHash);
}
