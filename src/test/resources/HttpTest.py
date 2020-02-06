import httplib, urllib
def  exe():
      httpClient = None
      try:
            params = urllib.urlencode({'name': 'tom', 'age': 22})
            headers = {"Content-type": "application/x-www-form-urlencoded"
                  , "Accept": "text/plain"}

            httpClient = httplib.HTTPConnection("https://shop-staff-beta.batman.com", 80, timeout=30)
            httpClient.request("GET", "//shop-admin/login?ref=toolbar", params, headers)

            response = httpClient.getresponse()
            print response.status
            print response.reason
            print response.read()
            print response.getheaders() #获取头信息
      except Exception, e:
            print e
      finally:
            if httpClient:
                  httpClient.close()