package com.peng.utils.thread;

import com.peng.model.wemedia.pojo.WmUser;

/**
 * @author: pengshengfeng
 * @date: 2023/9/6 10:35
 * @description:
 */
public class WmThreadLocalUtil {

    private static final ThreadLocal<WmUser> WM_USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void setWmUser(WmUser wmUser){
        WM_USER_THREAD_LOCAL.set(wmUser);
    }

    public static WmUser getWmUser(){
        return WM_USER_THREAD_LOCAL.get();
    }

    public static void  removeWmUser(){
        WM_USER_THREAD_LOCAL.remove();
    }
}
