package analysis;


/**
 * Created by Iiro Hietala on 17.5.2014.
 */
public enum Granularity{
    FILE("granularity.file"),

    METHOD("granularity.method");

    private String resourceKey;

    Granularity(String resourceKey) {
        this.resourceKey = resourceKey;
    }


    public String getResourceKey() {
        return this.resourceKey;
    }

}
