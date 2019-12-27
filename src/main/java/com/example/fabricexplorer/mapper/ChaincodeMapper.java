package com.example.fabricexplorer.mapper;

import com.example.fabricexplorer.entity.Chaincode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChaincodeMapper {
    int validChainCode(String chainCodeName);
    void saveChaincode(Chaincode chaincode);
    int getChainCodeCount();
}
