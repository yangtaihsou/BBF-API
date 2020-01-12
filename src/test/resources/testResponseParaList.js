var requestpara = {}
requestpara.age = '10'
requestpara.name = 'yangkuan'
var bookList = [];
bookList[0] = 'java';
bookList[1] = 'python';
//requestpara.bookList = bookList
p.log.info('-----request={}',requestpara)

var paraMap = {}
paraMap.httpMethod = 'POST'
paraMap.httpPara = requestpara
var response = p.http.call(
    "http://bbf-api/test/page/testResponseParaList",
    paraMap);
p.log.info('-----response={}',response)
return response