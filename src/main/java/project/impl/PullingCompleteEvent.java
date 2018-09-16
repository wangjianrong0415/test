package project.impl;


import project.Project;

/**
 * @author Iiro Hietala 13.5.2014.
 */
public class PullingCompleteEvent {
    private Project project;

    public PullingCompleteEvent(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
