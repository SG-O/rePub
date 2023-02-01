package de.sg_o.lib.rePub.container;

import de.sg_o.lib.rePub.opfPack.OpfPackage;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.READ;

public class XmlFile extends OcfFile{
    public XmlFile(Path location, OpfPackage pack) throws IOException {
        super(location, pack);
    }

    public Element parseXml() throws IOException, JDOMException {
        SAXBuilder sax = new SAXBuilder();
        sax.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                return new InputSource(new StringReader(""));
            }
        });
        InputStream inputStream = Files.newInputStream(getLocation(), READ);
        Document doc = sax.build(inputStream);
        return doc.getRootElement();
    }

    @Override
    public String toString() {
        return "XmlFile{" +
                "location=" + getLocation() +
                ", fileSystem='" + getFileSystem().toString() + '\'' +
                '}';
    }
}
