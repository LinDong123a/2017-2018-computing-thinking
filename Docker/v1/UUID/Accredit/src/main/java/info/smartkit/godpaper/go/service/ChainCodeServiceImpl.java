package info.smartkit.godpaper.go.service;

import info.smartkit.godpaper.go.settings.ChainCodeVariables;
import me.grapebaba.hyperledger.fabric.ErrorResolver;
import me.grapebaba.hyperledger.fabric.Fabric;
import me.grapebaba.hyperledger.fabric.Hyperledger;
import me.grapebaba.hyperledger.fabric.models.*;
import me.grapebaba.hyperledger.fabric.models.Error;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by smartkit on 21/06/2017.
 */
@Service
public class ChainCodeServiceImpl implements ChainCodeService {

        private static final HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR = new HttpLoggingInterceptor();
//
        static {
                HTTP_LOGGING_INTERCEPTOR.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        private Fabric FABRIC = null;

        private static Logger LOG = LogManager.getLogger(ChainCodeService.class);

        private Fabric getFabric(){
                if(FABRIC==null){
                        FABRIC = Hyperledger.fabric(ChainCodeVariables.baseUrl, HTTP_LOGGING_INTERCEPTOR);
                }
                return FABRIC;
        }


        /*
         * TODO:CA-register then enroll.
         * @see: https://stackoverflow.com/questions/39458479/hyperledger-new-user-register
         */
        @Override public void createRegistrar(String enrollId, String enrollSecret) {
                getFabric().createRegistrar(
                        Secret.builder()
                                .enrollId(enrollId)
                                .enrollSecret(enrollSecret)
                                .build())
                        .subscribe(new Action1<OK>() {
                                @Override
                                public void call(OK ok) {
                                        LOG.info("Create registrar ok message:"+ok);
                                }
                        }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                        Error error = ErrorResolver.resolve(throwable, Error.class);
                                        LOG.error("Error message:%s\n"+error);
                                }
                        });
        }

        @Override public void getRegistrar(String enrollId) {
                FABRIC.getRegistrar(enrollId)
                        .subscribe(new Action1<OK>() {
                                @Override
                                public void call(OK ok) {
                                        LOG.info("Get registrar ok message:%s\n"+ok);
                                }
                        });

                FABRIC.getRegistrarECERT(enrollId)
                        .subscribe(new Action1<OK>() {
                                @Override
                                public void call(OK ok) {
                                        LOG.info("Get registrar ecert ok message:%s\n"+ok);
                                }
                        });

                FABRIC.getRegistrarTCERT(enrollId)
                        .subscribe(new Action1<OK1>() {
                                @Override
                                public void call(OK1 ok) {
                                        for (String okString : ok.getOk()) {
                                                LOG.info("Get registrar tcert ok message:%s\n"+okString);
                                        }
                                }
                        });
        }

        @Override public void deleteRegistrar(String enrollId, String enrollSecret) {
                getFabric().deleteRegistrar(enrollId).subscribe(new Action1<OK>() {
                        @Override
                        public void call(OK ok) {
                                LOG.info("Delete registrar ok message:%s\n"+ok);
                        }
                });
        }

        @Override public ChaincodeOpResult invoke(String chainName,String enrollId, String[] args) {
                List<ChaincodeOpResult> opResults = new ArrayList<ChaincodeOpResult>();
                getFabric().chaincode(
                        ChaincodeOpPayload.builder()
                                .jsonrpc("2.0")
                                .id(1)
                                .method("invoke")
                                .params(
                                        ChaincodeSpec.builder()
                                                .chaincodeID(
                                                        ChaincodeID.builder()
                                                                .name(chainName)
                                                                .build())
                                                .ctorMsg(
                                                        ChaincodeInput.builder()
                                                                .function("invoke")
                                                                .args(Arrays.asList(args))//"aKey","aValue","bKey", "bValue"
                                                                .build())
                                                .secureContext(enrollId)
                                                .type(ChaincodeSpec.Type.GOLANG)//
                                                .build())
                                .build())
                        .flatMap(new Func1<ChaincodeOpResult, Observable<Transaction>>() {
                                @Override
                                public Observable<Transaction> call(ChaincodeOpResult chaincodeOpResult) {
                                        LOG.info("Invoke chaincode result:%s\n"+chaincodeOpResult);
                                        try {
                                                TimeUnit.SECONDS.sleep(3L);
                                        } catch (InterruptedException e) {
                                                e.printStackTrace();
                                        }
                                        opResults.add(chaincodeOpResult);
//
                                        return  getFabric().getTransaction(chaincodeOpResult.getResult().getMessage());
                                }
                        })
                        .subscribe(new Action1<Transaction>() {
                                @Override
                                public void call(Transaction transaction) {
                                        System.out.printf("Get transaction:%s\n", transaction);
                                }
                        });
                return opResults.get(0);
        }

        @Override public String queryBykey(String chainName,String enrollId,String key) {
                final String[] keyValue = { "" };
                getFabric().chaincode(
                        ChaincodeOpPayload.builder()
                                .jsonrpc("2.0")
                                .id(1)
                                .method("query")
                                .params(
                                        ChaincodeSpec.builder()
                                                .chaincodeID(
                                                        ChaincodeID.builder()
                                                                .name(chainName)
                                                                .build())
                                                .ctorMsg(
                                                        ChaincodeInput.builder()
                                                                .function("get")
                                                                .args(Collections.singletonList(key))
                                                                .build())
                                                .secureContext(enrollId)
                                                .type(ChaincodeSpec.Type.GOLANG)
                                                .build())
                                .build())
                        .subscribe(new Action1<ChaincodeOpResult>() {
                                @Override
                                public void call(ChaincodeOpResult chaincodeOpResult) {
                                        LOG.info("Query chaincode result:%s\n"+chaincodeOpResult);
                                        keyValue[0] = chaincodeOpResult.getResult().getMessage();
                                }
                        });
                return keyValue[0];


        }
        @Override public String queryAllKeys(String chainName,String enrollId) {
                final String[] allKeys = {""};
                getFabric().chaincode(
                        ChaincodeOpPayload.builder()
                                .jsonrpc("2.0")
                                .id(1)
                                .method("query")
                                .params(
                                        ChaincodeSpec.builder()
                                                .chaincodeID(
                                                        ChaincodeID.builder()
                                                                .name(chainName)
                                                                .build())
                                                .ctorMsg(
                                                        ChaincodeInput.builder()
                                                                .function("keys")
                                                                .build())
                                                .secureContext(enrollId)
                                                .type(ChaincodeSpec.Type.GOLANG)
                                                .build())
                                .build())
                        .subscribe(new Action1<ChaincodeOpResult>() {
                                @Override
                                public void call(ChaincodeOpResult chaincodeOpResult) {
                                        allKeys[0]=chaincodeOpResult.getResult().getMessage();
                                        LOG.info("Query chaincode result:%s\n"+chaincodeOpResult);
                                }
                        });
                return allKeys[0];
        }


