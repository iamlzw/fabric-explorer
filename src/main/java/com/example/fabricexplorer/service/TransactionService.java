package com.example.fabricexplorer.service;

import com.example.fabricexplorer.entity.Blocks;

import java.util.Map;

public interface TransactionService {
    Blocks getTxCountByBlockNum(Map map);
}
