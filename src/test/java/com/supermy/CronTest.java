package com.supermy; /**
 * Created by moyong on 14/12/27.
 */
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;


        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Date;

        import org.quartz.CronExpression;

public class CronTest {

//    GetNextValidTimeAfter方法:得到下一个有效的时间，比如表达式配置的是： /10 0 0 * * ？
//
//    意思是每间隔10s会执行一次作业，如果当前时间是:2010/1/1 10:10:00
//
//    哪么这个方法将返回：2010/1/1 10:10:10
//
//    GetNextInvalidTimeAfter方法:是得到下一个无效时间
//
//    GetNextValidTimeAfter方法：是得到下一次执行的时间
//
//    GetNextValidTimeAfter方法：是指给定的一个时间是否满足cron-like表达式

//在cron计划时间段

    public static void main(String[] args) {
        cronTest();
    }

    private static void cronTest() {
        try {
//            每隔5秒执行一次：*/5 * * * * ?
//            每隔1分钟执行一次：0 */1 * * * ?
//            每天23点执行一次：0 0 23 * * ?
//            每天凌晨1点执行一次：0 0 1 * * ?
//            每月1号凌晨1点执行一次：0 0 1 1 * ?
//                    每月最后一天23点执行一次：0 0 23 L * ?
//                    每周星期天凌晨1点实行一次：0 0 1 ? * L
//            在26分、29分、33分执行一次：0 26,29,33 * * * ?
//            每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?

//            0 0 12 * * ?	每天12点触发
//            0 15 10 ? * *	每天10点15分触发
//            0 15 10 * * ?	每天10点15分触发
//            0 15 10 * * ? *	每天10点15分触发
//            0 15 10 * * ? 2005	2005年每天10点15分触发
//            0 * 14 * * ?	每天下午的 2点到2点59分每分触发
//            0 0/5 14 * * ?	每天下午的 2点到2点59分(整点开始，每隔5分触发)
//            0 0/5 14,18 * * ?	每天下午的 2点到2点59分、18点到18点59分(整点开始，每隔5分触发)
//            0 0-5 14 * * ?	每天下午的 2点到2点05分每分触发
//            0 10,44 14 ? 3 WED	3月分每周三下午的 2点10分和2点44分触发
//            0 15 10 ? * MON-FRI	从周一到周五每天上午的10点15分触发
//            0 15 10 15 * ?	每月15号上午10点15分触发
//            0 15 10 L * ?	每月最后一天的10点15分触发
//            0 15 10 ? * 6L	每月最后一周的星期五的10点15分触发
//            0 15 10 ? * 6L 2002-2005	从2002年到2005年每月最后一周的星期五的10点15分触发
//            0 15 10 ? * 6#3	每月的第三周的星期五开始触发
//            0 0 12 1/5 * ?	每月的第一个中午开始每隔5天触发一次
//            0 11 11 11 11 ?	每年的11月11号 11点11分触发(光棍节)

//            0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
//            0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时
//            0 0 12 ? * WED 表示每个星期三中午12点
//            "0 0 12 * * ?" 每天中午12点触发
//            "0 15 10 ? * *" 每天上午10:15触发
//            "0 15 10 * * ?" 每天上午10:15触发
//            "0 15 10 * * ? *" 每天上午10:15触发
//            "0 15 10 * * ? 2005" 2005年的每天上午10:15触发
//            "0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发
//            "0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发
//            "0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
//            "0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发
//            "0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发
//            "0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发
//            "0 15 10 15 * ?" 每月15日上午10:15触发
//            "0 15 10 L * ?" 每月最后一日的上午10:15触发
//            "0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发
//            "0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发
//            "0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发

            //把时间转化到秒
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd HH:mm:ss");//小写的mm表示的是分钟
            String dstr="201505145 20:31:01";
            java.util.Date date=sdf.parse(dstr);

            //0 0/30 9-17 * * ?   朝9晚8工作时间内每半小时
            CronExpression exp1 = new CronExpression("* 0/30 9-20 * * ?");
            //给定的时间是否符合表达式；是否在计划内的时间段
            System.out.println("给定的时间是否符合表达式："+exp1.isSatisfiedBy(date));


            CronExpression exp = new CronExpression("* 0/30 9-20 * * ?");
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMDD HH:mm:ss");
            Date d = new Date();
            int i = 0;
            // 循环得到接下来n此的触发时间点，供验证
            while (i < 10) {

                d = exp.getNextValidTimeAfter(d);
                System.out.println(df.format(d));
                ++i;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}

