package test;

import helpers.SearchDoc;
import helpers.FilesSearcher;
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
        searchDocs = FilesSearcher.queryIndex(query);
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
