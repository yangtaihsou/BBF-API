
def exe(p) {
    println 'g1='+p.$1.equipment.os
    p.test='test123'
    p.accountIds=[1,23,56]
    def estimate = [:]
    estimate.estimateLevel = '1'
    estimate.estimateLevelStr = 'Level '+estimate.estimateLevel
    p.estimate = estimate
    p.$1.estimate = estimate
     def person = [:]
     person.name='yangkuan'
     p.person = person
}
