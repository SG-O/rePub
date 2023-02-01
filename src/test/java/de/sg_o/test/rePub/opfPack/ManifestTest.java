package de.sg_o.test.rePub.opfPack;

import de.sg_o.lib.rePub.container.Container;
import de.sg_o.lib.rePub.container.MediaType;
import de.sg_o.lib.rePub.container.Ocf;
import de.sg_o.lib.rePub.opfPack.Manifest;
import de.sg_o.lib.rePub.opfPack.OpfPackage;
import de.sg_o.test.rePub.common.TestFiles;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManifestTest {
    private static final List<Manifest> testData = new ArrayList<>();
    private static Manifest test0;
    private static Manifest test1;
    private static Manifest test2;
    private static Manifest test3;
    private static Manifest test4;

    @BeforeAll
    static void beforeAll() throws IOException, URISyntaxException, JDOMException {
        TestFiles.prepareTestFiles();
        for (Path testFile : TestFiles.getTestFiles()) {
            Ocf test = new Ocf(testFile);
            Container defaultContainer = test.getDefaultContainer();
            List<OpfPackage> renditions = defaultContainer.getRenditions();
            for (OpfPackage pack : renditions) {
                testData.add(pack.getManifest());
            }
        }
        Path testPath0 = TestFiles.getTestFile("Alices Adventures in Wonderland.epub");
        Path testPath1 = TestFiles.getTestFile("minimal-v2.epub");
        Path testPath2 = TestFiles.getTestFile("epub30-spec.epub");
        Path testPath3 = TestFiles.getTestFile("childrens-media-query.epub");
        Path testPath4 = TestFiles.getTestFile("svg-in-spine.epub");

        assertNotNull(testPath0);
        assertNotNull(testPath1);
        assertNotNull(testPath2);
        assertNotNull(testPath3);
        assertNotNull(testPath4);

        List<OpfPackage> pack0 = new Ocf(testPath0).getDefaultContainer().getRenditions();
        List<OpfPackage> pack1 = new Ocf(testPath1).getDefaultContainer().getRenditions();
        List<OpfPackage> pack2 = new Ocf(testPath2).getDefaultContainer().getRenditions();
        List<OpfPackage> pack3 = new Ocf(testPath3).getDefaultContainer().getRenditions();
        List<OpfPackage> pack4 = new Ocf(testPath4).getDefaultContainer().getRenditions();

        assert (pack0.size() > 0);
        assert (pack1.size() > 0);
        assert (pack2.size() > 0);
        assert (pack3.size() > 0);
        assert (pack4.size() > 0);

        test0 = pack0.get(0).getManifest();
        test1 = pack1.get(0).getManifest();
        test2 = pack2.get(0).getManifest();
        test3 = pack3.get(0).getManifest();
        test4 = pack4.get(0).getManifest();

        assertNotNull(test0);
        assertNotNull(test1);
        assertNotNull(test2);
        assertNotNull(test3);
        assertNotNull(test4);
    }

    @Test
    void testGetItems() {
        for (Manifest manifest : testData) {
            assertNotNull(manifest.getIdMap());
            assert (manifest.getIdMap().size() > 0);
        }
        assertEquals(45, test0.getIdMap().size());
        assertEquals(2, test1.getIdMap().size());
        assertEquals(14, test2.getIdMap().size());
        assertEquals(7, test3.getIdMap().size());
        assertEquals(14, test4.getIdMap().size());


    }

    @Test
    void testGetFromId() {
        assertNotNull(test0.getFromId("img01a"));
        assertNotNull(test1.getFromId("ncx"));
        assertNotNull(test2.getFromId("ci"));
        assertNotNull(test3.getFromId("image1"));
        assertNotNull(test4.getFromId("page001"));

        assertEquals(MediaType.GIF_A, test0.getFromId("img01a").getMediaType());
        assertEquals(MediaType.NCX, test1.getFromId("ncx").getMediaType());
        assertEquals(MediaType.JPEG_A, test2.getFromId("ci").getMediaType());
        assertEquals(MediaType.JPEG_B, test3.getFromId("image1").getMediaType());
        assertEquals(MediaType.SVG_A, test4.getFromId("page001").getMediaType());

        assertEquals(MediaType.GIF_A.getMime(), test0.getFromId("img01a").getMediaType().getMime());
        assertEquals(MediaType.NCX.getMime(), test1.getFromId("ncx").getMediaType().getMime());
        assertEquals(MediaType.JPEG_A.getMime(), test2.getFromId("ci").getMediaType().getMime());
        assertEquals(MediaType.JPEG_B.getMime(), test3.getFromId("image1").getMediaType().getMime());
        assertEquals(MediaType.SVG_A.getMime(), test4.getFromId("page001").getMediaType().getMime());
    }

    @Test
    void testGetPathFromId() throws IOException, JDOMException {
        for (Manifest manifest : testData) {
            assertNull(manifest.getFromId("doesNotExist"));
            for (Manifest.Item item: manifest.getIdMap()) {
                assertNotNull(manifest.getFromId(item.getId()).getFile());
            }
        }
        assertEquals("/images/alice01a.gif", test0.getFromId("img01a").getFile().getLocation().toString());
        assertEquals("/OEBPS/toc.ncx", test1.getFromId("ncx").getFile().getLocation().toString());
        assertEquals("/EPUB/img/epub_logo_color.jpg", test2.getFromId("ci").getFile().getLocation().toString());
        assertEquals("/EPUB/childrens-book-flowers.jpg", test3.getFromId("image1").getFile().getLocation().toString());
        assertEquals("/OPS/Images/cover.svg", test4.getFromId("page001").getFile().getLocation().toString());

        OpfPackage equalsTest = new Ocf(TestFiles.getTestFile("Alices Adventures in Wonderland.epub")).getDefaultContainer().getRenditions().get(0);

        assertNotEquals(test0.getFromId("img10a").getFile(), equalsTest.getManifest().getFromId("img10a").getFile());
        assertEquals(test0.getFromId("img10a").getFile(), test0.getFromId("img10a").getFile());

        assertEquals(test0.getFromId("img10a").getFile().hashCode(), equalsTest.getManifest().getFromId("img10a").getFile().hashCode());
        assertEquals(test0.getFromId("img10a").getFile().hashCode(), test0.getFromId("img10a").getFile().hashCode());

        assert(test0.getFromId("img10a").getFile().toString().startsWith("OcfFile{location='/images/alice10a.gif', fileSystem='"));
    }

    @Test
    void testToString() {
        for (Manifest manifest : testData) {
            assertNotNull(manifest.toString());
        }
    }
}