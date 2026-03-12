package com.huanlingsheng.domain;

import java.util.ArrayList;

/**
 * 敌人类
 * 功能：继承Character类，添加敌人特有属性和技能
 * 属性：技能名称、吸血状态、吸血状态计数
 */
public class Enemy extends Character
{
    private String skill;               // 敌人技能名称
    private boolean bloodsucking;       // 吸血状态标记（斗转星移技能）
    private int bloodsuckingcount;      // 吸血状态剩余次数

    /**
     * 无参构造方法
     */
    public Enemy()
    {
        super();                        // 调用父类构造方法
        skill = "";                     // 初始化技能为空
    }

    /**
     * 带参构造方法
     * @param name 敌人名称
     * @param hp 生命值
     * @param atk 攻击力
     * @param def 防御力
     * @param skill 技能名称
     */
    public Enemy(String name, int hp, int atk, int def, String skill)
    {
        super(name, hp, atk, def);      // 调用父类构造方法
        this.skill = skill;
        bloodsuckingcount = 0;          // 初始吸血状态计数为0
    }

    // Getter和Setter方法

    /**
     * 获取敌人技能名称
     * @return 技能名称
     */
    public String getSkill()
    {
        return skill;
    }

    /**
     * 设置敌人技能名称
     * @param skill 技能名称
     */
    public void setSkill(String skill)
    {
        this.skill = skill;
    }

    /**
     * 获取吸血状态剩余次数
     * @return 吸血状态次数
     */
    public int getBloodsuckingcount()
    {
        return bloodsuckingcount;
    }

    /**
     * 设置吸血状态剩余次数
     * @param bloodsuckingcount 吸血状态次数
     */
    public void setBloodsuckingcount(int bloodsuckingcount)
    {
        this.bloodsuckingcount = bloodsuckingcount;
    }

    /**
     * 判断是否处于吸血状态
     * @return true-吸血状态，false-正常状态
     */
    public boolean isBloodsucking()
    {
        return bloodsucking;
    }

    /**
     * 设置吸血状态
     * @param bloodsucking 吸血状态
     */
    public void setBloodsucking(boolean bloodsucking)
    {
        this.bloodsucking = bloodsucking;
    }

    /**
     * 减少吸血状态计数
     * 功能：每次攻击后减少吸血状态计数，归零时关闭吸血状态
     */
    public void decreaseBloodsuckingcount()
    {
        if(bloodsuckingcount>0)
        {
            bloodsuckingcount--;        // 减少吸血状态次数
        }
        else
        {
            bloodsucking=false;         // 吸血状态结束
        }
    }
}