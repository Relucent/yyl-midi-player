package yyl.midiplayer.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import yyl.midiplayer.common.util.MidiUtil;

@SuppressWarnings("serial")
public class MusicListModel extends AbstractTableModel {

    private List<MusicInfo> musicList = new ArrayList<>();
    private String[] columnNames = { "Play State", "Name", "Tracks", "Duration" };
    private int[] columnWidths = { 80, 500, 50, 50 };
    private int[] columnAligns = { SwingConstants.CENTER, SwingConstants.LEFT, SwingConstants.RIGHT, SwingConstants.RIGHT };
    private int activedRowIndex = -1;
    private PlayStatus playStatus = PlayStatus.STOPPED;

    /**
     * 音乐列表模型
     */
    public MusicListModel() {
        musicList = new ArrayList<>();
    }

    /**
     * 添加音乐
     * @param info 音乐信息
     */
    public void addMusicInfo(MusicInfo info) {
        if (info != null) {
            musicList.add(info);
            fireTableRowsInserted(musicList.size() - 1, musicList.size() - 1);
        }
    }

    /**
     * 删除音乐
     * @param rowIndex 行号索引
     */
    public void removeMusicInfo(int rowIndex) {
        musicList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    /**
     * 获得音乐信息
     * @param rowIndex 行号索引
     * @return 音乐信息
     */
    public MusicInfo getMusicInfo(int rowIndex) {
        if (rowIndex == -1 || rowIndex >= musicList.size()) {
            return null;
        }
        return musicList.get(rowIndex);
    }

    /**
     * 设置状态为活动的行号，如果没有活动的则返回 -1
     * @return 状态为活动的行号
     */
    public int getActivedRowIndex() {
        return activedRowIndex;
    }

    /**
     * 获得状态为活动的行号，如果没有活动的则设置 -1
     * @param rowIndex 活动的行号
     */
    public void setActivedRowIndex(int rowIndex) {
        activedRowIndex = rowIndex;
        fireTableRowsUpdated(0, musicList.size() - 1);
    }

    /**
     * 设置播放状态-1
     * @param PlayStatus 活动的行号
     */
    public void setPlayStatus(PlayStatus playStatus) {
        this.playStatus = playStatus;
        fireTableRowsUpdated(0, musicList.size() - 1);
    }

    /**
     * 获得列宽
     * @param columnIndex 列索引
     * @return 列宽
     */
    public int getColumnWidth(int columnIndex) {
        return columnWidths[columnIndex];
    }

    /**
     * 获得列文本对齐方式
     * @param columnIndex 列索引 ({@code SwingConstants.LEFT}, {@code SwingConstants.LEFT}, {@code SwingConstants.RIGHT})
     * @return 文本对齐方式
     */
    public int getColumnAlign(int columnIndex) {
        return columnAligns[columnIndex];
    }

    /**
     * 获得行数
     * @return 行数
     */
    @Override
    public int getRowCount() {
        return musicList.size();
    }

    /**
     * 获得列数
     * @return 列数
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * 获得列名称
     * @param 列索引
     * @return 列名称
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * 获得单元格的值
     * @param rowIndex 行索引
     * @param columnIndex 列索引
     * @return 单元格的值
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MusicInfo info = musicList.get(rowIndex);
        switch (columnIndex) {
        case 0:
            if (rowIndex == activedRowIndex) {
                return playStatus.name();
            }
            return " ";
        case 1:
            return info.getName();
        case 2:
            return info.getSequence().getTracks().length;
        case 3:
            return MidiUtil.formatMicrosecondLength(info.getSequence().getMicrosecondLength());
        default:
            return null;
        }
    }

    /**
     * 判断单元格是否允许编辑
     * @param rowIndex 行索引
     * @param columnIndex 列索引
     * @return 单元格是否允许编辑
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
