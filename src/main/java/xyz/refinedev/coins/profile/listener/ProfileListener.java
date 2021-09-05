package xyz.refinedev.coins.profile.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.refinedev.coins.profile.Profile;
import xyz.refinedev.coins.profile.ProfileHandler;

import java.util.Optional;

public class ProfileListener implements Listener {

    private final ProfileHandler profileHandler;

    /**
     * Profile listener to create
     * or delete profiles
     *
     * @param profileHandler {@link ProfileHandler}
     */

    public ProfileListener(ProfileHandler profileHandler) {
        this.profileHandler = profileHandler;
    }

    /**
     * Event triggered on the logging in
     * of the player, creates their profile
     *
     * @param event {@link AsyncPlayerPreLoginEvent}
     */

    @EventHandler
    public final void onAsyncPlayerJoinEvent(AsyncPlayerPreLoginEvent event) {
        profileHandler.createProfile(event.getUniqueId());
    }

    /**
     * Event triggered on the
     * Quitting of the player
     * To save the profile
     *
     * @param event {@link PlayerQuitEvent}
     */

    @EventHandler
    public final void onPlayerQuitEvent(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final Optional<Profile> profile = profileHandler.getProfile(player.getUniqueId());

        profile.ifPresent(value -> profileHandler.handleRemove(value, false));
    }

}
