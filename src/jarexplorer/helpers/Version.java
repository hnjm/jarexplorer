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
 * Version.java
 *
 * Created on June 29, 2007, 12:19 PM
 *
 */

package jarexplorer.helpers;

import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;

/**
 *
 * @author Marco
 */
public class Version {

    private static String address = "http://jarexplorer.sourceforge.net/version.xml";
    
    private int buildNumber;
    private String versionNumber;
    
    private static Version version;

    private Version() throws Exception {
        parseXml();
    }
    
    public static Version getVersionWithDelay() throws Exception {
        if (version == null) {
            //delay
            Thread.sleep(1000*5);
            version = new Version();
        }
        return version;
    }

    public static Version getVersion() throws Exception {
        if (version == null) {
            version = new Version();
        }
        return version;
    }
    
    public boolean isUptodate() {
        return jarexplorer.Main.BUILD >= buildNumber;
    }
    
    public String getVersionNumber() {
        return this.versionNumber;
    }

    private void parseXml() throws Exception {
        DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        URL jarExplorerXml = new java.net.URL(address);
        Document doc = builder.parse(jarExplorerXml.toURI().toString());

        XPathFactory xpfactory = XPathFactory.newInstance();
        XPath path = xpfactory.newXPath();

        versionNumber = path.evaluate("/jarexplorer/current/version", doc);        
        buildNumber   = Integer.parseInt(path.evaluate("/jarexplorer/current/build", doc));
    }
}