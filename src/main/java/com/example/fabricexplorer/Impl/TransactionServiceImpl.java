package com.example.fabricexplorer.Impl;

import com.example.fabricexplorer.entity.Blocks;
import com.example.fabricexplorer.mapper.TransactionMapper;
import com.example.fabricexplorer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("TransactionService")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;
    @Override
    public Blocks getTxCountByBlockNum(Map map) {
        return transactionMapper.getTxCountByBlockNum(map);
    }
}
