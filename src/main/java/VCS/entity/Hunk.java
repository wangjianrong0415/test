package VCS.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Hunk that has similar data as GNU Unified Diff.
 *
 * Created by Iiro Hietala on 23.5.2014.
 */

public class Hunk extends AbstractDomainObject {

    private List<Line> addedLines = new ArrayList<Line>();


    private Integer newLineCount;

    private Integer newStartLine;

    private Integer oldLineCount;

    private Integer oldStartLine;

    private ParsedDiff parsedDiff;

    private List<Line> removedLines = new ArrayList<Line>();

    public void addAddedLine(Line line) {
        if (!addedLines.contains(line)) {
            addedLines.add(line);
            line.setHunk(this);
        }
    }

    public void addRemovedLine(Line line) {
        if (!removedLines.contains(line)) {
            removedLines.add(line);
            line.setHunk(this);
        }
    }

    public List<Line> getAddedLines() {
        return addedLines;
    }

    public Integer getNewLineCount() {
        return newLineCount;
    }

    public Integer getNewStartLine() {
        return newStartLine;
    }

    public Integer getOldLineCount() {
        return oldLineCount;
    }

    public Integer getOldStartLine() {
        return oldStartLine;
    }

    public List<Line> getRemovedLines() {
        return removedLines;
    }

    public void removeAddedLine(Line line) {
        if (addedLines.contains(line)) {
            addedLines.remove(line);
            line.setHunk(null);
        }
    }

    public void removeRemovedLine(Line line) {
        if (removedLines.contains(line)) {
            removedLines.remove(line);
            line.setHunk(null);
        }
    }

    public void setNewLineCount(Integer newLineCount) {
        this.newLineCount = newLineCount;
    }

    public void setNewStartLine(Integer newStartLine) {
        this.newStartLine = newStartLine;
    }

    public void setOldLineCount(Integer oldLineCount) {
        this.oldLineCount = oldLineCount;
    }

    public void setOldStartLine(Integer oldStartLine) {
        this.oldStartLine = oldStartLine;
    }

    public ParsedDiff getParsedDiff() {
        return parsedDiff;
    }

    public void setParsedDiff(ParsedDiff parsedDiff) {
        this.parsedDiff = parsedDiff;
    }
}
