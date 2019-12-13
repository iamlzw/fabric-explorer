package com.example.fabricexplorer.Impl;

import com.example.fabricexplorer.entity.Chaincode;
import com.example.fabricexplorer.entity.PeerRefChainCode;
import com.example.fabricexplorer.mapper.ChaincodeMapper;
import com.example.fabricexplorer.mapper.PeerRefChaincodeMapper;
import com.example.fabricexplorer.service.ChaincodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ChaincodeService")
public class ChaincodeServiceImpl implements ChaincodeService {

    @Autowired
    private ChaincodeMapper chaincodeMapper;
    @Autowired
    private PeerRefChaincodeMapper peerRefChaincodeMapper;

    @Override
    public void saveChaincode(Chaincode chaincode) {
        if (chaincodeMapper.validChainCode(chaincode.getName()) == 0){
            chaincodeMapper.saveChaincode(chaincode);
        }
    }

    @Override
    public void savePeerRefChaincode(PeerRefChainCode peerRefChainCode) {
        if (peerRefChaincodeMapper.validCount(peerRefChainCode) == 0){
            peerRefChaincodeMapper.savePeerRefChaincode(peerRefChainCode);
        }
    }
}
