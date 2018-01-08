/*
 * Jode.java
 *
 * Created on February 23, 2007, 3:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jarexplorer.gui.decompiler;

import jarexplorer.model.Utilities;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import jode.decompiler.Decompiler;
import jode.decompiler.ProgressListener;

/**
 *
 * @author Marco
 */
class Jode {

    private String className;
    private ProgressListener p;

    private String sourceCode;
    private String log;

    /** Creates a new instance of Jode */
    Jode(String path, ProgressListener p) {
        this.className = Utilities.formatImportPathDir(path);
        this.p = p;
    }

    public String decompile() {
        try {
            StringWriter strWriter = new StringWriter();

            StringWriter logWriter = new StringWriter();
            PrintWriter logPrinter = new PrintWriter(logWriter);

            Decompiler decompiler = new Decompiler();
            decompiler.setErr(logPrinter);
            decompiler.setOption("style", "sun");
            decompiler.setClassPath(Utilities.getJarPath());
            decompiler.decompile(className, strWriter, p);

            sourceCode = strWriter.toString();
            log = logWriter.toString();
            logPrinter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return sourceCode;
    }

    public String getCode() {
        return sourceCode;
    }

    String getLog() {
        return log;
    }
}