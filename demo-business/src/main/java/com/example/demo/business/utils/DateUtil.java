package com.example.demo.business.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具
 *
 * @author xieqiuchen on 2020-03-11
 */
public class DateUtil {

    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String HHmm = "HHmm";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String HH_mm = "HH:mm";
    public static final String yyyy_MM_dd_chinese = "yyyy年MM月dd日";
    public static final String yyyy_MM_dd_HHmmss = "yyyy-MM-dd HHmmss";

    private static FastDateFormat fdf_yyyy_MM_dd_HH_mm_ss = FastDateFormat.getInstance(yyyy_MM_dd_HH_mm_ss);
    private static FastDateFormat fdf_yyyy_MM_dd = FastDateFormat.getInstance(yyyy_MM_dd);
    private static FastDateFormat fdf_yyyyMMdd = FastDateFormat.getInstance(yyyyMMdd);
    private static FastDateFormat fdf_HH_mm = FastDateFormat.getInstance(HHmm);
    private static FastDateFormat fdf_yyyyMMddHHmmss = FastDateFormat.getInstance(yyyyMMddHHmmss);


    //获得年月日
    public static Date getParsedYMD(Date date) {
        String str = fdf_yyyy_MM_dd.format(date);
        try {
            return DateUtils.parseDate(str, yyyy_MM_dd);
        } catch (ParseException e) {
            return null;
        }
    }


    //获取某个日期的开始时间
    public static Date getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    //获取某个日期的结束时间
    public static Date getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date addMonth(Date date, int amout) {
        return changeTime(date, amout, Calendar.MONTH);
    }

    public static Date addDay(Date date, int amout) {
        return changeTime(date, amout, Calendar.DATE);
    }

    public static Date addMinute(Date date, int amout) {
        return changeTime(date, amout, Calendar.MINUTE);
    }

