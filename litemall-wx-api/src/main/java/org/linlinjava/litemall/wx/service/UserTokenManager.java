package org.linlinjava.litemall.wx.service;

import org.linlinjava.litemall.wx.dto.SysUser;
import org.linlinjava.litemall.wx.util.JwtHelper;

/**
 * 维护用户token
 */
public class UserTokenManager {
	public static String generateToken(Integer id) {
        JwtHelper jwtHelper = new JwtHelper();
        return jwtHelper.createToken(id);
    }
    public static Integer getUserId(String token) {
    	JwtHelper jwtHelper = new JwtHelper();
    	Integer userId = jwtHelper.verifyTokenAndGetUserId(token);
    	if(userId == null || userId == 0){
    		return null;
    	}
        return userId;
    }

    public static String generateToken(Integer id, Integer userLevel) {
        JwtHelper jwtHelper = new JwtHelper();
        return jwtHelper.createToken(id, userLevel);
    }
    public static Integer getUserLevel(String token) {
        JwtHelper jwtHelper = new JwtHelper();
        Integer userLevel = jwtHelper.verifyTokenAndGetUserLevel(token);
        if(userLevel == null ){
            return null;
        }
        return userLevel;
    }

    public static SysUser getSysUser(String token){
        JwtHelper jwtHelper = new JwtHelper();
        SysUser sysUser = jwtHelper.getSysUser(token);
        if (sysUser == null || sysUser.getUserId() == null){
            return null;
        }
        return sysUser;
    }
}
