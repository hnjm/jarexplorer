/*
 * TreeEventListener.java
 *
 * Created on 3 ottobre 2006, 15.58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jarexplorer.gui.tree;

import java.awt.Container;
import java.awt.Cursor;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;

/**
 *
 * @author mmilon
 */
public class TreeEventListener implements TreeExpansionListener, TreeWillExpandListener {

    public Container ui;

    /** Creates a new instance of TreeEventListener */
    public TreeEventListener(Container ui) {
        this.ui = ui;
    }

    @Override
    public void treeExpanded(TreeExpansionEvent event) {
        //System.out.println("treeExpanded");
        ui.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void treeCollapsed(TreeExpansionEvent event) {
        //System.out.println("treeCollapsed");
        ui.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
        //System.out.println("treeWillExpand");
        ui.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
        //System.out.println("treeWillExpand");
        ui.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    }
}
