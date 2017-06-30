package info.smartkit.godpaper.go.service;

import me.grapebaba.hyperledger.fabric.models.ChaincodeOpResult;

/**
 * Created by smartkit on 21/06/2017.
 * @see: https://github.com/GrapeBaBa/hyperledger-java/blob/master/hyperledger-java-examples/src/main/java/me/grapebaba/hyperledger/fabric/examples/FabricExample.java
 */
public interface ChainCodeService {
         void createRegistrar(String enrollId,String enrollSecret);//e.g:"jim","6avZQLwcUe9b"
         void getRegistrar(String enrollId);
         void deleteRegistrar(String enrollId,String enrollSecret);
         ChaincodeOpResult invoke(String chainName,String enrollId, String[] args);
         //Query
         String queryBykey(String chainName,String enrollId,String key);
        String queryAllKeys(String chainName,String enrollId);
//         void queryB(String chainName,String enrollId,String type);
//         void queryC(String chainName,String enrollId,String type);
         void getBlockchain();
         void getBlock(int index);
         void getNetworkPeers();
        ChaincodeOpResult deploy( String chainName,String enrollId,String[] args);//e.g:"put","hello","world"
         //TODO:transactions related.
}
