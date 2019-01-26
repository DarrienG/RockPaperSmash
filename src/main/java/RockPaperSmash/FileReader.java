package RockPaperSmash;

import java.lang.RuntimeException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.net.URL;
import java.net.URISyntaxException;

public class FileReader {
    public static BufferedReader loadFile(FileResource rFile) throws java.io.IOException {
        InputStream file = null;
        if (rFile.getResource()) {
           file = ClassLoader.getSystemResourceAsStream(rFile.getFileName());
        } else {
            file = new FileInputStream(rFile.getFileName());
        }
        return new BufferedReader(new InputStreamReader(file, "UTF-8"));
    }

    public static File getTmpDir() {
        try {
            return Files.createTempDirectory("TmpBattleFiles").toFile();
        } catch(java.io.IOException e) {
            System.out.println("Unable to create temp dir");
            throw new RuntimeException(e);
        }
    }
}
