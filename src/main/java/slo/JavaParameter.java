package slo;

import VCS.entity.AbstractDomainObject;

/**
 * Created by Iiro Hietala on 18.5.2014.
 */

public class JavaParameter extends AbstractDomainObject {

    private JavaMethod javaMethod;

    private JavaType javaType;

    private String name;

    public JavaParameter() {
        super();
    }

    public JavaParameter(JavaType javaType, String name) {
        this();
        this.javaType = javaType;
        this.name = name;
    }

    public JavaMethod getJavaMethod() {
        return javaMethod;
    }

    public JavaType getJavaType() {
        return javaType;
    }

    public String getName() {
        return name;
    }

    public void setJavaMethod(JavaMethod javaMethod) {
        this.javaMethod = javaMethod;
    }

    public void setJavaType(JavaType javaType) {
        this.javaType = javaType;
    }

    public void setName(String name) {
        this.name = name;
    }
}
