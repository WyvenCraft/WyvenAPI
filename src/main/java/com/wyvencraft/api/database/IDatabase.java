package com.wyvencraft.api.database;

import org.jdbi.v3.core.Jdbi;

public interface IDatabase {
    Jdbi getJdbi();

    void load();
}
