package org.linlinjava.litemall.db.config;


import java.util.Objects;

/**
 * 线程用户信息
 */
public class WxUserThreadLocal {

    private WxUserThreadLocal() {
    }

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get() {
        return LOCAL.get();
    }

    public static Integer getUserLevel(){
        SysUser sysUser = LOCAL.get();
        return Objects.nonNull(sysUser) ? sysUser.getUserLevel() : null;
    }

    public static void remove() {
        LOCAL.remove();
    }
}