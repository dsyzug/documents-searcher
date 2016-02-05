package test;

import helpers.SearchDoc;
import helpers.SearchFiles;
import java.io.IOException;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import org.apache.lucene.queryparser.classic.ParseException;

public class SearchTableModel extends AbstractTableModel {

    public final static int FILE_NAME = 0;
    public final static int FILE_PATH = 1;
    
    List<SearchDoc> searchDocs;

    public final static String[] COLUMN_NAMES = {"File Name", "File Path"};

    public Object[][] values = {
        {"Clay", "Ashworth"},
        {"Jacob", "Ashworth"},
        {"Jordan", "Ashworth"},
        {"Evelyn", "Kirk"},
        {"Belle", "Spyres"}
    };
    
    public SearchTableModel(String query) throws Exception {
        searchDocs = SearchFiles.queryIndex(query);
    }
    
    public void updateDocsList(String query) throws ParseException, IOException{
        searchDocs = SearchFiles.queryIndex(query);
        System.out.println(searchDocs);
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
        return COLUMN_NAMES[column];
    }

}
