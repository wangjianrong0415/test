package feature;

import VCS.entity.AbstractDomainObject;
import VCS.entity.Diff;
import analysis.Analysis;
import slo.JavaMethod;
import slo.SLO;

/**
 * Represents a Changed Feature. The Granularity may be file- or method based.
 */
public class ChangedFeature extends AbstractDomainObject {

    private Analysis analysis;

    private Diff diff;

    private Feature feature;

    /**
     * Method level change
     */
    private JavaMethod method;

    /**
     * File level change
     */
    private SLO slo;

    public ChangedFeature() {
        super();
    }

    public ChangedFeature(Feature feature, SLO oldSlo, Diff diff) {
        this();
        this.feature = feature;
        this.slo = oldSlo;
        this.diff = diff;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public Diff getDiff() {
        return diff;
    }

    public Feature getFeature() {
        return feature;
    }

    public JavaMethod getMethod() {
        return method;
    }

    public SLO getSlo() {
        return slo;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    public void setDiff(Diff diff) {
        this.diff = diff;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public void setMethod(JavaMethod method) {
        this.method = method;
    }

    public void setSlo(SLO slo) {
        this.slo = slo;
    }
}
