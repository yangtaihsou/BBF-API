function exe(p) {
    p.log.info('-----estimateLevel={}','high level')
    var response = p.rpc.execute(
                    "http://user/api/v1/student/{studentId}/equipment_test/welcome?button={button}",
                    'GET', null, p);

    p.log.info('-----response={}',response)
    return response
}
