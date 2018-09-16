package project;


/**
 * Possible types for a version file
 *
 * @author Iiro Hietala 13.5.2014.
 */
public enum VersionFileType{
    PROPERTIES("versionFileType.properties"),

    XML("versionFileType.xml");

    private String resourceKey;

    VersionFileType(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getResourceKey() {
        return this.resourceKey;
    }

}
