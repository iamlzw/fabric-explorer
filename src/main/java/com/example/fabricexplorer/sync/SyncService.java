package com.example.fabricexplorer.sync;

import com.example.fabricexplorer.client.CAClient;
import com.example.fabricexplorer.client.ChannelClient;
import com.example.fabricexplorer.client.FabricClient;
import com.example.fabricexplorer.config.Config;
import com.example.fabricexplorer.entity.Blocks;
import com.example.fabricexplorer.mapper.BlocksMapper;
import com.example.fabricexplorer.user.UserContext;
import com.example.fabricexplorer.util.Util;
import org.bouncycastle.util.encoders.Hex;
import org.hyperledger.fabric.sdk.*;
import org.springframework.beans.factory.annotation.Autowired;

public class SyncService {

    @Autowired
    private BlocksMapper blocksMapper;

    private void init(){
    }
}
