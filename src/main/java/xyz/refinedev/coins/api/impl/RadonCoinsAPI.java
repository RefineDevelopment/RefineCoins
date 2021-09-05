package xyz.refinedev.coins.api.impl;

import xyz.refinedev.coins.RefineCoins;
import xyz.refinedev.coins.api.CoinsAPI;
import xyz.refinedev.coins.profile.Profile;

import java.util.Optional;
import java.util.UUID;

public class RefineCoinsAPI implements CoinsAPI {

    public static RefineCoinsAPI INSTANCE;

    /**
     * Sets the instance of the plugin
     */

    public RefineCoinsAPI() {
        INSTANCE = this;
    }

    /**
     * Gets the coins of a player
     *
     * @param uuid uuid of the player
     * @return {@link Integer}
     */

    @Override
    public int getCoins(UUID uuid) {
        final Optional<Profile> profile = RefineCoins.getInstance().getProfileHandler().getProfile(uuid);

        if (!profile.isPresent()) {
            throw new IllegalArgumentException("The profile you are trying to access is not avaiable!");
        }

        return profile.get().getCoins();
    }

    /**
     * Sets the coins of the player
     *
     * @param uuid  uuid of player
     * @param coins coins of player
     */

    @Override
    public void setCoins(UUID uuid, int coins) {
        final Optional<Profile> profile = RefineCoins.getInstance().getProfileHandler().getProfile(uuid);

        if (!profile.isPresent()) {
            throw new IllegalArgumentException("The profile you are trying to access is not avaiable!");
        }

        profile.get().setCoins(coins);
    }

}
