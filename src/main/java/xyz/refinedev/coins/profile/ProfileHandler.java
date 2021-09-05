package xyz.refinedev.coins.profile;

import lombok.Getter;
import xyz.refinedev.coins.RefineCoins;
import xyz.refinedev.coins.profile.listener.ProfileListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Getter
public class ProfileHandler {

    private final RefineCoins refineCoins;
    private final Map<UUID, Profile> profileMap = new HashMap<>();

    /**
     * Profile Manager
     *
     * @param refineCoins instance of {@link RefineCoins}
     */

    public ProfileHandler(RefineCoins refineCoins) {
        this.refineCoins = refineCoins;

        refineCoins.getServer().getPluginManager().registerEvents(new ProfileListener(this), refineCoins);
    }

    /**
     * Handles the creation of a profile
     *
     * @param uuid uuid of the profile
     */

    public final Optional<Profile> createProfile(UUID uuid) {
        final Profile profile = new Profile(uuid);

        refineCoins.getProfileStorage().load(profile);
        profileMap.put(uuid, profile);

        return Optional.of(profile);
    }

    /**
     * Handles the removal of a profile
     *
     * @param profile profile to remove
     */

    public final void handleRemove(Profile profile, boolean async) {
        refineCoins.getProfileStorage().save(profile, async);
        profileMap.remove(profile.getUuid());
    }

    /**
     * Gets a profile of a specific uuid
     *
     * @param uuid uuid to get the profile of
     * @return {@link Optional<Profile>}
     */

    public final Optional<Profile> getProfile(UUID uuid) {
        return Optional.ofNullable(profileMap.getOrDefault(uuid, null));
    }

}
