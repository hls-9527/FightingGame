package com.huanlingsheng.domain;

import java.util.Random;

/**
 * 用户实体类
 * 功能：存储用户登录注册信息
 * 属性：用户ID、用户名、密码、手机号、状态
 */
public class User
{
    private String id;        // 用户ID：自动生成，格式为nangua+5位随机数字
    private String username;  // 用户名：唯一，长度3-16位，字母数字组合
    private String password;  // 密码：长度3-8位，字母加数字组合
    private String phone;     // 手机号：11位纯数字，以1开头
    private boolean status;   // 状态：true-可用，false-锁定（密码输错3次后锁定）

    /**
     * 无参构造方法
     * 功能：创建用户时自动生成ID并设置状态为可用
     */
    public User()
    {
        id = setId();         // 自动生成用户ID
        this.status = true;   // 默认状态为可用
    }

    /**
     * 带参构造方法
     * @param username 用户名
     * @param password 密码
     * @param phone 手机号
     */
    public User(String username, String password, String phone)
    {
        id = setId();         // 自动生成用户ID
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.status = true;   // 默认状态为可用
    }

    // Getter和Setter方法

    /**
     * 获取用户ID
     * @return 用户ID
     */
    public String getId()
    {
        return id;
    }

    /**
     * 自动生成用户ID
     * 规则：nangua + 5位随机数字
     * @return 生成的用户ID
     */
    public String setId()
    {
        StringBuilder sb = new StringBuilder("nangua");  // ID前缀
        Random r = new Random();
        // 生成5位随机数字
        for (int i = 0; i < 5; i++)
        {
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 获取手机号
     * @return 手机号
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * 设置手机号
     * @param phone 手机号
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * 获取用户名
     * @return 用户名
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * 设置用户名
     * @param username 用户名
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * 获取密码
     * @return 密码
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * 设置密码
     * @param password 密码
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * 获取用户状态
     * @return true-可用，false-锁定
     */
    public boolean isStatus()
    {
        return status;
    }

    /**
     * 设置用户状态
     * @param status 用户状态
     */
    public void setStatus(boolean status)
    {
        this.status = status;
    }
}