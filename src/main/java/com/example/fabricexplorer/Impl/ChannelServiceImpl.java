package com.example.fabricexplorer.Impl;

import com.example.fabricexplorer.entity.Channel;
import com.example.fabricexplorer.entity.PeerRefChannel;
import com.example.fabricexplorer.mapper.ChannelMapper;
import com.example.fabricexplorer.mapper.PeerRefChannelMapper;
import com.example.fabricexplorer.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ChannelService")
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelMapper channelMapper;

    @Override
    public int getChannelCount(Channel channel) {
        return channelMapper.getChannelCount(channel);
    }

    @Override
    public void saveChannel(Channel channel) {
        if (channelMapper.getChannelCount(channel) == 0){
            channelMapper.saveChannel(channel);
        }else {
            channelMapper.updateChannel(channel);
        }
    }


}
