package xyz.refinedev.coins.profile.impl;

import org.bukkit.configuration.ConfigurationSection;
import xyz.refinedev.coins.RefineCoins;
import xyz.refinedev.coins.profile.Profile;
import xyz.refinedev.coins.profile.ProfileStorage;
import xyz.refinedev.coins.utils.config.BasicConfigurationFile;

import java.util.UUID;

public class FlatFileProfileStorage implements ProfileStorage {

    private final RefineCoins refineCoins;

    /**
     * Flat-File profile system manager
     *
     * @param refineCoins instance of {@link RefineCoins}
     */

    public FlatFileProfileStorage(RefineCoins refineCoins) {
        this.refineCoins = refineCoins;
    }

    /**
     * Loads the profile from the uuid
     *
     * @param profile uuid of the profile to be loaded
     */

    public void load(Profile profile) {
        refineCoins.getServer().getScheduler().runTaskAsynchronously(refineCoins, () -> {
            final UUID uuid = profile.getUuid();
            final ConfigurationSection section = refineCoins.getProfilesYML().getConfiguration().getConfigurationSection(uuid.toString());

            if (section == null || !refineCoins.getProfilesYML().getConfiguration().contains(uuid.toString())) {
                this.save(profile, false);
                return;
            }

            profile.setCoins(section.getInt("coins"));
        });
    }

    /**
     * Saves the profile
     *
     * @param profile profile to be saved
     */

    public void save(Profile profile, boolean async) {

        if (async) {
            refineCoins.getServer().getScheduler().runTaskAsynchronously(refineCoins, () -> save(profile, false));
            return;
        }

        final BasicConfigurationFile basicConfigurationFile = refineCoins.getProfilesYML();
        final UUID uuid = profile.getUuid();

        basicConfigurationFile.set(uuid.toString() + ".coins", profile.getCoins());

        basicConfigurationFile.save();
    }

}
