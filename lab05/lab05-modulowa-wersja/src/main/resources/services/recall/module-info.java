module recall {
    requires analysisserviceapi;
    provides ex.api.AnalysisService with RecallAnalysisService;
}