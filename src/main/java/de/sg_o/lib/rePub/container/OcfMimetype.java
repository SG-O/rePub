package de.sg_o.lib.rePub.container;

import de.sg_o.lib.rePub.opfPack.OpfPackage;

import java.io.IOException;
import java.nio.file.Path;

public class OcfMimetype extends OcfFile{
    public OcfMimetype(Path location, OpfPackage pack) throws IOException {
        super(location, pack);
    }
}
