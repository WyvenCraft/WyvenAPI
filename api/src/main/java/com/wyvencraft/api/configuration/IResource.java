package com.wyvencraft.api.configuration;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

public interface IResource {
    File getDataFolder();

    InputStream getResource(String name);

    Logger getLogger();
}
