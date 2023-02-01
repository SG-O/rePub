package de.sg_o.test.rePub.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.LinkedList;

public class TestFiles {
    private static final String[] testFileNames = {
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/accessible_epub_3.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/cc-shared-culture.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/childrens-literature.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/childrens-media-query.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/cole-voyage-of-life-tol.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/cole-voyage-of-life.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/epub30-spec.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/figure-gallery-bindings.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/georgia-cfi.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/georgia-pls-ssml.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/GhV-oeb-page.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/haruko-ahl.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/haruko-html-jpeg.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/haruko-jpeg.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/hefty-water.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/horizontally-scrollable-emakimono.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/indexing-for-eds-and-auths-3f.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/indexing-for-eds-and-auths-3md.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/internallinks.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/israelsailing.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/jlreq-in-english.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/jlreq-in-japanese.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/kusamakura-japanese-vertical-writing.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/kusamakura-preview-embedded.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/kusamakura-preview.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/linear-algebra.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/mahabharata.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/moby-dick-mo.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/moby-dick.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/mymedia_lite.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/page-blanche-bitmaps-in-spine.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/page-blanche.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/quiz-bindings.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/regime-anticancer-arabic.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/sous-le-vent.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/sous-le-vent_svg-in-spine.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/svg-in-spine.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/trees.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/vertically-scrollable-manga.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/wasteland-otf-obf.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/wasteland-otf.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/wasteland-woff-obf.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/wasteland-woff.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/wasteland.epub",
            "https://github.com/IDPF/epub3-samples/releases/download/20170606/WCAG.epub",
            "https://github.com/bmaupin/epub-samples/releases/download/v0.3/minimal-v2.epub",
            "https://s3-us-west-2.amazonaws.com/pressbooks-samplefiles/MetamorphosisJacksonTheme/Metamorphosis-jackson.epub",
            "https://filesamples.com/samples/ebook/epub/Alices%20Adventures%20in%20Wonderland.epub"
    };
    private static final HashMap<String, Path> testFiles = new HashMap<>();

    private static final Path tempDirWithPrefix;

    static {
        try {
            tempDirWithPrefix = Files.createTempDirectory("rePubTest");
            Runtime.getRuntime().addShutdownHook(new Thread(TestFiles::cleanup));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean importLocalTestFiles() throws IOException, URISyntaxException {
        File file = new File("src/test/epub/");
        String absolutePath = file.getAbsolutePath();
        Files.walkFileTree(Paths.get(absolutePath), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                testFiles.put(file.getFileName().toString(), file);
                return FileVisitResult.CONTINUE;
            }
        });
        return testFiles.size() > 0;
    }

    public static void prepareTestFiles() throws IOException, URISyntaxException {
        if (testFiles.size() > 0) return;
        if (importLocalTestFiles()) return;
        System.out.println("Getting test files...");
        for (String name: testFileNames) {
            try {
                Path downloaded = downloadFile(name, tempDirWithPrefix);
                testFiles.put(downloaded.getFileName().toString(),downloaded);
            } catch (IOException e) {
                System.out.println("Failed to get '" + name + "': " + e.getMessage());
            }
        }
    }

    private static Path downloadFile(String name, Path target) throws IOException {
        String[] split = name.split("/");
        String fileName = split[split.length - 1];
        System.out.println("Downloading " + fileName);
        InputStream in = new URL(name).openStream();
        Path out = target.resolve(fileName);
        Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
        in.close();
        return out;
    }

    public static LinkedList<Path> getTestFiles() {
        return new LinkedList<>(testFiles.values());
    }

    public static Path getTestFile(String filename) {
        return testFiles.get(filename);
    }

    public static void cleanup() {
        try {
            Files.walkFileTree(tempDirWithPrefix,
                    new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult postVisitDirectory(
                                Path dir, IOException exc) throws IOException {
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFile(
                                Path file, BasicFileAttributes attrs)
                                throws IOException {
                            Files.delete(file);
                            return FileVisitResult.CONTINUE;
                        }
                    });
        } catch (IOException ignore) {
        }
    }
}
