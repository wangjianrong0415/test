package project;

import VCS.entity.AbstractDomainObject;

/**
 * A branch in software repository of a project
 */
public class ProjectBranch extends AbstractDomainObject {

    private String branch;

    private Project project;

    public ProjectBranch(Project project, String branch) {
        this();
        this.project = project;
        this.branch = branch;
    }

    public ProjectBranch() {
        super();
    }

    public String getBranch() {
        return branch;
    }

    public Project getProject() {
        return project;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
