package com.example.fabricexplorer.service;

import com.example.fabricexplorer.entity.Channel;
import com.example.fabricexplorer.entity.PeerRefChannel;

public interface ChannelService {
    int getChannelCount(Channel channel);
    void saveChannel(Channel channel);

}
