package de.sg_o.lib.rePub.opfPack;

import org.jdom2.Attribute;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Meta {
    Map<String, List<Element>> metas = new HashMap<>();

    public Meta(List<Element> metas) {
        for (Element meta : metas) {
            Attribute property = meta.getAttribute("property", null);
            if (property != null) {
                String value = property.getValue();
                if (!this.metas.containsKey(value)) {
                    this.metas.put(value, new ArrayList<>());
                }
                this.metas.get(value).add(meta);
            }
        }
    }

    public Element searchFor(String property) {
        if (property == null) return null;
        List<Element> found = metas.get(property);
        if (found == null) return null;
        if (found.size() < 1) return null;
        return found.get(0);
    }

    public Element searchForRefining(String property, Element element) {
        if (property == null) return null;
        if (element == null) return null;
        Attribute id = element.getAttribute("id", null);
        if (id == null) return null;
        String reference = id.getValue();
        if (reference == null) return null;
        reference = "#" + reference.trim();
        List<Element> found = metas.get(property);
        if (found == null) return null;
        if (found.size() < 1) return null;
        for (Element entry : found) {
            Attribute refines = entry.getAttribute("refines", null);
            if (refines == null) continue;
            if (reference.equals(refines.getValue())) return entry;
        }
        return null;
    }
}
