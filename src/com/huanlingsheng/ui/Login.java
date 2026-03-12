package com.huanlingsheng.ui;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import com.huanlingsheng.domain.User;

/**
 * 登录注册界面类
 * 功能：实现用户登录、注册和退出功能
 * 作者：南瓜
 * 版本：1.0
 */
public class Login
{
    // 扫描器对象，用于接收用户输入
    Scanner sc = new Scanner(System.in);

    /**
     * 启动方法，显示主界面并处理用户选择
     * 功能：程序主循环，持续显示菜单并处理用户操作
     */
    public void start()
    {
        // 存储用户信息的集合（内存存储，程序重启后数据丢失）
        ArrayList<User> List = new ArrayList<>();

        // 循环显示主界面，直到用户选择退出
        while (true)
        {
            // 打印主界面
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3忘记密码 4退出");

            // 接收用户选择
            String choose = sc.nextLine();

            // 根据用户选择执行对应操作
            switch (choose)
            {
                case "1" -> login(List);  // 登录功能
                case "2" -> register(List);  // 注册功能
                case "3" -> forgetPassword(List);  // 忘记密码功能
                case "4" -> exit();  // 退出程序
                default -> System.out.println("输入错误，请重新输入");
            }
        }
    }

    /**
     * 登录方法
     * 功能：验证用户身份，登录成功后启动游戏
     * @param List 用户集合，存储所有注册用户信息
     */
    public void login(ArrayList<User> List)
    {
        // 提示用户输入用户名
        System.out.println("请输入用户名：");
        String username = sc.nextLine();

        // 检查用户名是否已注册
        if (!contains(List, username))
        {
            System.out.println("用户名" + username + "未注册，请先注册。");
            return;  // 未注册直接返回
        }

        // 查找用户在集合中的索引
        int index = findByName(List, username);
        User u = List.get(index);

        // 检查用户是否被锁定（密码输错3次后锁定）
        if(!u.isStatus())
        {
            System.out.println("用户" + username + "已经锁定，请联系南瓜官方客服：520-114514");
            return;  // 用户锁定直接返回
        }

        // 最多尝试3次登录
        for (int i = 0; i < 3; i++)
        {
            // 提示用户输入密码
            System.out.println("请输入密码：");
            String password = sc.nextLine();

            // 生成并显示验证码（防止暴力破解）
            String rightcode = getCode();
            System.out.println("验证码：" + rightcode);

            // 提示用户输入验证码
            System.out.println("请输入验证码（不区分大小写）：");
            String code = sc.nextLine();

            // 验证验证码是否正确（不区分大小写）
            if(!code.equalsIgnoreCase(rightcode))
            {
                // 第3次错误，锁定用户
                if(i == 2)
                {
                    u.setStatus(false);  // 锁定用户
                    System.out.println("用户" + username + "已经被锁定，请联系南瓜官方客服：520-114514");
                    return;
                }
                // 验证码错误，提示剩余次数
                System.out.println("验证码输入错误，您还有"+(2-i)+"次机会。");
                continue;  // 继续下一次尝试
            }

            // 验证密码是否正确
            if(!u.getPassword().equals(password))
            {
                // 第3次错误，锁定用户
                if(i == 2)
                {
                    u.setStatus(false);  // 锁定用户
                    System.out.println("用户" + username + "已经被锁定，请联系南瓜官方客服：520-114514");
                    return;
                }
                // 密码错误，提示剩余次数
                System.out.println("密码错误，您还有"+(2-i)+"次机会。");
                continue;  // 继续下一次尝试
            }

            // 登录成功
            System.out.println("登录成功！");
            // 启动游戏
            Fighting F = new Fighting();
            F.gameStart(username);
            //游戏结束
            System.exit(0);
            //结束程序
            return;
        }
    }

