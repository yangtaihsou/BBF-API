import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET
import static groovyx.net.http.Method.POST
def exe() {
    def domain = 'https://shop-staff-beta.bitmain.com'
    def http = new HTTPBuilder(domain+'/shop-admin/login?ref=toolbar')
    def headerMap = [:]
    http.request(POST, ContentType.URLENC) { req ->
        //uri.path = '/mail/help/tasks/'
        headers.'User-Agent' = 'Mozilla/5.0'
      //  headers.'Set-Cookie' = "${myCookie}"
        body = 'password=Abcd1234!&username=sysadmin&validate=abcd'
        //请求成功
        response.success = { resp, reader ->
            List<String> cookies = [];
            resp.getHeaders('Set-Cookie').each {
                String cookie = it.value.split(';')[0]
               // println 'cookie==='+cookie
                cookies.add(cookie)
            }
            println 'cookie==='+cookies
            println resp.responseBase.statusLine
            //println reader.text
            //println postdata;
            //loginRequest(postdata,cookies);
            String location = resp.getHeaders('Location')[0].value
            println location
            headerMap['cookie']= cookies.join(';');
            def locationUrl = domain+location;
            locationRequest(locationUrl,headerMap)
            return headerMap
        }


    }
}

def locationRequest(locationUrl,headerMap){
    def http = new HTTPBuilder(locationUrl)
   // cookies = cookies.join(';')
    cookies = headerMap['cookie']
    println cookies
    List<String> logincookies = [];
    http.request(GET, ContentType.TEXT) {
        headers.'cookie' = cookies
        //println cookies
        // println postData
        response.success = { resp, reader ->
           // println reader.text
            resp.getHeaders('Set-Cookie').each {
                String cookie = it.value.split(';')[0]
                //println 'cookie==='+cookie
                logincookies.add(cookie)
            }
            cookies = cookies +";"+logincookies.join(';')

            headerMap['cookie']= cookies
            getShopAdminProductData(headerMap)
            getShopAdminSubscription(headerMap)
        }
    }
}


def getShopAdminProductData(headerMap){
    def http = new HTTPBuilder('https://shop-staff-beta.bitmain.com/shop-admin/product/data?type=0&page=1&pagesize=1')
    http.request(POST, ContentType.JSON) {
        cookies = headerMap['cookie'];
        println cookies
        headers.'cookie' = cookies
        //请求成功
        response.success = { resp, reader ->
           //println reader
        }

    }
}



def getShopAdminSubscription(headerMap){
    def http = new HTTPBuilder('https://shop-staff-beta.bitmain.com/shop-admin/subscription/data?mty=&mrl=&page=1&pagesize=1')
    http.request(POST, ContentType.JSON) {
        cookies = headerMap['cookie'];
        println cookies
        headers.'cookie' = cookies
        //请求成功
        response.success = { resp, reader ->
            println '--------------------'+reader
        }

    }
}
static main(def args) {
    //getHttp()
   def headerMap = exe()
    println headerMap
    //getShopcartList()
}


