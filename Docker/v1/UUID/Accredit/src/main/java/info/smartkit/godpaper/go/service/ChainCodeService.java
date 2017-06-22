package info.smartkit.godpaper.go.service;


/**
 * Created by smartkit on 21/06/2017.
 * @see: https://github.com/GrapeBaBa/hyperledger-java/blob/master/hyperledger-java-examples/src/main/java/me/grapebaba/hyperledger/fabric/examples/FabricExample.java
 */
public interface ChainCodeService {
         void createRegistrar(String enrollId,String enrollSecret);
         void getRegistrar(String enrollId);
         void deleteRegistrar(String enrollId,String enrollSecret);
         void invoke(String chainName,String enrollId, String[] values);
         void queryB(String chainName,String enrollId,String type);
         void queryC(String chainName,String enrollId,String type);
         void getBlockchain();
         void getBlock(int index);
         void getNetworkPeers();
}
