
    println p
    println m
 p.log.info '-----estimateLevel={}','high level'
    def response = p.rpc.execute(
                    "http://user/api/v1/student/{studentId}/equipment_test/welcome?button={button}",
                    'GET', null, p);

    p.log.info '-----response={}',response
    return response
