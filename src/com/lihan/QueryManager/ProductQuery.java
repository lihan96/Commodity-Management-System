package com.lihan.QueryManager;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ProductQuery extends JPanel implements ActionListener {
    private Image backgroundImage = new ImageIcon("image/background.jpg").getImage();
    private JButton btnQuery = new JButton("查询");
    private JTextField[] text = new JTextField[5];
    private String[] cloNames = {"商品编号", "商品名称", "生产批号", "商品价格", "供应商编号"};
    private CustomTableModel tableModel;
    private JTable jtable;

    ProductQuery() {
        this.setSize(600, 510);
        this.setLayout(null);

        JLabel jLabel = new JLabel("请任意输入一栏查询(可以输入多栏)：");
        jLabel.setBounds(10, 10, 230, 20);
        jLabel.setForeground(Color.red);
        this.add(jLabel);

        JLabel jLabel2 = new JLabel("商品信息查询得到的信息如下：");
        jLabel2.setBounds(10, 180, 230, 20);
        jLabel2.setForeground(Color.red);
        this.add(jLabel2);

        //添加左边标签和文本框
        JLabel[] label = {new JLabel("商品编号:"), new JLabel("商品名称:"),
                new JLabel("商品存量:"), new JLabel("商品价格:"), new JLabel("供应商编号:")};
        for (int i = 0; i < label.length; i++) {
            text[i] = new JTextField();
            label[i].setBounds(10, 40 + 25 * i, 90, 20);
            text[i].setBounds(80, 40 + 25 * i, 120, 20);
            label[i].setHorizontalAlignment(JLabel.LEFT);

            this.add(text[i]);
            this.add(label[i]);
        }

        //添加查询按钮
        btnQuery.setBounds(350, 50, 150, 90);
        btnQuery.setBackground(new Color(0, 152, 210));
        this.add(btnQuery);

        //添加表格
        Class[] dataType = new Class[]{String.class, String.class, String.class, String.class, String.class};
        tableModel = new CustomTableModel(0, cloNames.length, cloNames, dataType);
        jtable = new JTable(tableModel);

        //向模型中添加数据
        Object[][] data = {{"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""}};
        for (Object[] aData : data) {
            tableModel.addRow(aData);
        }
        jtable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane jScrollPane = new JScrollPane(jtable);
        jScrollPane.setBounds(10, 210, 560, 220);
        this.add(jScrollPane);
        btnQuery.addActionListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnQuery) {
            Query();
        }
    }

    private void Query() {
        String[] ss = new String[cloNames.length];
        for (int i = 0; i < ss.length; i++) {
            ss[i] = text[i].getText();
        }
        for (int i = 0; i < ss.length; i++) {
            ss[i] = "%" + ss[i] + "%";
        }
        String sql = "SELECT * FROM tb_product WHERE pro_id like '" + ss[0] + "' "
                + "AND pro_name LIKE '" + ss[1] + "' AND num LIKE '" + ss[2]
                + "' AND price LIKE '" + ss[3] + "' AND sup_id LIKE '" + ss[4] + "'";
        QueryMethod.Query(sql, cloNames, jtable, tableModel);
    }
}
