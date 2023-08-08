package yyl.midiplayer.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yyl.midiplayer.Main;
import yyl.midiplayer.common.bus.EventBus;
import yyl.midiplayer.common.bus.EventType;
import yyl.midiplayer.common.constant.ArrayConstant;
import yyl.midiplayer.common.constant.ImageConstant;
import yyl.midiplayer.common.util.GuiUtil;

/**
 * 主窗体
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    private final static Logger LOG = LoggerFactory.getLogger(Main.class);

    private final MainMenuBar menuBar;
    private final MusicControlPanel controlPanel;
    private final MusicListPanel musicListPanel;

    public MainFrame() {

        // 程序图标
        setIconImage(ImageConstant.MUSIC);
        // 程序标题
        setTitle("Midi Player");
        // 设置主窗口的尺寸
        setPreferredSize(new Dimension(800, 600));
        // 默认关闭操作，当用户点击关闭按钮时，终止程序的执行
        setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);

        // 设置可获得焦点（以便接收键盘事件）
        setFocusable(true);

        menuBar = new MainMenuBar();

        // 控制条
        controlPanel = new MusicControlPanel();
        // 音乐列表
        musicListPanel = new MusicListPanel();

        initLayout();
        initListener();

        // 自适应布局并居中显示主窗口
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * 初始化布局
     */
    private void initLayout() {

        // 设置主窗体的菜单条
        setJMenuBar(menuBar);

        // 界面布局
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(controlPanel, BorderLayout.NORTH);
        contentPane.add(musicListPanel, BorderLayout.CENTER);
    }

    /**
     * 初始化事件监听
     */
    private void initListener() {

        final Container contentPane = getContentPane();
        final EventBus eventBus = EventBus.INSTANCE;

        contentPane.setDropTarget(new DropTarget(contentPane, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent event) {
                try {
                    // 获取拖放的传输数据
                    Transferable transferable = event.getTransferable();
                    // 判断数据是否包含文件类型
                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {

                        // 告诉系统该拖放操作应该采取什么行为
                        event.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

                        // 获取拖放的文件列表
                        @SuppressWarnings("unchecked")
                        List<File> fileList = List.class.cast(transferable.getTransferData(DataFlavor.javaFileListFlavor));

                        eventBus.publish(EventType.DND_FILES, fileList.toArray(ArrayConstant.EMPTY_FILE_ARRAY));

                        // if (fileList.size() > 0) {
                        // // 获取第一个文件对象
                        // File file = (File) fileList.get(0);
                        // // 显示文件名称
                        // // JOptionPane.showMessageDialog(contentPane, file.getAbsolutePath(), "File", JOptionPane.PLAIN_MESSAGE);
                        // }
                        // 该方法用于通知系统拖放操作的完成结果。当 success 参数为 true 时，表示拖放操作成功完成；当 success 参数为 false 时，表示拖放操作未成功完成或被取消。
                        event.dropComplete(true);
                    } else {
                        // 方法用于通知系统拒绝当前的拖放操作
                        event.rejectDrop();
                    }
                } catch (Exception e) {
                    // 方法用于通知系统拒绝当前的拖放操作
                    event.rejectDrop();
                    LOG.error(e.getMessage(), e);
                }
            }
        }));

        // 添加全局键盘监听器
        KeyListener globalKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                eventBus.publish(EventType.KEY_PRESSED, event);
            }
        };
        GuiUtil.traverseComponents(this, component -> {
            component.addKeyListener(globalKeyListener);
            return Boolean.TRUE;
        });

    }

    public MusicControlPanel getControlPanel() {
        return controlPanel;
    }

    public MusicListPanel getMusicListPanel() {
        return musicListPanel;
    }
}
