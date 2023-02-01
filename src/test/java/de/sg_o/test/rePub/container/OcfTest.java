package de.sg_o.test.rePub.container;

import de.sg_o.lib.rePub.container.Ocf;
import de.sg_o.test.rePub.common.TestFiles;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OcfTest {
    @BeforeAll
    static void beforeAll() throws IOException, URISyntaxException {
        TestFiles.prepareTestFiles();
    }

    @Test
    void listFiles() throws IOException, JDOMException {
        for (Path testFile : TestFiles.getTestFiles()) {
            Ocf test = new Ocf(testFile);
            assertDoesNotThrow(test::listSubFiles);
        }
    }
}