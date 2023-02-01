package de.sg_o.test.rePub.opfPack;

import de.sg_o.lib.rePub.container.Container;
import de.sg_o.lib.rePub.container.Ocf;
import de.sg_o.lib.rePub.opfPack.OpfPackage;
import de.sg_o.lib.rePub.opfPack.ReadingDirection;
import de.sg_o.lib.rePub.opfPack.Spine;
import de.sg_o.test.rePub.common.TestFiles;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpineTest {
    private static final List<Spine> testData = new ArrayList<>();
    private static Spine test0;
    private static Spine test1;
    private static Spine test2;
    private static Spine test3;
    private static Spine test4;

    @BeforeAll
    static void beforeAll() throws IOException, URISyntaxException, JDOMException {
        TestFiles.prepareTestFiles();
        for (Path testFile : TestFiles.getTestFiles()) {
            Ocf test = new Ocf(testFile);
            Container defaultContainer = test.getDefaultContainer();
            List<OpfPackage> renditions = defaultContainer.getRenditions();
            for (OpfPackage pack : renditions) {
                testData.add(pack.getSpine());
            }
        }
        Path testPath0 = TestFiles.getTestFile("Alices Adventures in Wonderland.epub");
        Path testPath1 = TestFiles.getTestFile("minimal-v2.epub");
        Path testPath2 = TestFiles.getTestFile("kusamakura-japanese-vertical-writing.epub");
        Path testPath3 = TestFiles.getTestFile("WCAG.epub");
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

        test0 = pack0.get(0).getSpine();
        test1 = pack1.get(0).getSpine();
        test2 = pack2.get(0).getSpine();
        test3 = pack3.get(0).getSpine();
        test4 = pack4.get(0).getSpine();

        assertNotNull(test0);
        assertNotNull(test1);
        assertNotNull(test2);
        assertNotNull(test3);
        assertNotNull(test4);
    }

    @Test
    void testGetPageProgressionDirection() {
        for (Spine spine : testData) {
            assertNotNull(spine.getPageProgressionDirection());
        }

        assertEquals(ReadingDirection.DEFAULT, test0.getPageProgressionDirection());
        assertEquals(ReadingDirection.DEFAULT, test1.getPageProgressionDirection());
        assertEquals(ReadingDirection.RTL, test2.getPageProgressionDirection());
        assertEquals(ReadingDirection.DEFAULT, test3.getPageProgressionDirection());
        assertEquals(ReadingDirection.DEFAULT, test4.getPageProgressionDirection());
    }

    @Test
    void testGetToc() {
        for (Spine spine : testData) {
            assertDoesNotThrow(spine::getToc);
        }

        assertEquals("/toc.ncx", test0.getToc().getLocation().toString());
        assertEquals("/OEBPS/toc.ncx", test1.getToc().getLocation().toString());
        assertEquals("/OPS/xhtml/toc.ncx", test2.getToc().getLocation().toString());
        assertNull(test3.getToc());
        assertNull(test4.getToc());
    }

    @Test
    void testGetAllItemRefs() {
        for (Spine spine : testData) {
            assert(spine.getAllItemRefs().size() > 0);
        }

        assertEquals(14, test0.getAllItemRefs().size());
        assertEquals(1, test1.getAllItemRefs().size());
        assertEquals(16, test2.getAllItemRefs().size());
        assertEquals(3, test3.getAllItemRefs().size());
        assertEquals(6, test4.getAllItemRefs().size());
    }

    @Test
    void testGetLinearItemRefs() {
        for (Spine spine : testData) {
            assert(spine.getLinearItemRefs().size() > 0);
        }

        assertEquals(14, test0.getLinearItemRefs().size());
        assertEquals(1, test1.getLinearItemRefs().size());
        assertEquals(15, test2.getLinearItemRefs().size());
        assertEquals(3, test3.getLinearItemRefs().size());
        assertEquals(6, test4.getLinearItemRefs().size());
    }

    @Test
    void testItemRefGetId() {
        for (Spine spine : testData) {
            List<Spine.ItemRef> itemRefs = spine.getAllItemRefs();
            for (Spine.ItemRef itemRef : itemRefs) {
                assertDoesNotThrow(itemRef::getId);
            }
        }

        List<Spine.ItemRef> itemRefs0 = test0.getAllItemRefs();
        List<Spine.ItemRef> itemRefs1 = test1.getAllItemRefs();
        List<Spine.ItemRef> itemRefs2 = test2.getAllItemRefs();
        List<Spine.ItemRef> itemRefs3 = test3.getAllItemRefs();
        List<Spine.ItemRef> itemRefs4 = test4.getAllItemRefs();

        assertNull(itemRefs0.get(0).getId());
        assertNull(itemRefs1.get(0).getId());
        assertNull(itemRefs2.get(0).getId());
        assertEquals("itemref_1", itemRefs3.get(0).getId());
        assertNull(itemRefs4.get(0).getId());
    }

    @Test
    void testItemRefGetItem() throws IOException, JDOMException {
        for (Spine spine : testData) {
            List<Spine.ItemRef> itemRefs = spine.getAllItemRefs();
            for (Spine.ItemRef itemRef : itemRefs) {
                assertNotNull(itemRef.getItem());
            }
        }

        List<Spine.ItemRef> itemRefs0 = test0.getAllItemRefs();
        List<Spine.ItemRef> itemRefs1 = test1.getAllItemRefs();
        List<Spine.ItemRef> itemRefs2 = test2.getAllItemRefs();
        List<Spine.ItemRef> itemRefs3 = test3.getAllItemRefs();
        List<Spine.ItemRef> itemRefs4 = test4.getAllItemRefs();

        assertEquals("titlepage1", itemRefs0.get(0).getItem().getId());
        assertEquals("section0001.xhtml", itemRefs1.get(0).getItem().getId());
        assertEquals("表紙", itemRefs2.get(0).getItem().getId());
        assertEquals("item_34", itemRefs3.get(0).getItem().getId());
        assertEquals("page001", itemRefs4.get(0).getItem().getId());
    }

    @Test
    void testItemRefGetFile() throws IOException, JDOMException {
        for (Spine spine : testData) {
            List<Spine.ItemRef> itemRefs = spine.getAllItemRefs();
            for (Spine.ItemRef itemRef : itemRefs) {
                assertNotNull(itemRef.getFile());
            }
        }

        List<Spine.ItemRef> itemRefs0 = test0.getAllItemRefs();
        List<Spine.ItemRef> itemRefs1 = test1.getAllItemRefs();
        List<Spine.ItemRef> itemRefs2 = test2.getAllItemRefs();
        List<Spine.ItemRef> itemRefs3 = test3.getAllItemRefs();
        List<Spine.ItemRef> itemRefs4 = test4.getAllItemRefs();

        assertEquals("/titlepage1.xhtml", itemRefs0.get(0).getFile().getLocation().toString());
        assertEquals("/OEBPS/xhtml/section0001.xhtml", itemRefs1.get(0).getFile().getLocation().toString());
        assertEquals("/OPS/xhtml/表紙.xhtml", itemRefs2.get(0).getFile().getLocation().toString());
        assertEquals("/EPUB/xhtml/WCAG-ch1-1.xhtml", itemRefs3.get(0).getFile().getLocation().toString());
        assertEquals("/OPS/Images/cover.svg", itemRefs4.get(0).getFile().getLocation().toString());
    }

    @Test
    void testItemRefGetProperties() throws IOException, JDOMException {
        for (Spine spine : testData) {
            List<Spine.ItemRef> itemRefs = spine.getAllItemRefs();
            for (Spine.ItemRef itemRef : itemRefs) {
                assertNotNull(itemRef.getProperties());
            }
        }
    }

    @Test
    void testToString() {
        for (Spine spine : testData) {
            assertNotNull(spine.toString());
        }
    }
}