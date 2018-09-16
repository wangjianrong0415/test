package slo;


/**
 * SLO Type
 */
public enum SLOType{
    SOURCE("SLOType.source"),

    OTHER_FILE("SLOType.file"),

    RESOURCE("SLOType.resource");

    private final String resourceKey;

    SLOType(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getResourceKey() {
        return null;
    }
}
