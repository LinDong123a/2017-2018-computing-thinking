package info.smartkit.godpaper.go.controller;

import info.smartkit.godpaper.go.pojo.Enroller;
import info.smartkit.godpaper.go.pojo.Invoker;
import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.service.ChainCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by smartkit on 21/06/2017.
 */
@RestController
@RequestMapping("/chain")
public class ChainCodeController {

        @Autowired ChainCodeService chainCodeService;
        @RequestMapping(method = RequestMethod.POST,value="/")
        public void createOne(@RequestBody Enroller enroller){
                chainCodeService.createRegistrar(enroller.getId(),enroller.getSecret());
        }

        @RequestMapping(method = RequestMethod.GET, value="/{enrollId}")
        public void get(@PathVariable String enrollId){
                chainCodeService.getRegistrar(enrollId);
        }

        @RequestMapping(method = RequestMethod.DELETE, value="/{enrollId}/{enrollSecret}")
        public void delete(@PathVariable String userId,@PathVariable String enrollSecret){
                chainCodeService.deleteRegistrar(userId,enrollSecret);
        }
        //Query,String chainName,String enrollId, String[] values
        @RequestMapping(method = RequestMethod.POST)
        public void invokeChain(@RequestBody Invoker invoker){
                chainCodeService.invoke(invoker.getChainName(),invoker.getEnrollId(),invoker.getValues());
        }
        @RequestMapping(method = RequestMethod.POST, value="/query")
        public void query(@RequestBody Invoker invoker){
                if(invoker.getValues().equals("b")) {
                        chainCodeService.queryB(invoker.getChainName(), invoker.getEnrollId(), invoker.getValues().toString());
                }
                if(invoker.getValues().equals("c")) {
                        chainCodeService.queryC(invoker.getChainName(), invoker.getEnrollId(), invoker.getValues().toString());
                }
        }
        //Block
        @RequestMapping(method = RequestMethod.GET, value="/block")
        public void getChainBlocks(){
                chainCodeService.getBlockchain();
        }
        @RequestMapping(method = RequestMethod.GET, value="/block/{index}")
        public void getChainBlock(@PathVariable int index){
                chainCodeService.getBlock(index);
        }
        @RequestMapping(method = RequestMethod.GET, value="/network/peers")
        public void getNetworkPeers(){
                chainCodeService.getNetworkPeers();
        }
}
