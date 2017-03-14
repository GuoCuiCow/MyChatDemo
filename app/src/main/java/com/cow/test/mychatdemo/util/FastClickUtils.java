package com.cow.test.mychatdemo.util;

/**
 * @author Bryce
 * @date 2015/10/30
 * @time 9:13
 */
public class FastClickUtils {

    private static long time;

    private static class SingletonHolder {
        private static final FastClickUtils INSTANCE  = new FastClickUtils();
    }

    private FastClickUtils() {
    }

    public static final FastClickUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public boolean isFastClick() {
        long curTime = System.currentTimeMillis();
        if (curTime - time < 400) {
            time = curTime;
            return true;
        } else {
            time = curTime;
            return false;
        }

    }

}
