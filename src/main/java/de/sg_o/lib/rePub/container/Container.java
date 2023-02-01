package de.sg_o.lib.rePub.container;

import de.sg_o.lib.rePub.opfPack.OpfPackage;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Container extends XmlFile {
    private static final Namespace namespace = Namespace.getNamespace("urn:oasis:names:tc:opendocument:xmlns:container");
    private ArrayList<OpfPackage> renditions = new ArrayList<>();
    private ArrayList<Link> links = new ArrayList<>();

    public Container(Path location) throws IOException, JDOMException {
        super(location, null);
        Element containerRoot = parseXml();
        Element rootFiles = containerRoot.getChild("rootfiles", namespace);
        if (rootFiles == null) throw new IOException("Invalid container");
        List<Element> rootFile = rootFiles.getChildren("rootfile", namespace);

        for (Element file : rootFile) {
            Attribute fullPath = file.getAttribute("full-path");
            Attribute mediaType = file.getAttribute("media-type");
            if (fullPath == null) continue;
            if (mediaType == null) continue;
            if (!mediaType.getValue().equals("application/oebps-package+xml")) continue;
            renditions.add((OpfPackage) getAbsolute(fullPath.getValue(), MediaType.OPF));
        }
        if (renditions.size() < 1) throw new IOException("Invalid container, no renditions");

        Element rawLinks = containerRoot.getChild("links", namespace);
        if (rawLinks != null) {
            List<Element> linkList = rawLinks.getChildren("link", namespace);
            for (Element link : linkList) {
                Attribute href = link.getAttribute("href");
                Attribute rel = link.getAttribute("rel");
                Attribute mediaType = link.getAttribute("media-type");
                if (href == null) continue;
                if (rel == null) continue;
                if (mediaType == null) continue;
                links.add(new Link(href.getValue(), rel.getValue(), mediaType.getValue()));
            }
        }
    }

    public ArrayList<OpfPackage> getRenditions() {
        return renditions;
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    @Override
    public String toString() {
        return "Container{" +
                "renditions=" + renditions +
                ", links=" + links +
                '}';
    }

    public class Link {
        String href;
        String rel;
        String mediaType;

        public Link(String href, String rel, String mediaType) {
            this.href = href;
            this.rel = rel;
            this.mediaType = mediaType;
        }

        @Override
        public String toString() {
            return "Link{" +
                    "location=" + getLocation() +
                    ", fileSystem='" + getFileSystem().toString() + '\'' +
                    ", href='" + href + '\'' +
                    ", rel='" + rel + '\'' +
                    ", mediaType='" + mediaType + '\'' +
                    '}';
        }
    }

}
