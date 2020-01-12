def exe(p):
    print p
    print p['log']
    p['log'].info('-----estimateLevel={}','high level')
    response = p['rpc'].execute(
                    "http://user/api/v1/student/{studentId}/equipment_test/welcome?button={button}",
                    'GET', None, p);

    p['log'].info('-----response={}',response)
    return response
