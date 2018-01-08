/*
 * NewClass2.java
 *
 * Created on March 2, 2007, 12:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jarexplorer.gui.decompiler;

/**
 *
 * @author fast_
 * @description http://forum.java.sun.com/thread.jspa?forumID=57&threadID=337070 
 */
import java.util.EventListener;
 
/**
 * The listener that's notified when an tab should be closed in the
 * <code>CloseableTabbedPane</code>.
 */
public interface CloseableTabbedPaneListener extends EventListener {
  /**
   * Informs all <code>CloseableTabbedPaneListener</code>s when a tab should be
   * closed
   * @param tabIndexToClose the index of the tab which should be closed
   * @return true if the tab can be closed, false otherwise
   */
  boolean closeTab(int tabIndexToClose);
}
