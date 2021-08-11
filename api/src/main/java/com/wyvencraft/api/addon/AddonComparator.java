package com.wyvencraft.api.addon;

import java.util.Comparator;
import java.util.List;

public final class AddonComparator implements Comparator<Addon> {
    @Override
    public int compare(Addon e1, Addon e2) {
        AddonDescription description1 = e1.getDescription();
        AddonDescription description2 = e2.getDescription();

        String expansionName1 = description1.getUnlocalizedName();
        String expansionName2 = description2.getUnlocalizedName();

        List<String> expansionDependencyList1 = description1.getAddonDependencies();
        List<String> expansionDependencyList2 = description2.getAddonDependencies();
        if (expansionDependencyList1.contains(expansionName2) && expansionDependencyList2.contains(expansionName1))
            throw new IllegalStateException("Cyclic Dependency: " + expansionName1 + ", " + expansionName2);

        if (expansionDependencyList1.contains(expansionName2)) return -1;
        if (expansionDependencyList2.contains(expansionName1)) return 1;
        return expansionName1.compareTo(expansionName2);
    }
}
