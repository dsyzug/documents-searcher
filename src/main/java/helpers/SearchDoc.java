package helpers;

public class SearchDoc {
    
    private final String fileName;
    private final String filePath;
    
    public static final int NUM_FIELDS = 2;

    public SearchDoc(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return "SearchDoc {" + "fileName=" + fileName + ", filePath=" + filePath + '}';
    }
}
