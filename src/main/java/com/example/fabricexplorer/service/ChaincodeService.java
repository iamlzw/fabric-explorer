package com.example.fabricexplorer.service;

import com.example.fabricexplorer.entity.Chaincode;
import com.example.fabricexplorer.entity.PeerRefChainCode;

public interface ChaincodeService {
    void saveChaincode(Chaincode chaincode);
    void savePeerRefChaincode(PeerRefChainCode peerRefChainCode);
    int getChainCodeCount();
}
