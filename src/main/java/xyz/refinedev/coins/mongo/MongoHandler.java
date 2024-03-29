package xyz.refinedev.coins.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import xyz.refinedev.coins.RefineCoins;
import xyz.refinedev.coins.utils.config.BasicConfigurationFile;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This Project is property of Refine Development Â© 2021
 * Redistribution of this Project is not allowed
 *
 * @author Drizzy
 * Created: 9/12/2021
 * Project: Array
 */

@Getter
public class MongoHandler {

    private final RefineCoins plugin;
    private final BasicConfigurationFile config;

    private MongoClient client;
    private MongoDatabase database;

    private MongoCollection<Document> profiles;

    public MongoHandler(RefineCoins plugin, BasicConfigurationFile config) {
        this.plugin = plugin;
        this.config = config;
        init();
    }

    public void init() {
        this.disableLogging();

        if (config.getBoolean("STORAGE.MONGO.URI-MODE")) {
            this.client = MongoClients.create(config.getString("STORAGE.MONGO.URI.CONNECTION_STRING"));
            this.database = client.getDatabase(config.getString("STORAGE.MONGO.URI.DATABASE"));

            this.profiles = this.database.getCollection("profiles");
            plugin.getLogger().info("&7Initialized MongoDB successfully!");
            return;
        }

        boolean auth = config.getBoolean("STORAGE.MONGO.NORMAL.AUTHENTICATION.ENABLED");
        String host = config.getString("STORAGE.MONGO.NORMAL.HOST");
        int port = config.getInteger("STORAGE.MONGO.NORMAL.PORT");

        String uri = "mongodb://" + host + ":" + port;

        if (auth) {
            String username = config.getString("STORAGE.MONGO.NORMAL.AUTHENTICATION.USERNAME");
            String password = config.getString("STORAGE.MONGO.NORMAL.AUTHENTICATION.PASSWORD");
            uri = "mongodb://" + username + ":" + password + "@" + host + ":" + port;
        }


        this.client = MongoClients.create(uri);
        this.database = client.getDatabase(config.getString("STORAGE.MONGO.URI.DATABASE"));

        this.profiles = this.database.getCollection("profiles");

        plugin.getLogger().info("&7Initialized MongoDB successfully!");
    }

    public void shutdown() {
        plugin.getLogger().info("&7Disconnecting &cMongo&7...");
        try {
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (this.client != null) this.client.close();
        plugin.getLogger().info("&7Disconnected &cMongo &7Successfully!");
    }

    public void disableLogging() {
        Logger mongoLogger = Logger.getLogger( "com.mongodb" );
        mongoLogger.setLevel(Level.SEVERE);

        Logger legacyLogger = Logger.getLogger( "org.mongodb" );
        legacyLogger.setLevel(Level.SEVERE);
    }
}
