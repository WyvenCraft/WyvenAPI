package com.wyvencraft.api.database;

import org.jdbi.v3.core.Jdbi;

public interface Database {
    Jdbi getJdbi();

    void load();
}
