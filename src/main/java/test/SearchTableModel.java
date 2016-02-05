package test;

import helpers.SearchDoc;
import helpers.SearchFiles;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.apache.lucene.queryparser.classic.ParseException;

public class SearchTableModel extends AbstractTableModel {
    
    List<SearchDoc> searchDocs;
    
    public SearchTableModel(){
        searchDocs = new ArrayList<>();
    }
    
    public void updateDocsList(String query) throws ParseException, IOException{
        if(query.trim().equals("")){
            return;
        }
        searchDocs = SearchFiles.queryIndex(query);
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
        if(column == 0 ){
            return searchDocs.get(row).getFileName();
        } 
        return searchDocs.get(row).getFilePath();
    }

    @Override
    public String getColumnName(int column) {
        return SearchDoc.COLUMN_NAMES[column];
    }

}
