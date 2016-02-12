package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dhirendra
 */
public final class Config {
    
    public static final String SEP = File.separator;
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String DEFAULT_DATA_DIR = USER_HOME + SEP  + "docs-searcher";
    public static final String DEFAULT_INDEX_DIR = DEFAULT_DATA_DIR + SEP + "index";
    public static final String PROP_FILE = DEFAULT_DATA_DIR + SEP + "config.properties";
    public static final String DEFAULT_DOCS_DIR = USER_HOME + SEP + "Documents";
    
    public static final String PROP_KEY_INDEX_DIR = "index_dir";
    public static final String PROP_KEY_DOCS_DIRS = "docs_dirs";
    
    private String indexPath;
    private final List<String> docsDirList = new ArrayList<>();
    
    public static void main(String[] args) {
        Config config = new Config();
    }

    public Config() {
        init();
    }
    public void init(){
        File dataDir = new File(String.valueOf(DEFAULT_DATA_DIR));
        if (! dataDir.exists()){
            dataDir.mkdir();
        }
        
        if (Files.exists(Paths.get(PROP_FILE))) {
           initFromPropFile();
        } else {
            indexPath = DEFAULT_INDEX_DIR;
            docsDirList.add(DEFAULT_DOCS_DIR);
            createDefaultPropFile();
        }
        System.out.println(indexPath);
        System.out.println(docsDirList);
    }

    private void initFromPropFile() {
        Logger.getLogger(Config.class.getName()).log(Level.INFO, "Loading properties file...");
        System.out.println("");
        Properties properties = new Properties();
        try (InputStream is = new FileInputStream(PROP_FILE)){
            properties.load(is);
            System.out.println("line 66" +properties + ", " + PROP_FILE);
            indexPath = properties.getProperty(PROP_KEY_INDEX_DIR);
            String[] dirNames = properties.getProperty(PROP_KEY_DOCS_DIRS).split(",");
            for(String dirName : dirNames){
                docsDirList.add(dirName);
            }
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, "Could not read properties file", ex);
        }
    }

    private void createDefaultPropFile() {
        Properties properties = new Properties();
        try (OutputStream out = new FileOutputStream(PROP_FILE)) {
            properties.setProperty(PROP_KEY_INDEX_DIR, DEFAULT_INDEX_DIR);
            StringBuilder sb = new StringBuilder();
            for(String dirPath : docsDirList){
                sb.append(dirPath);
            }
            properties.setProperty(PROP_KEY_DOCS_DIRS, sb.toString());
            properties.store(out, "PROPERTIES FILE");
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, "Could not create properties file", ex);
        }
    }

    public String getIndexPath() {
        return indexPath;
    }

    public List<String> getDocsDirList() {
        return docsDirList;
    }
}
