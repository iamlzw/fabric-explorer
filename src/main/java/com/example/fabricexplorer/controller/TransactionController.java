package com.example.fabricexplorer.controller;

import com.example.fabricexplorer.entity.Blocks;
import com.example.fabricexplorer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @ResponseBody
    @RequestMapping("/getTxCountByBlockNum")
    private Blocks getTxCountByBlockNum(Map map){
        return transactionService.getTxCountByBlockNum(map);
    }
}
