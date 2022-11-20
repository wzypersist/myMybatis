package com.wzy.mybatisXML.utils;

import java.util.Collection;
import java.util.Map;

public class CommonUtils {
    
    public static boolean isNotEmpty(String src){return src != null && src.trim().length()>0;}
    
    public static boolean isNotEmpty(Collection<?> collection){return collection != null && !collection.isEmpty();}

    public static boolean isNotEmpty(Map<?,?> map){return map != null && !map.isEmpty();}

    public static boolean isNotEmpty(Object[] objects){return objects != null && objects.length > 0;}

    public static String stringTrim(String src){return (src != null) ? src.trim() : null;}

}
