package app;

import helpers.FileSearchModel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
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

public class Application extends JFrame {

    private final JTable jTable;
    private final FileSearchModel searchTableModel;
    private final JTextField searchField;

    public static void main(String[] args) throws Exception {
        Application app = new Application();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //app.setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
        app.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //app.setSize(1200, 600);
        app.setVisible(true);
    }

    /**
     * Initialize UI Components
     * @throws Exception 
     */
    public Application() throws Exception {
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        searchField = new JTextField();
        setSearchFieldProperties();
        pane.add(searchField, BorderLayout.NORTH);

        searchTableModel = new FileSearchModel();
        if(searchTableModel == null){
            System.err.println("searchTableModel is null");
        }
        jTable = new JTable(searchTableModel);
        setJTableProperties();
        pane.add(new JScrollPane(jTable), BorderLayout.CENTER);
    }

    /**
     * add key listener on search text-box and trigger search on key release
     */
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
                    Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     * Add double click listener to open file with default system application
     */
    private void setJTableProperties() {
        // set column widths
        Dimension dim = getMaximumSize();
        jTable.getColumnModel().getColumn(0).setPreferredWidth((int) (dim.width * 0.3));
        jTable.getColumnModel().getColumn(1).setPreferredWidth((int) (dim.width * 0.6));
        jTable.getColumnModel().getColumn(2).setPreferredWidth((int) (dim.width * 0.1));

        // detect double click on table row and open file identified by file path
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    try {
                        Desktop.getDesktop().open(new File(table.getValueAt(row, 1).toString()));
                    } catch (IOException ex) {
                        Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    // Read text from search box and update table model.
    private void doSearch() throws Exception {
        searchTableModel.updateDocsList(searchField.getText());
        searchTableModel.fireTableDataChanged();
    }

}
