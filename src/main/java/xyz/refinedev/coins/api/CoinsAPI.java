package xyz.refinedev.coins.api;

import java.util.UUID;

public interface CoinsAPI {

    /**
     * Gets the coins of a player
     *
     * @param uuid uuid of the player
     * @return {@link Integer}
     */

    int getCoins(UUID uuid);

    /**
     * Sets the coins of the player
     *
     * @param uuid uuid of player
     * @param coins coins of player
     */

    void setCoins(UUID uuid, int coins);

}
