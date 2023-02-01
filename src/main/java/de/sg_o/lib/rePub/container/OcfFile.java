package de.sg_o.lib.rePub.container;

import de.sg_o.lib.rePub.nav.NcxFile;
import de.sg_o.lib.rePub.opfPack.OpfPackage;
import de.sg_o.lib.rePub.xhtml.XhtmlFile;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class OcfFile {
    private final Path location;
    protected OpfPackage pack;

    public OcfFile(Path location, OpfPackage pack) throws IOException {
        if (location == null) throw new IOException("Invalid File, location null");
        location = location.toAbsolutePath();
        if (!Files.exists(location)) {
            throw new IOException("Invalid File, missing");
        }
        this.location = location.normalize();
        if (!(this.getClass().equals(OpfPackage.class) || this.getClass().equals(Container.class) || this.getClass().equals(OcfMimetype.class))) {
            if (pack == null) throw new IOException("Invalid File, pack null");
        }
        this.pack = pack;
    }

    public Path getLocation() {
        return location;
    }

    public Path getParentFolder() {
        if (location == null) return null;
        Path parent = location.getParent();
        if (parent == null) parent = location.getRoot();
        return parent;
    }

    public FileSystem getFileSystem() {
        if (location == null) return null;
        return location.getFileSystem();
    }

    public byte[] readAllBytes() throws IOException {
        return Files.readAllBytes(location);
    }

    public Path getRelativePath(String relativeLocation) {
        if (relativeLocation == null) return null;
        Path parentFolder = getParentFolder();
        if (parentFolder == null) return null;
        return parentFolder.resolve(relativeLocation);
    }

    public OcfFile getRelative(String relativeLocation, MediaType mime) throws IOException, JDOMException {
        return open(getRelativePath(relativeLocation), mime, this.pack);
    }

    public OcfFile getAbsolute(String relativeLocation, MediaType mime) throws IOException, JDOMException {
        if (relativeLocation == null) return null;
        FileSystem fileSystem = getFileSystem();
        if (fileSystem == null) return null;
        return open(fileSystem.getPath(relativeLocation), mime, this.pack);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OcfFile ocfFile = (OcfFile) o;
        return Objects.equals(location, ocfFile.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        return "OcfFile{" +
                "location='" + location + '\'' +
                ", fileSystem='" + getFileSystem().toString() + '\'' +
                '}';
    }

    public static OcfFile open(Path location, MediaType mediaType, OpfPackage pack) throws IOException, JDOMException {
        switch (mediaType) {
            case XML_A:
            case XML_B:
                return new XmlFile(location, pack);
            case CONTAINER:
                return new Container(location);
            case OPF:
                return new OpfPackage(location);
            case NCX:
                return new NcxFile(location, pack);
            case MIMETYPE:
                return new OcfMimetype(location, pack);
            case XHTML:
                return new XhtmlFile(location, pack);
            default:
                return new OcfFile(location, pack);
        }
    }
}
