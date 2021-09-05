package xyz.refinedev.coins.profile.impl;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import xyz.refinedev.coins.RefineCoins;
import xyz.refinedev.coins.mongo.MongoHandler;
import xyz.refinedev.coins.profile.Profile;
import xyz.refinedev.coins.profile.ProfileStorage;

public class MongoProfileStorage implements ProfileStorage {

    private final RefineCoins refineCoins;
    private final MongoHandler mongoHandler;

    /**
     * Mongo profile storage system manager
     *
     * @param refineCoins instance of {@link RefineCoins}
     */

    public MongoProfileStorage(RefineCoins refineCoins) {
        this.refineCoins = refineCoins;
        this.mongoHandler = new MongoHandler(refineCoins);
    }

    /**
     * Loads the profile from the uuid
     *
     * @param profile uuid of the profile to be loaded
     */

    @Override
    public void load(Profile profile) {
        refineCoins.getServer().getScheduler().runTaskAsynchronously(refineCoins, () -> {
            final Document document = mongoHandler.getProfiles().find(Filters.eq("_id", profile.toString())).first();

            if (document == null) {
                this.save(profile, false);
                return;
            }

            profile.setCoins(document.getInteger("coins"));
        });
    }

    /**
     * Saves the profile
     *
     * @param profile profile to be saved
     */

    @Override
    public void save(Profile profile, boolean async) {

        if (async) {
            refineCoins.getServer().getScheduler().runTaskAsynchronously(refineCoins, () -> save(profile, false));
            return;
        }

        final Document document = mongoHandler.getProfiles().find(Filters.eq("_id", profile.toString())).first();

        if (document == null) {
            mongoHandler.getProfiles().insertOne(profile.toBson());
            return;
        }

        mongoHandler.getProfiles().replaceOne(document, profile.toBson(), new ReplaceOptions().upsert(true));
    }

}
