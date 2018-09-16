package feature;

import VCS.entity.AbstractDomainObject;
import project.Project;
import slo.SLO;
import slo.SLOType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a feature mapping from a pattern to a given feature name.
 * Created by Iiro Hietala on 17.5.2014.
 */
public class FeatureMapping extends AbstractDomainObject {

    private Project project;

    private MappingType mappingType;

    private String sourcePattern;

    private String featureName;

    public MappingType getMappingType() {
        return mappingType;
    }

    public String getSourcePattern() {
        return sourcePattern;
    }

    public void setMappingType(MappingType mappingType) {
        this.mappingType = mappingType;
    }

    public void setSourcePattern(String sourcePattern) {
        this.sourcePattern = sourcePattern;
    }


    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean appliesTo(SLO slo) {
        Pattern pattern = Pattern.compile(sourcePattern);
        switch (mappingType) {
            case PACKAGE_NAME:
                if (slo.getSloType() == SLOType.SOURCE) {
                    Matcher matcher = pattern.matcher(slo.getPackageName());
                    return matcher.find();
                }
                break;
            case CONTAINING_DIRECTORY_NAME:
                Integer index = slo.getPath().lastIndexOf('/');
                if (index > 0) {
                    String dir = slo.getPath().substring(0, index);
                    return pattern.matcher(dir).find();
                }
            case FILE_NAME:
                index = slo.getPath().lastIndexOf('/');
                if (index != -1) {
                    String file = slo.getPath().substring(index + 1);
                    return pattern.matcher(file).find();
                }
                break;
        }
        return false;
    }

    public static FeatureMapping newFeatureMapping() {
        FeatureMapping featureMapping = new FeatureMapping();
        featureMapping.setFeatureName("");
        featureMapping.setSourcePattern("");
        featureMapping.setMappingType(MappingType.PACKAGE_NAME);
        return featureMapping;
    }
}

