package analysis;

import VCS.entity.AbstractDomainObject;
import project.Project;

/**
 * Represents the configuration options that are used when performing an analysis.
 * @author Iiro Hietala 16.5.2014.
 */
public class AnalysisSetting extends AbstractDomainObject {

    public static AnalysisSetting newAnalysisSetting() {
        AnalysisSetting setting = new AnalysisSetting();
        setting.setBranch("");
        setting.setAutomaticFeatureMapping(true);
        setting.setEnabled(true);
        setting.setGranularity(Granularity.FILE);

        return setting;
    }

    private Boolean automaticFeatureMapping;

    private String branch;

    private Boolean enabled;

    private Granularity granularity;

    private Project project;

    public Boolean getAutomaticFeatureMapping() {
        return automaticFeatureMapping;
    }

    public String getBranch() {
        return branch;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Granularity getGranularity() {
        return granularity;
    }

    public Project getProject() {
        return project;
    }

    public void setAutomaticFeatureMapping(Boolean automaticFeatureMapping) {
        this.automaticFeatureMapping = automaticFeatureMapping;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setGranularity(Granularity granularity) {
        this.granularity = granularity;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
