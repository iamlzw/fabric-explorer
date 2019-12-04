package com.example.fabricexplorer.service;

import com.example.fabricexplorer.client.FabricClient;

public interface NetworkService {
    boolean synchNetWorkConfigToDB(FabricClient client) throws Exception;
}
