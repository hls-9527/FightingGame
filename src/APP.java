import com.huanlingsheng.ui.Fighting;
import com.huanlingsheng.ui.Login;

/**
 * 文字格斗游戏主程序类
 * 功能：程序入口，启动游戏登录界面
 * 作者：南瓜
 * 版本：1.0
 */
public class APP
{
    /**
     * 程序主入口方法
     * @param args 命令行参数
     */
    public static void main(String[] args)
    {
        // 显示欢迎信息
        System.out.println("欢迎来到文字格斗游戏");
        // 创建登录界面对象
        Login L = new Login();
        // 启动登录界面
        L.start();
    }
}