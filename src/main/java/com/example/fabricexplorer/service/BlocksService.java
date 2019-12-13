package com.example.fabricexplorer.service;

import com.example.fabricexplorer.entity.Blocks;

import java.util.Map;

public interface BlocksService {
    int getBlocksCount(Blocks blocks);
    void saveBlocks(Blocks blocks);
}