    /**
     * type Calendar.MINUTE、Calendar.DATE、Calendar.MONTH
     *
     * @param date
     * @param amout
     * @param type
     * @return
     */
    private static Date changeTime(Date date, int amout, Integer type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, amout);
        return calendar.getTime();
    }

    public static String parse2String(Date date, String format) {
        return FastDateFormat.getInstance(format).format(date);
    }

    public static String parse2String(Date date) {
        return FastDateFormat.getInstance(yyyy_MM_dd_HH_mm_ss).format(date);
    }


    public static Date string2date(String dateString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date string2date(String dateString) {
        return string2date(dateString, yyyy_MM_dd_HH_mm_ss);
    }


    /**
     * 计算2个日期之间相差的  相差多少年月日
     * 比如：2011-02-02 到  2017-03-02 相差 6年，1个月，0天
     *
     * @param fromDate YYYY-MM-DD
     * @param toDate   YYYY-MM-DD
     * @return 年, 月, 日 例如 1,1,1
     */
    public static String dayComparePrecise(String fromDate, String toDate) {
        Period period = Period.between(LocalDate.parse(fromDate), LocalDate.parse(toDate));
        StringBuffer sb = new StringBuffer();
        sb.append(Math.abs(period.getYears())).append(",")
                .append(Math.abs(period.getMonths())).append(",")
                .append(Math.abs(period.getDays()));
        return sb.toString();
    }

    /**
     * 获取两个日期的月份差值
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static int getMonthDiff(Date fromDate, Date toDate) {
        Period period = Period.between(LocalDate.parse(parse2String(fromDate, DateUtil.yyyy_MM_dd)), LocalDate.parse(parse2String(toDate, DateUtil.yyyy_MM_dd)));
        return Math.abs(period.getMonths());
    }

    /**
     * 获取两个日期的天差值
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static int getDayDiff(Date fromDate, Date toDate) {
        Period period = Period.between(LocalDate.parse(parse2String(fromDate, DateUtil.yyyy_MM_dd)), LocalDate.parse(parse2String(toDate, DateUtil.yyyy_MM_dd)));
        return Math.abs(period.getDays());
    }

    /**
     * 获取给定日期的前N天
     */
    public static Date getBeforeDate(Date fromDate, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 获得当天0点时间
     *
     * @return
     */
    public static Date getTimesMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得当天0点时间
     *
     * @return
     */
    public static Date getTimesMorning(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得当天24点时间
     *
     * @return
     */
    public static Date getTimesNight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得当天24点时间
     *
     * @return
     */
    public static Date getTodayEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        return cal.getTime();
    }

    /**
     * 获得当天24点时间
     *
     * @return
     */
    public static Date getTimesNight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /**
     * 获取昨天的开始时间
     *
     * @return
     */
    public static Date getTimesMorningOfYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesMorning());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取昨天的结束时间
     *
     * @return
     */
    public static Date getTimesNightOfYesterDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesNight());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取明天的开始时间
     *
     * @return
     */
    public static Date getTimesMorningOfTomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesMorning());
        cal.add(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    /**
     * 获取明天的结束时间
     *
     * @return
     */
    public static Date getTimesNightOfTomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesNight());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获得本周一0点时间
     *
     * @return
     */
    public static Date getTimesWeekMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }


    /**
     * 获得本周日24点时间
     *
     * @return
     */
    public static Date getTimesWeekNight() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekMorning());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTime();
    }

    /**
     * 获得本月第一天0点时间
     *
     * @return
     */
    public static Date getTimesMonthMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * 获得那个月的第一天0点时间
     *
     * @return
     */
    public static Date getTimesMonthMorning(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * 获得本月最后一天24点时间
     *
     * @return
     */
    public static Date getTimesMonthNight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }

    /**
     * 获得那个月的最后一天24点时间
     *
     * @return
     */
    public static Date getTimesMonthNight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }

    /**
     * 计算当前时间后一天的开始时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date getTimesStartOfTomorrow(Date date, Integer day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 计算当前时间后一天的结束时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date getTimesEndOfTomorrow(Date date, Integer day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取两个时间节点之前差几天
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getBetweenNowDays(Date startTime, Date endTime) {
        long endTimeLong = endTime.getTime();
        long stateTimeLong = startTime.getTime();
        // 结束时间-开始时间 = 天数
        return (endTimeLong - stateTimeLong) / (24 * 60 * 60 * 1000);
    }

    /**
     * 现在距当天0点的相对时间 单位 毫秒
     *
     * @return
     */
    public static long getTodayDiff() {
        return System.currentTimeMillis() - DateUtil.getTimesMorning().getTime();
    }

    /**
     * 现在周几
     * 1,2,3,4,5,6,7
     *
     * @return
     */
    public static int getWeekOffSite(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int offSite = c.get(Calendar.DAY_OF_WEEK) - 1;
        return offSite == 0 ? 7 : offSite;
    }

    /**
     * 当前时间在指定范围内
     *
     * @param start
     * @param end
     * @return
     */
    private static Boolean suitNow(Long start, Long end) {
        long todayDiff = DateUtil.getTodayDiff();
        return todayDiff >= start && todayDiff < end;
    }

    /**
     * 当前时间在指定星期范围内
     *
     * @param weekDayList
     * @return
     */
    private static Boolean suitWeek(List<Integer> weekDayList) {
        return weekDayList.contains(DateUtil.getWeekOffSite(new Date()));
    }

    /**
     * 计算两个时间段相差多少天
     *
     * @param oldDate
     * @param newDate
     * @return
     */
    public static Long getDiffDay(Date oldDate, Date newDate) {
        if (oldDate == null || newDate == null) {
            return null;
        }
        long time = getTimesMorning(newDate).getTime() - getTimesMorning(oldDate).getTime();
        return time / (3600 * 24 * 1000);
    }


    public static void main(String[] args) {
        // System.out.println(getDayStartTime(new Date()));
        // System.out.println(getDayEndTime(new Date()));
        // System.out.println(getParsedYMD(new Date()));
        System.out.println(dayComparePrecise("2021-05-20", "2021-07-21"));
        System.out.println(dayComparePrecise("2021-05-21", "2021-07-21"));
        System.out.println(dayComparePrecise("2021-05-22", "2021-07-21"));
        System.out.println(dayComparePrecise("2021-09-22", "2021-07-21"));
        System.out.println(dayComparePrecise("2021-10-22", "2021-07-21"));

    }


}
