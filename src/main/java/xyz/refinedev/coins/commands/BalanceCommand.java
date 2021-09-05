package xyz.refinedev.coins.commands;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import xyz.refinedev.coins.Locale;
import xyz.refinedev.coins.RefineCoins;
import xyz.refinedev.coins.profile.Profile;
import xyz.refinedev.coins.utils.chat.CC;

import java.util.Optional;

@AllArgsConstructor
public class BalanceCommand {

    private final RefineCoins refineCoins;

    /**
     * Showcases the balance of a player
     *
     * @param player sender
     */

    @Command(name = "", desc = "Check your coins balance")
    public final void balance(@Sender Player player) {
        final Optional<Profile> profile = refineCoins.getProfileHandler().getProfile(player.getUniqueId());

        profile.ifPresent(value -> player.sendMessage(CC.translate(Locale.BALANCE.getString().replace("<coins>", String.valueOf(value.getCoins())))));
    }

}
