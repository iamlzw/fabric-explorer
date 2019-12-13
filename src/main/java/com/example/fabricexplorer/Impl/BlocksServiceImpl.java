package com.example.fabricexplorer.Impl;

import com.example.fabricexplorer.entity.Blocks;
import com.example.fabricexplorer.mapper.BlocksMapper;
import com.example.fabricexplorer.mapper.ChannelMapper;
import com.example.fabricexplorer.service.BlocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("BlocksService")
public class BlocksServiceImpl implements BlocksService {

    @Autowired
    private BlocksMapper blocksMapper;
    @Autowired
    private ChannelMapper channelMapper;
    @Override
    public int getBlocksCount(Blocks blocks) {
        return blocksMapper.getBlocksCount(blocks);
    }

    @Override
    public void saveBlocks(Blocks blocks) {
        if (blocksMapper.getBlocksCount(blocks) == 0){
            blocksMapper.saveBlock(blocks);
            blocksMapper.updateChannelAfterSaveBlock(blocks.getChannel_genesis_hash());
        }
    }
}
