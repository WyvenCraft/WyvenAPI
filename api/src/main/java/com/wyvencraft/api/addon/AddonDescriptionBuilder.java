package com.wyvencraft.api.addon;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class AddonDescriptionBuilder {
    private final String mainClassName, unlocalizedName, version;
    private String displayName, description, website;
    private List<String> authorList, pluginDependencyList, addonDependencyList, pluginSoftDependencyList, addonSoftDependencyList;
    private boolean lateLoad;

    public AddonDescriptionBuilder(String mainClassName, String unlocalizedName, String version) {
        this.mainClassName = Objects.requireNonNull(mainClassName, "mainClassName must not be empty!");
        this.unlocalizedName = Objects.requireNonNull(unlocalizedName, "unlocalizedName must not be empty!");
        this.version = Objects.requireNonNull(version, "version must not be empty!");

        this.displayName = null;
        this.description = null;
        this.website = null;
        this.authorList = new ArrayList<>();
        this.pluginDependencyList = new ArrayList<>();
        this.addonDependencyList = new ArrayList<>();
        this.pluginSoftDependencyList = new ArrayList<>();
        this.addonSoftDependencyList = new ArrayList<>();
        this.lateLoad = false;
    }

    public AddonDescriptionBuilder withDisplayName(@Nullable String displayName) {
        this.displayName = displayName;
        return this;
    }

    public AddonDescriptionBuilder withDescription(@Nullable String description) {
        this.description = description;
        return this;
    }

    public AddonDescriptionBuilder withWebsite(@Nullable String website) {
        this.website = website;
        return this;
    }

    public AddonDescriptionBuilder withAuthors(List<String> authorList) {
        this.authorList = new ArrayList<>(authorList);
        return this;
    }

    public AddonDescriptionBuilder withPluginDependencies(List<String> pluginDependencyList) {
        this.pluginDependencyList = new ArrayList<>(pluginDependencyList);
        return this;
    }

    public AddonDescriptionBuilder withPluginSoftDependencies(List<String> pluginSoftDependencyList) {
        this.pluginSoftDependencyList = new ArrayList<>(pluginSoftDependencyList);
        return this;
    }

    public AddonDescriptionBuilder withAddonDependencies(List<String> addonDependencyList) {
        this.addonDependencyList = new ArrayList<>(addonDependencyList);
        return this;
    }

    public AddonDescriptionBuilder withAddonSoftDependencies(List<String> addonSoftDependencyList) {
        this.addonSoftDependencyList = new ArrayList<>(addonSoftDependencyList);
        return this;
    }

    public AddonDescriptionBuilder withLateLoad(boolean lateLoad) {
        this.lateLoad = lateLoad;
        return this;
    }

    @NotNull
    public AddonDescription build() {
        String displayName = (this.displayName == null ? this.unlocalizedName : this.displayName);
        String description = (this.description == null ? "" : this.description);
        return new AddonDescription(this.mainClassName, this.unlocalizedName, this.version, displayName,
                description, this.website, this.authorList, this.pluginDependencyList, this.pluginSoftDependencyList,
                this.addonDependencyList, this.addonSoftDependencyList, this.lateLoad);
    }
}
