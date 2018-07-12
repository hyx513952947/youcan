package top.huangguaniu.youcan.data.remote.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 * @author 侯延旭
 * @date 2018/7/11
 */
public class DateBean {

    private int status;
    private String message;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * year : 2018
         * month : 7
         * day : 11
         * lunarYear : 2018
         * lunarMonth : 5
         * lunarDay : 28
         * cnyear : 贰零壹捌
         * cnmonth : 五
         * cnday : 廿八
         * hyear : 戊戌
         * cyclicalYear : 戊戌
         * cyclicalMonth : 己未
         * cyclicalDay : 甲辰
         * suit : 纳采,订盟,嫁娶,移徙,入宅,出行,祭祀,祈福,斋醮,塑绘,开光,安香,出火,会亲友,解除,入学,竖柱,上梁,拆卸,盖屋,起基,栽种,牧养,纳畜
         * taboo : 安葬,破土,开市,开仓,出货财,启钻
         * animal : 狗
         * week : Wednesday
         * festivalList : ["中国航海日"]
         * jieqi : {"7":"小暑","23":"大暑"}
         * maxDayInMonth : 29
         * leap : false
         * lunarYearString : 戊戌
         * bigMonth : false
         */

        private int year;
        private int month;
        private int day;
        private int lunarYear;
        private int lunarMonth;
        private int lunarDay;
        private String cnyear;
        private String cnmonth;
        private String cnday;
        private String hyear;
        private String cyclicalYear;
        private String cyclicalMonth;
        private String cyclicalDay;
        private String suit;
        private String taboo;
        private String animal;
        private String week;
        private JieqiBean jieqi;
        private int maxDayInMonth;
        private boolean leap;
        private String lunarYearString;
        private boolean bigMonth;
        private List<String> festivalList;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getLunarYear() {
            return lunarYear;
        }

        public void setLunarYear(int lunarYear) {
            this.lunarYear = lunarYear;
        }

        public int getLunarMonth() {
            return lunarMonth;
        }

        public void setLunarMonth(int lunarMonth) {
            this.lunarMonth = lunarMonth;
        }

        public int getLunarDay() {
            return lunarDay;
        }

        public void setLunarDay(int lunarDay) {
            this.lunarDay = lunarDay;
        }

        public String getCnyear() {
            return cnyear;
        }

        public void setCnyear(String cnyear) {
            this.cnyear = cnyear;
        }

        public String getCnmonth() {
            return cnmonth;
        }

        public void setCnmonth(String cnmonth) {
            this.cnmonth = cnmonth;
        }

        public String getCnday() {
            return cnday;
        }

        public void setCnday(String cnday) {
            this.cnday = cnday;
        }

        public String getHyear() {
            return hyear;
        }

        public void setHyear(String hyear) {
            this.hyear = hyear;
        }

        public String getCyclicalYear() {
            return cyclicalYear;
        }

        public void setCyclicalYear(String cyclicalYear) {
            this.cyclicalYear = cyclicalYear;
        }

        public String getCyclicalMonth() {
            return cyclicalMonth;
        }

        public void setCyclicalMonth(String cyclicalMonth) {
            this.cyclicalMonth = cyclicalMonth;
        }

        public String getCyclicalDay() {
            return cyclicalDay;
        }

        public void setCyclicalDay(String cyclicalDay) {
            this.cyclicalDay = cyclicalDay;
        }

        public String getSuit() {
            return suit;
        }

        public void setSuit(String suit) {
            this.suit = suit;
        }

        public String getTaboo() {
            return taboo;
        }

        public void setTaboo(String taboo) {
            this.taboo = taboo;
        }

        public String getAnimal() {
            return animal;
        }

        public void setAnimal(String animal) {
            this.animal = animal;
        }

        public String getWeek() {
            if ("Monday".equals(week)){
                return "星期一";
            }else if ("Tuesday".equals(week)){
                return "星期二";
            }else if ("Wednesday".equals(week)){
                return "星期三";
            }else if ("Thursday".equals(week)){
                return "星期四";
            }else if ("Friday".equals(week)){
                return "星期五";
            }else if ("Saturday".equals(week)){
                return "星期六";
            }else if ("Sunday".equals(week)){
                return "星期日";
            }
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public JieqiBean getJieqi() {
            return jieqi;
        }

        public void setJieqi(JieqiBean jieqi) {
            this.jieqi = jieqi;
        }

        public int getMaxDayInMonth() {
            return maxDayInMonth;
        }

        public void setMaxDayInMonth(int maxDayInMonth) {
            this.maxDayInMonth = maxDayInMonth;
        }

        public boolean isLeap() {
            return leap;
        }

        public void setLeap(boolean leap) {
            this.leap = leap;
        }

        public String getLunarYearString() {
            return lunarYearString;
        }

        public void setLunarYearString(String lunarYearString) {
            this.lunarYearString = lunarYearString;
        }

        public boolean isBigMonth() {
            return bigMonth;
        }

        public void setBigMonth(boolean bigMonth) {
            this.bigMonth = bigMonth;
        }

        public List<String> getFestivalList() {
            return festivalList;
        }

        public void setFestivalList(List<String> festivalList) {
            this.festivalList = festivalList;
        }

        public static class JieqiBean {
            /**
             * 7 : 小暑
             * 23 : 大暑
             */

            @SerializedName("7")
            private String _$7;
            @SerializedName("23")
            private String _$23;

            public String get_$7() {
                return _$7;
            }

            public void set_$7(String _$7) {
                this._$7 = _$7;
            }

            public String get_$23() {
                return _$23;
            }

            public void set_$23(String _$23) {
                this._$23 = _$23;
            }
        }
    }
}
