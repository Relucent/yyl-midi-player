package yyl.midiplayer.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import yyl.midiplayer.common.bus.EventBus;
import yyl.midiplayer.common.bus.EventType;
import yyl.midiplayer.common.constant.ImageConstant;

@SuppressWarnings("serial")
public class MusicControlPanel extends JPanel {

    private final JButton playButton;
    private final JButton stopButton;
    private final JButton pauseButton;
    private final VolumeSlider volumeSlider;
    private final ProgressSlider progressSlider;

    public MusicControlPanel() {

        Dimension buttonSize = new Dimension(20, 20);

        stopButton = new MusicControlButton(ImageConstant.STOP, buttonSize);
        playButton = new MusicControlButton(ImageConstant.PLAY, buttonSize);
        pauseButton = new MusicControlButton(ImageConstant.PAUSE, buttonSize);
        volumeSlider = new VolumeSlider();
        progressSlider = new ProgressSlider();

        initLayout();
        initListener();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {

        setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.add(stopButton);
        buttonsPanel.add(playButton);
        buttonsPanel.add(pauseButton);

        JPanel slidersPanel = new JPanel(new BorderLayout());

        volumeSlider.setPreferredSize(new Dimension(100, volumeSlider.getPreferredSize().height));
        progressSlider.setSnapToTicks(false);

        slidersPanel.add(volumeSlider, BorderLayout.WEST);
        slidersPanel.add(progressSlider, BorderLayout.CENTER);

        add(buttonsPanel, BorderLayout.WEST);
        add(slidersPanel, BorderLayout.CENTER);
    }

    /**
     * 初始化事件监听
     */
    private void initListener() {
        stopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EventBus.INSTANCE.publish(EventType.CLICK_STOP, null);
            }
        });
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EventBus.INSTANCE.publish(EventType.CLICK_PLAY, null);
            }
        });
        pauseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EventBus.INSTANCE.publish(EventType.CLICK_PAUSE, null);
            }
        });

        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double ratio = getVolumeRatio();
                EventBus.INSTANCE.publish(EventType.CLICK_UPDATE_VOLUME_RATIO, ratio);
            }
        });

        progressSlider.setAfterMousePressedListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double value = getProgressValue();
                EventBus.INSTANCE.publish(EventType.CLICK_UPDATE_PROGRESS, value);
            }
        });
    }

    public double getVolumeRatio() {
        return volumeSlider.getVolumeRatio();
    }

    public void setProgressValue(double progress) {
        progressSlider.setProgressValue(progress);
    }

    public double getProgressValue() {
        return progressSlider.getProgressValue();
    }
}