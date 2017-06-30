package info.smartkit.godpaper.go.controller;

import info.smartkit.godpaper.go.pojo.Enroller;
import info.smartkit.godpaper.go.pojo.Invoker;
import info.smartkit.godpaper.go.service.ChainCodeService;
import info.smartkit.godpaper.go.settings.ChainCodeProperties;
import info.smartkit.godpaper.go.settings.ChainCodeVariables;
import me.grapebaba.hyperledger.fabric.models.ChaincodeOpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by smartkit on 21/06/2017.
 */
@RestController
@RequestMapping("/chain")
public class ChainCodeController {

        @Autowired ChainCodeService chainCodeService;
        @Autowired ChainCodeProperties chainCodeProperties;
//        @RequestMapping(method = RequestMethod.POST,value="/")
//        public void createOne(@RequestBody Enroller enroller){
//                chainCodeService.createRegistrar(enroller.getId(),enroller.getSecret());
//        }

//        @RequestMapping(method = RequestMethod.GET, value="/{enrollId}")
//        public void get(@PathVariable String enrollId){
//                chainCodeService.getRegistrar(enrollId);
//        }

//        @RequestMapping(method = RequestMethod.GET, value="/deploy/{name}")
//        public void deploy(@PathVariable String name){
//                chainCodeService.deploy(name);
//        }

//        @RequestMapping(method = RequestMethod.DELETE, value="/{enrollId}/{enrollSecret}")
//        public void delete(@PathVariable String userId,@PathVariable String enrollSecret){
//                chainCodeService.deleteRegistrar(userId,enrollSecret);
//        }
        //Query,String chainName,String enrollId, String[] values
//        @RequestMapping(method = RequestMethod.POST, value="/invoke/")
//        public void invokeChain(@RequestBody Invoker invoker){
//                chainCodeService.invoke(invoker.getChainName(),invoker.getEnrollId(),invoker.getValues());
//        }
//        @RequestMapping(method = RequestMethod.POST, value="/query")
//        public void query(@RequestBody Invoker invoker){
//                if(invoker.getValues().equals("b")) {
//                        chainCodeService.queryB(invoker.getChainName(), invoker.getEnrollId(), invoker.getValues().toString());
//                }
//                if(invoker.getValues().equals("c")) {
//                        chainCodeService.queryC(invoker.getChainName(), invoker.getEnrollId(), invoker.getValues().toString());
//                }
//        }
//Deploy init/Invoke
        @RequestMapping(method = RequestMethod.GET, value="/invoke/{key}/{value}")
        public ChaincodeOpResult putKeyValue(@PathVariable String key,@PathVariable String value){
                String[] putArgs = {key,value};
                return chainCodeService.invoke(chainCodeProperties.getChainName(),chainCodeProperties.getEnrollId(),putArgs);
//                return chainCodeService.deploy(chainCodeProperties.getChainName(),chainCodeProperties.getEnrollId(),putArgs);
        }
        //Query
        @RequestMapping(method = RequestMethod.GET, value="/query/{key}")
        public String queryByKey(@PathVariable String key){
                return chainCodeService.queryBykey(chainCodeProperties.getChainName(),chainCodeProperties.getEnrollId(),key);
        }
        @RequestMapping(method = RequestMethod.GET, value="/query/keys")
        public String queryAllKeys(){
                return chainCodeService.queryAllKeys(chainCodeProperties.getChainName(),chainCodeProperties.getEnrollId());
        }
        //Block
//        @RequestMapping(method = RequestMethod.GET, value="/block")
//        public void getChainBlocks(){
//                chainCodeService.getBlockchain();
//        }
//        @RequestMapping(method = RequestMethod.GET, value="/block/{index}")
//        public void getChainBlock(@PathVariable int index){
//                chainCodeService.getBlock(index);
//        }
//        @RequestMapping(method = RequestMethod.GET, value="/network/peers")
//        public void getNetworkPeers(){
//                chainCodeService.getNetworkPeers();
//        }
}
