package slo;


/**
 * Represents a JavaType (i.e. ParameterType, VariableType, etc.)
 * 
 */
public class JavaType {

    private String fullyQualifiedType;

    private String type;

    public JavaType() {
        super();
    }

    public JavaType(String fullyQualifiedType, String type) {
        this();
        this.fullyQualifiedType = fullyQualifiedType;
        this.type = type;
    }

    public String getFullyQualifiedType() {
        return fullyQualifiedType;
    }

    public String getType() {
        return type;
    }

    public void setFullyQualifiedType(String fullyQualifiedName) {
        this.fullyQualifiedType = fullyQualifiedName;
    }

    public void setType(String name) {
        this.type = name;
    }
}
