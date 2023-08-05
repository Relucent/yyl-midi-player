package yyl.midiplayer.common.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yyl.midiplayer.Main;
import yyl.midiplayer.common.constant.ImageConstant;

public class GuiUtil {

    private final static Logger LOG = LoggerFactory.getLogger(Main.class);

    /**
     * 初始化外观
     */
    public static void initSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            LOG.error("initLookAndFeel Error!", e);
        }
    }

    /**
     * 创建图像对象
     * @param path 图像路径
     * @return 图像对象
     */
    public static Image createImage(String path) {
        try (InputStream input = ImageConstant.class.getResourceAsStream(path)) {
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            return Toolkit.getDefaultToolkit().createImage(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 缩放图片
     * @param iamge 图片对象
     * @param size 缩放后的尺寸
     * @return 缩放后的图片对象
     */
    public static Image getScaledImage(Image image, Dimension size) {
        return image.getScaledInstance((int) size.getWidth(), (int) size.getHeight(), Image.SCALE_SMOOTH);
    }

    /**
     * 创建图标
     * @param path 图片路径
     * @param size 图标尺寸
     * @return 图标对象
     */
    public static ImageIcon createImageIcon(String path, Dimension size) {
        Image image = GuiUtil.createImage(path);
        Image scaledImage = image.getScaledInstance((int) size.getWidth(), (int) size.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * 遍历父组件及其所有子组件，并执行自定义的操作
     * @param parent 父组件
     * @param function 执行的操作，如果该方法返回false，则不在继续遍历该组件的子组件
     */
    public static void traverseComponents(Component parent, Function<Component, Boolean> function) {

        if (Boolean.FALSE.equals(function.apply(parent))) {
            return;
        }

        if (parent instanceof Container) {
            Container container = (Container) parent;
            for (Component child : container.getComponents()) {
                traverseComponents(child, function);
            }
        }
    }
}
