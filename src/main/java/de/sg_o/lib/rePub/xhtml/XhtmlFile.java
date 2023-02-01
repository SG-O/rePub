package de.sg_o.lib.rePub.xhtml;

import de.sg_o.lib.rePub.container.XmlFile;
import de.sg_o.lib.rePub.opfPack.OpfPackage;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.READ;

public class XhtmlFile extends XmlFile {
    private Document document = null;
    public XhtmlFile(Path location, OpfPackage pack) throws IOException, JDOMException {
        super(location, pack);
    }

    public void open() throws IOException {
        InputStream inputStream = Files.newInputStream(getLocation(), READ);
        this.document = Jsoup.parse(inputStream, "UTF-8", super.getLocation().toString());
    }

    public Document getDocument() throws IOException {
        if (document == null) open();
        return document;
    }

    @Override
    public String toString() {
        return "XhtmlFile{" +
                "location = '" + getLocation() + '\'' +
                ", fileSystem='" + getFileSystem().toString() + '\'' +
                ", document=" + document +
                '}';
    }
}
