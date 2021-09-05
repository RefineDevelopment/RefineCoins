package xyz.refinedev.coins.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import xyz.refinedev.coins.RefineCoins;
import xyz.refinedev.coins.profile.Profile;

import java.util.Optional;

public class CoinsPlaceholderExtension extends PlaceholderExpansion {

    private final RefineCoins refineCoins;

    /**
     * Coins place holder api expnasion
     *
     * @param refineCoins instance of {@link RefineCoins}
     */

    public CoinsPlaceholderExtension(RefineCoins refineCoins) {
        this.refineCoins = refineCoins;
    }

    /**
     * Identifier for the placeholder
     *
     * @return {@link String}
     */

    @Override
    public String getIdentifier() {
        return "coins";
    }

    /**
     * Author of the plugin
     *
     * @return {@link String}
     */

    @Override
    public String getAuthor() {
        return "refine";
    }

    /**
     * Version of the plugin
     *
     * @return {@link String}
     */

    @Override
    public String getVersion() {
        return "1.0";
    }

    /**
     * Makes sure that the place holder expansion
     * can register
     *
     * @return true
     */

    @Override
    public boolean canRegister() {
        return true;
    }

    /**
     * Gets the value
     *
     * @param p player for the value
     * @param params and what it's requesting
     * @return {@link String}
     */

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        final Optional<Profile> profile = refineCoins.getProfileHandler().getProfile(p.getUniqueId());

        if (!profile.isPresent())
            return "Could not find profile";

        if (params.equalsIgnoreCase("balance"))
            return String.valueOf(profile.get().getCoins());

        return null;
    }
}
