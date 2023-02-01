package de.sg_o.test.rePub.opfPack;

import de.sg_o.lib.rePub.container.Container;
import de.sg_o.lib.rePub.container.Ocf;
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

class OpfPackageTest {
    static List<OpfPackage> testData = new ArrayList<>();

    @BeforeAll
    static void beforeAll() throws IOException, URISyntaxException, JDOMException {
        TestFiles.prepareTestFiles();
        for (Path testFile : TestFiles.getTestFiles()) {
            Ocf test = new Ocf(testFile);
            Container defaultContainer = test.getDefaultContainer();
            List<OpfPackage> renditions = defaultContainer.getRenditions();
            testData.addAll(renditions);
        }
    }

    @Test
    void testToString() {
        for (OpfPackage pack : testData) {
            assertNotNull(pack.toString());
        }
    }
}