package info.smartkit.godpaper.go;

import info.smartkit.godpaper.go.settings.ChainCodeVariables;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hyperledger.java.shim.ChaincodeBase;
import org.hyperledger.java.shim.ChaincodeStub;

import java.util.Map;

/**
 * Created by smartkit on 30/06/2017.
 */
public class UUIDAccreditChainCode extends ChaincodeBase {

//        static {
//                String host = ChainCodeVariables.baseUrl;
////                int port = 7051;
//        }

        private static Logger LOG = LogManager.getLogger(UUIDAccreditChainCode.class);


        @Override public String run(ChaincodeStub stub, String function, String[] args) {
                LOG.info("In run, function:"+function);
                switch (function) {
                case "init":
                        for (int i = 0; i < args.length; i += 2)
                                stub.putState(args[i], args[i + 1]);
                        break;
                case "invoke":
                        for (int i = 0; i < args.length; i += 2)
                                stub.putState(args[i], args[i + 1]);
                        break;
                case "put":
                        stub.putState(args[0], args[1]);
                        break;
                case "del":
                        for (String arg : args)
                                stub.delState(arg);
                        break;
                default:
                        LOG.error("No matching case for function:"+function);

                }
                LOG.error("No matching case for function:"+function);
                return null;
        }

        @Override public String query(ChaincodeStub stub, String function, String[] args) {
                LOG.info("query");
                switch (function){
                case "get": {
                        return stub.getState(args[0]);
                }
                case "keys":{
                        Map<String, String> keysIter = null;
                        if (args.length >= 2) {
                                keysIter = stub.rangeQueryState(args[0], args[1]);
                        }else{
                                keysIter = stub.rangeQueryState("","");
                        }

                        return keysIter.keySet().toString();
                }
                default:
                        LOG.error("No matching case for function:"+function);
                        return "";
                }
        }

        @Override public String getChaincodeID() {

                return ChainCodeVariables.chainName;
        }



//        public static void main(String[] args) throws Exception {
////                System.out.println("Hello world! starting "+args);
//                LOG.info("UUIDAccreditChainCode main with args:"+ Arrays.toString(args));
//                ChainCodeVariables.chainCode = new UUIDAccreditChainCode();
////                String[] helloArgs = {"put", "KEY-1", "Chaincode Initialized"};
//                ChainCodeVariables.chainCode.start(null);//Notice: Default with chain code register behavior
////Test code.
//
//        }

}
