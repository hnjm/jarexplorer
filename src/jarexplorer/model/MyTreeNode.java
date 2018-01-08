/*
 * MyTreeNode.java
 *
 * Created on October 31, 2006, 8:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jarexplorer.model;

/**
 *
 * @author ap
 */
public interface MyTreeNode {
    public Object getChild(Object parent, int index);
    public int getChildCount(Object parent);
    public boolean isLeaf(Object node);
    public int getIndexOfChild(Object parent, Object child);
    public String getType();
}
