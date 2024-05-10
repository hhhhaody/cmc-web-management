package com.example.utils;
import lombok.experimental.UtilityClass;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
     * 拼音工具类
     */
    @UtilityClass
    public class PinYinUtil {

        /**
         * 获取汉字串拼音，英文字符不变
         * @param chinese 汉字串
         * @return 汉语拼音
         */
        public static String getFullSpell(String chinese) {
            StringBuffer pybf = new StringBuffer();
            char[] arr = chinese.toCharArray();
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > 128) {
                    try {
                        pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                } else {
                    pybf.append(arr[i]);
                }
            }
            return pybf.toString();
        }

        /**
         * 获取汉字串拼音首字母，英文字符不变
         * @param chinese 汉字串
         * @return 汉语拼音首字母
         */
        public static String getFirstSpell(String chinese) {
            StringBuffer pybf = new StringBuffer();
            char[] arr = chinese.toCharArray();
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > 128) {
                    try {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                        if (temp != null) {
                            pybf.append(temp[0].charAt(0));
                        }
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                } else {
                    pybf.append(arr[i]);
                }
            }
            return pybf.toString().replaceAll("\\W", "").trim();
        }

        public static String getBatchString(String name, String spec, LocalDateTime operateTime){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMddHHmm");
            System.out.print(name);
            System.out.print(spec);
            System.out.print(operateTime);
            return getFirstSpell(name).toUpperCase()+"#"+spec+"-"+dtf.format(operateTime);
        }

    public static String getBatchStringDate(String name, String spec, LocalDateTime operateTime, Integer num){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");
        return getFirstSpell(name).toUpperCase()+"#"+spec+"-"+dtf.format(operateTime) +"-"+ String.format("%03d", num);
    }

    public static String getSerialString(String name, String batch, String batchSame){
        return getFirstSpell(name).toUpperCase()+batch+"-"+batchSame;
    }

        public static void main(String[] args) {

            System.out.println(getFullSpell("吃饭了吗"));
            System.out.println(getFirstSpell("吃饭了吗").toUpperCase());
            System.out.println(getBatchStringDate("sdf","1",LocalDateTime.now(),2));
        }

}
