package com.wyvencraft.api.addon;

import java.util.List;

public interface AddonDescription {
    /**
     * @return name of addon
     */
    String getName();


    /**
     * @return name + version
     */
    String getFullName();

    /**
     * @return addon displayname
     */
    String getDisplayName();

    /**
     * @return path to main class
     */
    String getMainClass();

    /**
     * @return addon description
     */
    String getDescription();

    /**
     * @return addon version
     */
    String getVersion();

    /**
     * @return names of all authors
     */
    List<String> getAuthors();
}
