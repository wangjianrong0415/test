package project;

import VCS.entity.AbstractDomainObject;
import VCS.entity.PluginConfiguration;
import VCS.entity.PluginType;
import analysis.Analysis;
import analysis.AnalysisSetting;
import feature.Feature;
import feature.FeatureMapping;
import feature.MappingType;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Iiro Hietala 13.5.2014.
 */
@XmlRootElement
public class Project extends AbstractDomainObject {

    public static Project newProject() {
        // TODO: Remove these before 1.0
        Project p = new Project();

        // test Commit

        int a = 0;

        p.setName("J-Ace");
        p.getPluginConfiguration().setPluginType(PluginType.GIT);
        p.getPluginConfiguration().setCloneUrl("https://github.com/aironi/jace.git");
        p.getPluginConfiguration().setCommitIdPattern("Jace #(\\d+)");
        p.getPluginConfiguration().setLocalDirectory("");
        p.getPluginConfiguration().setUserName("");
        p.getPluginConfiguration().setPassword("");
        p.getReleaseInfo().setVersionFileType(VersionFileType.XML);
        p.getReleaseInfo().setPathToVersionFile("/build/pom.xml");
        p.getReleaseInfo().setPattern("/project/properties/jace.version");

        addFeatureMapping(p, MappingType.PACKAGE_NAME, "(.*services\\.analysis.*)","Business Logic - Analysis");
        addFeatureMapping(p, MappingType.PACKAGE_NAME, "(.*services.vcs.*)","Business Logic - VCS");
        addFeatureMapping(p, MappingType.PACKAGE_NAME, "(.*services.project.*)","Business Logic - Project");
        addFeatureMapping(p, MappingType.PACKAGE_NAME, "(.*domain\\.vcs.*)","Business Entities - VCS");
        addFeatureMapping(p, MappingType.PACKAGE_NAME, "(.*domain.analysis.*)","Business Entities - Analysis");
        addFeatureMapping(p, MappingType.PACKAGE_NAME, "(.*domain\\.project.*)","Business Entities - Project");
        addFeatureMapping(p, MappingType.PACKAGE_NAME, "(.*domain.feature.*)","Business Entities - Feature");
        addFeatureMapping(p, MappingType.PACKAGE_NAME, "(.*domain.slo.*)","Business Entities - SLO");
        addFeatureMapping(p, MappingType.PACKAGE_NAME, "(.*domain.vcs.*)","Business Entities - Other");
        addFeatureMapping(p, MappingType.PACKAGE_NAME, "(.*dao.vcs.*)","Data Access - VCS");
        addFeatureMapping(p, MappingType.PACKAGE_NAME, "(.*dao.analysis.*)","Data Access - Analysis");
        addFeatureMapping(p, MappingType.PACKAGE_NAME, "(.*dao.project.*)","Data Accesss - Project");
        addFeatureMapping(p, MappingType.CONTAINING_DIRECTORY_NAME, "(\\/dao\\/.*)","Data Access - Other");
        addFeatureMapping(p, MappingType.PACKAGE_NAME, "(.*jace\\.common.*)","Common Library");
        addFeatureMapping(p, MappingType.PACKAGE_NAME, "(.*jace.web.*)","User Interface");
        addFeatureMapping(p, MappingType.PACKAGE_NAME, "(.*jace.rest.*)","RESTful Web Services");
        addFeatureMapping(p, MappingType.CONTAINING_DIRECTORY_NAME, "(\\/build\\/.*)","J-Ace Project Definition");
        addFeatureMapping(p, MappingType.CONTAINING_DIRECTORY_NAME, "(\\/web\\/.*)","User Interface");

        /*
         * p.setName(""); p.getPluginConfiguration().setPluginType(PluginType.GIT);
         * p.getPluginConfiguration().setCloneUrl(""); p.getPluginConfiguration().setLocalDirectory("");
         * p.getReleaseInfo().setVersionFileType(VersionFileType.XML); p.getReleaseInfo().setPathToVersionFile("");
         * p.getReleaseInfo().setPattern("");
         */
        return p;
    }

