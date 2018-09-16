package VCS.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Represents a Commit in a VCS. Contains common information that is stored in a Commit.
 *
 * Created by Iiro Hietala on 23.5.2014.
 */
// 'Commit' seems to be reserved word (internal error from EclipseLink)
public class Commit extends AbstractDomainObject {

    private String author;

    private Date authorDateOfChange;

    private String authorEmail;

    private String authorName;

    private TimeZone authorTimeZone;

    private Integer authorTimeZoneOffSet;

    private String commitId;

    private List<Diff> diffs = new ArrayList<Diff>();

    private String message;

    private String formattedTimeZoneOffset;

    private void addDiff(Diff diff) {
        if (!diffs.contains(diff)) {
            diffs.add(diff);
            diff.setCommit(this);
        }
    }

    public String getAuthor() {
        return author;
    }

    public Date getAuthorDateOfChange() {
        return authorDateOfChange;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public String getAuthorName() {
        return authorName;
    }

    public TimeZone getAuthorTimeZone() {
        return authorTimeZone;
    }

    public Integer getAuthorTimeZoneOffSet() {
        return authorTimeZoneOffSet;
    }

    public String getCommitId() {
        return commitId;
    }

    public List<Diff> getDiffs() {
        return diffs;
    }

    public String getFormattedTimeZoneOffset() {
        if (getAuthorTimeZoneOffSet() != null) {
            int tz = getAuthorTimeZoneOffSet() / 60;
            StringBuilder sb = new StringBuilder();
            sb.append("UTC");
            if (tz < 0) {
                sb.append("-");
            } else {
                sb.append("+");
            }
            sb.append(tz);
            return sb.toString();
        } else {
            return "";
        }
    }

    public String getMessage() {
        return message;
    }

    private void removeDiff(Diff diff) {
        if (diffs.contains(diff)) {
            diffs.remove(diff);
            diff.setCommit(null);
        }
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAuthorDateOfChange(Date authorDateOfChange) {
        this.authorDateOfChange = authorDateOfChange;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setAuthorTimeZone(TimeZone authorTimeZone) {
        this.authorTimeZone = authorTimeZone;
    }

    public void setAuthorTimeZoneOffSet(Integer authorTimeZoneOffSet) {
        this.authorTimeZoneOffSet = authorTimeZoneOffSet;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toHumanReadable() {
        StringBuilder sb = new StringBuilder(1024);
        sb.append("Commit ID=").append(this.commitId)
                .append(" Message=").append(this.message)
                .append(" Author=").append(this.author)
                .append(" E-mail=").append(this.authorEmail)
                .append(" Diffs=").append(this.diffs.size()).append("\r\n");
        return sb.toString();
    }
}
