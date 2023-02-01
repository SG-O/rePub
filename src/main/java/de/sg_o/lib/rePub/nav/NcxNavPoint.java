package de.sg_o.lib.rePub.nav;

import de.sg_o.lib.rePub.container.OcfFile;
import de.sg_o.lib.rePub.opfPack.Manifest;
import de.sg_o.lib.rePub.opfPack.OpfPackage;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;


public class NcxNavPoint extends NavEntry{
    private final OcfFile ncx;

    public NcxNavPoint(Element navPoint, OpfPackage pack, NcxFile ncx) throws IOException, JDOMException {
        super(pack);
        if (ncx == null) throw new IOException("Can't parse NcxNavPoint, ncx null");
        this.ncx = ncx;
        if (navPoint == null) throw new IOException("Can't parse NcxNavPoint, navPoint null");
        Element navLabel = navPoint.getChild("navLabel", NcxFile.NAMESPACE);
        if (navLabel == null) throw new IOException("Can't parse NcxNavPoint, navLabel null");
        super.label = NcxFile.parseText(navLabel);
        Element content = navPoint.getChild("content", NcxFile.NAMESPACE);
        if (content == null) throw new IOException("Can't parse NcxNavPoint, content null");
        Attribute src = content.getAttribute("src");
        parseSource(src);
        List<Element> subNavPoints = navPoint.getChildren("navPoint", NcxFile.NAMESPACE);
        for (Element e : subNavPoints) {
            if (super.subEntries == null) super.subEntries = new LinkedList<>();
            super.subEntries.add(new NcxNavPoint(e, pack, ncx));
        }
    }

    private void parseSource(Attribute src) throws IOException, JDOMException {
        if (src == null) throw new IOException("Can't parse NcxNavPoint, source null");
        String srcString = src.getValue();
        if (srcString == null) throw new IOException("Can't parse NcxNavPoint, source string null");
        String[] srcSplit = srcString.split("#");
        if ((srcSplit.length < 1) || (srcSplit.length > 2)) throw new IOException("Can't parse NcxNavPoint, can't parse source");
        Path absoluteSrc = ncx.getParentFolder().resolve(srcSplit[0]);
        super.absoluteFilePath = absoluteSrc.toAbsolutePath();
    }
}
