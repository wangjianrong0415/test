import VCS.GitService;
import VCS.impl.GitServiceImpl;
import project.Project;

public class testGit {

    public static void main(String args[]){
        Project project = Project.newProject();

        int b = 0;

        GitService gitService = new GitServiceImpl();
        gitService.cloneRepo(project.getPluginConfiguration().getCloneUrl(), project.getPluginConfiguration()
                .getLocalDirectory(), project.getPluginConfiguration().getUserName(), project.getPluginConfiguration()
                .getPassword());
//        for (String branch : gitService.listBranches(project.getPluginConfiguration().getLocalDirectory())) {
//            project.addBranch(new ProjectBranch(project, branch));
//        }

    }
}
