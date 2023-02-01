package de.sg_o.lib.rePub.opfPack;

import de.sg_o.lib.rePub.container.MediaType;
import de.sg_o.lib.rePub.nav.NavFile;
import de.sg_o.lib.rePub.container.OcfFile;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manifest {
    private static final Namespace namespace = Namespace.getNamespace("http://www.idpf.org/2007/opf");
    private final HashMap<String, Item> idMap = new HashMap<>();
    private final HashMap<Path, Item> pathMap = new HashMap<>();
    private OcfFile nav = null;

    public Manifest(Element manifest, OpfPackage pack) throws IOException, JDOMException {
        if (pack == null) throw new IOException("Can't parse Manifest, package null");
        List<Element> items = manifest.getChildren("item", namespace);
        if (items.size() < 1) throw new IOException("Invalid Manifest, no items");
        for (Element item : items) {
            Item parsed = new Item(item, pack);
            if (parsed.properties.contains(ItemProperty.NAV)) {
                if (nav == null) {
                    nav = parsed.getFile();
                }
            }
            this.idMap.put(parsed.getId(), parsed);
            this.pathMap.put(parsed.getFile().getLocation(), parsed);
        }
    }

    public List<Item> getIdMap() {
        return new ArrayList<>(idMap.values());
    }

    public Item getFromId(String id) {
        if (id == null) return null;
        return idMap.get(id);
    }

    public Item getFromPath(Path path) {
        if (path == null) return null;
        return pathMap.get(path);
    }

    public OcfFile getNav() {
        return nav;
    }

    @Override
    public String toString() {
        return "Manifest{" +
                ", items=" + idMap.values() +
                '}';
    }

    public static class Item {
        private final String id;
        private final String href;
        private final OcfFile file;
        private final MediaType mediaType;
        private String fallbackId = null;
        private String mediaOverlayId = null;
        private final List<ItemProperty> properties = new ArrayList<>();


        public Item(Element item, OpfPackage pack) throws IOException, JDOMException {
            if (pack == null) throw new IOException("Can't parse Item, package null");
            Attribute id = item.getAttribute("id");
            if (id == null) throw new IOException("Invalid Manifest, item without id");
            if (id.getValue() == null) throw new IOException("Invalid Manifest, item with empty id");
            Attribute href = item.getAttribute("href");
            if (href == null) throw new IOException("Invalid Manifest, item without href");
            if (href.getValue() == null) throw new IOException("Invalid Manifest, item with empty href");
            Attribute mediaType = item.getAttribute("media-type");
            if (mediaType == null) throw new IOException("Invalid Manifest, item without media-type");
            if (mediaType.getValue() == null) throw new IOException("Invalid Manifest, item with empty media-type");
            this.id = id.getValue();
            this.href = href.getValue();
            this.mediaType = MediaType.fromMime(mediaType.getValue());

            Attribute fallbackId = item.getAttribute("fallback");
            if (fallbackId != null) {
                if (fallbackId.getValue() != null) {
                    this.fallbackId = fallbackId.getValue();
                }
            }

            Attribute mediaOverlayId = item.getAttribute("media-overlay");
            if (mediaOverlayId != null) {
                if (mediaOverlayId.getValue() != null) {
                    this.mediaOverlayId = mediaOverlayId.getValue();
                }
            }

            Attribute properties = item.getAttribute("properties");
            if (properties != null) {
                if (properties.getValue() != null) {
                    String[] propertiesSplit = properties.getValue().trim().split(" ");
                    for (String property : propertiesSplit) {
                        this.properties.add(ItemProperty.fromProperty(property.trim()));
                    }
                }
            }

            if (this.properties.contains(ItemProperty.NAV)) {
                this.file = new NavFile(pack.getRelativePath(this.href), pack);
            } else {
                this.file = pack.getRelative(this.href, this.mediaType);
                if (this.file == null) throw new IOException("Invalid Manifest, file null");
            }
        }

        public String getId() {
            return id;
        }

        public String getHref() {
            return href;
        }

        public MediaType getMediaType() {
            return mediaType;
        }

        public String getFallbackId() {
            return fallbackId;
        }

        public String getMediaOverlayId() {
            return mediaOverlayId;
        }

        public OcfFile getFile() throws IOException, JDOMException {
            return file;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "id='" + id + '\'' +
                    ", href='" + href + '\'' +
                    ", mediaType='" + mediaType + '\'' +
                    ", fallbackId='" + fallbackId + '\'' +
                    ", mediaOverlayId='" + mediaOverlayId + '\'' +
                    ", properties=" + properties +
                    '}';
        }
    }

    public enum ItemProperty {
        COVER_IMAGE("cover-image"),
        MATHML("mathml"),
        NAV("nav"),
        REMOTE_RESOURCES("remote-resources"),
        SCRIPTED("scripted"),
        SVG("svg"),
        UNKNOWN("unknown");

        String property;

        private static final Map<String, ItemProperty> properties = new HashMap<>();

        static {
            for (ItemProperty r : values()) {
                properties.put(r.property, r);
            }
        }

        ItemProperty(String property) {
            this.property = property;
        }

        public String getProperty() {
            return property;
        }

        public static ItemProperty fromProperty(String property) {
            if (property == null) return UNKNOWN;
            ItemProperty ret = properties.get(property);
            if (ret == null) return UNKNOWN;
            return ret;
        }
    }
}
