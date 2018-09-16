package feature;

import VCS.entity.AbstractDomainObject;
import project.Project;
import slo.SLO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a named feature related to a project.
 */

public class Feature extends AbstractDomainObject {

    private List<SLO> slos = new ArrayList<SLO>();

    private String name;

    private Project project;

    public void addSlo(SLO slo) {
        if (!this.slos.contains(slo)) {
            this.slos.add(slo);
            slo.setFeature(this);
        }
    }

    public List<SLO> getSlos() {
        return Collections.unmodifiableList(slos);
    }

    public String getName() {
        return name;
    }

    public Project getProject() {
        return project;
    }

    public void removeSlos(SLO slo) {
        if (this.slos.contains(slo)) {
            slos.remove(slo);
            slo.setFeature(null);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
