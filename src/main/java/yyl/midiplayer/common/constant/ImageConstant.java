package yyl.midiplayer.common.constant;

import java.awt.Image;

import yyl.midiplayer.common.util.GuiUtil;

/**
 * 图片常量类，定义了一些应用所用到的图标图片
 */
public class ImageConstant {

    public static final Image MUSIC = GuiUtil.createImage("/yyl/midiplayer/icon/music.png");
    public static final Image NEXT = GuiUtil.createImage("/yyl/midiplayer/icon/next.png");
    public static final Image PAUSE = GuiUtil.createImage("/yyl/midiplayer/icon/pause.png");
    public static final Image PLAY = GuiUtil.createImage("/yyl/midiplayer/icon/play.png");
    public static final Image PREVIOUS = GuiUtil.createImage("/yyl/midiplayer/icon/previous.png");
    public static final Image STOP = GuiUtil.createImage("/yyl/midiplayer/icon/stop.png");

    /** 常量类，不需要实例化 */
    private ImageConstant() {
    }
}
