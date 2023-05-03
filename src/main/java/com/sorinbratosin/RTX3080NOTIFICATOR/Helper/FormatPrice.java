package com.sorinbratosin.RTX3080NOTIFICATOR.Helper;


public class FormatPrice {

    public static Double format(String stringToFormatToDouble) {
        stringToFormatToDouble = stringToFormatToDouble.trim().toLowerCase().replace("lei", "").replaceAll("\\s+","");
        return Double.parseDouble(stringToFormatToDouble);
    }
}
