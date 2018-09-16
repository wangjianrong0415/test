package VCS.entity;

import project.Project;

/**
 * The Diff object represents a single diff of a file.
 */
public class Diff extends AbstractDomainObject {

    private Commit commit;

    private ModificationType modificationType;

    private String newPath;

    private String oldPath;

    private ParsedDiff parsedDiff;

    private Project project;

    public Commit getCommit() {
        return commit;
    }

    public ModificationType getModificationType() {
        return modificationType;
    }

    public String getNewPath() {
        return newPath;
    }

    public String getOldPath() {
        return oldPath;
    }

    public ParsedDiff getParsedDiff() {
        return parsedDiff;
    }

    public Project getProject() {
        return project;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public void setModificationType(ModificationType modificationType) {
        this.modificationType = modificationType;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }

    public void setOldPath(String oldPath) {
        this.oldPath = oldPath;
    }

    public void setParsedDiff(ParsedDiff diff) {
        this.parsedDiff = diff;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
