package VCS;

import VCS.entity.Diff;

import java.util.List;

public interface Service {
    void checkout(String localDirectory, String branch);

    /**
     * Clone a repository
     *
     * @param cloneUrl
     *            Remote URL
     * @param localDirectory
     *            Local Directory
     * @param userName
     *            Username (optional, may be null)
     * @param passWord
     *            Password (optional, may be null))
     */
    void cloneRepo(String cloneUrl, String localDirectory, String userName, String passWord);

    /**
     * List all branches in a repository
     * @param localDirectory The local directory where the local repository resides
     * @return List of Strings containing the exact branch names
     */
    List<String> listBranches(String localDirectory);

    /**
     * Performs a 'pull' operation in to a repository. The end result is that most recent changes are
     * updated to the current branch.
     * @param localDirectory The local directory where the local repository resides
     * @param userName The possible user name for the repository (optional)
     * @param password The possible pasword word the repository (optional)
     * @return
     */
    List<Diff> pull(String localDirectory, String userName, String password);
}
