package com.bm.csxy.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.format.Time;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author TangWei 2013-10-24上午10:38:01
 */
public class Tools {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 格式化价格
     *
     * @param argStr
     * @return
     */
    public static String getFloatDotStr(String argStr) {
        float arg = Float.valueOf(argStr);
        DecimalFormat fnum = new DecimalFormat("##0.00");
        return fnum.format(arg);
    }

    /**
     * 格式化价格
     *
     * @param argStr
     * @return
     */
    public static String getFloatCommaStr(String argStr) {
        argStr = argStr.replace(",", ".");
        float arg = Float.valueOf(argStr);
        DecimalFormat fnum = new DecimalFormat("##0.00");
        return fnum.format(arg).replace(".", ",");
    }

    public static boolean IsHaveInternet(final Context context) {
        try {
            ConnectivityManager manger = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manger.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        } catch (Exception e) {
            return false;
        }
    }

    // 得到versionName
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;

    }

    public static String millisToString(long millis) {
        boolean negative = millis < 0;
        millis = Math.abs(millis);

        millis /= 1000;
        int sec = (int) (millis % 60);
        millis /= 60;
        int min = (int) (millis % 60);
        millis /= 60;
        int hours = (int) millis;

        String time;
        DecimalFormat format = (DecimalFormat) NumberFormat
                .getInstance(Locale.US);
        format.applyPattern("00");
        if (millis > 0) {
            time = (negative ? "-" : "")
                    + (hours == 0 ? 00 : hours < 10 ? "0" + hours : hours)
                    + ":" + (min == 0 ? 00 : min < 10 ? "0" + min : min) + ":"
                    + (sec == 0 ? 00 : sec < 10 ? "0" + sec : sec);
        } else {
            time = (negative ? "-" : "") + min + ":" + format.format(sec);
        }
        return time;
    }

    // 得到versionName
    public static int getVerCode(Context context) {
        int verCode = 0;
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;

    }

    /**
     * 判断 多个字段的值否为空
     *
     * @return true为null或空; false不null或空
     * @author Michael.Zhang 2013-08-02 13:34:43
     */
    public static boolean isNull(String... ss) {
        for (int i = 0; i < ss.length; i++) {
            if (null == ss[i] || ss[i].equals("")
                    || ss[i].equalsIgnoreCase("null")) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断 一个字段的值否为空
     *
     * @param s
     * @return
     * @author Michael.Zhang 2013-9-7 下午4:39:00
     */
    public static boolean isNull(String s) {
        if (null == s || s.trim().equals("") || s.equalsIgnoreCase("null")) {
            return true;
        }

        return false;
    }

    /**
     * 判断sd卡是否存在
     *
     * @return
     * @author Michael.Zhang 2013-07-04 11:30:54
     */
    public static boolean judgeSDCard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 显示纯汉字的星期名称
     *
     * @param i 星期：1,2,3,4,5,6,7
     * @return
     * @author TangWei 2013-10-25上午11:31:51
     */
    public static String changeWeekToHanzi(int i) {
        switch (i) {
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            case 7:
                return "星期日";
            default:
                return "";
        }
    }

    /**
     * 验证手机号码
     *
     * @param phone
     * @return
     * @author TangWei
     */
    public static boolean validatePhone(String phone) {
        Pattern p = Pattern
                .compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
        Matcher matcher = p.matcher(phone);
        return matcher.matches();
    }

    public static boolean validateLoginPassWord(String pwd) {
        if (isNull(pwd))
            return false;
        String pattern = "[a-zA-Z0-9]{6,16}";
        return pwd.matches(pattern);

    }

    /**
     * 检验用户名 可以输入a到z 0到9 汉字的3到8位字符
     *
     * @param pwd
     * @return
     */
    public static boolean validateUserName(String pwd) {
        if (isNull(pwd))
            return false;
        String pattern = "[a-zA-Z0-9\u4E00-\u9FA5]{3,8}";
        return pwd.matches(pattern);

    }

    /**
     * 检查身份证是 否合法,15位或18位(或者最后一位为X)
     */
    public static boolean validateIdCard(String idCard) {
        if (isNull(idCard)) {
            return false;
        }
        return idCard.matches("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$");
    }

    /**
     * 简单的验证一下银行卡号
     *
     * @param bankCard 信用卡是16位，其他的是13-19位
     * @return
     */
    public static boolean validateBankCard(String bankCard) {
        if (isNull(bankCard))
            return false;
        String pattern = "^\\d{13,19}$";
        return bankCard.matches(pattern);
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     * @author TangWei 2013-12-13下午2:33:16
     */
    public static boolean validateEmail(String email) {
        if (isNull(email))
            return false;
        String pattern = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
        return email.matches(pattern);
    }

    /**
     * 将100以内的阿拉伯数字转换成中文汉字（15变成十五）
     *
     * @param round 最大值50
     * @return >99的，返回“”
     */
    public static String getHanZi1(int round) {
        if (round > 99 || round == 0) {
            return "";
        }
        int ge = round % 10;
        int shi = (round - ge) / 10;
        String value = "";
        if (shi != 0) {
            if (shi == 1) {
                value = "十";
            } else {
                value = getHanZi2(shi) + "十";
            }

        }
        value = value + getHanZi2(ge);
        return value;
    }

    /**
     * 将0-9 转换为 汉字（ _一二三四五六七八九）
     *
     * @param round
     * @return
     */
    public static String getHanZi2(int round) {
        String[] value = {"", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        return value[round];
    }

    /**
     * 将服务器返回的日期转换为固定日期
     *
     * @param str
     * @return
     */
    public static String convertoZPMTime(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = "";
        if (date != null) {
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy.M.dd");
            result = format2.format(date);
        }

        return result;

    }

    /**
     * 将服务器返回的日期转换为优惠券需要显示的日期
     *
     * @param str
     * @return
     */
    public static String convertoCouponTime(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = "";
        if (date != null) {
            SimpleDateFormat format2 = new SimpleDateFormat("M/dd");
            result = format2.format(date);
        }

        return result;

    }

    /**
     * 得到设备屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static byte[] getImgByte(String filePath) {
        File file = new File(filePath);
        InputStream is = null;
        byte[] b = null;
        try {
            is = new FileInputStream(file);
            b = new byte[Integer.parseInt(String.valueOf(file.length()))];
            is.read(b);
        } catch (Exception e) {

        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    public static String getBASE64(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        // 对字节数组Base64编码
//		BASE64Encoder encoder = new BASE64Encoder();
//		return encoder.encode(data);//返回Base64编码过的字节数组字符串
//		return DESUtil.Base64.encode(bytes) ;// 返回Base64编码过的字节数组字符串

        String encodeString = new String(Base64.encode(bytes, Base64.DEFAULT));
        Log.e("yzh", encodeString);
        return encodeString;
    }

    public static String encodeBase64(byte[] input) throws Exception {
        Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
        Method mainMethod = clazz.getMethod("encode", byte[].class);
        mainMethod.setAccessible(true);
        Object retObj = mainMethod.invoke(null, new Object[]{input});
        return (String) retObj;
    }

    public static String fileBase64String(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = fis.read(buffer)) >= 0) {
                baos.write(buffer, 0, count);

            }
            fis.close();
            String uploadBuffer = new String(Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT));
            return uploadBuffer;
        } catch (Exception e) {
            return null;

        }
    }

    public static Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static boolean compareNowTime(String startTime, String endTime) {
        boolean isDayu = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date parse = dateFormat.parse(startTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();
            if (diff <= 0) {
                isDayu = true;
            } else {
                isDayu = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isDayu;
    }

    public static Long getTimeValue(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long value = 0;
        try {
            Date parse = dateFormat.parse(time);
            value = parse.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowTime() {
        String timeString = null;
        Time time = new Time();
        time.setToNow();
        String year = thanTen(time.year);
        String month = thanTen(time.month + 1);
        String monthDay = thanTen(time.monthDay);
        String hour = thanTen(time.hour);
        String minute = thanTen(time.minute);
        String s = thanTen(time.second);
        timeString = year + "-" + month + "-" + monthDay + " " + hour + ":" + minute + ":" + s;
        return timeString;
    }

    public static String getNowTime1() {
        String timeString = null;
        Time time = new Time();
        time.setToNow();
        String year = thanTen(time.year);
        String month = thanTen(time.month + 1);
        String monthDay = thanTen(time.monthDay);
        String hour = thanTen(time.hour);
        String minute = thanTen(time.minute);
        String s = thanTen(time.second);
        timeString = year + "-" + month + "-" + monthDay;
        return timeString;
    }

    /**
     * 十一下加零
     *
     * @param str
     * @return
     */
    public static String thanTen(int str) {

        String string = null;

        if (str < 10) {
            string = "0" + str;
        } else {
            string = "" + str;
        }
        return string;
    }



    public static double spilt19_7(String content) {
        double value = 0;
        Log.e("yzh", "--" + content);
        if (content.split(",").length > 1) {
            String s19 = content.split(",")[0].split(":")[1].substring(1, content.split(",")[0].split(":")[1].length() - 1);
            String s7 = content.split(",")[1].split(":")[1].substring(1, content.split(",")[1].split(":")[1].length() - 2);
            value = Double.parseDouble(s19) + Double.parseDouble(s7);
        } else {
            String s19 = content.split(",")[0].split(":")[1].substring(1, content.split(",")[0].split(":")[1].length() - 1);
            value = Double.parseDouble(s19);
        }

        return value;

    }

    public static double spilt19(String content) {
        if (content.contains("vat_19")) {
            String s19 = content.split(",")[0].split(":")[1].substring(1, content.split(",")[0].split(":")[1].length() - 1);
            return Double.parseDouble(s19);
        } else {
            return 0;
        }


    }

    public static double spilt7(String content) {
        if (content.contains("vat_7")) {
            String s7 = content.split(",")[1].split(":")[1].substring(1, content.split(",")[1].split(":")[1].length() - 2);
            return Double.parseDouble(s7);
        } else {
            return 0;
        }

    }



    //设置国际化语言
    public static void setLanguage(Context context, Locale myLocale) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    /**
     * 获取本地的版本号
     *
     * @return
     */
    public static String getVersionNameNum(Context context) {
        try {
            // 获取的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packInfo.versionName;
        } catch (Exception e) {

        }
        return "";
    }

    public static boolean checkNet(Context context){
        NetworkInfo netIntfo = null;
        ConnectivityManager cm ;
                try {
                       cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
                       netIntfo =  cm.getActiveNetworkInfo();
                     } catch (Exception e) {
                        //异常处理
                       return false;
                     }
        if(netIntfo==null){
            return false;
        }else{
            return true;
        }
    }




    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static boolean checkBankCard(String cardNo){
        if(Tools.isNull(cardNo)){
            return false;
        }
        return cardNo.matches("^\\d{13,16}$|^\\d{19}$");
    }

    public static boolean checkCreditCard(String cardNo){
        if(checkOther(cardNo)){
            return true;
        }else{
            Pattern p = Pattern
                    .compile("^\\d{13,16}$|^\\d{19}$");
            Matcher matcher = p.matcher(cardNo);
            return matcher.matches();
        }

    }

    public static boolean checkOther(String cardNo){
        if(cardNo.length()==13||cardNo.length()==14||cardNo.length()==15||cardNo.length()==16){
            String s2=cardNo.substring(cardNo.length()-4);
            String s1=cardNo.substring(0,cardNo.length()-4);
            for(int i=0;i<s1.toCharArray().length;i++){
                if(!(s1.toCharArray()[i]+"").equals("x")){
                    Log.e("yzh","1");
                    return false;
                }
            }
            try {
                Integer.parseInt(s2);
            }catch (Exception e){
                Log.e("yzh","2");
                return false;
            }
            return  true;
        }else{
            Log.e("yzh","3");
            return false;
        }
    }


    public static String changeMoneyRule(String money){
        String result="";
        String regEx="[^-0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(money);
        String string = m.replaceAll(" ").trim();

        //以空格为分割符在讲数字存入一个字符串数组中
        String[] strArr = string.split(" ");

        for(int i=0;i<strArr.length;i++){
            if(i!=strArr.length-1){
                result+=strArr[i];
            }else{
                if(i==0){
                    result+=strArr[i];
                }else{
                    result+=("."+strArr[i]);
                }

            }
        }
        return result;
    }



    public static double changeStringToDouble(String content){
        double result=0;
        try {
            result=Double.parseDouble(content);
            return result;
        }catch (Exception e){
            return 0;
        }
    }

    public static boolean checkHaveChinese(String name){
        char[] chars=name.toCharArray();
        for(int i=0;i<chars.length;i++){
            if(isChinese(chars[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * 判定输入汉字
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     * @param name
     * @return
     */
    public static boolean checkNameChese(String name)
    {
        boolean res=true;
        char [] cTemp = name.toCharArray();
        for(int i=0;i<name.length();i++)
        {
            if(!isChinese(cTemp[i]))
            {
                res=false;
                break;
            }
        }
        return res;
    }

    public static String formatTime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }

    public static String getHanyuPinyin(String content){
        char[] target= content.toCharArray();
        String result="";
        String temp="";
        ArrayList<String> list=new ArrayList<>();
        if(Tools.isNull(content)){
            return result;
        }


        try {
            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

            for (int i = 0; i< target.length; i++){

                if(Character.toString(target[i]).matches("[\\u4E00-\\u9FA5]+")){
//                        if(i==0){
//                            result+=PinyinHelper.toHanyuPinyinStringArray(target[i],format)[0];
//                        }else{
//                            result+=PinyinHelper.toHanyuPinyinStringArray(target[i],format)[0]+" ";
//                        }
                    list.add(temp);
                    temp="";
                    list.add(PinyinHelper.toHanyuPinyinStringArray(target[i],format)[0]);


                }else{
                    if(!Character.toString(target[i]).equals(" ")){
                        if((target[i]+"").matches("[0-9]+")){
                            if(!Tools.isNull(temp)){
                                if(isNumeric(temp)){
                                    //上次存储的都是数字
                                    temp+=target[i];
                                }else{
                                    //不是数字
                                    list.add(temp);
                                    temp=target[i]+"";
                                }
                            }else{
                                temp+=target[i];
                            }
                        }else{
                            if(!Tools.isNull(temp)){
                                if(isNumeric(temp)){
                                    //上次存储的都是数字
                                    list.add(temp);
                                    temp=target[i]+"";

                                }else{
                                    //不是数字
                                    temp+=target[i];
                                }
                            }else{
                                temp+=target[i];
                            }
                        }
                    }else{
                        list.add(temp);
                        temp="";
                        list.add(Character.toString(target[i]));
                    }

                }

//                        result+=target[i];
            }




            list.add(temp);
        }catch (Exception e){

        }

        for(int i=0;i<list.size();i++){
            if(Tools.isNull(list.get(i))){
//                result+=list.get(i);
            }else{
                result+=list.get(i)+" ";
            }

        }

        return result.toUpperCase().trim();
    }
}
