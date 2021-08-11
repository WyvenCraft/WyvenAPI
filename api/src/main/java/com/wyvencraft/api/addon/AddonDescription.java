package com.wyvencraft.api.addon;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AddonDescription {
    private final String mainClassName, unlocalizedName, version, displayName, description, website;
    private final List<String> authorList, pluginDependList, pluginSoftDependList, addonDependList,
            addonSoftDependList;
    private final boolean lateLoad;

    AddonDescription(String mainClassName, String unlocalizedName, String version, String displayName,
                         String description, String website, List<String> authorList, List<String> pluginDependList,
                         List<String> pluginSoftDependList, List<String> addonDependList,
                         List<String> addonSoftDependList, boolean lateLoad) {
        this.mainClassName = Objects.requireNonNull(mainClassName, "mainClassName cannot be empty or null!");
        this.unlocalizedName = Objects.requireNonNull(unlocalizedName, "unlocalizedName cannot be empty or null!");
        this.version = Objects.requireNonNull(version, "version cannot be empty or null!");

        this.displayName = displayName;
        this.description = description;
        this.website = website;

        this.authorList = authorList;
        this.pluginDependList = pluginDependList;
        this.pluginSoftDependList = pluginSoftDependList;
        this.addonDependList = addonDependList;
        this.addonSoftDependList = addonSoftDependList;

        this.lateLoad = lateLoad;
    }

    @NotNull
    public String getMainClassName() {
        return this.mainClassName;
    }

    @NotNull
    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    @NotNull
    public String getDisplayName() {
        return this.displayName;
    }

    @NotNull
    public String getVersion() {
        return this.version;
    }

    @Nullable
    public String getDescription() {
        return this.description;
    }

    @Nullable
    public String getWebsite() {
        return this.website;
    }

    @NotNull
    public List<String> getAuthors() {
        return this.authorList;
    }

    @NotNull
    public List<String> getPluginDependencies() {
        return Collections.unmodifiableList(this.pluginDependList);
    }

    @NotNull
    public List<String> getPluginSoftDependencies() {
        return Collections.unmodifiableList(this.pluginSoftDependList);
    }

    @NotNull
    public List<String> getAddonDependencies() {
        return Collections.unmodifiableList(this.addonDependList);
    }

    @NotNull
    public List<String> getAddonSoftDependencies() {
        return Collections.unmodifiableList(this.addonSoftDependList);
    }

    @NotNull
    public String getFullName() {
        String displayName = getDisplayName();
        String version = getVersion();
        return (displayName + " v" + version);
    }

    public boolean isLateLoad() {
        return this.lateLoad;
    }
}
