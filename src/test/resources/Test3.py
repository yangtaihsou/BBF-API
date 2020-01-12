
def exe(p) {
 p.returnData = [:]
 p.returnData.code=200
  p.returnData.message = "SUCCESS"
  p.returnData.data=p.welcomeInfo.response
// ptServiceApi.getClassLevelByListAccountIds返回值
if( p.classlevelInfo==null){
return;
}

//PtResponses
def ptResponses = p.classlevelInfo.response[p.accountId]
println 'ptResponses='+ptResponses
if( ptResponses==null){
return;
}
def classLevel = ptResponses.classLevel

if(classLevel!=null && classLevel!=-1){
    p.welcomeInfo.response.placement.entryPoint = "Level " + classLevel;
}

 //设置WelcomeResponse的placement
  p.welcomeInfo.response.placement.repeat=true
//是否执行下一步
 p.exeNext = true;
}
