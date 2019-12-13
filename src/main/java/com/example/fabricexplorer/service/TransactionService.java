package com.example.fabricexplorer.service;

import com.example.fabricexplorer.entity.Blocks;
import com.example.fabricexplorer.entity.Transaction;

import java.util.Map;

public interface TransactionService {
    Blocks getTxCountByBlockNum(Map map);
    void saveTransaction(Transaction transaction);
}
