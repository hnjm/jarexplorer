/*
 * Copyright (C) 2006  Marco Milon
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * DropJarListener.java
 *
 * Created on November 3, 2006, 10:02 PM
 */

package jarexplorer.gui.tree;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author ap
 */
public class DropJarListener implements DropTargetListener {

    GuiManager uiManager;


    public DropJarListener(GuiManager uiManager) {
        this.uiManager = uiManager;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        if (!isDragAcceptable(dtde)) {
            dtde.rejectDrag();
            return;
        }
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        if (!isDragAcceptable(dtde)) {
            dtde.rejectDrag();
            return;
        }
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
    }

    /**
     * @todo fix unchecked warning
     */
    @SuppressWarnings(value = "unchecked")
    @Override
    public void drop(DropTargetDropEvent dtde) {
        dtde.acceptDrop(DnDConstants.ACTION_COPY);

        Transferable transferable = dtde.getTransferable();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        try {
            for (int i = 0; i < flavors.length; i++) {
                DataFlavor d = flavors[i];
                if (d.equals(DataFlavor.javaFileListFlavor)) {
                    java.util.List<File> fileList = (java.util.List<File>) transferable.getTransferData(d);
                    String path = fileList.get(0).getAbsolutePath();
                    if (path.toLowerCase().trim().endsWith(".jar")) {
                        uiManager.jarDropped(path);
                        dtde.dropComplete(true);
                    } else {
                        dtde.dropComplete(false);
                    }
                } else if (d.equals(DataFlavor.stringFlavor)) {
                    String url = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                    if (url.toLowerCase().trim().endsWith(".jar")) {
                        uiManager.jarDropped(new URL(url).getPath());
                        dtde.dropComplete(true);
                    } else {
                        dtde.dropComplete(false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @todo fix unchecked warning
     */
    @SuppressWarnings(value = "unchecked")
    private boolean isDragAcceptable(DropTargetDragEvent dtde) {
        boolean isCopyOrMove = (dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
        Transferable transferable = dtde.getTransferable();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        try {
            for (int i = 0; i < flavors.length; i++) {
                DataFlavor d = flavors[i];

                if (d.equals(DataFlavor.javaFileListFlavor)) {
                    java.util.List<File> fileList = (java.util.List<File>) transferable.getTransferData(d);
                    String path = fileList.get(0).getAbsolutePath();
                    return path.toLowerCase().trim().endsWith(".jar") && isCopyOrMove;
                } else if (d.equals(DataFlavor.stringFlavor)) {
                    String path = (String) transferable.getTransferData(d);
                    return path.toLowerCase().trim().endsWith(".jar") && isCopyOrMove;
                }
            }
        } catch (UnsupportedFlavorException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    private boolean validateDrop(String path, boolean dndType) {
        return path.toLowerCase().trim().endsWith(".jar") && dndType;
    }
}
