package VCS.impl;

import VCS.GitService;
import VCS.entity.*;

import exception.JaceRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.IOUtils;

public class GitServiceImpl implements GitService {
    private static final Logger LOG = LoggerFactory.getLogger(GitServiceImpl.class);

    private static final String REF_HEAD = "HEAD";
    public static final String HUNK_IDENTIFIER = "@@";

    /**
     * Checkout a branch to local directory
     *
     * @param localDirectory
     *            Local directory with existing git repository
     * @param branch
     *            Branch to checkout
     */
    public void checkout(String localDirectory, String branch) {
        Repository repository = resolveRepository(localDirectory);
        try {
            if (!branch.equals(repository.getFullBranch())) {
                // Change branch if it differs from current branch
                Git git = new Git(repository);

                String shortName;
                if (branch.contains("/")) {
                    shortName = branch.substring(branch.lastIndexOf("/") + 1);
                } else {
                    shortName = branch; // most probably doesn't ever occur nor is sane in git sense
                }
                try {
                    git.branchCreate().setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK)
                            .setStartPoint(branch).setName(shortName).call();
                } catch (GitAPIException e) {
                    if (e instanceof RefAlreadyExistsException) {
                        // No worries, the branch already exists locally
                    } else {
                        throw new JaceRuntimeException("Failed to checkout branch '" + branch
                                + "' to local directory '" + localDirectory + "'", e);
                    }
                }

                try {
                    git.checkout().setName(shortName).setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK)
                            .call();
                } catch (GitAPIException e) {
                    throw new JaceRuntimeException("Failed to checkout branch '" + branch + "' to local directory ' "
                            + localDirectory + "'", e);
                }

            }
        } catch (IOException e) {
            throw new JaceRuntimeException("Failed to find a git repository in directory ' " + localDirectory + "'", e);
        } finally {
            repository.close();
        }
    }

    /**
     * Clone a git repository from given clone URL to the local directory
     *
     * @param cloneUrl
     *            URL to be cloned
     * @param localDirectory
     *            Local directory to clone into
     */
    public void cloneRepo(String cloneUrl, String localDirectory, String userName, String passWord) {
        try {
            CloneCommand cloneCommand = Git.cloneRepository().setURI(cloneUrl).setDirectory(new File(localDirectory));

            if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(passWord)) {
                UsernamePasswordCredentialsProvider credProv = new UsernamePasswordCredentialsProvider(userName,
                        passWord);
                cloneCommand.setCredentialsProvider(credProv);
            }
            LOG.info("cloneRepo({}, {}, {}, ***) called. Cloning...", cloneUrl, localDirectory, userName);
            Git call = cloneCommand.call();

            call.close();
            LOG.info("cloneRepo(): Clone OK: {}", cloneUrl);
        } catch (GitAPIException e) {
            throw new JaceRuntimeException("Failed to clone a remote git repository from URI '" + cloneUrl
                    + "' into directory ' " + localDirectory + "'", e);
        }
    }

    /**
     * List all remote branches for a local repository
     *
     * @param localDirectory
     *            Local directory with existing git repository
     * @return List of Branch names in an alphabetically sorted unmodifiable list
     */
    public List<String> listBranches(String localDirectory) {
        Repository repository = resolveRepository(localDirectory);

        List<Ref> branchList;
        Git git = new Git(repository);
        try {
            branchList = git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call();
        } catch (GitAPIException e) {
            throw new JaceRuntimeException("Failed to fetch a branch list for git repo ' " + localDirectory + "'", e);
        } finally {
            repository.close();
        }

        List<String> branches = new ArrayList<String>();
        for (Ref branch : branchList) {
            branches.add(branch.getName());
        }
        Collections.sort(branches);
        return Collections.unmodifiableList(branches);
    }

    /**
     * Parses a git diff and produces a ParsedDiff object
     *
     * @param diff
     *            Diff to parse
     * @return
     */
    protected ParsedDiff parseDiff(String diff) {
        ParsedDiff parsedDiff = new ParsedDiff();

        try {
            List<String> diffLines = IOUtils.readLines(new StringReader(diff));
            Hunk hunk = null;
            int oldLineNumber = 0;
            int newLineNumber = 0;
            for (String line : diffLines) {
                if (line.startsWith(HUNK_IDENTIFIER) && line.endsWith(HUNK_IDENTIFIER)) {
                    hunk = parseHunk(line);
                    parsedDiff.addHunk(hunk);
                    oldLineNumber = newLineNumber = 0;
                } else if (hunk != null) {
                    // Reading a hunk
                    if (!line.startsWith("-") && !line.startsWith("+")) {
                        newLineNumber++;
                        oldLineNumber++;
                    } else if (line.startsWith("+")) {
                        hunk.addAddedLine(new Line(hunk.getNewStartLine() + newLineNumber, line));
                        newLineNumber++;
                    } else if (line.startsWith("-")) {
                        hunk.addRemovedLine(new Line(hunk.getOldStartLine() + oldLineNumber, line));
                        oldLineNumber++;
                    }
                }
            }

        } catch (IOException e) {
            throw new IllegalStateException("Failed to parse git diff", e);
        }

        return parsedDiff;
    }

    private Matcher getMatcher(String pattern, String input) {
        Pattern p = Pattern.compile(pattern);
        return p.matcher(input);
    }

    private Hunk parseHunk(String line) {
        Hunk hunk = new Hunk();
        // A very fine implementation of
        // http://www.gnu.org/software/diffutils/manual/diffutils.html#Detailed-Unified

        Matcher matcher = getMatcher("-(\\d+) \\+(\\d+)", line);
        if (matcher.find()) {
            hunk.setOldStartLine(Integer.parseInt(matcher.group(1)));
            hunk.setOldLineCount(1);
            hunk.setNewStartLine(Integer.parseInt(matcher.group(2)));
            hunk.setNewLineCount(1);
        } else {
            matcher = getMatcher("-(\\d+),(\\d+) \\+(\\d+)", line);
            if (matcher.find()) {
                hunk.setOldStartLine(Integer.parseInt(matcher.group(1)));
                hunk.setOldLineCount(Integer.parseInt(matcher.group(2)));
                hunk.setNewStartLine(Integer.parseInt(matcher.group(3)));
                hunk.setNewLineCount(1);
            } else {
                matcher = getMatcher("-(\\d+),(\\d+) \\+(\\d+)", line);
                if (matcher.find()) {
                    hunk.setOldStartLine(Integer.parseInt(matcher.group(1)));
                    hunk.setOldLineCount(1);
                    hunk.setNewStartLine(Integer.parseInt(matcher.group(2)));
                    hunk.setNewLineCount(Integer.parseInt(matcher.group(3)));
                } else {
                    matcher = getMatcher("-(\\d+),(\\d+) \\+(\\d+),(\\d+)", line);
                    if (matcher.find()) {
                        hunk.setOldStartLine(Integer.parseInt(matcher.group(1)));
                        hunk.setOldLineCount(Integer.parseInt(matcher.group(2)));
                        hunk.setNewStartLine(Integer.parseInt(matcher.group(3)));
                        hunk.setNewLineCount(Integer.parseInt(matcher.group(4)));
                    }
                }
            }
        }
        return hunk;
    }


    public List<Diff> pull(String localDirectory, String username, String password) {
        LOG.info("GitServiceImpl.pull() called with localDirectory = " + localDirectory);
        Repository repository = resolveRepository(localDirectory);
        Git git = new Git(repository);

        String fullBranchName = resolveFullBranch(repository);
        Ref oldHead = null;
        try {
            oldHead = git.getRepository().findRef(fullBranchName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        PullResult pullResult = pullInternal(git, username, password);

        LOG.info("GitServiceImpl.pull: MergeStatus=" + pullResult.getMergeResult().getMergeStatus());
        if (!pullResult.getMergeResult().getMergeStatus().isSuccessful()) {
            throw new IllegalStateException("The merging was not successful when pulling.");
        }

        return resolveChanges(git, pullResult, oldHead);
    }

    /**
     * Performs pull operation
     * @param git
     * @param userName
     * @param passWord
     * @return
     */
    private PullResult pullInternal(Git git, String userName, String passWord) {
        LOG.info("GitServiceImpl.pullInternal() called");

        PullResult pullResult = null;
        try {

            PullCommand pullCommand = git.pull();

            if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(passWord)) {
                UsernamePasswordCredentialsProvider credProv = new UsernamePasswordCredentialsProvider(userName,
                        passWord);
                pullCommand.setCredentialsProvider(credProv);
            }

            pullResult = pullCommand.call();
        } catch (Exception e) {
            throw new JaceRuntimeException("Failed to pull with rebase into directory ' " + git.getRepository().getDirectory() + "'", e);
        } finally {
            git.getRepository().close();
        }
        LOG.info("GitServiceImpl.pullInternal: Returning pullResult.fetchResult: {}", pullResult != null ? pullResult.getFetchResult() : "null");
        return pullResult;
    }


    /**
     * Resolves changes based on pullResult and oldHead ref of the repo.
     *
     * @param git
     * @param pullResult
     * @param oldHead
     * @return
     */
    private List<Diff> resolveChanges(Git git, PullResult pullResult, Ref oldHead)  {
        LOG.info("GitServiceImpl.resolveChanges() called");
        List<Diff> diffs = new ArrayList<Diff>();

        String fullBranch = resolveFullBranch(git.getRepository());

        if (pullResult.getMergeResult().getMergeStatus() == MergeResult.MergeStatus.FAST_FORWARD) {
            Ref newHead = pullResult.getFetchResult().getAdvertisedRef(fullBranch);
            LOG.info("Old head Ref: " + oldHead.getName() + " objectId=" + oldHead.getObjectId() + " - New head Ref: Name=" + newHead.getName() + " objectId=" + newHead.getObjectId());
            List<RevCommit> revCommits = resolveRevCommits(git, oldHead.getObjectId(), newHead.getObjectId());
            diffs.addAll(resolveDiffs(git, oldHead.getObjectId(), revCommits));
        }

        return diffs;
    }

    /**
     * Resolves Diffs from a list of RevCommits starting from 'startCommitId'.
     * @param git
     * @param startCommitId ObjectId to start diffing
     * @param revCommits RevCommits to iterate
     * @return
     */
    private List<Diff> resolveDiffs(Git git, ObjectId startCommitId, List<RevCommit> revCommits) {
        LOG.info("GitServiceImpl.resolveDiffs() called. startCommitId={}", startCommitId);
        List<Diff> diffs = new ArrayList<Diff>();

        for (RevCommit commit : revCommits) {
            Commit jaceCommit = createCommit(commit);
            LOG.debug("GitServiceImpl.resolveDiffs: Created new Commit: " + jaceCommit.toHumanReadable());

            LOG.debug("GitServiceImpl.resolveDiffs: Diffing old and new versions...");

            // Diff the old and new revisions and iterate the diffs.
            DiffCommand diffCommand = git.diff().setOldTree(resolveTreeIterator(git.getRepository(), startCommitId))
                    .setNewTree(resolveTreeIterator(git.getRepository(), commit.toObjectId()));
            try {
                List<DiffEntry> diffEntries = diffCommand.call();
                LOG.info("GItServiceImpl.resolveDiffs: There are " + diffEntries.size() + " diffs. Parsing them...");

                diffs.addAll(parseDiffEntries(git, jaceCommit, diffEntries));
                startCommitId = commit.toObjectId();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return diffs;
    }

    private Commit createCommit(RevCommit commit) {
        Commit jaceCommit = new Commit();
        jaceCommit.setMessage(StringUtils.left(commit.getFullMessage().toString(), 4090));
        jaceCommit.setAuthorName(commit.getAuthorIdent().getName());
        jaceCommit.setAuthorEmail(commit.getAuthorIdent().getEmailAddress());
        jaceCommit.setAuthorTimeZone(commit.getAuthorIdent().getTimeZone());
        jaceCommit.setAuthorTimeZoneOffSet(commit.getAuthorIdent().getTimeZoneOffset());
        jaceCommit.setAuthorDateOfChange(commit.getAuthorIdent().getWhen());
        return jaceCommit;
    }

    private List<Diff> parseDiffEntries(Git git, Commit jaceCommit, List<DiffEntry> diffEntries) throws IOException {
        LOG.info("GitServiceImpl.parseDiffEntries() called");
        List<Diff> diffs = new ArrayList<Diff>();
        for (DiffEntry diffEntry : diffEntries) {
            LOG.info("GitServiceImpl.parseDiffEntries: Parsing diffEntry: " + diffEntry.toString());
            ByteArrayOutputStream diffOut = new ByteArrayOutputStream();
            DiffFormatter diffFormatter = new DiffFormatter(diffOut);
            diffFormatter.setRepository(git.getRepository());
            diffFormatter.format(diffEntry);

            LOG.info("GitServiceImpl.parseDIffEntries: Creating new J-ACE Diff");
            // Collect changed data and store it in db temporarily until analysis
            Diff diff = new Diff();
            diff.setOldPath(diffEntry.getOldPath());
            diff.setNewPath(diffEntry.getNewPath());
            diff.setParsedDiff(parseDiff(diffOut.toString())); // FIXME: encoding
            diff.setModificationType(ModificationType.valueOf(diffEntry.getChangeType().name()));
            diff.setCommit(jaceCommit);
            diffs.add(diff);
        }
        return diffs;
    }

    /**
     * Resolves List of RevCommit objects in chronological order between the given two objectIds.
     * @param git
     * @param oldObjectId
     * @param newObjectId
     * @return
     */
    private List<RevCommit> resolveRevCommits(Git git, ObjectId oldObjectId, ObjectId newObjectId) {
        LOG.info("GitServiceImpl.resolveRevCommits() called. oldObjectId=" + oldObjectId + " newObjectId=" + newObjectId);
        Iterable<RevCommit> commits = null;
        try {
            commits = git.log().addRange(oldObjectId, newObjectId).call();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        // Only process current branch changes
        List<RevCommit> revCommits = new ArrayList<RevCommit>();

        for (RevCommit commit : commits) {
            revCommits.add(commit);
        }
        Collections.reverse(revCommits);
        LOG.info("Returning " + revCommits.size() + " RevCommits");
        return revCommits;
    }

    private String resolveFullBranch(Repository repository) {
        try {
            return repository.getFullBranch();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Resolves the .git dir for a given dir and builds a Repository object
     *
     * @param localDirectory
     * @return
     */
    private Repository resolveRepository(String localDirectory) {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository;
        try {
            repository = builder.setGitDir(new File(localDirectory + "/.git")).build();
        } catch (IOException e) {
            throw new JaceRuntimeException("Failed to find a git repository in directory ' " + localDirectory + "'", e);
        }
        return repository;
    }

    /**
     * Resolves TreeIterator for a given objectId
     * @param repository
     * @param objectId
     * @return
     */
    private AbstractTreeIterator resolveTreeIterator(Repository repository, ObjectId objectId) {
        // from the commit we can build the tree which allows us to construct the TreeParser
        //noinspection Duplicates
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(objectId);
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();

            return treeParser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
