package xyz.refinedev.coins;

import com.jonahseguin.drink.CommandService;
import com.jonahseguin.drink.Drink;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.refinedev.coins.commands.BalanceCommand;
import xyz.refinedev.coins.commands.CoinsCommand;
import xyz.refinedev.coins.commands.PayCommand;
import xyz.refinedev.coins.commands.provider.OfflinePlayerCommandProvider;
import xyz.refinedev.coins.placeholder.CoinsPlaceholderExtension;
import xyz.refinedev.coins.profile.ProfileHandler;
import xyz.refinedev.coins.profile.ProfileStorage;
import xyz.refinedev.coins.profile.impl.FlatFileProfileStorage;
import xyz.refinedev.coins.profile.impl.MongoProfileStorage;
import xyz.refinedev.coins.utils.config.BasicConfigurationFile;

@Getter
public class RefineCoins extends JavaPlugin {

    @Getter
    private static RefineCoins instance;

    private ProfileStorage profileStorage;
    private ProfileHandler profileHandler;

    private BasicConfigurationFile profilesYML;
    private BasicConfigurationFile messagesYML;

    /**
     * Loading logic of the plugin
     */

    @Override
    public void onLoad() {
        instance = this;

        this.saveDefaultConfig();
        this.profilesYML = new BasicConfigurationFile(this, "profiles");
        this.messagesYML = new BasicConfigurationFile(this, "messages");
    }

    /**
     * Enabling logic of the plugin
     */

    @Override
    public void onEnable() {
        this.profileHandler = new ProfileHandler(this);

        if (!getConfig().getBoolean("STORAGE.MONGO-STORAGE")) {
            profileStorage = new FlatFileProfileStorage(this);
        } else {
            profileStorage = new MongoProfileStorage(this);
        }

        if (getConfig().getBoolean("SETTINGS.PLACEHOLDERAPI")) {
            new CoinsPlaceholderExtension(this).register();
        }

        final CommandService drink = Drink.get(this);

        drink.bind(OfflinePlayer.class).toProvider(new OfflinePlayerCommandProvider());

        drink.register(new CoinsCommand(this), "coins");
        drink.register(new BalanceCommand(this), "balance");
        drink.register(new PayCommand(this), "cpay");

        drink.registerCommands();
    }

    /**
     * Disabling logic of the plugin
     */

    @Override
    public void onDisable() {
        profileHandler.getProfileMap().values().forEach(profile -> profileStorage.save(profile, false));
    }

}
