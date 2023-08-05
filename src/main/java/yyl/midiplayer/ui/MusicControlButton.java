package yyl.midiplayer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import yyl.midiplayer.common.util.GuiUtil;

@SuppressWarnings("serial")
public class MusicControlButton extends JButton {

    public MusicControlButton(Image iamge, Dimension size) {
        Image scaledImage = GuiUtil.getScaledImage(iamge, size);
        setIcon(new ImageIcon(scaledImage));
        setPreferredSize(size);
        setContentAreaFilled(false);// 使按钮的内容（包括图标和文本）背景透明
        setBorderPainted(false); // 设置按钮边框不可见
        setFocusPainted(false);// 不会绘制焦点状态的虚线
    }

    @Override
    protected void paintComponent(Graphics g) {
        ButtonModel model = getModel();
        if (model.isPressed()) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        } else if (model.isRollover()) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paintComponent(g);
    }
}
