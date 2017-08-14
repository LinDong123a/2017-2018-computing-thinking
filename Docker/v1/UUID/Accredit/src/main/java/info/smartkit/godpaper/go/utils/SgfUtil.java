package info.smartkit.godpaper.go.utils;

import info.smartkit.godpaper.go.service.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by smartkit on 04/07/2017.
 */
public class SgfUtil {
        private static Logger LOG = LogManager.getLogger(SgfUtil.class);

        public static String getWorkingDir() {
                String workingDir = System.getProperty("user.dir");
                LOG.info("workingDir:"+workingDir);
                return workingDir;
        }

        public static String getSgfLocal(String context) {
                // if(!new File("/uploads/").exists()) new File("/uploads/").mkdirs();
                // return "/uploads/";
                return getWorkingDir() + "/target/classes/sgf/" + context;
        }
        public static String getAiFilesLocal(String context) {
                // if(!new File("/uploads/").exists()) new File("/uploads/").mkdirs();
                // return "/uploads/";
                return getWorkingDir() + "/target/classes/AI_FILEs/" + context;
        }


}
