package info.smartkit.godpaper.go.service;

import info.smartkit.godpaper.go.utils.SgfUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by smartkit on 10/07/2017.
 */
@Service
public class AierServiceImpl implements AierService{
        @Override public File createModelFolder(String name) throws IOException {
                String folderPathStr = SgfUtil.getAiFilesLocal(name);
                File folderFile = new File(folderPathStr);
                if(!folderFile.exists()) {
                        FileUtils.forceMkdir(folderFile);
                }
                return folderFile;
        }

        @Override public void copySgfFiles(String srcDir,String destDir) throws IOException {
                //mkdir
                FileUtils.forceMkdir(new File(SgfUtil.getSgfLocal(destDir)));
                //cp
                File srcFolder = new File(SgfUtil.getSgfLocal(srcDir));
                File destFolder = new File(SgfUtil.getSgfLocal(destDir));
               FileUtils.copyDirectory(srcFolder,destFolder);
        }
}
