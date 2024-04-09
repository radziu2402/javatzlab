module kappa {
    requires analysisserviceapi;
    provides ex.api.AnalysisService with KappaAnalysisService;
}