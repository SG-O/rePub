package de.sg_o.test.rePub.opfPack;

import de.sg_o.lib.rePub.container.Container;
import de.sg_o.lib.rePub.container.Ocf;
import de.sg_o.lib.rePub.opfPack.Metadata;
import de.sg_o.lib.rePub.opfPack.OpfPackage;
import de.sg_o.lib.rePub.opfPack.Subject;
import de.sg_o.test.rePub.common.TestFiles;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MetadataTest {
    static List<Metadata> testData = new ArrayList<>();
    private static Metadata test0;
    private static Metadata test1;
    private static Metadata test2;
    private static Metadata test3;
    private static Metadata test4;

    @BeforeAll
    static void beforeAll() throws IOException, URISyntaxException, JDOMException {
        TestFiles.prepareTestFiles();
        for (Path testFile : TestFiles.getTestFiles()) {
            Ocf test = new Ocf(testFile);
            Container defaultContainer = test.getDefaultContainer();
            List<OpfPackage> renditions = defaultContainer.getRenditions();
            for (OpfPackage pack : renditions) {
                testData.add(pack.getMetadata());
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

        test0 = pack0.get(0).getMetadata();
        test1 = pack1.get(0).getMetadata();
        test2 = pack2.get(0).getMetadata();
        test3 = pack3.get(0).getMetadata();
        test4 = pack4.get(0).getMetadata();

        assertNotNull(test0);
        assertNotNull(test1);
        assertNotNull(test2);
        assertNotNull(test3);
        assertNotNull(test4);
    }
    @Test
    void testGetIdentifiers() throws IOException, JDOMException {
        for (Metadata meta : testData) {
            assert (meta.getIdentifiers().length > 0);
        }
    }

    @Test
    void testGetLanguages() {
        for (Metadata meta : testData) {
            assert (meta.getLanguages().length > 0);
        }
    }

    @Test
    void testGetContributors() {
        assertEquals(1, test0.getContributors().length);
        assertEquals(0, test1.getContributors().length);
        assertEquals(0, test2.getContributors().length);
        assertEquals(2, test3.getContributors().length);
        assertEquals(1, test4.getContributors().length);

        assertEquals("[Contributor{name='calibre (3.21.0) [http://calibre-ebook.com]', role=BKP, fileAs='null'}]",
                Arrays.toString(test0.getContributors()));
        assertEquals("[]", Arrays.toString(test1.getContributors()));
        assertEquals("[]", Arrays.toString(test2.getContributors()));
        assertEquals("[Contributor{name='Liza Daly', role=MRK, fileAs='null'}, " +
                        "Contributor{name='University of California Libraries', role=null, fileAs='null'}]",
                Arrays.toString(test3.getContributors()));
        assertEquals("[Contributor{name='Takeshi Kanai', role=null, fileAs='null'}]",
                Arrays.toString(test4.getContributors()));
    }

    @Test
    void testGetCreators() {
        assertEquals(1, test0.getCreators().length);
        assertEquals(0, test1.getCreators().length);
        assertEquals(1, test2.getCreators().length);
        assertEquals(2, test3.getCreators().length);
        assertEquals(1, test4.getCreators().length);

        assertEquals("[Contributor{name='Lewis Carroll', role=AUT, fileAs='null'}]",
                Arrays.toString(test0.getCreators()));
        assertEquals("[]", Arrays.toString(test1.getCreators()));
        assertEquals("[Contributor{name='EPUB 3 Working Group', role=null, fileAs='null'}]",
                Arrays.toString(test2.getCreators()));
        assertEquals("[Contributor{name='Thomas Crane', role=AUT, fileAs='Crane, Thomas'}, " +
                        "Contributor{name='Ellen Elizabeth Houghton', role=ILL, fileAs='Houghton, Ellen Elizabeth'}]",
                Arrays.toString(test3.getCreators()));
        assertEquals("[Contributor{name='ePub Sample project', role=null, fileAs='null'}]",
                Arrays.toString(test4.getCreators()));
    }

    @Test
    void testGetDate() {
        assertEquals(ZonedDateTime.parse("1865-07-04T00:00Z"), test0.getDate());
        assertNull(test1.getDate());
        assertNull(test2.getDate());
        assertNull(test3.getDate());
        assertNull(test4.getDate());
    }

    @Test
    void testGetSubject() {
        assertEquals(new Subject("fiction"), test0.getSubject());
        assertNull(test1.getSubject());
        assertNull(test2.getSubject());
        assertEquals(new Subject("France -- Description and travel Juvenile literature"), test3.getSubject());
        assertNull(test4.getSubject());
    }

    @Test
    void testGetType() {
        assertNull(test0.getType());
        assertNull(test1.getType());
        assertNull(test2.getType());
        assertNull(test3.getType());
        assertNull(test4.getType());
    }

    @Test
    void testToString() {
        for (Metadata meta : testData) {
            assertNotNull(meta.toString());
        }
    }
}