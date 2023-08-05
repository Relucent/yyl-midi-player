package yyl.midiplayer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import yyl.midiplayer.common.bus.EventBus;
import yyl.midiplayer.common.bus.EventType;

/**
 * 主菜单条
 */
@SuppressWarnings("serial")
public class MainMenuBar extends JMenuBar {

    private final JMenu fileMenu;
    private final JMenuItem openItem;
    private final JMenuItem exitItem;

    public MainMenuBar() {

        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        exitItem = new JMenuItem("Exit");

        initLayout();
        initListener();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        add(fileMenu);
    }

    /**
     * 初始化事件监听
     */
    private void initListener() {
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventBus.INSTANCE.publish(EventType.CLICK_OPEN, null);
            }
        });
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventBus.INSTANCE.publish(EventType.CLICK_EXIT, null);
            }
        });
    }
}
