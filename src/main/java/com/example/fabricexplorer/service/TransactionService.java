package com.example.fabricexplorer.service;

import com.example.fabricexplorer.entity.Blocks;
import com.example.fabricexplorer.entity.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    Blocks getTxCountByBlockNum(Map map);
    void saveTransaction(Transaction transaction);
    int getTxCount();
    List<Map> getTxList(Map map);
    List<Map> getTxCountList(String channelGenesisHash);
}
