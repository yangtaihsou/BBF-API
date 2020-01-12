
def exe(p) {

// orderClient.normalOrderCount
if( p.orderCountInfo==null || p.orderCountInfo.response<=0 ){
    return;
}
//PtResponses
def ptResponses = p.classlevelInfo[p.accountId]
if (ptResponses.ptCounts == null
     || ptResponses.ptCounts <= 0) {
         p.welcomeInfo.placement.repeat= Boolean.FALSE
 }


 }
