package xyz.refinedev.coins.utils.config;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Getter
@Setter
public class BasicConfigurationFile extends AbstractConfigurationFile {

    private final File file;
    private final YamlConfiguration configuration;

    public BasicConfigurationFile(JavaPlugin plugin, String name, boolean overwrite) {
        super(plugin, name);

        this.file = new File(plugin.getDataFolder(), name + ".yml");
        plugin.saveResource(name + ".yml", overwrite);

        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public BasicConfigurationFile(JavaPlugin plugin, String name) {
        this(plugin, name, false);
    }

    public String getString(String path) {
        return this.configuration.contains(path) ? ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path)) : null;
    }

    public String getStringOrDefault(String path, String or) {
        String toReturn = this.getString(path);
        if (toReturn == null) {
            this.getConfiguration().set(path, or);
            this.save();
            return or;
        }
        return toReturn;
    }

    public int getInteger(String path) {
        return this.configuration.contains(path) ? this.configuration.getInt(path) : 0;
    }

    public boolean getBoolean(String path) {
        return this.configuration.contains(path) && this.configuration.getBoolean(path);
    }

    public double getDouble(String path) {
        return this.configuration.contains(path) ? this.configuration.getDouble(path) : 0.0D;
    }

    public Object get(String path) {
        return this.configuration.contains(path) ? this.configuration.get(path) : null;
    }

    public List<String> getStringList(String path) {
        return this.configuration.contains(path) ? this.configuration.getStringList(path) : null;
    }

    public void reload() {
        try {
            getConfiguration().load(file);
            getConfiguration().save(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public BasicConfigurationFile set(String path, Object object) {
        this.configuration.set(path, object);
        return this;
    }

    public void save() {
        try {
            getConfiguration().save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
