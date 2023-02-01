package de.sg_o.lib.rePub.opfPack;

import de.sg_o.lib.rePub.container.XmlFile;
import de.sg_o.lib.rePub.nav.*;
import org.jdom2.*;

import java.io.IOException;
import java.nio.file.Path;

public class OpfPackage extends XmlFile {
    private static final Namespace NAMESPACE = Namespace.getNamespace("http://www.idpf.org/2007/opf");
    int uniqueIdentifierIndex;
    float version;

    Metadata metadata;
    Manifest manifest;
    Spine spine;
    Nav nav;

    public OpfPackage(Path location) throws IOException, JDOMException {
        super(location, null);
        super.pack = this;
        Element packageRoot = parseXml();
        Attribute uniqueIdentifier = packageRoot.getAttribute("unique-identifier");
        Attribute version = packageRoot.getAttribute("version");
        if (uniqueIdentifier == null) throw new IOException("Invalid package, no unique identifier");
        if (version == null) throw new IOException("Invalid package, no version specified");
        try {
            this.version = version.getFloatValue();
        } catch (DataConversionException e) {
            throw new IOException("Invalid package, version invalid");
        }
        if (this.version >= 4 || this.version < 2) throw new IOException("Invalid package, version unsupported");
        Element metadata = packageRoot.getChild("metadata", NAMESPACE);
        if (metadata == null) throw new IOException("Invalid package, metadata missing");
        this.metadata = new Metadata(metadata, uniqueIdentifier.getValue());
        this.uniqueIdentifierIndex = this.metadata.getUniqueIdentifierIndex();
        Element manifest = packageRoot.getChild("manifest", NAMESPACE);
        if (manifest == null) throw new IOException("Invalid package, manifest missing");
        this.manifest = new Manifest(manifest, this);
        Element spine = packageRoot.getChild("spine", NAMESPACE);
        if (spine == null) throw new IOException("Invalid package, spine missing");
        this.spine = new Spine(spine, this);
        if (this.manifest.getNav() != null) {
            this.nav = (NavFile) this.manifest.getNav();
        } else if (this.spine.getToc() != null) {
            this.nav = (NcxFile) this.spine.getToc();
        } else throw new IOException("Invalid package, toc not found");
    }

    public float getVersion() {
        return version;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public Manifest getManifest() {
        return manifest;
    }

    public Spine getSpine() {
        return spine;
    }

    public Nav getNav() {
        return nav;
    }

    @Override
    public String toString() {
        return "OpfPackage{" +
                "location = '" + getLocation() + '\'' +
                ", fileSystem='" + getFileSystem().toString() + '\'' +
                ", uniqueIdentifierIndex=" + uniqueIdentifierIndex +
                ", version=" + version +
                ", metadata=" + metadata +
                ", manifest=" + manifest +
                '}';
    }
}
