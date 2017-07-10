package info.smartkit.godpaper.go.service;

import java.io.IOException;
import java.util.List;

/**
 * Created by smartkit on 10/07/2017.
 */
public interface AierService {
        void createModelFolder(String name) throws IOException;
        void copySgfFiles(String srcDir,String destDir) throws IOException;
}
