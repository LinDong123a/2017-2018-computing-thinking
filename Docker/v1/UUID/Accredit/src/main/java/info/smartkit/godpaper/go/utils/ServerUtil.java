package info.smartkit.godpaper.go.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by smartkit on 04/07/2017.
 */
public class ServerUtil {
        public static InetAddress getInetAddress() {
                InetAddress inetAddr = null;
                try {
                        inetAddr = InetAddress.getLocalHost();
                        //			hostname = inetAddr.getHostName();
                        System.out.println("Your current Internet address : " + inetAddr.toString());
                } catch (UnknownHostException e) {
                        e.printStackTrace();
                }
                return inetAddr;
        }
}
