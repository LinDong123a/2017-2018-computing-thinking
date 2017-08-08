package info.smartkit.godpaper.go.utils;

import org.apache.commons.lang.RandomStringUtils;

/**
 * Created by smartkit on 11/07/2017.
 */
public class StringUtil {
        //
        public static String getUuidString(String prefix,int length){
                String suffix = RandomStringUtils.randomAlphanumeric(length).toLowerCase();
                return prefix+"_"+suffix;
        }
}
