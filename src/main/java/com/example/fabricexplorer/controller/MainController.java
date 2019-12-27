package com.example.fabricexplorer.controller;


import com.example.fabricexplorer.service.BlocksService;
import com.example.fabricexplorer.service.ChaincodeService;
import com.example.fabricexplorer.service.PeerService;
import com.example.fabricexplorer.service.TransactionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import sun.applet.Main;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("main")
@RestController
@CrossOrigin("http://localhost:8081")
public class MainController {

    private final static Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private BlocksService blocksService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PeerService peerService;

    @Autowired
    private ChaincodeService chaincodeService;

    @RequestMapping("/getCounts")
    @ResponseBody
    public Map getCounts(){
        Map map = new HashMap();
        map.put("blocksCount",blocksService.getBlocksCount());
        map.put("txCount",transactionService.getTxCount());
        map.put("nodesCount",peerService.getNodesCount());
        map.put("chaincodeCount",chaincodeService.getChainCodeCount());
        return map;
    }

    @RequestMapping("/getTxCountList")
    @ResponseBody
    public List getTxCountList(@RequestParam String channelGenesisHash){
        return transactionService.getTxCountList(channelGenesisHash);
    }

    @RequestMapping(value = "/getBlockActivityList",method = RequestMethod.POST)
    public List<Map> getBlockActivityList(@RequestParam String channelGenesisHash){
//        log.info(channelGenesisHash);
        return blocksService.getBlockActivityList(channelGenesisHash);
    }

    @RequestMapping(value = "/getBlockAndTxList",method = RequestMethod.POST)
    public Map getBlockAndTxList(
            @RequestParam(value = "channelGenesisHash",required = true) String channelGenesisHash,
            @RequestParam(value = "blockNum",required = false) String blockNum,
            @RequestParam(value = "from",required = true) long from,
            @RequestParam(value = "to",required = true) long to,
            @RequestParam(value = "orgs",required = true) List orgs,
            @RequestParam(value = "current",required = false) int current,
            @RequestParam(value = "pageSize",required = false) int pageSize) {
//        log.info("channelGenesisHash:"+channelGenesisHash+"from"+from+"to:"+to+"orgs:"+orgs);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fromDateString = df.format(from);
        String toDateString = df.format(to);
        if (orgs.size() == 0){
            orgs = null;
        }
        PageHelper.startPage(current+1,pageSize);
        Map paramMap = new HashMap();
        paramMap.put("channelGenesisHash",channelGenesisHash);
        paramMap.put("blockNum",blockNum);
        paramMap.put("from",fromDateString);
        paramMap.put("to",toDateString);
        paramMap.put("orgs",orgs);
        List blocksInfo = blocksService.getBlockAndTxList(paramMap);
        PageInfo p = new PageInfo(blocksInfo);
        Map re = new HashMap();
        re.put("blocksInfo",blocksInfo);
        re.put("pageInfos",p);
        return re;
    }

    @RequestMapping(value = "/getTxList",method = RequestMethod.POST)
    public Map getTxList(
            @RequestParam(value = "channelGenesisHash",required = true) String channelGenesisHash,
            @RequestParam(value = "from",required = true) long from,
            @RequestParam(value = "to",required = true) long to,
            @RequestParam(value = "blockNum",required = true) String blockNum,
            @RequestParam(value = "txid",required = true) String txid,
            @RequestParam(value = "orgs",required = true) List orgs,
            @RequestParam(value = "current",required = false) int current,
            @RequestParam(value = "pageSize",required = false) int pageSize){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fromDateString = df.format(from);
        String toDateString = df.format(to);
        if (orgs.size() == 0){
            orgs = null;
        }
        PageHelper.startPage(current+1,pageSize);
        Map paramMap = new HashMap();
        paramMap.put("channelGenesisHash",channelGenesisHash);
        paramMap.put("blockNum",blockNum);
        paramMap.put("from",fromDateString);
        paramMap.put("to",toDateString);
        paramMap.put("orgs",orgs);
        paramMap.put("txid",txid);
        List blocksInfo = transactionService.getTxList(paramMap);
        PageInfo p = new PageInfo(blocksInfo);
        Map re = new HashMap();
        re.put("txInfo",blocksInfo);
        re.put("pageInfos",p);
        return re;
    }
}
