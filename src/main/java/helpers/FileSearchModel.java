package helpers;

import helpers.SearchDoc;
import helpers.FilesSearcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.apache.lucene.queryparser.classic.ParseException;

public class FileSearchModel extends AbstractTableModel {
    
    private List<SearchDoc> searchDocs;
    private final FilesSearcher filesSearcher;
    
    public FileSearchModel(){
        searchDocs = new ArrayList<>();
        filesSearcher = new FilesSearcher();
    }
    
    public void updateDocsList(String query) throws ParseException, IOException{
        if(query.trim().equals("")){
            return;
        }
        searchDocs = filesSearcher.queryIndex(query);
    }

    @Override
    public int getRowCount() {
        return searchDocs.size();
    }

    @Override
    public int getColumnCount() {
        return SearchDoc.NUM_FIELDS;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return searchDocs.get(row).getFileData(column);
    }

    @Override
    public String getColumnName(int column) {
        return SearchDoc.COLUMN_NAMES[column];
    }

}
