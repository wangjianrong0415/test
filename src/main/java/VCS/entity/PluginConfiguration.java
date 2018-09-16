package VCS.entity;


/**
 * Defines a plugin configuration that may be used to connect to a VS system.
 * @author Iiro Hietala 13.5.2014.
 */


public class PluginConfiguration {


    private String cloneUrl;

    private String commitIdPattern;

    private String localDirectory;

    private String password;

    private PluginType pluginType;

    private String userName;

    public String getCloneUrl() {
        return cloneUrl;
    }

    public String getCommitIdPattern() {
        return commitIdPattern;
    }

    public String getLocalDirectory() {
        return localDirectory;
    }

    public String getPassword() {
        return password;
    }

    public PluginType getPluginType() {
        return pluginType;
    }

    public String getUserName() {
        return userName;
    }

    public void setCloneUrl(String cloneUrl) {
        this.cloneUrl = cloneUrl;
    }

    public void setCommitIdPattern(String commitIdPattern) {
        this.commitIdPattern = commitIdPattern;
    }

    public void setLocalDirectory(String localDirectory) {
        this.localDirectory = localDirectory;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPluginType(PluginType pluginType) {
        this.pluginType = pluginType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
