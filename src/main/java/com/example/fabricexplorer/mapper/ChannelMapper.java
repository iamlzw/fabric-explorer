package com.example.fabricexplorer.mapper;

import com.example.fabricexplorer.entity.Channel;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ChannelMapper {
//    Channel getChannelConfig(Map map);
//    Channel getChannel(Map map);
    int getChannelCount(Channel channel);
    void saveChannel(Channel channel);
    void updateChannel(Channel channel);
//    int existChannel(String channelName);

}
