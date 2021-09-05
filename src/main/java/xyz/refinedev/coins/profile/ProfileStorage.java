package xyz.refinedev.coins.profile;

public interface ProfileStorage {

    /**
     * Loads the profile from the uuid
     *
     * @param profile uuid of the profile to be loaded
     */

    void load(Profile profile);

    /**
     * Saves the profile
     *
     * @param profile profile to be saved
     */

    void save(Profile profile, boolean async);

}
