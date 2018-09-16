package slo;

import VCS.entity.AbstractDomainObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Iiro Hietala on 18.5.2014.
 */

public class JavaMethod extends AbstractDomainObject {

    private Integer endLine;

    private String name;

    private List<JavaParameter> parameters = new ArrayList<JavaParameter>();

    private JavaType returnType;

    private SLO slo;

    private Integer startLine;

    public void addParameter(JavaParameter javaParameter) {
        if (!this.parameters.contains(javaParameter)) {
            parameters.add(javaParameter);
            javaParameter.setJavaMethod(this);
        }
    }

    public Integer getEndLine() {
        return endLine;
    }

    public String getName() {
        return name;
    }

    public List<JavaParameter> getParameters() {
        return Collections.unmodifiableList(parameters);
    }

    public JavaType getReturnType() {
        return returnType;
    }

    public SLO getSlo() {
        return slo;
    }

    public Integer getStartLine() {
        return startLine;
    }

    public void removeParameter(JavaParameter javaParameter) {
        if (this.parameters.contains(javaParameter)) {
            parameters.remove(javaParameter);
        }
    }

    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReturnType(JavaType returnType) {
        this.returnType = returnType;
    }

    public void setSlo(SLO slo) {
        this.slo = slo;
    }

    public void setStartLine(Integer startLine) {
        this.startLine = startLine;
    }
}
