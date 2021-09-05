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
public class PayCommand {

    private final RefineCoins refineCoins;

    /**
     * Pays a specific amount of
     * coins to a player
     *
     * @param player player of which will pay he coins
     * @param target target of which will receive the coins
     * @param coins coins to be sent
     */

    @Command(name = "", desc = "Pays a user an amount of coins", usage = "<player> <coins>")
    public final void cPay(@Sender Player player, Player target, int coins) {

        if (!Locale.ENABLE_PAY.getBoolean()) {
            player.sendMessage(CC.translate("&cThe owner of the server has disabled the paying feature!"));
            return;
        }

        if (target.getUniqueId().equals(player.getUniqueId())) {
            player.sendMessage(CC.translate(Locale.MAY_NOT_USE_ON_SELF.getString()));
            return;
        }

        final Optional<Profile> playerProfile = refineCoins.getProfileHandler().getProfile(player.getUniqueId());
        final Optional<Profile> targetProfile = refineCoins.getProfileHandler().getProfile(target.getUniqueId());

        playerProfile.ifPresent(value -> {
            if (value.getCoins() < coins) {
                player.sendMessage(CC.translate(Locale.NOT_ENOUGH_COINS.getString()));
                return;
            }

            value.setCoins(value.getCoins() - coins);
            player.sendMessage(CC.translate(Locale.COINS_GAVE.toString().replace("<player>", target.getName()).replace("<coins>", String.valueOf(coins))));
        });

        targetProfile.ifPresent(value -> {
            value.setCoins(value.getCoins() + coins);
            player.sendMessage(CC.translate(Locale.COINS_GIVEN.getString().replace("<player>", player.getName()).replace("<coins>", String.valueOf(coins))));
        });
    }


}
