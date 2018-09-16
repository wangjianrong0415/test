package VCS.entity;


/**
 * Defines all of the supported VCS plugin types.
 *
 * @author Iiro Hietala 13.5.2014.
 */
public enum PluginType {
    GIT("plugintype.git");

    private final String resourceKey;

    PluginType(String resourceKey) {
        this.resourceKey = resourceKey;
    }


    public String getResourceKey() {
        return this.resourceKey;
    }

}
