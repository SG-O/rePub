package de.sg_o.lib.rePub.container;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;

import static java.nio.file.StandardOpenOption.READ;

public class Ocf {
    FileSystem zipFs;
    Container defaultContainer;

    public Ocf(@NotNull Path file) throws IOException, JDOMException {
        this.zipFs = FileSystems.newFileSystem(file, null);
        String mimetype = new String(getSubFile("mimetype", MediaType.MIMETYPE).readAllBytes());
        if (!mimetype.equals("application/epub+zip")) throw new IOException("Invalid filetype");
        this.defaultContainer = (Container) getSubFile("META-INF/container.xml", MediaType.CONTAINER);
    }

    public OcfFile getSubFile(String name, MediaType mime) throws IOException, JDOMException {
        return OcfFile.open(this.zipFs.getPath(name), mime, null);
    }

    public Container getDefaultContainer() {
        return defaultContainer;
    }

    public HashMap<String, Path> listSubFiles() throws IOException {
        HashMap<String, Path> content = new HashMap<>();
        if (zipFs == null) throw new IOException("Zip FileSystem null");
        for (Path root : zipFs.getRootDirectories()) {
            Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    content.put(file.toString(), file);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        return content;
    }
}
