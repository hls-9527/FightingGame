package com.huanlingsheng.domain;

import java.util.ArrayList;

/**
 * 英雄角色类（玩家角色）
 * 功能：继承Character类，添加魔法值和技能系统
 * 属性：魔法值、最大魔法值、技能列表
 */
public class Hero extends Character
{
    private int mp;                     // 当前魔法值
    private int maxMp;                  // 最大魔法值
    private ArrayList<String> skill;    // 技能列表
    private ArrayList<Consumable> packageList; // 道具背包

    /**
     * 无参构造方法
     */
    public Hero()
    {
        super();                        // 调用父类构造方法
        skill = new ArrayList<String>(); // 初始化技能列表
        packageList = new ArrayList<Consumable>(); // 初始化道具背包
        packageList.add(new Consumable("桃子", 0));
        packageList.add(new Consumable("煎蛋", 0));
        packageList.add(new Consumable("花酿鸡", 0));
        packageList.add(new Consumable("黄酒", 0));
        packageList.add(new Consumable("黑背鲈鱼", 0));
        packageList.add(new Consumable("白玉汤", 0));
    }

    /**
     * 带参构造方法
     * @param name 角色名称
     * @param hp 生命值
     * @param mp 魔法值
     * @param atk 攻击力
     * @param def 防御力
     */
    public Hero(String name, int hp, int mp, int atk, int def)
    {
        super(name, hp, atk, def);      // 调用父类构造方法
        this.mp = mp;
        this.maxMp = mp;                // 初始最大魔法值等于当前魔法值
        skill = new ArrayList<String>(); // 初始化技能列表
        packageList = new ArrayList<Consumable>(); // 初始化道具背包
        packageList.add(new Consumable("桃子", 0));
        packageList.add(new Consumable("煎蛋", 0));
        packageList.add(new Consumable("花酿鸡", 0));
        packageList.add(new Consumable("黄酒", 0));
        packageList.add(new Consumable("黑背鲈鱼", 0));
        packageList.add(new Consumable("白玉汤", 0));
    }

    // Getter和Setter方法

    /**
     * 获取技能列表
     * @return 技能列表
     */
    public ArrayList<String> getSkill()
    {
        return skill;
    }

    /**
     * 设置技能列表
     * @param skill 技能列表
     */
    public void setSkill(ArrayList<String> skill)
    {
        this.skill = skill;
    }

    /**
     * 获取当前魔法值
     * @return 当前魔法值
     */
    public int getMp()
    {
        return mp;
    }

    /**
     * 设置当前魔法值
     * @param mp 魔法值
     */
    public void setMp(int mp)
    {
        this.mp = mp;
    }

    /**
     * 获取最大魔法值
     * @return 最大魔法值
     */
    public int getMaxMp()
    {
        return maxMp;
    }

    /**
     * 设置最大魔法值
     * @param maxMp 最大魔法值
     */
    public void setMaxMp(int maxMp)
    {
        this.maxMp = maxMp;
    }

    public ArrayList<Consumable> getPackageList()
    {
        return packageList;
    }

    public void setPackageList(ArrayList<Consumable> packageList)
    {
        this.packageList = packageList;
    }

    /**
     * 恢复魔力值
     * @param Mp 恢复的魔力值
     */
    public void recoverMp(int Mp)
    {
        mp += Mp;           // 增加魔法值
        if (mp > maxMp)
        {
            mp = maxMp;     // 魔法值不能超过最大值
        }
    }

    /**
     * 扣除魔力值（使用技能时调用）
     * @param useMp 消耗的魔力值
     */
    public void useMagic(int useMp)
    {
        mp -= useMp;        // 减少魔法值
        if (mp < 0)
        {
            mp = 0;         // 魔法值不能为负数
        }
    }

    // 判断该道具是否可用
    public boolean isConsumableAvailable(String name)
    {
        for (Consumable consumable : packageList)
        {
            if (consumable.getName().equals(name))
            {
                return consumable.getNum() > 0;
            }
        }
        return false;
    }

    // 获得道具，数量+1
    public void getConsumable(String name)
    {
        for (Consumable consumable : packageList)
        {
            if (consumable.getName().equals(name))
            {
                consumable.setNum(consumable.getNum() + 1);
                break;
            }
        }
    }

    // 使用道具，数量-1
    public void useConsumable(String name)
    {
        for (Consumable consumable : packageList)
        {
            if (consumable.getName().equals(name))
            {
                consumable.setNum(consumable.getNum() - 1);
                break;
            }
        }
    }

    /**
     * 重写展示角色信息方法
     * @return 包含魔法值的角色信息字符串
     */
    @Override
    public String show()
    {
        return getName() + "[生命：" + getHp() +"/"+ getMaxHp() + "\t魔法值：" + getMp() +"/"+ getMaxMp() + "\t攻击力：" + getAtk() + "\t防御力：" + getDef() + "]";
    }
}