    /**
     * 注册方法
     * 功能：创建新用户并添加到用户集合
     * @param List 用户集合，用于存储新注册用户
     */
    public void register(ArrayList<User> List)
    {
        // 创建新用户对象
        User u = new User();

        // 循环获取用户名（直到输入符合要求）
        while (true)
        {
            // 提示用户输入用户名
            System.out.println("请输入用户名：");
            String username = sc.nextLine();

            // 检查用户名长度（3-16位）
            if (!checklen(3, 16, username))
            {
                System.out.println("用户名长度必须在3 ~ 16位，请重新输入。");
                continue;  // 长度不符合要求，重新输入
            }

            // 检查用户名格式（字母数字组合，不能是纯数字）
            if (!checkUsername(username))
            {
                System.out.println("用户名只能由字母、数字组成，不能是纯数字，请重新输入。");
                continue;  // 格式不符合要求，重新输入
            }

            // 检查用户名是否已存在
            if (contains(List, username))
            {
                System.out.println("该用户名已被注册，请重新输入。");
                continue;  // 用户名已存在，重新输入
            }

            // 设置用户名（符合所有要求）
            u.setUsername(username);
            break;  // 用户名验证通过，退出循环
        }

        // 循环获取手机号（直到输入符合要求）
        while (true)
        {
            // 提示用户输入手机号
            System.out.println("请输入手机号：");
            String phone = sc.nextLine();

            // 检查手机号长度（必须11位）
            if (!checklen(11, 11, phone))
            {
                System.out.println("手机号长度必须为11位，请重新输入。");
                continue;  // 长度不符合要求，重新输入
            }

            // 检查手机号格式（1开头，11位数字）
            if (!checkPhone(phone))
            {
                System.out.println("手机号格式错误，请重新输入。");
                continue;  // 格式不符合要求，重新输入
            }

            // 设置手机号（符合所有要求）
            u.setPhone(phone);
            break;  // 手机号验证通过，退出循环
        }

        // 循环获取密码（直到输入符合要求）
        while (true)
        {
            // 提示用户输入密码
            System.out.println("请输入密码：");
            String password = sc.nextLine();

            // 检查密码长度（3-8位）
            if (!checklen(3, 8, password))
            {
                System.out.println("密码长度必须在3 ~ 8位，请重新输入。");
                continue;  // 长度不符合要求，重新输入
            }

            // 检查密码格式（字母加数字组合）
            if (!checkPassword(password))
            {
                System.out.println("密码只能是字母加数字的组合，请重新输入。");
                continue;  // 格式不符合要求，重新输入
            }

            // 提示用户确认密码（防止输入错误）
            System.out.println("请再次输入密码：");
            String confirmPassword = sc.nextLine();

            // 验证两次密码是否一致
            if (!confirmPassword.equals(password))
            {
                System.out.println("两次输入密码不一致，请重新输入。");
                continue;  // 密码不一致，重新输入
            }

            // 设置密码（符合所有要求）
            u.setPassword(password);
            break;  // 密码验证通过，退出循环
        }

        // 添加用户到集合（注册完成）
        List.add(u);
        System.out.println("用户:" + u.getUsername() + "注册成功！");
    }

    /**
     * 忘记密码方法
     * 功能：通过手机号验证重置用户密码
     * @param List 用户集合，包含所有注册用户
     */
    public void forgetPassword(ArrayList<User> List)
    {
        // 提示用户输入用户名
        System.out.println("请输入用户名：");
        String username = sc.nextLine();

        // 查找用户名是否存在
        if (!contains(List, username))
        {
            System.out.println("当前用户名未注册，请先注册。");
            return;  // 用户名不存在，直接返回
        }

        // 创建用户对象引用
        int index = findByName(List, username);
        User u = List.get(index);

        // 循环获取手机号（用于身份验证）
        while (true)
        {
            // 提示用户输入手机号
            System.out.println("请输入手机号：");
            String phone = sc.nextLine();

            // 检查手机号是否匹配（身份验证）
            if (!u.getPhone().equals(phone))
            {
                System.out.println("手机号错误，请重新输入。");
                continue;  // 手机号不匹配，重新输入
            }

            break;  // 手机号验证通过，退出循环
        }

        // 循环获取新密码（直到输入符合要求）
        while(true)
        {
            // 提示用户输入新密码
            System.out.println("请输入新密码：");
            String newPassword = sc.nextLine();

            // 检查新密码长度（3-8位）
            if (!checklen(3, 8, newPassword))
            {
                System.out.println("密码长度必须在3 ~ 8位，请重新输入。");
                continue;  // 长度不符合要求，重新输入
            }

            // 检查新密码格式（字母加数字组合）
            if (!checkPassword(newPassword))
            {
                System.out.println("密码只能是字母加数字的组合，请重新输入。");
                continue;  // 格式不符合要求，重新输入
            }

            // 再次确认密码（防止输入错误）
            System.out.println("请再次输入新密码：");
            String confirmNewPassword = sc.nextLine();

            // 验证两次新密码是否一致
            if (!confirmNewPassword.equals(newPassword))
            {
                System.out.println("两次输入新密码不一致，请重新输入。");
                continue;  // 密码不一致，重新输入
            }

            // 设置新密码（重置成功）
            List.get(index).setPassword(newPassword);
            System.out.println("密码重置成功！");
            break;  // 密码重置完成，退出循环
        }
    }

    /**
     * 检查密码格式是否正确
     * 规则：密码必须包含至少一个字母和一个数字，且不能包含其他字符
     * @param password 待检查的密码字符串
     * @return true-格式正确，false-格式错误
     */
    public boolean checkPassword(String password)
    {
        // 获取密码中字符、数字和其他字符的数量
        int[] count = getCount(password);

        // 密码必须包含至少一个字母和一个数字，且不能包含其他字符
        return count[0] > 0 && count[1] > 0 && count[2] == 0;
    }

