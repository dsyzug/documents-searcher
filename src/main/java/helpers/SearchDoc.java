package helpers;

public class SearchDoc {
    
    public static final String FIELD_STR_FILE_NAME = "filename";
    public static final String FIELD_STR_FILE_PATH = "filepath";
    public static final String FIELD_LONG_FILE_MODIFIED = "modified";
    
    public static final int NUM_FIELDS = 3;
    public static final String[] COLUMN_NAMES = {"File Name", "File Path", "Modified"};
    
    
    private final String fileName;
    private final String filePath;
    private final Long fileModified;

    public SearchDoc(String fileName, String filePath, Long fileModified) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileModified = fileModified;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public Long getFileModified() {
        return fileModified;
    }
    
    public Object getFileData(int column){
        if(column == 0){
            return getFileName();
        } else if(column == 1){
            return getFilePath();
        } else if (column == 2){
            return getFileModified();
        }
        return null;
    }
    

    @Override
    public String toString() {
        return "SearchDoc {" + "fileName=" + fileName + ", filePath=" + filePath + '}';
    }
}
