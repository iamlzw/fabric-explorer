package com.example.fabricexplorer.service;

import com.example.fabricexplorer.entity.Blocks;

import java.util.List;
import java.util.Map;

public interface BlocksService {
    int getBlocksCount();
    void saveBlocks(Blocks blocks);
    List<Map> getBlockActivityList(String channelGenesisHash);
    List<Map> getBlockAndTxList(Map map);
}
