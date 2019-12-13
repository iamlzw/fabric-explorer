package com.example.fabricexplorer.controller;

import com.example.fabricexplorer.entity.Peer;
import com.example.fabricexplorer.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("peer")
@CrossOrigin("http://localhost:8081")
public class PeerController {

    @Autowired
    private PeerService peerService;

    @RequestMapping("/getPeers")
    @ResponseBody
    public List<Peer> getPeers(){
        return peerService.getPeers();
    }
    @RequestMapping("/getNodesCount")
    @ResponseBody
    public int getNodesCount(){
        return peerService.getNodesCount();
    }
}
