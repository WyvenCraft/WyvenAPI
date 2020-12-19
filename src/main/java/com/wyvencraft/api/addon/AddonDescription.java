package com.wyvencraft.api.addon;

import java.util.List;

public interface AddonDescription {
    String getName();

    String getFullName();

    String getDisplayName();

    String getMainClass();

    String getDescription();

    String getVersion();

    List<String> getAuthors();
}
