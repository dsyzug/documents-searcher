package test;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class SearchTableTest extends JFrame {

    private final JTable jTable;
    private final SearchTableModel searchTableModel;
    
    private final JTextField searchField;
    
    private static final int LAYOUT_WIDTH = 1600;
    private static final int LAYOUT_HEIGHT = 400;

    public static void main(String[] args) throws Exception {
        SearchTableTest stt = new SearchTableTest();
        stt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stt.setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
        //stt.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        stt.setVisible(true);
    }

    public SearchTableTest() throws Exception {
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        searchField = new JTextField();
        setSearchFieldProperties();
        
        searchTableModel = new SearchTableModel();
        jTable = new JTable(searchTableModel);
        setJTableProperties();
        
        pane.add(searchField, BorderLayout.NORTH);
        pane.add(new JScrollPane(jTable),BorderLayout.CENTER);
    }
    
    private void doSearch() throws Exception {
        searchTableModel.updateDocsList(searchField.getText());
        searchTableModel.fireTableDataChanged();
    } 

    private void setSearchFieldProperties() {
        searchField.setToolTipText("Search Anything");
        searchField.setMargin(new Insets(5, 5, 5, 5));
        //searchField.setHorizontalAlignment(JTextField.LEFT);
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
    }

    private void setJTableProperties() {
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    
                    System.out.println("val" +table.getValueAt(row, 1));
                    try {
                        Desktop.getDesktop().open(new File(table.getValueAt(row, 1).toString()         ));
                    } catch (IOException ex) {
                        Logger.getLogger(SearchTableTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        jTable.getColumnModel().getColumn(0).setPreferredWidth((int) (LAYOUT_WIDTH * 0.3));
        jTable.getColumnModel().getColumn(1).setPreferredWidth((int) (LAYOUT_WIDTH * 0.6));
    }

}