//        @Override public void queryB(String chainName,String enrollId,String type) {
//                getFabric().chaincode(
//                        ChaincodeOpPayload.builder()
//                                .jsonrpc("2.0")
//                                .id(1)
//                                .method("query")
//                                .params(
//                                        ChaincodeSpec.builder()
//                                                .chaincodeID(
//                                                        ChaincodeID.builder()
//                                                                .name(chainName)
//                                                                .build())
//                                                .ctorMsg(
//                                                        ChaincodeInput.builder()
//                                                                .function("query")
//                                                                .args(Collections.singletonList("b"))
//                                                                .build())
//                                                .secureContext(enrollId)
//                                                .type(ChaincodeSpec.Type.GOLANG)
//                                                .build())
//                                .build())
//                        .subscribe(new Action1<ChaincodeOpResult>() {
//                                @Override
//                                public void call(ChaincodeOpResult chaincodeOpResult) {
//                                        LOG.info("Query chaincode result:%s\n"+chaincodeOpResult);
//                                }
//                        });
//        }

//        @Override public void queryC(String chainName,String enrollId,String type) {
//                getFabric().chaincode(
//                        ChaincodeOpPayload.builder()
//                                .jsonrpc("2.0")
//                                .id(1)
//                                .method("query")
//                                .params(
//                                        ChaincodeSpec.builder()
//                                                .chaincodeID(
//                                                        ChaincodeID.builder()
//                                                                .name(chainName)
//                                                                .build())
//                                                .ctorMsg(
//                                                        ChaincodeInput.builder()
//                                                                .function("query")
//                                                                .args(Collections.singletonList("c"))
//                                                                .build())
//                                                .secureContext(enrollId)
//                                                .type(ChaincodeSpec.Type.GOLANG)
//                                                .build())
//                                .build())
//                        .subscribe(new Action1<ChaincodeOpResult>() {
//                                @Override
//                                public void call(ChaincodeOpResult chaincodeOpResult) {
//                                        LOG.info("Query chaincode result:%s\n"+chaincodeOpResult);
//                                }
//                        });
//        }

        @Override public void getBlockchain() {
                getFabric().getBlockchain()
                        .subscribe(new Action1<BlockchainInfo>() {

                                @Override
                                public void call(BlockchainInfo blockchainInfo) {
                                        LOG.info("Get blockchain info:%s\n"+blockchainInfo);
                                }
                        });

        }

        @Override public void getBlock(int index) {
                getFabric().getBlock(index)
                        .subscribe(new Action1<Block>() {
                                @Override
                                public void call(Block block) {
                                        LOG.info("Get Block info:%s\n"+block);
                                }
                        }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                        Error error = ErrorResolver.resolve(throwable, Error.class);
                                        System.out.printf("Error message:%s\n", error.getError());
                                }
                        });
        }

        @Override public void getNetworkPeers() {
                getFabric().getNetworkPeers().subscribe(new Action1<PeersMessage>() {
                        @Override
                        public void call(PeersMessage peersMessage) {
                                for (PeerEndpoint peerEndpoint : peersMessage.getPeers()) {
                                        LOG.info("Peer message:%s\n"+peerEndpoint);
                                }

                        }
                });
        }

        /*
        /{"jsonrpc": "2.0",   "method": "deploy",   "params": {    "type": 1,     "chaincodeID":{        "name": "hello"    },     "CtorMsg": {        "args": [""]     }   },   "id": 1 }
        */
        @Override public ChaincodeOpResult deploy( String chainName,String enrollId,String[] args) {
                List<ChaincodeOpResult> opResults = new ArrayList<ChaincodeOpResult>();
                //
                getFabric().chaincode(
                        ChaincodeOpPayload.builder()
                                .jsonrpc("2.0")
                                .id(1)
                                .method("deploy")
                                .params(
                                        ChaincodeSpec.builder()
                                                .chaincodeID(
                                                        ChaincodeID.builder()
                                                                .name(chainName)
//                                                                .path("github.com/hyperledger/fabric/examples/chaincode/go/chaincode_example02")
                                                                .build())
                                                .ctorMsg(
                                                        ChaincodeInput.builder()
                                                                .function("init")
                                                                .args(Arrays.asList(args))//e.g:"aKey","aValue"，"bKey","bValue"
                                                                .build())
                                                .type(ChaincodeSpec.Type.GOLANG)
                                                .secureContext(enrollId)
                                                .build())
                                .build())
                        .subscribe(new Action1<ChaincodeOpResult>() {
                                @Override
                                public void call(ChaincodeOpResult chaincodeOpResult) {
                                        LOG.info("Deploy chaincode result:%s\n"+chaincodeOpResult);
                                        opResults.add(chaincodeOpResult);
                                }
                        }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                                Error error = ErrorResolver.resolve(throwable, Error.class);
                                LOG.error("Error message:%s\n"+error.getError());
                        }
                });
                return opResults.get(0);
        }

        //        private <T> Observable<T> attachErrorHandler(Observable<T> obs) {
//                return obs.onErrorResumeNext(throwable -> {
//                        System.out.println("Handling error by printint to console: " + throwable);
//                        return Observable.empty();
//                });
//        }
//
//        // Use like this:
//        Observable<String> unsafeObs = getErrorProducingObservable();
//        Observable<String> safeObservable = attachErrorHandler(unsafeObs);
//        // This call will now never cause OnErrorNotImplementedException
//        safeObservable.subscribe(s -> System.out.println("Result: " + s));
}
