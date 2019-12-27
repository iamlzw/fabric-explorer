package com.example.fabricexplorer.Impl;

import com.example.fabricexplorer.entity.Blocks;
import com.example.fabricexplorer.entity.Transaction;
import com.example.fabricexplorer.mapper.TransactionMapper;
import com.example.fabricexplorer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("TransactionService")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;
    @Override
    public Blocks getTxCountByBlockNum(Map map) {
        return transactionMapper.getTxCountByBlockNum(map);
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        if (transactionMapper.existTransaction(transaction) == 0){
            transactionMapper.saveTransaction(transaction);
            transactionMapper.updateChainCodeAfterSaveTransaction(transaction);
            transactionMapper.updateChannelAfterSaveTransaction(transaction);
        }
    }

    @Override
    public int getTxCount() {
        return transactionMapper.getTxCount();
    }

    @Override
    public List<Map> getTxList(Map map) {
        return transactionMapper.getTxList(map);
    }

    @Override
    public List<Map> getTxCountList(String channelGenesisHash) {
        return transactionMapper.getTxCountList(channelGenesisHash);
    }
}
