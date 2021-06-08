package com.romantulchak.virtualuniversity.utils;

public class PageUtil {
    public static int getFrontendPageNumber(int page){
        if(page <= 0){
            return 0;
        }
        return page - 1;
    }
}
