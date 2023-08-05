package yyl.midiplayer;

import javax.swing.SwingUtilities;

import yyl.midiplayer.common.util.GuiUtil;
import yyl.midiplayer.controller.MusicPlayerController;
import yyl.midiplayer.model.MusicPlayer;
import yyl.midiplayer.ui.MainFrame;

public class Main {
    public static void main(String[] args) {
        // 在事件调度线程（EDT）上创建并显示GUI
        SwingUtilities.invokeLater(() -> {
            // 设置系统样式
            GuiUtil.initSystemLookAndFeel();

            // 音乐播放程序
            MusicPlayer player = new MusicPlayer();

            // 创建主窗口并
            MainFrame frame = new MainFrame();

            // 初始化控制器
            new MusicPlayerController(frame, player);

            // 显示主窗体
            frame.setVisible(true);
        });
    }
}
