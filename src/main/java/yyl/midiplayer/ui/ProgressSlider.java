package yyl.midiplayer.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 播放进度条
 */
@SuppressWarnings("serial")
public class ProgressSlider extends JSlider {

    private final static int RANGE_MAX = 10000;

    private final AtomicReference<ChangeListener> afterMousePressedListener = new AtomicReference<>();

    public ProgressSlider() {
        super(JSlider.HORIZONTAL, 0, RANGE_MAX, 0);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                stateChanged(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                stateChanged(e);
            }

            private void stateChanged(MouseEvent e) {
                JSlider slider = (JSlider) e.getSource();
                int value = getValueFromMousePosition(slider, e.getX());
                int original = slider.getValue();
                if (value == original) {
                    return;
                }
                slider.setValue(value);
                ChangeListener listener = afterMousePressedListener.get();
                if (listener != null) {
                    listener.stateChanged(new ChangeEvent(slider));
                }
            }
        });
    }

    /**
     * 获取鼠标点击坐标对应滑块的精确数字
     * @param slider 滑块
     * @param mouseX X坐标
     * @return 鼠标点击坐标对应滑块的精确数字
     */
    private int getValueFromMousePosition(JSlider slider, int mouseX) {
        int minValue = slider.getMinimum();
        int maxValue = slider.getMaximum();
        int sliderWidth = slider.getWidth();
        double valuePerPixel = (double) (maxValue - minValue) / (double) sliderWidth;
        int value = (int) (valuePerPixel * mouseX);
        return Math.min(maxValue, Math.max(minValue, value));
    }

    /**
     * 设置点击进度条事件监听
     * @param listener 点击进度条事件监听
     */
    public void setAfterMousePressedListener(ChangeListener listener) {
        afterMousePressedListener.set(listener);
    };

    /**
     * 设置进度值（取值范围0到1，0表示开始，1表示结束）
     * @param progress 进度值
     */
    public void setProgressValue(double progress) {
        int maximum = getMaximum();
        setValue(Math.max(0, Math.min((int) (maximum * progress), maximum)));
    }

    /**
     * 获取进度值（取值范围0到1，0表示开始，1表示结束）
     * @return 进度值
     */
    public double getProgressValue() {
        int maximum = getMaximum();
        int value = getValue();
        return value == 0 ? 0 : (value == maximum ? 1 : ((double) value) / ((double) maximum));
    }
}
