package xyz.refinedev.coins.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.refinedev.coins.RefineCoins;

import java.util.Collections;

@Getter
public class MongoHandler {

    private final RefineCoins refineCoins;

    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> profiles;

    /**
     * Constructs a new instance of {@link MongoHandler}
     *
     * @param refineCoins instance of {@link RefineCoins}
     */

    public MongoHandler(RefineCoins refineCoins) {
        this.refineCoins = refineCoins;
        this.connect();
    }

    /**
     * Connect logic of the mongo database
     */

    private void connect() {
        FileConfiguration mainConfig = refineCoins.getConfig();

        if (mainConfig.getBoolean("STORAGE.MONGO.URI-MODE")) {
            MongoClient client = new MongoClient(new MongoClientURI(mainConfig.getString("STORAGE.MONGO.URI.CONNECTION_STRING")));
            this.mongoDatabase = client.getDatabase(mainConfig.getString("STORAGE.MONGO.URI.DATABASE"));
        } else {
            MongoClient client;
            if (mainConfig.getBoolean("STORAGE.MONGO.NORMAL.AUTHENTICATION.ENABLED")) {
                MongoCredential credential = MongoCredential.createCredential(
                        mainConfig.getString("STORSTORAGE.MONGO.NORMAL.AUTHENTICATION.USERNAME"),
                        mainConfig.getString("STORAGE.MONGO.NORMAL.DATABASE"),
                        mainConfig.getString("STORAGE.MONGO.NORMAL.AUTHENTICATION.PASSWORD").toCharArray()
                );

                client = new MongoClient(new ServerAddress(mainConfig.getString("STORAGE.MONGO.NORMAL.HOST"),
                        mainConfig.getInt("STORAGE.MONGO.NORMAL.PORT")), Collections.singletonList(credential));
            } else {
                client = new MongoClient(mainConfig.getString("STORAGE.MONGO.NORMAL.HOST"),
                        mainConfig.getInt("STORAGE.MONGO.NORMAL.PORT"));
            }
            this.mongoDatabase = client.getDatabase(mainConfig.getString("STORAGE.MONGO.NORMAL.DATABASE"));
        }

        profiles = mongoDatabase.getCollection("profiles");
    }

}
