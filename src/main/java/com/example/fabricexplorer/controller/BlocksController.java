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
import org.apache.ibatis.annotations.Param;
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
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/blocks")
public class BlocksController {

    private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
    private static final String EXPECTED_EVENT_NAME = "event";

    @Autowired
    private BlocksService blocksService;

    @RequestMapping("/getBlocksCount")
    public int getBlocksCount(){
        return blocksService.getBlocksCount();
    }

    @RequestMapping("/getBlockActivityList")
    public List<Map> getBlockActivityList(String channelGenesisHash){
        return blocksService.getBlockActivityList(channelGenesisHash);
    }
}
