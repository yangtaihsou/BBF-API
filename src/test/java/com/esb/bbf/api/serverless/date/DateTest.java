package com.esb.bbf.api.serverless.date;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;

@Slf4j
public class DateTest {
    DateSdk date = new DateSdk();
    @Test
    public void testString(){
        log.info(date.string(new Date().getTime(),null));
        log.info(date.string(new Date().getTime(),"yyyy-MM-dd HH:mm:ss EE"));
        log.info(date.string(new Date().getTime(),"yyyy-MM-dd HH:mm:ss"));
        log.info(date.string(new Date().getTime(),"yyyy-MM-dd HH:mm"));
        log.info(date.string(new Date().getTime(),"yyyy-MM-dd HH"));
        log.info(date.string(new Date().getTime(),"yyyy-MM-dd"));
        log.info(date.string(new Date().getTime(),"MM/dd/yyyy hh:mm:ss.SSSa"));
        log.info(date.string(new Date().getTime(),"dd-MM-yyyy HH:mm:ss"));
        log.info(date.string(new Date().getTime(),"EEEE dd MMMM, yyyy HH:mm:ssa"));
        log.info(date.string(new Date().getTime(),"MM/dd/yyyy HH:mm ZZZZ"));
        log.info(date.string(new Date().getTime(),"MM/dd/yyyy HH:mm Z"));
        log.info(date.string(new Date().getTime(),"yyyy/MM/dd HH:mm:ss EE"));
        log.info(date.string(new Date().getTime(),"yyyy年MM月dd日 HH:mm:ss EE"));
    }
    @Test
    public void toLong(){

        long time = date.toLong("2019-09-18 11:39:41",null);
        log.info(time+"");
        log.info(date.string(time,null));

    }
    @Test
    public void plus(){
        long initTime = 1568777981000L;
        log.info(date.string(initTime,null));
        log.info( date.plus(initTime,null,59)+"");

        long time =  date.plus(initTime,"millis",59);
        log.info("millis"+date.string(time,null));
        time =  date.plus(initTime,"second",59);
        log.info("second"+date.string(time,null));
        time =  date.plus(initTime,"minute",59);
        log.info("minute"+date.string(time,null));
        time =  date.plus(initTime,"hour",59);
        log.info("hour"+date.string(time,null));
        time =  date.plus(initTime,"day",59);
        log.info("day"+date.string(time,null));
        time =  date.plus(initTime,"week",59);
        log.info("week"+date.string(time,null));
        time =  date.plus(initTime,"month",10);
        log.info("month"+date.string(time,null));
        time =  date.plus(initTime,"year",-59);
        log.info("year"+date.string(time,null));
    }
}
