package slo;


/**
 * Represents a status of a SLO. The CURRENT is the most recent version of a SLO.
 *
 * Created by Iiro Hietala on 27.5.2014.
 */
public enum SLOStatus{

    CURRENT("sloStatus.current"),

    OLD("sloStatus.old"),

    DELETED("sloStatus.deleted");

    private final String resourceKey;

    private SLOStatus(String resourceKey) {
        this.resourceKey = resourceKey;
    }


    public String getResourceKey() {
        return this.resourceKey;
    }

}
