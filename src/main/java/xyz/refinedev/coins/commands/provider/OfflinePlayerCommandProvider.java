package xyz.refinedev.coins.commands.provider;

import com.jonahseguin.drink.argument.CommandArg;
import com.jonahseguin.drink.exception.CommandExitMessage;
import com.jonahseguin.drink.parametric.DrinkProvider;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import xyz.refinedev.coins.utils.chat.CC;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

public class OfflinePlayerCommandProvider extends DrinkProvider<OfflinePlayer> {

    @Override
    public boolean doesConsumeArgument() {
        return true;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public OfflinePlayer provide(CommandArg arg, List<? extends Annotation> annotations) throws CommandExitMessage {
        String s = arg.get();
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(s);

        if (arg == null || s == null || !offlinePlayer.hasPlayedBefore() || offlinePlayer == null)
            throw new CommandExitMessage(CC.translate("&cThe player &e" + s + " &chas never played before!"));

        return offlinePlayer;
    }

    @Override
    public String argumentDescription() {
        return "player";
    }

    @Override
    public List<String> getSuggestions(String prefix) {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

}
