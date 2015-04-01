package model;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import util.DbUtil;
import dao.UserDao;

public class ScPanel extends JPanel {
	private Vector<String> vector_data, vector_head;
	private JTable jtable;
	private JScrollPane jsp;
	private JButton jb0, jb2, jb3, jbDelSummit, jbDelCancel, jbSetSummit, jbSetCancel;
	private UserDao userDao = new UserDao();
	private DbUtil dbUtil = new DbUtil();
	private CardLayout card = new CardLayout();
	private JPanel jpanelAll = new JPanel(card);
	private JTextField jtfDelSid, jtfDelCid, jtfSetSid, jtfSetCid, jtfSetgrade;
	private JLabel jlScCount = new JLabel();

	@SuppressWarnings("unchecked")
	public ScPanel() throws Exception {

		vector_head = new Vector<String>();
		vector_head.add("学生号");
		vector_head.add("课程号");
		vector_head.add("成绩");
		vector_data = userDao.checkSc(dbUtil.getCon());
		DefaultTableModel dtm = new DefaultTableModel(vector_data, vector_head);
		jtable = new JTable();
		jtable.setModel(dtm);

		jsp = new JScrollPane(jtable);
		jsp.setBorder(new TitledBorder("选修表："));

		refresh();

		JPanel jpanelDel = new JPanel();
		jpanelDel.add(new JLabel("学生号:"));
		jpanelDel.add(jtfDelSid = new JTextField(20));
		jpanelDel.add(new JLabel("课程号:"));
		jpanelDel.add(jtfDelCid = new JTextField(10));
		jpanelDel.add(jbDelSummit = new JButton("提交"));
		jpanelDel.add(jbDelCancel = new JButton("取消"));
		jpanelDel.add(new JLabel("*可直接在选修表中选中其中一项或多项*"));// 空白标签，无功能，使删除页面的组件摆列更整齐。
		jpanelDel.add(new JLabel("*然后点击删除按钮来直接删除*"));// 空白标签，无功能，使删除页面的组件摆列更整齐。
		jpanelDel.add(new JLabel());// 空白标签，无功能，使删除页面的组件摆列更整齐。
		jpanelDel.setLayout(new GridLayout(7, 2, 25, 50));
		jpanelDel.setBorder(new TitledBorder("删除选修"));

		JPanel jpanelSet = new JPanel();
		jpanelSet.add(new JLabel("学生号:"));
		jpanelSet.add(jtfSetSid = new JTextField(10));
		jpanelSet.add(new JLabel("课程号:"));
		jpanelSet.add(jtfSetCid = new JTextField(10));
		jpanelSet.add(new JLabel("新成绩:"));
		jpanelSet.add(jtfSetgrade = new JTextField(20));
		;
		jpanelSet.add(jbSetSummit = new JButton("提交"));
		jpanelSet.add(jbSetCancel = new JButton("取消"));
		jpanelSet.setLayout(new GridLayout(7, 2, 25, 50));
		jpanelSet.setBorder(new TitledBorder("修改选修"));

		JPanel panel = new JPanel();
		// panel.add(jb0 = new JButton("刷新"));
		// panel.add(jb1 = new JButton("添加选修"));
		panel.add(new JLabel("在学生页面\n添加选修"));
		panel.add(jb0 = new JButton("刷新"));
		panel.add(jb2 = new JButton("删除选修"));
		panel.add(jb3 = new JButton("修改选修"));
		panel.setLayout(new GridLayout(4, 1, 5, 10));
		panel.setBorder(new TitledBorder("功能区:"));

		jpanelAll.add(jsp, "All");
		jpanelAll.add(jpanelDel, "Del");
		jpanelAll.add(jpanelSet, "Set");

		this.add(jpanelAll, BorderLayout.CENTER);
		this.add(panel, BorderLayout.EAST);
		this.add(jlScCount, BorderLayout.SOUTH);

		
		jb0.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				refresh();
			}
		});
		
		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (jtable.getSelectedRows().length == 0) {
					card.show(jpanelAll, "Del");
					jtfDelSid.requestFocusInWindow();
				} else {
					if (JOptionPane.showConfirmDialog(null, "确定要删除选定的学生选修成绩吗?") == 0)
						try {
							deleteScByTable();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				}
			}
		});

		jb3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				card.show(jpanelAll, "Set");
				jtfSetSid.requestFocusInWindow();
				jtfSetSid.setText("" + jtable.getValueAt(jtable.getSelectedRow(), 0));
			}
		});

		jbDelSummit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String sid = jtfDelSid.getText().trim();
				String cid = jtfDelCid.getText().trim();

				UserDao userDao = new UserDao();
				DbUtil dbUtil = new DbUtil();

				if (sid.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入学生号！");
					return;
				}
				if (sid.length() > 5) {
					JOptionPane.showMessageDialog(null, "学生号过长，请重新输入！\n学生号应小于5个字符！");
					return;
				}
				Student student = new Student();
				try {
					student = new UserDao().isExistStudent(dbUtil.getCon(), sid);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				if (student == null) {
					JOptionPane.showMessageDialog(null, "该学生不存在!");
					return;
				}

				if (cid.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入课程号！");
					return;
				}
				if (cid.length() > 3) {
					JOptionPane.showMessageDialog(null, "课程号过长，请重新输入！\n课程号应小于5个字符！");
					return;
				}
				Course course = new Course();
				try {
					course = new UserDao().isExistCourse(dbUtil.getCon(), cid);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				if (course == null) {
					JOptionPane.showMessageDialog(null, "该课程不存在!");
					return;
				}

				try {
					userDao.deleteSc(dbUtil.getCon(), sid, cid);
				} catch (Exception e2) {
				}
				JOptionPane.showMessageDialog(null, "删除成功!");
				refresh();
				jtfDelSid.setText(null);
				jtfDelCid.setText(null);
				card.first(jpanelAll);

			}
		});

		jbDelCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtfDelSid.setText(null);
				jtfDelCid.setText(null);
				card.first(jpanelAll);
			}
		});

		jbSetSummit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				UserDao userDao = new UserDao();
				DbUtil dbUtil = new DbUtil();

				String sid = jtfSetSid.getText().trim();
				String cid = jtfSetCid.getText().trim();
				String grade = jtfSetgrade.getText().trim();

				if (sid.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入学生号！");
					return;
				}
				if (sid.length() > 5) {
					JOptionPane.showMessageDialog(null, "学生号过长，请重新输入！\n学生号应小于5个字符！");
					return;
				}
				Student student = new Student();
				try {
					student = new UserDao().isExistStudent(dbUtil.getCon(), sid);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				if (student == null) {
					JOptionPane.showMessageDialog(null, "该学生不存在!");
					return;
				}

				if (cid.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入课程号！");
					return;
				}
				if (cid.length() > 3) {
					JOptionPane.showMessageDialog(null, "课程号过长，请重新输入！\n课程号应小于5个字符！");
					return;
				}
				Course course = new Course();
				try {
					course = new UserDao().isExistCourse(dbUtil.getCon(), cid);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				if (course == null) {
					JOptionPane.showMessageDialog(null, "该课程不存在!");
					return;
				}

				if (grade.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入成绩！");
					return;
				}

				try {
					userDao.setSc(dbUtil.getCon(), sid, cid, Float.parseFloat(grade));
				} catch (Exception e2) {
				}
				jtfSetSid.setText(null);
				jtfSetCid.setText(null);
				jtfSetgrade.setText(null);
				refresh();
				card.first(jpanelAll);
			}
		});

		jbSetCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtfSetSid.setText(null);
				jtfSetCid.setText(null);
				jtfSetgrade.setText(null);
				card.first(jpanelAll);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void refresh() {
		try {
			vector_data = userDao.checkSc(dbUtil.getCon());
			jtable.updateUI();
			jlScCount.setText("当前表中共 " + jtable.getRowCount() + " 条记录!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void deleteScByTable() throws Exception {
		String values1[] = new String[100];
		String values2[] = new String[100];
		int rows[] = jtable.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			values1[i] = (String) jtable.getValueAt(rows[i], 0);
			values2[i] = (String) jtable.getValueAt(rows[i], 1);
			userDao.deleteSc(dbUtil.getCon(), values1[i], values2[i]);
		}
		JOptionPane.showMessageDialog(null, "删除成功!");
		refresh();

	}

}
