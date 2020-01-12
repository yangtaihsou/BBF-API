def exe(p) {
    p.log.info '-----estimateLevel={}',p.studentInfo.response.estimateLevel
    def estimate = [:]
    //设置WelcomeResponse的estimate
     p.welcomeInfo.response.estimate=estimate
     //$2是StudentResponse
     estimate.estimateLevel = p.studentInfo.response.estimateLevel
     estimate.estimateTime = p.studentInfo.response.estimateTime
     if(p.studentInfo.response!=null
     && p.studentInfo.response.estimateLevel!=null
     && p.studentInfo.response.estimateLevel!=-1){
            estimate.estimateLevelStr = "Level " + p.studentInfo.response.estimateLevel
     }
     p.classlevelInfo = [:]
     p.classlevelInfo.request=[:]
     p.classlevelInfo.request.uriVariables = null
     p.classlevelInfo.request.body = [p.accountId,p.accountId]
}
