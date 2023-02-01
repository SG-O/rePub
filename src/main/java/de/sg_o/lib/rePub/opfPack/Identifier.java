package de.sg_o.lib.rePub.opfPack;

import org.jdom2.Attribute;
import org.jdom2.Element;

import java.time.ZonedDateTime;
import java.util.Objects;

public class Identifier {
    private final String identifier;
    private final ZonedDateTime lastModified;
    private IdentifierType identifierType;

    public Identifier(String identifier,  ZonedDateTime lastChanged) {
        this.identifier = identifier.trim();
        this.lastModified = lastChanged;
    }

    public void setIdentifierType(Element identifierType) {
        if (identifierType == null) return;
        String identifierTypeString = identifierType.getValue();
        if (identifierTypeString == null) return;
        Attribute scheme = identifierType.getAttribute("scheme", null);
        String identifierScheme = null;
        if (scheme != null) {
            identifierScheme = scheme.getValue();
        }
        this.identifierType = new IdentifierType(identifierTypeString, identifierScheme);
    }

    public String getIdentifier() {
        return identifier;
    }

    public IdentifierType getIdentifierType() {
        return identifierType;
    }

    public ZonedDateTime getLastModified() {
        return lastModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(identifier, that.identifier) && Objects.equals(lastModified, that.lastModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, lastModified);
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "identifier='" + identifier + ((lastModified != null) ? ("@" + lastModified) : "") + '\'' +
                ", identifierType='" + identifierType + '\'' +
                '}';
    }

    public class IdentifierType {
        private final String identifierType;
        private final String identifierScheme;

        public IdentifierType(String identifierType, String identifierScheme) {
            if (identifierType != null) {
                identifierType = identifierType.trim();
            }
            if (identifierScheme != null) {
                identifierScheme = identifierScheme.trim();
            }
            this.identifierType = identifierType;
            this.identifierScheme = identifierScheme;
        }

        public String getIdentifierType() {
            return identifierType;
        }

        public String getIdentifierScheme() {
            return identifierScheme;
        }

        @Override
        public String toString() {
            return "IdentifierType{" +
                    "identifierType='" + identifierType + '\'' +
                    ", identifierScheme='" + identifierScheme + '\'' +
                    '}';
        }
    }
}
