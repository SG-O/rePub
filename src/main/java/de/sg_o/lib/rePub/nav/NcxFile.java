package de.sg_o.lib.rePub.nav;

import de.sg_o.lib.rePub.container.XmlFile;
import de.sg_o.lib.rePub.opfPack.OpfPackage;
import org.jdom2.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class NcxFile extends XmlFile implements Nav{
    public static final Namespace NAMESPACE = Namespace.getNamespace("http://www.daisy.org/z3986/2005/ncx/");

    private String uid;
    private int depth;
    private int totalPageCount;
    private int maxPageNumber;

    private String title;
    private String author;
    protected LinkedList<NavEntry> navMap = new LinkedList<>();
    private final Element pageList;
    private final Element navList;

    public NcxFile(Path location, OpfPackage pack) throws IOException, JDOMException {
        super(location, pack);
        Element ncxRoot = parseXml();
        Element head = ncxRoot.getChild("head", NAMESPACE);
        Element title = ncxRoot.getChild("docTitle", NAMESPACE);
        Element author = ncxRoot.getChild("docAuthor", NAMESPACE);
        Element navMap = ncxRoot.getChild("navMap", NAMESPACE);
        Element pageList = ncxRoot.getChild("pageList", NAMESPACE);
        Element navList = ncxRoot.getChild("navList", NAMESPACE);

        if (head == null) throw new IOException("Can't parse NCX file, head null");
        if (title == null) throw new IOException("Can't parse NCX file, docTitle null");
        if (navMap == null) throw new IOException("Can't parse NCX file, navMap null");

        parseHead(head);
        parseDocTitle(title);
        parseNavMap(navMap);

        if (author != null) {
            parseDocAuthor(author);
        }

        this.pageList = pageList;
        this.navList = navList;

    }

    private void parseHead(Element head) {
        List<Element> metas = head.getChildren("meta", NAMESPACE);
        for (Element meta : metas) {
            Attribute name = meta.getAttribute("name", null);
            Attribute content = meta.getAttribute("content", null);
            if ((name != null) && (content != null)) {
                String value = name.getValue();
                try {
                    switch (value) {
                        case "dtb:uid":
                            this.uid = content.getValue();
                            break;
                        case "dtb:depth":
                            this.depth = content.getIntValue();
                            break;
                        case "dtb:totalPageCount":
                            this.totalPageCount = content.getIntValue();
                            break;
                        case "dtb:maxPageNumber":
                            this.maxPageNumber = content.getIntValue();
                            break;
                    }
                } catch (DataConversionException ignore) {
                }
            }
        }
    }

    private void parseDocTitle(Element title) {
        this.title = parseText(title);
    }

    private void parseDocAuthor(Element author) {
        this.author = parseText(author);
    }

    private void parseNavMap(Element navMap) throws IOException, JDOMException {
        if (navMap == null) throw new IOException("Can't parse NCX file, navMap null");
        List<Element> navPoints = navMap.getChildren("navPoint", NAMESPACE);
        if (navPoints == null) throw new IOException("Can't parse NCX file, navLabels null");
        if (navPoints.size() < 1) throw new IOException("Can't parse NCX file, navLabels empty");
        for (Element navPoint : navPoints) {
            this.navMap.add(new NcxNavPoint(navPoint, pack, this));
        }
    }

    public String getUid() {
        return uid;
    }

    public int getDepth() {
        return depth;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public int getMaxPageNumber() {
        return maxPageNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public LinkedList<NavEntry> getNavMap() {
        return navMap;
    }

    public Element getPageList() {
        return pageList;
    }

    public Element getNavList() {
        return navList;
    }

    @Override
    public List<NavEntry> getNavEntries() {
        return this.navMap;
    }

    @Override
    public String toString() {
        return "NcxFile{" +
                "uid='" + uid + '\'' +
                ", depth=" + depth +
                ", totalPageCount=" + totalPageCount +
                ", maxPageNumber=" + maxPageNumber +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", navMap=" + navMap +
                '}';
    }

    public static String parseText(Element containsText) {
        if (containsText == null) return null;
        Element text = containsText.getChild("text", NAMESPACE);
        if (text == null) return null;
        return text.getValue();
    }
}
