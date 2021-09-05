package xyz.refinedev.coins.profile;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class Profile {

    private final UUID uuid;

    private int coins;

    /**
     * Constructs a new instance of {@link Profile}
     * using a profile
     *
     * @param uuid uuid to create profile
     */

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Returns a BSON document
     * containing all profile data
     *
     * @return {@link Document}
     */

    public final Document toBson() {
        return new Document("_id", uuid.toString())
                .append("coins", coins);
    }

}
