package slo;

import VCS.entity.AbstractDomainObject;
import analysis.Analysis;
import analysis.slo.SLOImport;
import feature.Feature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Software Life-cycle Object, SLO. A SLO amy represent a source file, a resource, or any other
 * file that is analysed.
 *
 * The SLOs with SLOType.SOURCE hold a special meaning as they may contain dependencies.
 */

public class SLO extends AbstractDomainObject {

    private Analysis analysis;

    private String className;

    private List<SLO> dependantOf = new ArrayList<SLO>();


    private List<SLO> dependsOn = new ArrayList<SLO>();


    private List<SLO> extendedBy = new ArrayList<SLO>();


    private SLO extending;


    private Feature feature;


    private List<JavaMethod> javaMethods = new ArrayList<JavaMethod>();


    private String packageName;


    private String path;


    private String qualifiedClassName;


    private List<SLOImport> sloImports = new ArrayList<SLOImport>();


    private SLOStatus sloStatus;


    private SLOType sloType;

    public SLO() {
        super();
    }

    public SLO(String path, SLOType type) {
        this();
        this.path = path;
        this.sloType = type;
        this.sloStatus = SLOStatus.CURRENT;
    }

    public void addDependency(SLO dependency) {
        if (!this.getDependsOn().contains(dependency)) {
            this.getDependsOn().add(dependency);
            dependency.getDependantOf().add(this);
        }
    }

    public void addMethod(JavaMethod method) {
        if (!this.javaMethods.contains(method)) {
            javaMethods.add(method);
            method.setSlo(this);
        }
    }

    public void addSLOImport(SLOImport sloImport) {
        if (!this.getSloImports().contains(sloImport)) {
            getSloImports().add(sloImport);
            sloImport.setSlo(this);
        }
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public String getClassName() {
        return className;
    }

    public List<SLO> getDependantOf() {
        return dependantOf;
    }

    public List<SLO> getDependsOn() {
        return dependsOn;
    }

    public List<SLO> getExtendedBy() {
        return extendedBy;
    }

    public SLO getExtending() {
        return extending;
    }

    public Feature getFeature() {
        return feature;
    }

    public List<JavaMethod> getJavaMethods() {
        return Collections.unmodifiableList(javaMethods);
    }

    public String getPackageName() {
        return packageName;
    }

    public String getPath() {
        return path;
    }

    public String getQualifiedClassName() {
        return qualifiedClassName;
    }

    public List<SLOImport> getSloImports() {
        return sloImports;
    }

    public SLOStatus getSloStatus() {
        return sloStatus;
    }

    public SLOType getSloType() {
        return sloType;
    }

    public void removeDependency(SLO slo) {
        this.getDependsOn().remove(slo);
        slo.getDependantOf().remove(this);
    }

    public void removeMethod(JavaMethod method) {
        if (this.javaMethods.contains(method)) {
            javaMethods.add(method);
            method.setSlo(this);
        }
    }

    public void removeSLOImport(SLOImport sloImport) {
        if (this.getSloImports().contains(sloImport)) {
            getSloImports().remove(sloImport);
            sloImport.setSlo(null);
        }
    }

    public void removeSLOImports() {
        for (SLOImport sloImport : getSloImports()) {
            sloImport.setSlo(null);
        }
        getSloImports().clear();
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setExtending(SLO parent) {
        this.extending = parent;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setQualifiedClassName(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
    }

    public void setSloStatus(SLOStatus sloStatus) {
        this.sloStatus = sloStatus;
    }

    public void setSloType(SLOType sloType) {
        this.sloType = sloType;
    }

    public void clearDependsOnList() {
        List<SLO> dependsOn = new ArrayList<SLO>(getDependsOn());
        for (SLO slo : dependsOn) {
            slo.removeDependency(slo);
        }
    }

    public void clearDependantOfList() {
        List<SLO> dependantOf = new ArrayList<SLO>(getDependantOf());
        for (SLO slo : dependantOf) {
            slo.removeDependency(slo);
        }
    }
}
