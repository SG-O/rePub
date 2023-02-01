package de.sg_o.lib.rePub.nav;

import de.sg_o.lib.rePub.opfPack.OpfPackage;
import de.sg_o.lib.rePub.xhtml.XhtmlFile;
import org.jdom2.JDOMException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class NavFile extends XhtmlFile implements Nav {
    String title;
    protected LinkedList<NavEntry> navMap = new LinkedList<>();

    public NavFile(Path location, OpfPackage pack) throws IOException, JDOMException {
        super(location, pack);
        super.open();
        Document rootDocument = super.getDocument();
        if (rootDocument == null) throw new IOException("Can't parse NavFile, rootDocument null");
        Element title = rootDocument.selectFirst("title");
        if (title != null) {
            this.title = title.text();
        }
        Element navElement = rootDocument.selectFirst("nav");
        if (navElement == null) throw new IOException("Can't parse NavFile, no nav element found");
        Elements navChildren = navElement.children();
        Element ol = null;
        for (Element element : navChildren) {
            if (element.tag().equals(Tag.valueOf("ol"))) {
                ol = element;
            }
        }
        if (ol == null) throw new IOException("Can't parse NavFile, no ol element found");
        Elements olChildren = ol.children();
        for (Element element : olChildren) {
            if (element.tag().equals(Tag.valueOf("li"))) {
                navMap.add(new NavListItem(element, pack, this));
            }
        }
    }

    @Override
    public String getTitle() {
        if (title == null) {
            String[] titles = super.pack.getMetadata().getTitles();
            if (titles.length > 0) return titles[0];
        }
        return title;
    }

    @Override
    public List<NavEntry> getNavEntries() {
        return navMap;
    }

    @Override
    public String toString() {
        return "NavFile{" +
                "title='" + title + '\'' +
                ", navMap=" + navMap +
                '}';
    }
}
