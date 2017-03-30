package com.xiaoka.xksupportutils.unit;

/**
 * Created by changping on 16/9/9.
 */
public class PriceUnitUtils {

//    static DecimalFormat df = new DecimalFormat("#.##");

    public static double removePriceUnitDouble(String price) {
        if (price==null||price.equals(""))
            return 0.0;
        if (price.contains("￥")) {
            return Double.parseDouble(price.split("￥")[1]);
        } else {
            return Double.parseDouble(price);
        }

    }

    public static String removePriceUnitString(String price) {
        if (price==null||price.equals(""))
            return "0.0";
        if (price.contains("￥")) {
            return formatBigNumber(Double.parseDouble(price.split("￥")[1]));
        } else {
            return formatBigNumber(Double.parseDouble(price));
        }

    }

    public static String setPriceUnit(String price) {
        if (price==null||price.equals(""))
            return "￥0";
        if (price.contains("￥") ) {
//            return df.format(Double.valueOf(price));
            return formatBigNumber((((double)(long)(Double.parseDouble(price.split("￥")[1])*100+0.5))/100));
        }else if (price.contains("¥")) {
//            return df.format(Double.valueOf(price));
            return formatBigNumber((((double)(long)(Double.parseDouble(price.split("¥")[1])*100+0.5))/100));
        }else {
            return "￥" + formatBigNumber((((double)(long)(Double.parseDouble(price)*100+0.5))/100));
        }
    }

    public static String setPriceUnit(double price) {
        return "￥" + formatBigNumber((((double)(long)(price*100+0.5))/100));
    }

    public static String setPriceUnit(int price) {
        return "￥" + formatBigNumber(price);
    }

    public static String formatBigNumber(double value) {
        if(value != 0.00){
            java.text.DecimalFormat df = new java.text.DecimalFormat("#########0.00");
            return df.format(value);
        }else{
            return "0.00";
        }

    }

    public static String formatBigNumberDouble(String value) {
        if(value != null){
            if(Double.parseDouble(value.toString()) != 0.0){
                java.text.DecimalFormat df = new java.text.DecimalFormat("#########0.00");
                return df.format(Double.parseDouble(value.toString()));
            }else{
                return "0.00";
            }
        }
        return "0.00";

    }

    public static String formatBigNumberInt(String value) {
        String price="0";
        if(value != null){
            if (value.contains(".")){
                price=value.split("\\.")[0];
            }else {
                price=value;
            }
            if(Integer.parseInt(price) != 0){
                java.text.DecimalFormat df = new java.text.DecimalFormat("##########");
                return df.format(Integer.parseInt(price));
            }else{
                return "0";
            }
        }
        return "0";

    }

    public static String formatBigNumber(Double value) {
        if(value != null){
            if(value.doubleValue() != 0.00){
                java.text.DecimalFormat df = new java.text.DecimalFormat("##########.00");
                return df.format(value.doubleValue());
            }else{
                return "0.00";
            }
        }
        return "";
    }

    public static String formatBigNumber(int value) {
        if(value != 0){
            java.text.DecimalFormat df = new java.text.DecimalFormat("##########");
            return df.format(value);
        }else{
            return "0";
        }

    }
}
