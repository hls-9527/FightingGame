package com.huanlingsheng.domain;

/**
 * 角色基类
 * 功能：定义游戏角色的基本属性和行为
 * 属性：名称、生命值、攻击力、防御力、状态等
 */
public class Character
{
    private String name;           // 角色名称
    private int hp;                // 当前生命值
    private int maxHp;             // 最大生命值
    private int atk;               // 攻击力
    private int def;               // 防御力
    private boolean defending;     // 防御状态标记
    private boolean attacking;     // 攻击状态标记（蓄势待发）
    private int defendingcount;    // 防御状态剩余回合数

    /**
     * 无参构造方法
     */
    public Character()
    {

    }

    /**
     * 带参构造方法
     * @param name 角色名称
     * @param hp 生命值
     * @param atk 攻击力
     * @param def 防御力
     */
    public Character(String name, int hp, int atk, int def)
    {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;           // 初始最大生命值等于当前生命值
        this.atk = atk;
        this.def = def;
        defendingcount = 0;        // 初始防御状态回合数为0
    }

    // Getter和Setter方法

    /**
     * 获取角色名称
     * @return 角色名称
     */
    public String getName()
    {
        return name;
    }

    /**
     * 设置角色名称
     * @param name 角色名称
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * 获取当前生命值
     * @return 当前生命值
     */
    public int getHp()
    {
        return hp;
    }

    /**
     * 设置当前生命值
     * @param hp 生命值
     */
    public void setHp(int hp)
    {
        this.hp = hp;
    }

    /**
     * 获取最大生命值
     * @return 最大生命值
     */
    public int getMaxHp()
    {
        return maxHp;
    }

    /**
     * 设置最大生命值
     * @param maxHp 最大生命值
     */
    public void setMaxHp(int maxHp)
    {
        this.maxHp = maxHp;
    }

    /**
     * 获取攻击力
     * @return 攻击力
     */
    public int getAtk()
    {
        return atk;
    }

    /**
     * 设置攻击力
     * @param atk 攻击力
     */
    public void setAtk(int atk)
    {
        this.atk = atk;
    }

    /**
     * 获取防御力
     * @return 防御力
     */
    public int getDef()
    {
        return def;
    }

    /**
     * 设置防御力
     * @param def 防御力
     */
    public void setDef(int def)
    {
        this.def = def;
    }

    /**
     * 获取防御状态剩余回合数
     * @return 防御状态回合数
     */
    public int getDefendingcount()
    {
        return defendingcount;
    }

    /**
     * 设置防御状态剩余回合数
     * @param defendingcount 防御状态回合数
     */
    public void setDefendingcount(int defendingcount)
    {
        this.defendingcount = defendingcount;
    }

    /**
     * 判断是否处于防御状态
     * @return true-防御状态，false-正常状态
     */
    public boolean isDefending()
    {
        return defending;
    }

    /**
     * 设置防御状态
     * @param defending 防御状态
     */
    public void setDefending(boolean defending)
    {
        this.defending = defending;
    }

    /**
     * 减少防御状态回合数
     * 功能：每回合结束时减少防御状态计数，归零时关闭防御状态
     */
    public void decreaseEnemyDefendingcount()
    {
        if (defendingcount > 0)
        {
            defendingcount--;      // 减少防御状态回合数
        }
        else
        {
            defending = false;     // 防御状态结束
        }
    }

    public void decreasePlayerDefendingcount()
    {
        if (defendingcount > 0)
        {
            defendingcount--;      // 减少防御状态回合数
            if (defendingcount == 0)
            {
                defending = false;     // 防御状态结束
            }
        }

    }

    /**
     * 判断是否处于攻击状态（蓄势待发）
     * @return true-攻击状态，false-正常状态
     */
    public boolean isAttacking()
    {
        return attacking;
    }

    /**
     * 设置攻击状态
     * @param attacking 攻击状态
     */
    public void setAttacking(boolean attacking)
    {
        this.attacking = attacking;
    }

    /**
     * 判断角色是否存活
     * @return true-存活，false-死亡
     */
    public boolean isAlive()
    {
        return hp > 0;  // 生命值大于0表示存活
    }

    /**
     * 角色受到伤害
     * @param damage 受到的伤害值
     */
    public void takeDamage(int damage)
    {
        hp -= damage;   // 减少生命值
        if (hp < 0)
        {
            hp = 0;     // 生命值不能为负数
        }
    }

    /**
     * 角色恢复生命值
     * @param hp 恢复的生命值
     */
    public void recoverHp(int hp)
    {
        this.hp += hp;  // 增加生命值
        if (this.hp > maxHp)
        {
            this.hp = maxHp;  // 生命值不能超过最大值
        }
    }

    /**
     * 展示角色信息
     * @return 角色信息字符串
     */
    public String show()
    {
        return name + "[生命：" + hp + "\t攻击力：" + atk + "\t防御力：" + def + "]";
    }
}