package model;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class CoursePanel extends JPanel {
	private Vector<String> vector_data, vector_head;
	private JTable jtable;
	private JButton jb1, jb2, jb3, jbAddSummit, jbAddCancel, jbDelSummit, jbDelCancel, jbSetSummit, jbSetCancel;
	private CardLayout card = new CardLayout();
	private JTextField jtfAddCid, jtfAddName, jtfDelCidOrName, jtfSetCidOrName, jtfSetCid, jtfSetName;
	private JPanel jpanelAll = new JPanel(card);
	private JComboBox jckAddCredit, jckSetCredit;
	private UserDao userDao = new UserDao();
	private DbUtil dbUtil = new DbUtil();
	private JScrollPane jsp;
	private JLabel jlCourseCount=new JLabel();
	
	@SuppressWarnings("unchecked")
	public CoursePanel() throws Exception {
		vector_head = new Vector<String>();
		vector_head.add("课程号");
		vector_head.add("课程名");
		vector_head.add("学分");

		vector_data = userDao.checkCourse(dbUtil.getCon());
		DefaultTableModel dtm = new DefaultTableModel(vector_data, vector_head);
		jtable = new JTable();
		jtable.setModel(dtm);

		jsp = new JScrollPane(jtable);
		jsp.setBorder(new TitledBorder("课程表："));
		refresh();

		JPanel jpanelAdd = new JPanel();
		jpanelAdd.add(new JLabel("课程号(ID):"));
		jpanelAdd.add(jtfAddCid = new JTextField(5));
		jpanelAdd.add(new JLabel("课程名:"));
		jpanelAdd.add(jtfAddName = new JTextField(20));
		jpanelAdd.add(new JLabel("课程学分:"));
		jckAddCredit = new JComboBox();
		for (int i = 0; i < 10; i++)
			jckAddCredit.addItem(i);
		jpanelAdd.add(jckAddCredit);
		jpanelAdd.add(jbAddSummit = new JButton("提交"));
		jpanelAdd.add(jbAddCancel = new JButton("取消"));
		jpanelAdd.setLayout(new GridLayout(7, 2, 25, 50));
		jpanelAdd.setBorder(new TitledBorder("添加课程:"));

		JPanel jpanelDel = new JPanel();
		JPanel jpanelDel2 = new JPanel();
		JPanel jpanelDel3 = new JPanel();
		jpanelDel2.add(new JLabel("课程号或名称:"));
		jpanelDel2.add(jtfDelCidOrName = new JTextField(20));
		jpanelDel2.add(jbDelSummit = new JButton("提交"));
		jpanelDel2.add(jbDelCancel = new JButton("取消"));
		jpanelDel3.add(new JLabel("*可直接在课程表中选中其中一项或多项,然后点击删除按钮来直接删除*"));
		jpanelDel.add(jpanelDel2);
		jpanelDel.add(jpanelDel3);
		jpanelDel.setLayout(new GridLayout(2, 1));
		jpanelDel.setBorder(new TitledBorder("删除课程:"));

		JPanel jpanelSet = new JPanel();
		jpanelSet.add(new JLabel("课程号或名称:"));
		jpanelSet.add(jtfSetCidOrName = new JTextField(20));
		jpanelSet.add(new JLabel("新课程号:"));
		jpanelSet.add(jtfSetCid = new JTextField(3));
		jpanelSet.add(new JLabel("新名称:"));
		jpanelSet.add(jtfSetName = new JTextField(20));
		jpanelSet.add(new JLabel("新学分:"));
		jckSetCredit = new JComboBox();
		for (int i = 0; i < 10; i++)
			jckSetCredit.addItem(i);
		jpanelSet.add(jckSetCredit);
		jpanelSet.add(jbSetSummit = new JButton("提交"));
		jpanelSet.add(jbSetCancel = new JButton("取消"));
		jpanelSet.setBorder(new TitledBorder("修改课程信息:"));
		jpanelSet.setLayout(new GridLayout(7, 2, 25, 50));

		jpanelAll.add(jsp, "All");
		jpanelAll.add(jpanelAdd, "Add");
		jpanelAll.add(jpanelDel, "Del");
		jpanelAll.add(jpanelSet, "Set");

		JPanel panel = new JPanel();
//		panel.add(jb0 = new JButton("刷新"));
		panel.add(jb1 = new JButton("添加课程"));
		panel.add(jb2 = new JButton("删除课程"));
		panel.add(jb3 = new JButton("修改课程"));
		panel.setLayout(new GridLayout(3, 1, 5, 10));
		panel.setBorder(new TitledBorder("功能区:"));

		this.add(jpanelAll, BorderLayout.CENTER);
		this.add(panel, BorderLayout.EAST);
		this.add(jlCourseCount,BorderLayout.SOUTH);
		
//		jb0.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				refresh();
//			}
//		});

		jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				card.show(jpanelAll, "Add");
				jtfAddCid.requestFocusInWindow();
			}
		});

		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jtable.getSelectedRows().length == 0) {
					card.show(jpanelAll, "Del");
					jtfDelCidOrName.requestFocusInWindow();
				} else {
					if (JOptionPane.showConfirmDialog(null, "确定要删除选定的课程吗?") == 0)
						try {
							deleteCourseByTable();
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
				jtfSetCidOrName.requestFocusInWindow();
				jtfSetCidOrName.setText(""+jtable.getValueAt(jtable.getSelectedRow(), 1));
			}
		});

		jbAddSummit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String cid = jtfAddCid.getText().trim();
				String name = jtfAddName.getText().trim();
				float credit = Float.parseFloat(jckAddCredit.getSelectedItem().toString());

				if (cid.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入课程号！");
					return;
				}
				if (jtfAddCid.getText().length() > 3) {
					JOptionPane.showMessageDialog(null, "课程号过长，请重新输入！\n课程号应小于3个字符！");
					return;
				}
				if (name.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入课程名：");
					return;
				}
				if (jtfAddName.getText().length() > 30) {
					JOptionPane.showMessageDialog(null, "课程名过长，请重新输入！\n课程名应小于30个字符！");
					return;
				}
				if (jckAddCredit.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "请选择学分！");
					return;
				}
				UserDao userDao = new UserDao();
				DbUtil dbUtil = new DbUtil();
				Course course = new Course();
				try {
					course = userDao.isExistCourse(dbUtil.getCon(), cid);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (course != null) {
					JOptionPane.showMessageDialog(null, "该课程已存在!");
					return;
				}

				try {
					userDao.addCourse(dbUtil.getCon(), cid, name, credit);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, "添加成功!");
				jtfAddCid.setText(null);
				jtfAddName.setText(null);
				jckAddCredit.setSelectedIndex(0);
				refresh();
				card.first(jpanelAll);
			}
		});

		jbAddCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jtfAddCid.setText(null);
				jtfAddName.setText(null);
				jckAddCredit.setSelectedIndex(0);
				card.first(jpanelAll);
			}
		});

		jbDelSummit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String cidOrName = jtfDelCidOrName.getText().trim();
				if (cidOrName.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入课程号或名称");
					return;
				}

				UserDao userDao = new UserDao();
				DbUtil dbUtil = new DbUtil();
				Course course = new Course();
				try {
					course = userDao.isExistCourse(dbUtil.getCon(), cidOrName);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (course == null) {
					JOptionPane.showMessageDialog(null, "该课程不存在!");
					jtfDelCidOrName.setText(null);
					return;
				}

				try {
					// if(userDao.isCourseSelected(dbUtil.getCon(),
					// cidOrName)!=null){
					// JOptionPane.showMessageDialog(null, "该课程已有学生选修，不可删除");
					// return;
					// }

					try {
						userDao.deleteCourse(dbUtil.getCon(), cidOrName);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				jtfDelCidOrName.setText(null);
				refresh();
				card.first(jpanelAll);

			}
		});

		jbDelCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jtfDelCidOrName.setText(null);
				card.first(jpanelAll);
			}
		});

		jbSetSummit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String newCidOrName = jtfSetCidOrName.getText().trim();
				String newCid = jtfSetCid.getText().trim();
				String newName = jtfSetName.getText().trim();
				// float newCredit =
				// Float.parseFloat(jckSetCredit.getSelectedItem().toString());

				if (newCidOrName.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入课程号或名称!");
					return;
				}

				UserDao userDao = new UserDao();
				DbUtil dbUtil = new DbUtil();
				Course course = new Course();
				try {
					course = userDao.isExistCourse(dbUtil.getCon(), newCidOrName);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (course == null) {
					JOptionPane.showMessageDialog(null, "该课程不存在!");
					jtfDelCidOrName.setText(null);
					return;
				}

				try {
					if (!newCid.isEmpty()) {
						if (jtfSetCid.getText().length() > 3) {
							JOptionPane.showMessageDialog(null, "课程号过长，请重新输入！\n课程号应小于3个字符！");
							return;
						}
						userDao.setNewCourseCid(dbUtil.getCon(), newCid, newCidOrName);
					}
					if (!newName.isEmpty()) {
						if (jtfSetName.getText().length() > 30) {
							JOptionPane.showMessageDialog(null, "课程名过长，请重新输入！\n课程名应小于30个字符！");
							return;
						}
						userDao.setNewCourseName(dbUtil.getCon(), newName, newCidOrName);
					}
					if (jckSetCredit.getSelectedIndex() != 0)
						userDao.setNewCourseCredit(dbUtil.getCon(), jckSetCredit.getSelectedIndex(), newCidOrName);

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, "修改成功!");
				jtfSetCidOrName.setText(null);
				jtfSetCid.setText(null);
				jtfSetName.setText(null);
				jckSetCredit.setSelectedIndex(0);
				refresh();
				card.first(jpanelAll);
			}

		});

		jbSetCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jtfSetCidOrName.setText(null);
				jtfSetCid.setText(null);
				jtfSetName.setText(null);
				jckSetCredit.setSelectedIndex(0);
				card.first(jpanelAll);
			}
		});

	}

	@SuppressWarnings("unchecked")
	private void refresh() {
		try {
			vector_data = userDao.checkCourse(dbUtil.getCon());
			jtable.updateUI();
			jlCourseCount.setText("当前表中共 " + jtable.getRowCount() + " 条记录!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteCourseByTable() throws Exception {
		String values[] = new String[100];
		String courseName[] = new String[100];
		int rows[] = jtable.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			values[i] = (String) jtable.getValueAt(rows[i], 0);
			courseName[i] = (String) jtable.getValueAt(rows[i], 1);
			if (userDao.isCourseSelected(dbUtil.getCon(), values[i]) == true) {
				JOptionPane.showMessageDialog(null, "该课程 【 " + courseName[i] + " 】 已有学生选修，不可删除");
				return;
			} else {
				userDao.deleteCourse(dbUtil.getCon(), values[i]);
				JOptionPane.showMessageDialog(null, " 【 " + courseName[i] + " 】 删除成功!");
			}
			// System.out.println(userDao.isCourseSelected(dbUtil.getCon(),
			// values[i]));

		}
		refresh();
	}
}