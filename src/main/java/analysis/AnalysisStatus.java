package analysis;


/**
 * Created by Iiro Hietala on 17.5.2014.
 */
public enum AnalysisStatus{
    COMPLETE("analysisStatus.complete"),

    INITIAL_ANALYSIS("analysisStatus.initialAnalysis"),

    ANALYSING("analysisStatus.analysing"),

    ERROR("analysisStatus.error"),

    DISABLED("analysisStatus.disabled");

    private String resourceKey;

    AnalysisStatus(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getResourceKey() {
        return this.resourceKey;
    }
}
