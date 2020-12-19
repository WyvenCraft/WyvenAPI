package com.wyvencraft.api.player;

import com.sun.istack.internal.Nullable;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface WyvenPlayer {
    /**
     * Get player
     *
     * @return Will return null if player is offline.
     */
    @Nullable
    Player getPlayer();

    /**
     * Get player uuid
     *
     * @return Uuid of the player.
     */
    @Nullable
    UUID getUID();
}
