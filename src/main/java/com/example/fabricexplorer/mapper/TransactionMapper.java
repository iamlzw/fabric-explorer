package com.example.fabricexplorer.mapper;

import com.example.fabricexplorer.entity.Blocks;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TransactionMapper {
    Blocks getTxCountByBlockNum(Map map);
}
