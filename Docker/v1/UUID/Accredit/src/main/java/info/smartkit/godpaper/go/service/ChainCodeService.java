package info.smartkit.godpaper.go.service;


/**
 * Created by smartkit on 21/06/2017.
 * @see: https://github.com/GrapeBaBa/hyperledger-java/blob/master/hyperledger-java-examples/src/main/java/me/grapebaba/hyperledger/fabric/examples/FabricExample.java
 */
public interface ChainCodeService {
        public void createRegistrar(String enrollId,String enrollSecret);
        public void getRegistrar(String enrollId);
        public void deleteRegistrar(String enrollId,String enrollSecret);
        public void invoke(String chainName,String enrollId, String[] values);
        public void queryB(String chainName,String enrollId,String type);
        public void queryC(String chainName,String enrollId,String type);
        public void getBlockchain();
        public void getBlock(int index);
        public void getNetworkPeers();
}
