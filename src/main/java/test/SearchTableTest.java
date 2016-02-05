package test;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class SearchTableTest extends JFrame {

    private JTable table;
    private SearchTableModel searchTableModel;
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;

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
        
        searchButton = new javax.swing.JButton();
        searchButton.setText("Search");
        searchButton.setHorizontalAlignment(SwingConstants.RIGHT);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    doSearch(evt);
                } catch (Exception ex) {
                    Logger.getLogger(SearchTableTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        pane.add(searchField, BorderLayout.NORTH);
        pane.add(searchButton, BorderLayout.EAST);
        
        searchTableModel = new SearchTableModel("*:*");
        table = new JTable(searchTableModel);
        // pane.add(table, BorderLayout.CENTER);
        JScrollPane jsp = new JScrollPane(table);
        pane.add(jsp,BorderLayout.SOUTH);
    }
    
    private void doSearch(java.awt.event.ActionEvent evt) throws Exception {
        searchTableModel.updateDocsList(searchField.getText());
        searchTableModel.fireTableDataChanged();
    } 

}
