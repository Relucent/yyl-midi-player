package yyl.midiplayer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import yyl.midiplayer.common.bus.EventBus;
import yyl.midiplayer.common.bus.EventType;
import yyl.midiplayer.common.constant.ColorConstant;
import yyl.midiplayer.model.MusicInfo;
import yyl.midiplayer.model.MusicListModel;
import yyl.midiplayer.model.PlayStatus;

@SuppressWarnings("serial")
public class MusicListPanel extends JPanel {

    private final JTable musicTable;
    private final MusicListModel tableModel;

    public MusicListPanel() {

        tableModel = new MusicListModel();
        musicTable = new JTable(tableModel);

        initLayout();
        initListener();
    }

    /**
     * 初始化布局和样式
     */
    private void initLayout() {

        musicTable.setRowHeight(30);
        musicTable.setShowGrid(false);
        musicTable.setShowVerticalLines(false);
        musicTable.setGridColor(Color.LIGHT_GRAY);
        musicTable.setBackground(Color.WHITE);

        TableColumnModel columnModel = musicTable.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            int width = tableModel.getColumnWidth(i);
            int align = tableModel.getColumnAlign(i);
            column.setPreferredWidth(width);
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(align);
            column.setCellRenderer(centerRenderer);
        }
        musicTable.setDefaultRenderer(Object.class, new CustomTableCellRendererextends());

        JScrollPane tableScrollPane = new JScrollPane(musicTable);
        setLayout(new BorderLayout());
        add(tableScrollPane, BorderLayout.CENTER);
    }

    /**
     * 初始化事件监听
     */
    private void initListener() {

        // 添加MouseListener监听双击事件
        musicTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) { // 双击事件
                    int selectedRow = musicTable.getSelectedRow();
                    if (selectedRow != -1) { // 确保有选中的行
                        tableModel.setActivedRowIndex(selectedRow);
                        EventBus.INSTANCE.publish(EventType.DBL_CLICK_ACTIVED_MUSIC, null);
                    }
                }
            }
        });
    }

    public int[] getSelectedRows() {
        return musicTable.getSelectedRows();
    }

    public int getRowCount() {
        return tableModel.getRowCount();
    }

    public int getActivedRowIndex() {
        return tableModel.getActivedRowIndex();
    }

    public void setActivedRowIndex(int rowIndex) {
        tableModel.setActivedRowIndex(rowIndex);
    }

    public void setPlayStatus(PlayStatus playStatus) {
        tableModel.setPlayStatus(playStatus);
    }

    public void addMusicInfo(MusicInfo info) {
        tableModel.addMusicInfo(info);
    }

    public void removeMusicInfo(int rowIndex) {
        tableModel.removeMusicInfo(rowIndex);
    }

    public MusicInfo getMusicInfo(int rowIndex) {
        return tableModel.getMusicInfo(rowIndex);
    }

    private static class CustomTableCellRendererextends extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (component instanceof JComponent) {
                ((JComponent) component).setBorder(null);
            }

            if (isSelected) {
                component.setBackground(ColorConstant.CLR_E0E0FF); // 选中蓝色背景
                component.setForeground(ColorConstant.CLR_000000);
            } else if (row % 2 == 0) {
                component.setBackground(ColorConstant.CLR_FFFFFF); // 偶数行白色背景
            } else {
                component.setBackground(ColorConstant.CLR_F8F8F8); // 奇数行灰色背景
            }
            return component;
        }
    }
}
