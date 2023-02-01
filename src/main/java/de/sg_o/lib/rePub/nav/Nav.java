package de.sg_o.lib.rePub.nav;

import java.util.List;

public interface Nav {
    public String getTitle();
    public List<NavEntry> getNavEntries();

    @Override
    public String toString();
}
