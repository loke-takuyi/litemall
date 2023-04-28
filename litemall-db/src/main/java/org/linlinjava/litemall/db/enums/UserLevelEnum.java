package org.linlinjava.litemall.db.enums;

/**
 * @author loke
 * @version 1.0.0
 * @description TODO
 * @date 2023/04/28 16:36
 */
public enum UserLevelEnum {
    tag_user(0, "普通用户"),
    retail_user(1, "零售用户"),
    wholesale_user(2, "批发用户"),
    ;

    public Integer code;
    public String description;

    UserLevelEnum(Integer userLevel, String description) {
        this.code = userLevel;
        this.description = description;
    }
}
