package xyz.refinedev.coins.commands;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Require;
import com.jonahseguin.drink.annotation.Sender;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.refinedev.coins.Locale;
import xyz.refinedev.coins.RefineCoins;
import xyz.refinedev.coins.profile.Profile;
import xyz.refinedev.coins.utils.chat.CC;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CoinsCommand {

    private final RefineCoins refineCoins;
    private final List<String> helpMessage = CC.translate(Arrays.asList(
            CC.CHAT_BAR,
            "&c&lCoins Help Message",
            CC.CHAT_BAR,
            "&c/coins help &7- Showcases this help message",
            "&c/coins set <player> <coins> &7- Sets the coins of a player",
            "&c/coins give <player> <coins> &7- Gives the player coins",
            "&c/coins take <player> <coins> &7- Takes from the player coins",
            CC.CHAT_BAR
    ));

    /**
     * Public coins command
     *
     * @param refineCoins instance of {@link RefineCoins}
     */

    public CoinsCommand(RefineCoins refineCoins) {
        this.refineCoins = refineCoins;
    }

    /**
     * Coins help command
     *
     * @param sender sender
     */

    @Command(name = "", desc = "Help Message")
    @Require(value = "refine.coins.admin")
    public final void coins(@Sender CommandSender sender) {
        helpMessage.forEach(sender::sendMessage);
    }

    /**
     * Coins help command
     *
     * @param sender sender
     */

    @Command(name = "help", desc = "Help Message")
    @Require(value = "refine.coins.admin")
    public final void coinsHelp(@Sender CommandSender sender) {
        this.coins(sender);
    }

    /**
     * Coins set command
     *
     * @param sender        command sender
     * @param offlinePlayer player to set the coins of
     */

    @Command(name = "set", desc = "Set the coins of a player", usage = "<player> <coins>")
    @Require(value = "refine.coins.admin")
    public final void coinsSetCommand(@Sender CommandSender sender, OfflinePlayer offlinePlayer, int coins) {
        final Optional<Profile> profile = offlinePlayer.isOnline() ?
                refineCoins.getProfileHandler().getProfile(offlinePlayer.getUniqueId()) :
                refineCoins.getProfileHandler().createProfile(offlinePlayer.getUniqueId());

        profile.ifPresent(value ->  value.setCoins(coins));
        sender.sendMessage(CC.translate(Locale.COINS_SET.getString().replace("<player>", Objects.requireNonNull(offlinePlayer.getName())).replace("<coins>", String.valueOf(coins))));
    }

    /**
     * Coins give command
     *
     * @param sender        command sender
     * @param offlinePlayer player to give the coins to
     */


    @Command(name = "give", desc = "Gives the player the coins specified", usage = "<player> <coins>")
    @Require(value = "refine.coins.admin")
    public final void coinsGiveCommand(@Sender CommandSender sender, OfflinePlayer offlinePlayer, int coins) {
        final Optional<Profile> profile = offlinePlayer.isOnline() ?
                refineCoins.getProfileHandler().getProfile(offlinePlayer.getUniqueId()) :
                refineCoins.getProfileHandler().createProfile(offlinePlayer.getUniqueId());

        profile.ifPresent(value ->  {
            value.setCoins(value.getCoins() + coins);

            if (offlinePlayer.isOnline()) {
                Player player = (Player) offlinePlayer;
                player.sendMessage(CC.translate(Locale.COINS_GIVEN.getString().replace("<player>", sender.getName()).replace("<coins>", String.valueOf(coins))));
            }

        });
        sender.sendMessage(CC.translate(Locale.COINS_GAVE.getString().replace("<player>", Objects.requireNonNull(offlinePlayer.getName())).replace("<coins>", String.valueOf(coins))));
    }

    /**
     * Coins take command
     *
     * @param sender        command sender
     * @param offlinePlayer player to give the coins to
     */


    @Command(name = "take", desc = "Gives the player the coins specified", usage = "<player> <coins>")
    @Require(value = "refine.coins.admin")
    public final void coinsTakeCommand(@Sender CommandSender sender, OfflinePlayer offlinePlayer, int coins) {
        final Optional<Profile> profile = offlinePlayer.isOnline() ?
                refineCoins.getProfileHandler().getProfile(offlinePlayer.getUniqueId()) :
                refineCoins.getProfileHandler().createProfile(offlinePlayer.getUniqueId());

        profile.ifPresent(value -> {

            if (value.getCoins() < coins) {
                sender.sendMessage(CC.translate(Locale.NOT_ENOUGH_COINS.getString().replace("you", "The user")));
                return;
            }

            value.setCoins(value.getCoins() - coins);
        });
        sender.sendMessage(CC.translate(Locale.COINS_TAKE.getString().replace("<player>", Objects.requireNonNull(offlinePlayer.getName())).replace("<coins>", String.valueOf(coins))));
    }

}
