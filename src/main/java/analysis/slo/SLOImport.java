package analysis.slo;

import VCS.entity.AbstractDomainObject;
import slo.SLO;


/**
 * An object that represents an import to other module.
 */

public class SLOImport extends AbstractDomainObject {

    private String qualifiedClassName;


    private SLO slo;

    public SLOImport() {
        super();
    }

    public SLOImport(String qualifiedClassName) {
        this();
        setQualifiedClassName(qualifiedClassName);
    }

    public String getQualifiedClassName() {
        return qualifiedClassName;
    }

    public SLO getSlo() {
        return slo;
    }

    public void setQualifiedClassName(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
    }

    public void setSlo(SLO slo) {
        this.slo = slo;
    }
}
