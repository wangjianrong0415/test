package analysis;

import VCS.entity.AbstractDomainObject;
import feature.ChangedFeature;
import project.Project;
import slo.SLO;

import java.util.*;

/**
 * Represents an analysis that is performed on a repository.
 * Contains the SLOs (Software Life-Cycle Objects) and ChangedFeatures that are
 * identified in an analysis.
 */
public class Analysis extends AbstractDomainObject {

    private AnalysisSetting analysisSetting;

    private AnalysisStatus analysisStatus;

    private List<ChangedFeature> changedFeatures = new ArrayList<ChangedFeature>();

    private Boolean initialAnalysis;

    private Project project;


    private String releaseVersion;

    private Set<SLO> slos = new HashSet<SLO>();

    public Analysis() {
    }

    public void addChangedFeature(ChangedFeature changedFeature) {
        if (!changedFeatures.contains(changedFeature)) {
            changedFeatures.add(changedFeature);
            changedFeature.setAnalysis(this);
        }
    }

    public void addSlo(SLO slo) {
        if (!slos.contains(slo)) {
            slos.add(slo);
            slo.setAnalysis(this);
        }
    }

    public AnalysisSetting getAnalysisSetting() {
        return analysisSetting;
    }

    public AnalysisStatus getAnalysisStatus() {
        return analysisStatus;
    }

    public List<ChangedFeature> getChangedFeatures() {
        return Collections.unmodifiableList(changedFeatures);
    }

    public Boolean getInitialAnalysis() {
        return initialAnalysis;
    }

    public Project getProject() {
        return project;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public Set<SLO> getSlos() {
        return Collections.unmodifiableSet(slos);
    }

    public void removeChangedFeature(ChangedFeature changedFeature) {
        if (changedFeatures.contains(changedFeature)) {
            changedFeatures.remove(changedFeature);
            changedFeature.setAnalysis(null);
        }
    }

    public void removeSlo(SLO slo) {
        if (slos.contains(slo)) {
            slos.remove(slo);
            slo.setAnalysis(null);
        }
    }

    public void setAnalysisSetting(AnalysisSetting analysisSetting) {
        this.analysisSetting = analysisSetting;
    }

    public void setAnalysisStatus(AnalysisStatus analysisStatus) {
        this.analysisStatus = analysisStatus;
    }

    public void setInitialAnalysis(Boolean initialAnalysis) {
        this.initialAnalysis = initialAnalysis;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }
}
