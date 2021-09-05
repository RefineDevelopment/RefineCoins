package xyz.refinedev.coins;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public enum Locale {

    COINS_SET("COINS.SET"),
    COINS_GAVE("COINS.GAVE"),
    COINS_GIVEN("COINS.GIVEN"),
    COINS_TAKE("COINS.TAKE"),
    BALANCE("OTHER.BALANCE"),
    ENABLE_PAY(RefineCoins.getInstance().getConfig(), "SETTINGS.ENABLE-PAY"),
    NOT_ENOUGH_COINS("ERROR.NOT-ENOUGH-COINS"),
    MAY_NOT_USE_ON_SELF("ERROR.MAY-NOT-SEND-TO-SELF");

    private final FileConfiguration fileConfiguration;
    private final String path;

    /**
     * Locale used to get messages
     *
     * @param fileConfiguration configuration file to get from
     * @param path path to get from
     */

    Locale(FileConfiguration fileConfiguration, String path) {
        this.fileConfiguration = fileConfiguration;
        this.path = path;
    }

    /**
     * Locale used to get messages
     *
     * @param path path to get from
     */

    Locale(String path) {
        this.fileConfiguration = RefineCoins.getInstance().getMessagesYML().getConfiguration();
        this.path = path;
    }

    /**
     * Gets the string of that path
     *
     * @return {@link String}
     */

    public final String getString() {
        return fileConfiguration.getString(path);
    }

    /**
     * Gets the integer of that path
     *
     * @return {@link Integer}
     */

    public final int getInteger() {
        return fileConfiguration.getInt(path);
    }

    /**
     * Gets the boolean of that path
     *
     * @return {@link Boolean}
     */

    public final boolean getBoolean() {
        return fileConfiguration.getBoolean(path);
    }

    /**
     * Sends a message to a sender
     *
     * @param sender sender to send the message to
     */

    public final void sendMessage(CommandSender sender) {
        sender.sendMessage(getString());
    }

    /**
     * Sends a message to a player
     *
     * @param player player to send the message to
     */

    public final void sendMessage(Player player) {
        player.sendMessage(getString());
    }

}
