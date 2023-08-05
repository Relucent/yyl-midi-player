package yyl.midiplayer.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 * 音量滑动条
 */
@SuppressWarnings("serial")
public class VolumeSlider extends JSlider {

    private final static int RANGE_MAX = 10000;

    public VolumeSlider() {
        super(JSlider.HORIZONTAL, 0, RANGE_MAX, (int) (RANGE_MAX * 0.8));
        setUI(new CustomSliderUI(this));
    }

    /**
     * 获取音量比值（取值范围0到1，0表示无声音，1表示全音量）
     * @return 音量比值
     */
    public double getVolumeRatio() {
        int maximum = getMaximum();
        int value = getValue();
        return value == 0 ? 0 : (value == maximum ? 1 : ((double) value) / ((double) maximum));
    }

    /**
     * 自定义滑动条UI
     */
    private class CustomSliderUI extends BasicSliderUI {

        public CustomSliderUI(JSlider slider) {
            super(slider);
        }

        /**
         * 绘制滑动条的轨道
         * @param g 图形绘制对象
         */
        @Override
        public void paintTrack(Graphics g) {
            // 获取组件的宽度和高度
            int width = getWidth();
            int height = getHeight();

            // 开启抗锯齿
            if (g instanceof Graphics2D) {
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }

            int paddingTop = 10;
            int paddingRight = 2;
            int paddingBottom = 10;
            int paddingLeft = 2;
            // 设置绘图颜色和绘图对象
            g.setColor(Color.LIGHT_GRAY);
            int x1 = width - paddingRight;
            int x2 = paddingLeft;
            int y1 = paddingTop;
            int y2 = height - paddingBottom;
            g.drawLine(x1, y1, x2, y2);
        }
    }
}
