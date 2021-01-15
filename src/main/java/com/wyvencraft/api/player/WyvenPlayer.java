package com.wyvencraft.api.player;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface WyvenPlayer {
    /**
     * Get player
     *
     * @return Will return null if player is offline.
     */
    Player getPlayer();

    /**
     * Get player uuid
     *
     * @return Uuid of the player.
     */
    UUID getUID();
}
