package com.wyvencraft.api;

import org.jdbi.v3.core.Jdbi;

public interface Database {
    Jdbi getJdbi();
}
