package de.sg_o.lib.rePub.nav;

import de.sg_o.lib.rePub.container.OcfFile;
import de.sg_o.lib.rePub.opfPack.OpfPackage;

import java.nio.file.Path;
import java.util.LinkedList;

public abstract class NavEntry {
    private final OpfPackage pack;
    protected String label;
    protected Path absoluteFilePath;

    protected LinkedList<NavEntry> subEntries;

    public NavEntry(OpfPackage pack) {
        this.pack = pack;
    }

    public OpfPackage getPack() {
        return pack;
    }

    public String getLabel() {
        return label;
    }

    public Path getAbsoluteFilePath() {
        return absoluteFilePath;
    }

    public LinkedList<NavEntry> getSubEntries() {
        return subEntries;
    }

    @Override
    public String toString() {
        return "NavEntry{" +
                "label='" + label + '\'' +
                ", absoluteFilePath=" + absoluteFilePath +
                ", subEntries=" + subEntries +
                '}';
    }
}
