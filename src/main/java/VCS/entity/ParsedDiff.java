package VCS.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The ParsedDiff contains the lsit of Hunks that relate to a Diff.
 */

public class ParsedDiff extends AbstractDomainObject {


    private Diff diff;

    private List<Hunk> hunks = new ArrayList<Hunk>();

    public void addHunk(Hunk hunk) {
        if (!hunks.contains(hunk)) {
            hunks.add(hunk);
            hunk.setParsedDiff(this);
        }
    }

    public Diff getDiff() {
        return diff;
    }

    public List<Hunk> getHunks() {
        return hunks;
    }

    public void removeHunk(Hunk hunk) {
        if (hunks.contains(hunk)) {
            this.hunks.remove(hunk);
            hunk.setParsedDiff(null);
        }
    }

    public void setDiff(Diff diff) {
        this.diff = diff;
    }
}
