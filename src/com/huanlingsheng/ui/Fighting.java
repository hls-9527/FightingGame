package com.huanlingsheng.ui;

import com.huanlingsheng.domain.Enemy;
import com.huanlingsheng.domain.Hero;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * 战斗系统类
 * 功能：处理游戏的核心战斗逻辑，包括角色创建、战斗回合、技能系统等
 * 作者：南瓜
 * 版本：3.1
 */
public class Fighting
{
    // 扫描器对象，用于接收用户输入
    Scanner sc = new Scanner(System.in);

    /**
     * 游戏启动方法
     * 功能：初始化游戏环境，创建角色，管理战斗流程
     *
     * @param username 玩家用户名
     */
    public void gameStart(String username)
    {
        // 显示游戏的标题
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("    🎮 " + username + "欢迎来到文字格斗游戏 🎮   ");
        System.out.println("╚════════════════════════════════════════╝");

        // 创建玩家角色
        Hero player = createPlayer(username);
        System.out.println("角色创建成功！");

        // 显示角色信息
        System.out.println();
        System.out.println("🌟 初始属性为：" + player.show());
        System.out.println("🌟 拥有技能：" + player.getSkill());

        // 添加敌人列表
        ArrayList<Enemy> enemyList = new ArrayList<>();
        enemyList.add(new Enemy("初级战士", 160, 25, 20, "猛击"));
        enemyList.add(new Enemy("敏捷刺客", 100, 40, 10, "快速攻击"));
        enemyList.add(new Enemy("重装坦克", 200, 20, 30, "防御姿态"));
        enemyList.add(new Enemy("神秘法师", 120, 35, 15, "斗转星移"));
        enemyList.add(new Enemy("游猎射手", 130, 30, 15, "蓄势待发"));

        // 初始化嘲讽系统
        ArrayList<String> trashTalk = new ArrayList<>();
        trashTalk.add("我都比你会玩，你这操作看得我尴尬癌都犯了。");
        trashTalk.add("通关？你连活着走出战斗都做不到。");
        trashTalk.add("你的血条是纸糊的吧，一碰就碎。");
        trashTalk.add("你这水平，建议直接投降，省得浪费时间。");
        trashTalk.add("别人打怪，你被怪按在地上摩擦，丢人。");
        trashTalk.add("一顿操作猛如虎，一看战绩零杠五。");
        trashTalk.add("打输了就算了，还输得这么难看，绝了。");
        trashTalk.add("建议回新手村重修，别出来给冒险者丢脸。");

        // 战前准备
        int count = 1;  // 战斗场次计数
        int win = -1;   // 胜利场次计数（-1表示新手教学）

        // 主游戏循环：玩家存活时继续战斗
        while (player.isAlive())
        {
            // 如果不是第一场战斗，增强敌人属性
            if (win != -1)
            {
                for (int i = 0; i < enemyList.size(); i++)
                {
                    Enemy e = enemyList.get(i);
                    e.setMaxHp(e.getMaxHp() + 100);  // 增加最大生命值
                    e.setHp(e.getMaxHp());          // 恢复满血
                    e.setAtk(e.getAtk() + 10);      // 增加攻击力
                    e.setDef(e.getDef() + 5);       // 增加防御力
                    e.setDefending(false);          // 重置防御状态
                }
            }

            // 随机挑选一名敌人
            Random r = new Random();
            int index = r.nextInt(enemyList.size());
            Enemy enemy = enemyList.get(index);

            // 显示敌人信息
            System.out.println(enemy.show());

            // 判断第几场战斗
            System.out.println("═══════════════════════════════════════");
            if(count!=11)
            {
                System.out.println("⚔️ 第 " + count + " 场战斗开始！对手: " + enemy.getName());
            }
            else
            {
                System.out.println("⚔️ 决胜局！ ⚔️ 对手: " + enemy.getName());
            }

            // 战斗回合循环
            int round = 1;  // 回合计数
            while (player.isAlive() && enemy.isAlive())
            {
                System.out.println("---------------------------------------");
                System.out.println("⚔️ 第 " + round + " 回合开始！");

                // 打印血条和蓝条
                System.out.println(getHealthBar(player.getName(), player.getHp(), player.getMaxHp()));
                System.out.println(getMpBar(player.getName().length(), player.getMp(), player.getMaxMp()));
                System.out.println(getHealthBar(enemy.getName(), enemy.getHp(), enemy.getMaxHp()));

                // 玩家回合
                playerTurn(player, enemy);

                // 判断敌人是否存活
                if (!enemy.isAlive())
                {
                    System.out.println("🎉 你击败了 " + enemy.getName() + "！");
                    // 重置敌人特殊状态
                    switch (enemy.getName())
                    {
                        case "游猎射手":
                            enemy.setAttacking(false);
                            break;
                        case "重装坦克":
                            enemy.setDefending(false);
                            enemy.setBloodsuckingcount(0);
                            break;
                        case "神秘法师":
                            enemy.setBloodsucking(false);
                            enemy.setBloodsuckingcount(0);
                            break;
                        default:
                            break;
                    }
                    win++;  // 增加胜利场次
                    break;
                }

                // 敌人回合
                enemyTurn(player, enemy);

                // 判断玩家是否存活
                if (!player.isAlive())
                {
                    System.out.println("💀 " + player.getName() + " 被 " + enemy.getName() + " 击败了！");
                    // 随机选择嘲讽语句
                    int randomIndex = r.nextInt(trashTalk.size());
                    String randomTrashTalk = trashTalk.get(randomIndex);
                    System.out.println();
                    System.out.println(enemy.getName() + ":" + randomTrashTalk);
                    System.out.println();
                    break;
                }

                round++;  // 进入下一回合
            }

            // 战后结算
            if (player.isAlive())
            {
                // 通关条件：胜利10场
                if (win == 10)
                {
                    System.out.println();
                    System.out.println("恭喜你，通关了！");
                    System.out.println("请联系南瓜官方领取神秘奖大奖！");
                    break;
                }

                // 新手教学关卡提示
                if (win == 0)
                {
                    System.out.println();
                    System.out.println("恭喜你通过了新手教学！准备好迎接接下来的挑战吧！");
                    System.out.println();
                }

                // 每3胜获得属性提升
                if (win % 3 == 0 && win != 0)
                {
                    player.setMaxHp(player.getMaxHp() + 100);  // 增加最大生命值
                    player.setMaxMp(player.getMaxMp() + 50);   // 增加最大魔法值
                    player.setAtk(player.getAtk() + 20);       // 增加攻击力
                    player.setDef(player.getDef() + 10);      // 增加防御力
                    System.out.println("你获得了属性提升！\n当前属性为：" + player.show());
                    System.out.println();
                }

                // 恢复已损生命值的50%-80%
                if (player.getHp() < player.getMaxHp())
                {
                    int lostHp = player.getMaxHp() - player.getHp();
                    int recover = (int) (lostHp * 0.5 + r.nextInt((int) (lostHp * 0.3)));
                    System.out.println("💚 战斗结束！你恢复了 " + recover + " 点生命值 (已损生命的" + Math.round(recover * 100.0 / (player.getMaxHp() - player.getHp())) + "%)");
                    player.recoverHp(recover);
                    System.out.println();
                }

                // 恢复最大魔力值的40%-60%
                if (player.getMp() < player.getMaxMp())
                {
                    int temp = (int) (player.getMaxMp() * 0.4 + r.nextInt((int) (player.getMaxMp() * 0.2)));
                    int recoverMp = Math.min(temp, player.getMaxMp() - player.getMp());
                    System.out.println("🔷 恢复了 " + recoverMp + " 点魔力值 (最大魔力的" + Math.round(recoverMp * 100.0 / player.getMaxMp()) + "%)");
                    player.recoverMp(recoverMp);
                    System.out.println();
                }

                //获得道具
                int probability = r.nextInt(10);
                if (probability >= 5)
                {
                    int randomindex = r.nextInt(player.getPackageList().size());
                    System.out.println("恭喜你获得祝福！你获得了道具：" + player.getPackageList().get(randomindex).getName());
                    player.getConsumable(player.getPackageList().get(randomindex).getName());
                    System.out.println();
                }
                // 打印胜场数
                System.out.println("🏆 当前胜场: " + win);
            } else  // 玩家死亡
            {
                System.out.println("═══════════════════════════════════════");
                System.out.println("游戏结束！");
                System.out.println("你最终胜场数为: " + win + "场");
                break;
            }

            // 询问玩家是否选择继续
            System.out.println("是否选择继续战斗？(y/n)");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("y"))
            {
                count++;  // 进入下一场战斗
                continue;
            } else if (choice.equalsIgnoreCase("n"))
            {
                System.out.println("═══════════════════════════════════════");
                System.out.println("游戏结束！");
                System.out.println("你最终胜场数为: " + win + "场");
                break;
            } else
            {
                System.out.println("没有这个选项，游戏默认继续！");
                System.out.println();
                continue;
            }
        }
    }

    /**
     * 敌人回合行动
     * 功能：处理敌人的AI行为，包括普通攻击和技能使用
     *
     * @param player 玩家角色
     * @param enemy  敌人角色
     */
    private void enemyTurn(Hero player, Enemy enemy)
    {
        System.out.println("===== " + enemy.getName() + " 的回合 ======");

        // 判断是普通攻击还是技能攻击（30%概率使用技能）
        String action = "普通攻击";
        Random r = new Random();
        int num = r.nextInt(10);

        if (num >= 7)  // 7-9的概率使用技能
        {
            action = enemy.getSkill();
        }

        // 根据行动执行攻击或技能
        switch (action)
        {
            case "普通攻击":
                int damage1 = attack(enemy.getAtk(), player.getDef());
                // 蓄势待发状态：伤害翻倍
                if (enemy.isAttacking())
                {
                    damage1 *= 2;
                    enemy.setAttacking(false);
                }
                // 玩家防御状态：伤害减半
                if (player.isDefending())
                {
                    damage1 = (int) (damage1 * 0.5);
                }
                System.out.println("⚔️ " + enemy.getName() + " 对你使用了普通攻击，造成 " + damage1 + " 点伤害！");
                player.takeDamage(damage1);
                break;

            case "猛击":
                System.out.println("战士采取了猛击！");
                int damage2 = strongAttack(enemy.getAtk(), player.getDef());
                System.out.println("💥 " + enemy.getName() + " 对你使用了猛击，造成 " + damage2 + " 点伤害！");
                player.takeDamage(damage2);
                break;

            case "快速攻击":
                System.out.println("刺客采取了快速攻击！");
                // 连续两次攻击，每次80%伤害
                for (int i = 0; i < 2; i++)
                {
                    int temp = attack(enemy.getAtk(), player.getDef());
                    int damage3 = (int) (temp * 0.8);
                    System.out.println("🌀 " + enemy.getName() + " 对你使用了快速攻击，造成 " + damage3 + " 点伤害！");
                    player.takeDamage(damage3);
                }
                break;

            case "防御姿态":
                System.out.println("坦克采取了防御姿态！");
                enemy.setDefending(true);
                int damage5 = (int) (attack(enemy.getAtk(), player.getDef()) * 0.5);
                enemy.setDefendingcount(2);  // 防御状态持续2回合
                System.out.println("🛡️ " + enemy.getName() + " 摆出了防御姿态，同时造成" + damage5 + "点伤害！");
                player.takeDamage(damage5);
                break;

            case "斗转星移":
                System.out.println("法师采取了斗转星移！");
                enemy.setBloodsucking(true);
                enemy.setBloodsuckingcount(enemy.getBloodsuckingcount() + 2);  // 吸血状态持续2次攻击
                int damage3 = attack(enemy.getAtk(), player.getDef());
                int recover = damage3;  // 吸血量等于造成的伤害
                System.out.println("🔄 " + enemy.getName() + " 对你使用了斗转星移，造成 " + damage3 + " 点伤害，回复了 " + recover + " 点生命值！");
                enemy.recoverHp(recover);
                enemy.decreaseBloodsuckingcount();  // 减少吸血状态计数
                player.takeDamage(damage3);
                break;

            case "蓄势待发":
                System.out.println("射手采取了蓄势待发！");
                int damage4 = (int) (attack(enemy.getAtk(), player.getDef()) * 0.9);
                // 蓄势待发状态：伤害翻倍
                if (enemy.isAttacking())
                {
                    damage4 *= 2;
                    enemy.setAttacking(false);
                }
                enemy.setAttacking(true);  // 激活蓄势待发状态
                System.out.println("🏹 " + enemy.getName() + " 对你使用了蓄势待发，造成 " + damage4 + " 点伤害！");
                player.takeDamage(damage4);
                break;
        }

        // 清算技能状态：减少防御状态计数
        enemy.decreaseEnemyDefendingcount();
        player.decreasePlayerDefendingcount();
    }

    /**
     * 玩家回合行动选择
     * 功能：显示玩家可用的技能并处理玩家选择
     *
     * @param player 玩家角色
     * @param enemy  敌人角色
     */
    public void playerTurn(Hero player, Enemy enemy)
    {
        System.out.println("===== 你的回合 ======");
        int sign = 0;
        recycle:
        while (true)
        {
            System.out.println("1. 普通攻击");
            System.out.println("2. 强力一击 (消耗30MP)");
            System.out.println("3. 生命汲取 (消耗20MP)");
            System.out.println("4. 魔力汲取 (消耗50HP)");
            System.out.println("5. 使用道具");
            System.out.print("选择行动 (1-5): ");
            String choice = sc.nextLine();

            switch (choice)
            {
                default:
                    System.out.println("无效选择，默认使用普通攻击！");
                case "1":
                    attackTurn(player, enemy);  // 普通攻击
                    break recycle;
                case "2":
                    // 检查MP是否足够
                    if (player.getMp() < 30)
                    {
                        System.out.println("你没有足够的MP，无法使用强力一击！默认使用普通攻击！");
                        attackTurn(player, enemy);
                        break recycle;
                    }
                    player.useMagic(30);  // 消耗魔法值
                    int damage2 = strongAttack(player.getAtk(), enemy.getDef());
                    damage2 = endDamage(player, enemy, damage2);  // 计算最终伤害
                    System.out.println("💥 你对 " + enemy.getName() + " 使用了强力一击，造成 " + damage2 + " 点伤害！(消耗30MP)");
                    enemy.takeDamage(damage2);
                    break recycle;
                case "3":
                    // 检查MP是否足够
                    if (player.getMp() < 20)
                    {
                        System.out.println("你没有足够的MP，无法使用生命汲取！默认使用普通攻击！");
                        attackTurn(player, enemy);
                        break recycle;
                    }
                    player.useMagic(20);  // 消耗魔法值
                    lifeDraw(player, player.getMaxHp());  // 生命汲取技能
                    break recycle;
                case "4":
                    // 检查HP是否足够
                    if (player.getHp() <= 50)
                    {
                        System.out.println("你没有足够的HP，无法使用魔力汲取！默认使用普通攻击！");
                        attackTurn(player, enemy);
                        break recycle;
                    }
                    player.setHp(player.getHp() - 50);  // 消耗生命值
                    magicDraw(player, player.getMaxMp());  // 魔力汲取技能
                    break recycle;
                case "5":
                    // 检查当前回合是否已经用过道具
                    if (sign == 2)
                    {
                        System.out.println("一回合只能使用两次道具，请重新选择！");
                        continue recycle;
                    }
                    // 检查道具是否可用
                    System.out.println("===== 道具背包 ======");
                    System.out.println("1. 桃子 " + player.getPackageList().get(0).getNum() + " 个 恢复100点生命值");
                    System.out.println("2. 煎蛋 " + player.getPackageList().get(1).getNum() + " 个 最大生命值+20%");
                    System.out.println("3. 花酿鸡 " + player.getPackageList().get(2).getNum() + " 个 当前回合受到的伤害减半");
                    System.out.println("4. 黄酒 " + player.getPackageList().get(3).getNum() + " 个 当前回合造成的伤害翻倍");
                    System.out.println("5. 黑背鲈鱼 " + player.getPackageList().get(4).getNum() + " 个 恢复100点魔力值");
                    System.out.println("6. 白玉汤 " + player.getPackageList().get(5).getNum() + " 个 最大魔力值+20%");
                    System.out.println("0. 返回上级选项");
                    System.out.print("选择道具 (0-7): ");
                    String choose = sc.nextLine();

                    switch (choose)
                    {
                        case "1":
                            if (checkConsumable(player, "桃子"))
                            {
                                player.useConsumable("桃子");
                                System.out.println("使用成功！你恢复了100点生命值！");
                                player.recoverHp(100);
                                sign++;
                            }
                            continue recycle;
                        case "2":
                            if (checkConsumable(player, "煎蛋"))
                            {
                                player.useConsumable("煎蛋");
                                System.out.println("使用成功！你最大生命值增加了20%！");
                                player.setMaxHp((int) (player.getMaxHp() * 1.2));
                                sign++;
                            }
                            continue recycle;
                        case "3":
                            if (checkConsumable(player, "花酿鸡"))
                            {
                                player.useConsumable("花酿鸡");
                                System.out.println("使用成功！当前回合受到的伤害减半！");
                                player.setDefending(true);
                                player.setDefendingcount(1);
                                sign++;
                            }
                            continue recycle;
                        case "4":
                            if (checkConsumable(player, "黄酒"))
                            {
                                player.useConsumable("黄酒");
                                System.out.println("使用成功！下回合造成的伤害翻倍！");
                                player.setAttacking(true);
                                sign++;
                            }
                            continue recycle;
                        case "5":
                            if (checkConsumable(player, "黑背鲈鱼"))
                            {
                                player.useConsumable("黑背鲈鱼");
                                System.out.println("使用成功！你恢复了100点魔力值！");
                                player.recoverMp(100);
                                sign++;
                            }
                            continue recycle;
                        case "6":
                            if (checkConsumable(player, "白玉汤"))
                            {
                                player.useConsumable("白玉汤");
                                System.out.println("使用成功！你最大魔力值增加了20%！");
                                player.setMaxMp((int) (player.getMaxMp() * 1.2));
                                sign++;
                            }
                            continue recycle;
                        case "0":
                            System.out.println("返回上级选项...");
                            continue recycle;
                        default:
                            System.out.println("无效选项！请重新选择！");
                            System.out.println();
                            continue recycle;
                    }
            }
        }
    }

    /**
     * 普通攻击回合实现
     *
     * @param player 玩家角色
     * @param enemy  敌人角色
     */
    public void attackTurn(Hero player, Enemy enemy)
    {
        int damage1 = attack(player.getAtk(), enemy.getDef());
        damage1 = endDamage(player, enemy, damage1);  // 计算最终伤害
        System.out.println("⚔️ 你对 " + enemy.getName() + " 使用了普通攻击，造成 " + damage1 + " 点伤害！");
        enemy.takeDamage(damage1);
    }

    /**
     * 普通攻击伤害计算
     *
     * @param atk 攻击力
     * @param def 防御力
     * @return 造成的伤害值
     */
    public int attack(int atk, int def)
    {
        int damage = atk - def;  // 基础伤害计算
        if (damage < 0)
        {
            damage = 0;  // 伤害不能为负数
        }
        return damage;
    }

    /**
     * 强力一击伤害计算（双倍伤害）
     *
     * @param atk 攻击力
     * @param def 防御力
     * @return 造成的伤害值
     */
    public int strongAttack(int atk, int def)
    {
        int damage = (atk - def) * 2;  // 双倍伤害
        if (damage < 0)
        {
            damage = 0;  // 伤害不能为负数
        }
        return damage;
    }

    /**
     * 生命汲取技能
     * 功能：恢复玩家生命值，恢复量为最大生命值的10%-50%
     *
     * @param player 玩家角色
     * @param MaxHp  最大生命值
     */
    public void lifeDraw(Hero player, int MaxHp)
    {
        Random r = new Random();
        int Minrecover = (int) (MaxHp * 0.1);   // 最小恢复量：10%
        int Maxrecover = (int) (MaxHp * 0.5);   // 最大恢复量：50%

        int recover = r.nextInt(Minrecover, Maxrecover + 1);
        player.recoverHp(recover);
        System.out.println("💚 你对 自己 使用了生命汲取，恢复了 " + recover + " 点生命！(消耗20MP)");
    }

    /**
     * 魔力汲取技能
     * 功能：恢复玩家魔法值，恢复量为最大魔法值的25%-50%
     *
     * @param player 玩家角色
     * @param MaxMp  最大魔法值
     */
    public void magicDraw(Hero player, int MaxMp)
    {
        Random r = new Random();
        int Minrecover = (int) (MaxMp * 0.25);  // 最小恢复量：25%
        int Maxrecover = (int) (MaxMp * 0.5);   // 最大恢复量：50%

        int recover = r.nextInt(Minrecover, Maxrecover + 1);
        player.recoverMp(recover);
        System.out.println("💙 你对 自己 使用了魔力汲取，恢复了 " + recover + " 点魔力！(消耗50HP)");
    }

    /**
     * 生成生命值进度条
     * 功能：根据当前生命值和最大生命值生成可视化进度条
     *
     * @param name  角色名称
     * @param hp    当前生命值
     * @param Maxhp 最大生命值
     * @return 生命值进度条字符串
     */
    public String getHealthBar(String name, int hp, int Maxhp)
    {
        int barLength = 30;  // 进度条长度
        int filledLength = (int) Math.ceil((hp * 1.0 / Maxhp) * barLength);
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": [");

        // 构建进度条
        for (int i = 0; i < barLength; i++)
        {
            if (i < filledLength)
            {
                sb.append("█");  // 已填充部分
            } else
            {
                sb.append("░");  // 未填充部分
            }
        }
        sb.append("] ").append(hp).append("/").append(Maxhp);
        return sb.toString();
    }

    /**
     * 生成魔法值进度条
     * 功能：根据当前魔法值和最大魔法值生成可视化进度条
     *
     * @param nameLength 角色名称长度（用于对齐）
     * @param mp         当前魔法值
     * @param Maxmp      最大魔法值
     * @return 魔法值进度条字符串
     */
    public String getMpBar(int nameLength, int mp, int Maxmp)
    {
        int barLength = 30;  // 进度条长度
        int filledLength = (int) Math.ceil((mp * 1.0 / Maxmp) * barLength);
        StringBuilder sb = new StringBuilder();

        // 添加空格对齐（根据角色名称长度 + ": [" 的长度）
        // nameLength是名字长度，需要加上": [ "这些字符的长度
        for (int i = 0; i < nameLength + 3; i++)  // 改为+3，对应": ["
        {
            sb.append(" ");
        }

        // 构建进度条（去掉"MP: "前缀）
        sb.append("[");
        for (int i = 0; i < barLength; i++)
        {
            if (i < filledLength)
            {
                sb.append("█");  // 已填充部分
            } else
            {
                sb.append("░");  // 未填充部分
            }
        }
        sb.append("] ").append(mp).append("/").append(Maxmp).append(" MP");
        return sb.toString();
    }

    /**
     * 最终伤害计算
     * 功能：考虑防御状态和攻击状态的最终伤害计算
     *
     * @param player 玩家角色
     * @param enemy  敌人角色
     * @param damage 基础伤害值
     * @return 最终伤害值
     */
    public int endDamage(Hero player, Enemy enemy, int damage)
    {
        // 敌人防御状态：伤害减半
        if (enemy.isDefending())
        {
            damage = damage /= 2;
        }
        // 玩家蓄势待发状态：伤害翻倍
        if (player.isAttacking())
        {
            damage *= 2;
            player.setAttacking(false);  // 重置攻击状态
        }
        return damage;
    }

    // 检查道具数量是否充足
    public boolean checkConsumable(Hero player, String consumableName)
    {
        if (!player.isConsumableAvailable(consumableName))
        {
            System.out.println("道具数量不足，无法使用！请重新选择！");
            System.out.println();
            return false;
        }
        return true;
    }

    /**
     * 创建玩家角色
     * 功能：引导用户分配100点属性点，创建英雄角色
     * 分配规则：生命值每点+10HP，魔力值每点+5MP，攻击力每点+2ATK，防御力每点+1DEF
     *
     * @param username 玩家用户名
     * @return 创建的英雄角色对象
     */
    public Hero createPlayer(String username)
    {
        // 创建玩家角色
        System.out.println("创建您的角色：");
        System.out.println("您的角色名称为：" + username);

        // 分配属性
        int points = 100;  // 总属性点
        System.out.println("您总共有" + points + "个属性点");
        System.out.println("可分配的属性有：1.生命值（每点+10HP） 2.魔力值（每点+5MP） 3.攻击力（每点+2ATK） 4.防御力（每点+1DEF）");
        System.out.println("建议平均分配哦~");
        System.out.println();

        // 分配生命值
        int hpPoints = assign(points, "生命值");
        points -= hpPoints;
        System.out.println("--------------------");

        // 分配魔力值
        int mpPoints = assign(points, "魔力值");
        points -= mpPoints;
        System.out.println("--------------------");

        // 分配攻击力
        int atkPoints = assign(points, "攻击力");
        points -= atkPoints;
        System.out.println("--------------------");

        // 分配防御力
        int defPoints = assign(points, "防御力");
        points -= defPoints;
        System.out.println("--------------------");

        // 给玩家属性加点
        // 基础属性：生命50，魔力30，攻击10，防御0
        Hero player = new Hero(username,
                50 + (hpPoints * 10),   // 生命值 = 基础50 + 分配点数×10
                30 + (mpPoints * 5),    // 魔力值 = 基础30 + 分配点数×5
                10 + (atkPoints * 2),   // 攻击力 = 基础10 + 分配点数×2
                defPoints);              // 防御力 = 基础0 + 分配点数×1

        // 添加初始化技能
        ArrayList<String> skillList = new ArrayList<>();
        skillList.add("普通攻击");
        skillList.add("强力一击");
        skillList.add("生命汲取");
        skillList.add("魔力汲取");
        player.setSkill(skillList);

        return player;
    }

    /**
     * 分配属性点
     * 功能：提示用户输入分配给指定属性的点数，并进行有效性验证
     *
     * @param points           当前剩余属性点总数
     * @param specialPointName 属性名称（如"生命值"、"魔力值"等）
     * @return 用户输入的有效分配点数
     */
    public int assign(int points, String specialPointName)
    {
        System.out.println("请输入您要分配给" + specialPointName + "的属性点（当前剩余属性点：" + points + "）：");

        int assignPoints = 0;
        try {
            String input = sc.nextLine();
            // 处理空输入
            if (input.trim().isEmpty()) {
                System.out.println("输入不能为空！默认分配0点！");
                return 0;
            }
            assignPoints = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("请输入有效的数字！默认分配0点！");
            return 0;
        }

        // 检查输入是否有效（不能为负数）
        if (assignPoints < 0)
        {
            System.out.println("无效输入！默认分配0点！");
            return 0;
        }

        // 检查输入是否超过剩余点数
        if (assignPoints > points)
        {
            System.out.println("属性点不足！剩余属性点全部分配到" + specialPointName + "！");
            return points;
        }

        return assignPoints;
    }
}