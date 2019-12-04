package com.example.fabricexplorer.service;

import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

public interface PeerService {
    void insertNewPeers() throws Exception;
}