    /**
     * 统计字符串中字母、数字和其他字符的数量
     * 功能：分析字符串的字符组成，用于格式验证
     * @param password 要统计的字符串
     * @return 包含字母数、数字数和其他字符数的数组 [字母数, 数字数, 其他字符数]
     */
    public int[] getCount(String password)
    {
        // 初始化计数器
        int charCount = 0;  // 字母数量
        int numCount = 0;   // 数字数量
        int OtherCount = 0; // 其他字符数量

        // 遍历字符串中的每个字符
        for (int i = 0; i < password.length(); i++)
        {
            // 判断字符类型并计数
            if (Character.isLetter(password.charAt(i)))
            {
                charCount++;  // 字母计数
            } else if (Character.isDigit(password.charAt(i)))
            {
                numCount++;   // 数字计数
            } else
            {
                OtherCount++; // 其他字符计数
            }
        }

        // 返回统计结果
        int[] count = new int[]{charCount, numCount, OtherCount};
        return count;
    }

    /**
     * 根据用户名查找用户在集合中的索引
     * 功能：在用户集合中线性搜索指定用户名
     * @param list 用户集合
     * @param username 要查找的用户名
     * @return 用户索引（从0开始），未找到返回-1
     */
    public int findByName(ArrayList<User> list ,String username)
    {
        // 遍历用户集合
        for (int i = 0; i < list.size(); i++)
        {
            // 找到匹配的用户名
            if (list.get(i).getUsername().equals(username))
            {
                return i;  // 返回索引位置
            }
        }
        // 未找到
        return -1;
    }

    /**
     * 检查集合中是否存在指定用户名
     * 功能：判断用户名是否已被注册
     * @param list 用户集合
     * @param username 要检查的用户名
     * @return true-用户名已存在，false-用户名不存在
     */
    public boolean contains(ArrayList<User> list, String username)
    {
        // 遍历用户集合
        for (int i = 0; i < list.size(); i++)
        {
            // 找到匹配的用户名
            if (list.get(i).getUsername().equals(username))
            {
                return true;  // 用户名存在
            }
        }
        // 未找到
        return false;  // 用户名不存在
    }

    /**
     * 检查用户名格式是否正确
     * 规则：用户名必须包含至少一个字母，可包含数字，且不能包含其他字符
     * @param username 待检查的用户名字符串
     * @return true-格式正确，false-格式错误
     */
    public boolean checkUsername(String username)
    {
        // 获取用户名中字符、数字和其他字符的数量
        int[] count = getCount(username);

        // 用户名必须包含至少一个字母，可包含数字，且不能包含其他字符
        return count[0] > 0 && count[1] >= 0 && count[2] == 0;
    }

    /**
     * 检查字符串长度是否在指定范围内
     * 功能：验证输入字符串的长度限制
     * @param min 最小长度（包含）
     * @param max 最大长度（包含）
     * @param str 要检查的字符串
     * @return true-长度在范围内，false-长度不符合要求
     */
    public boolean checklen(int min, int max, String str)
    {
        return str.length() >= min && str.length() <= max;
    }

    /**
     * 生成验证码
     * 规则：长度为5，由4位字母和1位数字组成，字母不区分大小写
     * @return 生成的验证码字符串
     */
    public static String getCode()
    {
        // 随机数生成器
        Random r = new Random();
        // 存储字母的集合
        ArrayList<Character> list = new ArrayList<>();

        // 添加所有大小写字母到集合（52个字母）
        for (int i = 0; i < 26; i++)
        {
            list.add((char) ('a' + i));  // 小写字母 a-z
            list.add((char) ('A' + i));  // 大写字母 A-Z
        }

        // 生成验证码
        StringBuilder sb = new StringBuilder();
        // 随机添加4个字母
        for (int i = 0; i < 4; i++)
        {
            int index = r.nextInt(list.size());
            sb.append(list.get(index));
        }

        // 随机在某个位置插入一个数字（0-9）
        int index = r.nextInt(4);
        sb.insert(index, r.nextInt(10));
        return sb.toString();
    }

    /**
     * 检查手机号格式是否正确
     * 规则：手机号必须以数字1开头，长度为11位纯数字
     * @param phone 待检查的手机号字符串
     * @return true-格式正确，false-格式错误
     */
    public boolean checkPhone(String phone)
    {
        // 手机号必须以数字1开头，长度为11位
        return phone.matches("1\\d{10}");
    }

    /**
     * 退出方法
     * 功能：退出程序，显示感谢信息
     */
    public void exit()
    {
        System.out.println("感谢游玩，期待下次相见~");
        System.exit(0);  // 退出程序
    }
}