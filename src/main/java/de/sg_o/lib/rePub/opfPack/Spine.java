package de.sg_o.lib.rePub.opfPack;

import de.sg_o.lib.rePub.nav.NcxFile;
import de.sg_o.lib.rePub.container.OcfFile;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;

import java.io.IOException;
import java.util.*;

public class Spine {
    private static final Namespace namespace = Namespace.getNamespace("http://www.idpf.org/2007/opf");

    private ReadingDirection pageProgressionDirection = ReadingDirection.DEFAULT;
    private OcfFile toc = null;
    private final LinkedList<ItemRef> itemRefs = new LinkedList<>();

    public Spine(Element spine, OpfPackage pack) throws IOException, JDOMException {
        if (spine == null) throw new IOException("Can't parse Spine, null");
        if (pack == null) throw new IOException("Can't parse Spine, package null");
        Attribute pageProgressionDirection = spine.getAttribute("page-progression-direction");
        if (pageProgressionDirection != null) {
            if (pageProgressionDirection.getValue() != null) {
                this.pageProgressionDirection = ReadingDirection.fromCode(pageProgressionDirection.getValue());
            }
        }

        Attribute toc = spine.getAttribute("toc");
        if (toc != null) {
            if (toc.getValue() != null) {
                Manifest.Item tocItem = pack.getManifest().getFromId(toc.getValue());
                if (tocItem != null) {
                    this.toc = tocItem.getFile();
                    if (this.toc == null) throw new IOException("Can't parse Spine, toc not found");
                    if (!this.toc.getClass().equals(NcxFile.class)) throw new IOException("Can't parse Spine, toc of wrong mime");
                }
            }
        }

        List<Element> items = spine.getChildren("itemref", namespace);
        if (items.size() < 1) throw new IOException("Invalid Spine, no itemRefs");
        for (Element item : items) {
            ItemRef parsed = new ItemRef(item, pack);
            this.itemRefs.add(parsed);
        }
    }

    public ReadingDirection getPageProgressionDirection() {
        return pageProgressionDirection;
    }

    public OcfFile getToc() {
        return toc;
    }

    public LinkedList<ItemRef> getAllItemRefs() {
        return itemRefs;
    }

    public LinkedList<ItemRef> getLinearItemRefs() {
        LinkedList<ItemRef> linear = new LinkedList<>();
        for (ItemRef ref : itemRefs) {
            if (ref.isLinear()) linear.add(ref);
        }
        return linear;
    }

    @Override
    public String toString() {
        return "Spine{" +
                "pageProgressionDirection=" + pageProgressionDirection +
                ", toc=" + toc +
                ", itemRefs=" + itemRefs +
                '}';
    }

    public class ItemRef {
        private String id = null;
        private final Manifest.Item item;
        private boolean linear = true;
        private final List<ItemRefProperty> properties = new ArrayList<>();

        private final OpfPackage pack;

        public ItemRef(Element item, OpfPackage pack) throws IOException {
            if (pack == null) throw new IOException("Can't parse ItemRef, package null");
            this.pack = pack;
            Attribute idRef = item.getAttribute("idref");
            if (idRef == null) throw new IOException("Invalid Spine, item without idRef");
            if (idRef.getValue() == null) throw new IOException("Invalid Spine, item with empty idRef");
            this.item = pack.getManifest().getFromId(idRef.getValue());
            if (this.item == null) throw new IOException("Invalid Spine, item not found");

            Attribute id = item.getAttribute("id");
            if (id != null) {
                if (id.getValue() != null) {
                    this.id = id.getValue();
                }
            }

            Attribute linear = item.getAttribute("linear");
            if (linear != null) {
                if (linear.getValue() != null) {
                    this.linear = !linear.getValue().trim().equals("no");
                }
            }

            Attribute properties = item.getAttribute("properties");
            if (properties != null) {
                if (properties.getValue() != null) {
                    String[] propertiesSplit = properties.getValue().trim().split(" ");
                    for (String property : propertiesSplit) {
                        this.properties.add(ItemRefProperty.fromProperty(property.trim()));
                    }
                }
            }
        }

        public String getId() {
            return id;
        }

        public Manifest.Item getItem() {
            return item;
        }

        public OcfFile getFile() throws IOException, JDOMException {
            return item.getFile();
        }

        public boolean isLinear() {
            return linear;
        }

        public List<ItemRefProperty> getProperties() {
            return properties;
        }

        @Override
        public String toString() {
            return "ItemRef{" +
                    "id='" + id + '\'' +
                    ", item=" + item +
                    ", linear=" + linear +
                    ", properties=" + properties +
                    '}';
        }
    }

    public enum ItemRefProperty {
        COVER_IMAGE("page-spread-left"),
        MATHML("page-spread-right"),
        UNKNOWN("unknown");

        String property;

        private static final Map<String, ItemRefProperty> properties = new HashMap<>();

        static {
            for (ItemRefProperty r : values()) {
                properties.put(r.property, r);
            }
        }

        ItemRefProperty(String property) {
            this.property = property;
        }

        public String getProperty() {
            return property;
        }

        public static ItemRefProperty fromProperty(String property) {
            if (property == null) return UNKNOWN;
            ItemRefProperty ret = properties.get(property);
            if (ret == null) return UNKNOWN;
            return ret;
        }
    }
}
