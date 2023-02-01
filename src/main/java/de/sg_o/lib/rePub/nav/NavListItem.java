package de.sg_o.lib.rePub.nav;

import de.sg_o.lib.rePub.container.OcfFile;
import de.sg_o.lib.rePub.opfPack.OpfPackage;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;

public class NavListItem extends NavEntry{
    private final NavFile navFile;

    public NavListItem(Element navItem, OpfPackage pack, NavFile navFile) throws IOException {
        super(pack);
        if (navFile == null) throw new IOException("Can't parse NavListItem, navFile null");
        this.navFile = navFile;
        if (!navItem.tag().equals(Tag.valueOf("li"))) throw new IOException("Can't parse NavListItem, navItem has wrong tag");
        Elements elements = navItem.children();
        for (Element element : elements) {
            parseElement(element);
        }
    }

    private void parseElement (Element element) throws IOException {
        Tag tag = element.tag();
        if (tag.equals(Tag.valueOf("a"))) {
            super.label = element.text();
            String href = element.attr("href");
            if (href.length() > 0) {
                String[] hrefSplit = href.split("#");
                if ((hrefSplit.length < 1) || (hrefSplit.length > 2))
                    throw new IOException("Can't parse NavListItem, can't parse href");
                Path absoluteSrc = navFile.getParentFolder().resolve(hrefSplit[0]);
                super.absoluteFilePath = absoluteSrc.toAbsolutePath();
            }
        } else if (tag.equals(Tag.valueOf("span"))) {
            super.label = element.text();
        } else if (tag.equals(Tag.valueOf("ol"))) {
            Elements subNavItems = element.children();
            for (Element e : subNavItems) {
                if (super.subEntries == null) super.subEntries = new LinkedList<>();
                super.subEntries.add(new NavListItem(e, super.getPack(), navFile));
            }
        }
    }
}
