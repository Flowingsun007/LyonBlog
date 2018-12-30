package com.flowingsun.common.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static Calendar calendar = Calendar.getInstance();
    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/2 16:20
     *@Description  计算今天周几
     */
    public static int getTodayWeek(){
        calendar.setTime(new Date());
        int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(week<0) week=7;
        return week;
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/2 16:20
     *@Description  计算工作分钟数
     */
    public static int getMinute(Date startDate,Date endDate){
        long start = startDate.getTime();
        long end = endDate.getTime();
        int minute = (int)(end-start)/(1000*60);
        return minute;
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/2 16:27
     *@Description  从数据库获取的某个(时分秒)时间 + 当前年月日，返回装配后的时间
     */
    public static Date getDate(int hour,int minute,int second){
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        return calendar.getTime();
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/6 23:00
     *@Description 从数据库取出的某个(时分秒)时间 + 1970年1月1日，返回装配后的时间
     * P.S.不用装配，默认取出的(时分秒)时间会自动装配成此格式。
     */
    public static Date getPartTime(int hour,int minute,int second){
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        calendar.set(1970,0,1);
        return calendar.getTime();
    }

}