    private static void addFeatureMapping(Project p, MappingType mappingType, String pattern, String featureName) {
        FeatureMapping mapping = new FeatureMapping();
        mapping.setMappingType(mappingType);
        mapping.setSourcePattern(pattern);
        mapping.setFeatureName(featureName);
        p.addFeatureMapping(mapping);
    }

    private List<Analysis> analyses = new ArrayList<Analysis>();

    private List<AnalysisSetting> analysisSetting = new ArrayList<AnalysisSetting>();

    private List<ProjectBranch> branches = new ArrayList<ProjectBranch>();

    private List<Feature> features = new ArrayList<Feature>();

    private List<FeatureMapping> featureMappings = new ArrayList<FeatureMapping>();


    // unique = true, since the project name is used as the clone dir under working dir.
    private String name;

    private PluginConfiguration pluginConfiguration = new PluginConfiguration();

    private ReleaseInfo releaseInfo = new ReleaseInfo();

    public Project() {
    }

    public void addAnalysis(Analysis analysis) {
        if (!analyses.contains(analysis)) {
            this.analyses.add(analysis);
            analysis.setProject(this);
        }
    }

    public void addAnalysisSetting(AnalysisSetting setting) {
        if (!analysisSetting.contains(setting)) {
            setting.setProject(this);
            analysisSetting.add(setting);
        }
    }

    public void addBranch(ProjectBranch branch) {
        if (!branches.contains(branch)) {
            branches.add(branch);
            branch.setProject(this);
        }
    }

    public void addFeature(Feature feature) {
        if (!features.contains(feature)) {
            features.add(feature);
            feature.setProject(this);
        }
    }

    public void addFeatureMapping(FeatureMapping featureMapping) {
        if (!featureMappings.contains(featureMapping)) {
            featureMappings.add(featureMapping);
            featureMapping.setProject(this);
        }
    }


    public List<Analysis> getAnalyses() {
        return Collections.unmodifiableList(analyses);
    }

    public List<AnalysisSetting> getAnalysisSetting() {
        return Collections.unmodifiableList(analysisSetting);
    }

    public List<ProjectBranch> getBranches() {
        return Collections.unmodifiableList(branches);
    }

    public List<Feature> getFeatures() {
        return Collections.unmodifiableList(features);
    }

    public String getName() {
        return name;
    }

    public PluginConfiguration getPluginConfiguration() {
        return pluginConfiguration;
    }

    public ReleaseInfo getReleaseInfo() {
        return releaseInfo;
    }

    public void removeAllBranches() {
        List<ProjectBranch> removedBranches = new ArrayList<ProjectBranch>(branches);
        for (ProjectBranch b : removedBranches) {
            removeBranch(b);
        }
    }

    public void removeAnalysis(Analysis analysis) {
        if (analyses.contains(analysis)) {
            this.analyses.remove(analysis);
            analysis.setProject(null);
        }
    }

    public void removeAnalysisSetting(AnalysisSetting setting) {
        if (analysisSetting.contains(setting)) {
            analysisSetting.remove(setting);
            setting.setProject(null);
        }
    }

    public void removeBranch(ProjectBranch branch) {
        if (branches.contains(branch)) {
            branches.remove(branch);
            branch.setProject(null);
        }
    }

    public void removeFeature(Feature feature) {
        if (features.contains(feature)) {
            features.remove(feature);
            feature.setProject(null);

        }
    }

    public void removeFeatureMapping(FeatureMapping featureMapping) {
        if (featureMappings.contains(featureMapping)) {
            featureMappings.remove(featureMapping);
            featureMapping.setProject(null);
        }
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPluginConfiguration(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    public void setReleaseInfo(ReleaseInfo releaseInfo) {
        this.releaseInfo = releaseInfo;
    }

    public List<FeatureMapping> getFeatureMappings() {
        return Collections.unmodifiableList(featureMappings);
    }

    public void setFeatureMappings(List<FeatureMapping> featureMappings) {
        this.featureMappings = featureMappings;
    }
}
