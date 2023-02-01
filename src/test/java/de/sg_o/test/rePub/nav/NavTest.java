package de.sg_o.test.rePub.nav;

import de.sg_o.lib.rePub.container.Container;
import de.sg_o.lib.rePub.container.Ocf;
import de.sg_o.lib.rePub.nav.Nav;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NavTest {
    static List<Nav> testData = new ArrayList<>();
    private static Nav test0;
    private static Nav test1;
    private static Nav test2;
    private static Nav test3;
    private static Nav test4;

    @BeforeAll
    static void beforeAll() throws IOException, URISyntaxException, JDOMException {
        TestFiles.prepareTestFiles();
        for (Path testFile : TestFiles.getTestFiles()) {
            Ocf test = new Ocf(testFile);
            Container defaultContainer = test.getDefaultContainer();
            List<OpfPackage> renditions = defaultContainer.getRenditions();
            for (OpfPackage pack : renditions) {
                testData.add(pack.getNav());
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

        test0 = pack0.get(0).getNav();
        test1 = pack1.get(0).getNav();
        test2 = pack2.get(0).getNav();
        test3 = pack3.get(0).getNav();
        test4 = pack4.get(0).getNav();

        assertNotNull(test0);
        assertNotNull(test1);
        assertNotNull(test2);
        assertNotNull(test3);
        assertNotNull(test4);
    }

    @Test
    void testGetTitle() {
        for (Nav nav : testData) {
            assertNotNull(nav);
            assertNotNull(nav.getTitle());
        }

        assertNotNull(test0.getTitle());
        assertNotNull(test1.getTitle());
        assertNotNull(test2.getTitle());
        assertNotNull(test3.getTitle());
        assertNotNull(test4.getTitle());

        assertEquals("Alice's Adventures in Wonderland", test0.getTitle());
        assertEquals("Your title here", test1.getTitle());
        assertEquals("EPUB 3 Specifications - Table of Contents", test2.getTitle());
        assertEquals("Abroad", test3.getTitle());
        assertEquals("Table of Contents", test4.getTitle());
    }

    @Test
    void testGetNavEntries() {
        for (Nav nav : testData) {
            assertNotNull(nav);
            assertNotNull(nav.getNavEntries());
            assert(nav.getNavEntries().size() > 0);
        }

        assertNotNull(test0.getNavEntries());
        assertNotNull(test1.getNavEntries());
        assertNotNull(test2.getNavEntries());
        assertNotNull(test3.getNavEntries());
        assertNotNull(test4.getNavEntries());

        assertEquals(12, test0.getNavEntries().size());
        assertEquals(1, test1.getNavEntries().size());
        assertEquals(11, test2.getNavEntries().size());
        assertEquals(1, test3.getNavEntries().size());
        assertEquals(3, test4.getNavEntries().size());
    }
}