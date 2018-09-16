package VCS.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Base class for all domain objects in the project.
 * Contains the mandatory fields for all domain objects.
 */
public abstract class AbstractDomainObject implements Serializable {

    private Date created;

    private Long id;

    private Date updated;

    public Date getCreated() {
        return created;
    }

    public Long getId() {
        return id;
    }

    public Date getUpdated() {
        return updated;
    }

    public void onPrePersist() {
        setCreated(new Date());
    }

    public void onPreUpdate() {
        setUpdated(new Date());
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
