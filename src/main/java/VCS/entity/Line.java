package VCS.entity;


/**
 * Represents a line in source code that relates to a Hunk.
 *
 * Created by Iiro Hietala on 23.5.2014.
 */
public class Line extends AbstractDomainObject {

    private Hunk hunk;

    private String line;

    private Integer lineNumber;

    public Line() {
    }

    public Line(int lineNumber, String line) {
        this();
        this.lineNumber = lineNumber;
        this.line = line;
    }

    public Hunk getHunk() {
        return hunk;
    }

    public String getLine() {
        return line;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setHunk(Hunk hunk) {
        this.hunk = hunk;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }
}
