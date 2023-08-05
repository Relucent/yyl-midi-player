package yyl.midiplayer.common.constant;

import java.awt.Image;

import yyl.midiplayer.common.util.GuiUtil;

public class ImageConstant {

    public static final Image NEXT = GuiUtil.createImage("/yyl/midiplayer/icon/next.png");
    public static final Image PAUSE = GuiUtil.createImage("/yyl/midiplayer/icon/pause.png");
    public static final Image PLAY = GuiUtil.createImage("/yyl/midiplayer/icon/play.png");
    public static final Image PREVIOUS = GuiUtil.createImage("/yyl/midiplayer/icon/previous.png");
    public static final Image STOP = GuiUtil.createImage("/yyl/midiplayer/icon/stop.png");

    /** 常量类，不需要实例化 */
    private ImageConstant() {
    }
}
