package project;

/**
 * Configuration options for determining the release version of a project
 *
 * @author Iiro Hietala 13.5.2014.
 */
public class ReleaseInfo {

    private String pathToVersionFile;

    /**
     * Regular expression pattern or XPath depending on versionFileType
     */
    private String pattern;

    private VersionFileType versionFileType;

    public String getPathToVersionFile() {
        return pathToVersionFile;
    }

    public String getPattern() {
        return pattern;
    }

    public VersionFileType getVersionFileType() {
        return versionFileType;
    }

    public void setPathToVersionFile(String pathToVersionFile) {
        this.pathToVersionFile = pathToVersionFile;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setVersionFileType(VersionFileType versionFileType) {
        this.versionFileType = versionFileType;
    }
}
