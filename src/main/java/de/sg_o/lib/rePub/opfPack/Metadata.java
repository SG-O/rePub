package de.sg_o.lib.rePub.opfPack;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Namespace;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class Metadata {
    private static final Namespace dcNamespace = Namespace.getNamespace("dc", "http://purl.org/dc/elements/1.1/");
    private static final Namespace namespace = Namespace.getNamespace("http://www.idpf.org/2007/opf");

    private final Identifier[] identifiers;
    private int uniqueIdentifierIndex = -1;

    private final String[] titles;
    private final Locale[] languages;

    private Contributor[] contributors = null;
    private Contributor[] creators = null;
    private ZonedDateTime date = null;
    private Subject subject;
    private String type = null;

    //TODO add certain metadata

    //TODO add links


    public Metadata(Element metadata, String uniqueIdentifier) throws IOException {
        List<Element> identifiers = metadata.getChildren("identifier", dcNamespace);
        if (identifiers.size() < 1) throw new IOException("Metadata invalid, no identifiers found");
        Meta metas = new Meta(metadata.getChildren("meta", namespace));
        ZonedDateTime lastModified = null;
        Element lastModifiedElement = metas.searchFor("dcterms:modified");
        if (lastModifiedElement != null) {
            lastModified = ZonedDateTime.parse(lastModifiedElement.getValue());
        }
        this.identifiers = new Identifier[identifiers.size()];
        for (int i = 0; i < identifiers.size(); i++) {
            Element identifier = identifiers.get(i);
            Attribute id = identifier.getAttribute("id", null);
            if (id == null) throw new IOException("Metadata invalid, identifier missing id");
            if (id.getValue().trim().equals(uniqueIdentifier.trim())) {
                this.uniqueIdentifierIndex = i;
            }
            Element identifierType = metas.searchForRefining("identifier-type", identifier);
            this.identifiers[i] = new Identifier(identifier.getValue(), lastModified);
            this.identifiers[i].setIdentifierType(identifierType);
        }
        if (this.uniqueIdentifierIndex < 0) throw new IOException("Metadata invalid, unique identifier not found");

        List<Element> titles = metadata.getChildren("title", dcNamespace);
        if (titles.size() < 1) throw new IOException("Metadata invalid, no title found");
        this.titles = new String[titles.size()];
        for (int i = 0; i < titles.size(); i++) {
            this.titles[i] = titles.get(i).getValue().trim();
        }

        List<Element> languages = metadata.getChildren("language", dcNamespace);
        if (languages.size() < 1) throw new IOException("Metadata invalid, no language found");
        this.languages = new Locale[languages.size()];
        for (int i = 0; i < languages.size(); i++) {
            this.languages[i] = Locale.forLanguageTag(languages.get(i).getValue().trim());
        }

        List<Element> contributors = metadata.getChildren("contributor", dcNamespace);
        if (contributors.size() > 0) {
            this.contributors = new Contributor[contributors.size()];
            for (int i = 0; i < contributors.size(); i++) {
                Element contributor = contributors.get(i);
                this.contributors[i] = new Contributor(contributor.getValue());
                this.contributors[i].setRole(metas.searchForRefining("role", contributor), contributor);
                this.contributors[i].setFileAs(metas.searchForRefining("file-as", contributor));
            }
        } else {
            this.contributors = new Contributor[0];
        }

        List<Element> creators = metadata.getChildren("creator", dcNamespace);
        if (creators.size() > 0) {
            this.creators = new Contributor[creators.size()];
            for (int i = 0; i < creators.size(); i++) {
                Element creator = creators.get(i);
                this.creators[i] = new Contributor(creator.getValue());
                this.creators[i].setRole(metas.searchForRefining("role", creator), creator);
                this.creators[i].setFileAs(metas.searchForRefining("file-as", creator));
            }
        } else {
            this.creators = new Contributor[0];
        }

        Element date = metadata.getChild("date", dcNamespace);
        if (date != null) {
            String dateRaw = date.getValue();
            if (dateRaw != null) {
                try {
                    this.date = ZonedDateTime.parse(dateRaw);
                } catch (Exception ignore) {
                    this.date = attemptTimeRead(dateRaw);
                }
            }
        }

        Element subject = metadata.getChild("subject", dcNamespace);
        if (subject != null) {
            this.subject = new Subject(subject.getValue());
            this.subject.setAuthority(metas.searchForRefining("authority", subject));
            this.subject.setTerm(metas.searchForRefining("term", subject));
        }

        Element type = metadata.getChild("type", dcNamespace);
        if (type != null) {
            this.type = type.getValue();
        }
    }

    public Identifier[] getIdentifiers() {
        return identifiers;
    }

    protected int getUniqueIdentifierIndex() {
        return uniqueIdentifierIndex;
    }

    public String[] getTitles() {
        return titles;
    }

    public Locale[] getLanguages() {
        return languages;
    }

    public Contributor[] getContributors() {
        return contributors;
    }

    public Contributor[] getCreators() {
        return creators;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getType() {
        return type;
    }

    public static ZonedDateTime attemptTimeRead(String dateString) {
        Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
            put("^\\d{8}$", "yyyyMMdd");
            put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
            put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
            put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
            put("^\\d{12}$", "yyyyMMddHHmm");
            put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
            put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
            put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
            put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
            put("^\\d{14}$", "yyyyMMddHHmmss");
            put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
            put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
            put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
            put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
        }};

        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                String dateFormat = DATE_FORMAT_REGEXPS.get(regexp);
                SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                try {
                    Date date = formatter.parse(dateString);
                    return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC").normalized());
                } catch (ParseException e) {
                    continue;
                }
            }
        }
        return null; // Unknown format.
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "identifiers=" + Arrays.toString(identifiers) +
                ", titles=" + Arrays.toString(titles) +
                ", languages=" + Arrays.toString(languages) +
                ", contributors=" + Arrays.toString(contributors) +
                ", creators=" + Arrays.toString(creators) +
                ", date=" + date +
                ", subject='" + subject + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
