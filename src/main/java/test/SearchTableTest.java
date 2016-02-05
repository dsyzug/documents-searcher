package test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class SearchTableTest extends JFrame {

    private final JTable jTable;
    private final SearchTableModel searchTableModel;
    
    private final javax.swing.JTextField searchField;

    public static void main(String[] args) throws Exception {
        SearchTableTest stt = new SearchTableTest();
        stt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stt.setSize(1000, 600);
        stt.setVisible(true);
    }

    public SearchTableTest() throws Exception {
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        
        searchField = new javax.swing.JTextField();
        searchField.setToolTipText("Search Anything");
        searchField.setHorizontalAlignment(JTextField.LEFT);
        searchField.addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    doSearch();
                } catch (Exception ex) {
                    Logger.getLogger(SearchTableTest.class.getName()).log(Level.SEVERE, null, ex);
                }     
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }
        });
        
        pane.add(searchField, BorderLayout.NORTH);
        
        searchTableModel = new SearchTableModel();
        jTable = new JTable(searchTableModel);
        JScrollPane jsp = new JScrollPane(jTable);
        pane.add(jsp,BorderLayout.SOUTH);
    }
    
    private void doSearch() throws Exception {
        searchTableModel.updateDocsList(searchField.getText());
        searchTableModel.fireTableDataChanged();
    } 

}
