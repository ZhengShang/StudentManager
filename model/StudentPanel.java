package model;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

public class StudentPanel extends JPanel {
	private Vector<String> vector_data, vector_head;
	private JTable jtable;
	private JPanel panelAll;
	private JButton jb1, jb2, jb3, jb4, jbAddSummit, jbAddCancel, jbAddScSummit, jbAddScCancel, jbDelSummit, jbDelCancel, jbSetSummit, jbSetCancel;
	// private JLabel jlSid, jlName, jlSex, jlAge;
	private JTextField jtfAddSid, jtfAddName, jtfDelName, jtfSetSid, jtfSetName, jtfSetSidName, jtfAddScSid, jtfAddScCid, jtfAddScgrade;
	private JComboBox jckAddSex, jckAddAge, jckSetSex, jckSetAge;
	private CardLayout card = new CardLayout();
	private UserDao userDao = new UserDao();
	private DbUtil dbUtil = new DbUtil();
	private JLabel jlStudentCount = new JLabel(), jlAddScName = new JLabel();

	@SuppressWarnings("unchecked")
	public StudentPanel() throws Exception {

		vector_head = new Vector<String>();
		vector_head.add("ѧ����");
		vector_head.add("����");
		vector_head.add("�Ա�");
		vector_head.add("����");
		vector_data = userDao.checkStudent(dbUtil.getCon());
		DefaultTableModel dtm = new DefaultTableModel(vector_data, vector_head);
		jtable = new JTable();
		jtable.setModel(dtm);

		refresh();

		JScrollPane jsp = new JScrollPane(jtable);
		jsp.setBorder(new TitledBorder("ѧ����:"));

		JPanel panelAdd = new JPanel();
		panelAdd.add(new JLabel("ѧ����:"));
		panelAdd.add(jtfAddSid = new JTextField(5));
		panelAdd.add(new JLabel("����:"));
		panelAdd.add(jtfAddName = new JTextField(20));
		panelAdd.add(new JLabel("�Ա�:"));
		String sex[] = { "��", "Ů" };
		jckAddSex = new JComboBox(sex);
		panelAdd.add(jckAddSex);
		panelAdd.add(new JLabel("����:"));
		String age[] = new String[100];
		for (int i = 0; i < 100; i++)
			age[i] = Integer.toString(i);
		jckAddAge = new JComboBox(age);
		panelAdd.add(jckAddAge);
		panelAdd.add(jbAddSummit = new JButton("�ύ"));
		panelAdd.add(jbAddCancel = new JButton("ȡ��"));
		panelAdd.setLayout(new GridLayout(7, 2, 25, 50));
		panelAdd.setBorder(new TitledBorder("���ѧ��:"));

		jtfAddSid.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					addStudent();
			}
		});

		jtfAddName.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					addStudent();
			}
		});

		jckAddAge.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					addStudent();
			}
		});

		JPanel panelDel = new JPanel();
		JPanel ppanelDel = new JPanel();
		JPanel pppanelDel = new JPanel();
		ppanelDel.add(new JLabel("������ѧ�Ż�����:"));
		ppanelDel.add(jtfDelName = new JTextField(20));
		ppanelDel.add(jbDelSummit = new JButton("�ύ"));
		ppanelDel.add(jbDelCancel = new JButton("ȡ��"));
		pppanelDel.add(new JLabel("*��ֱ����ѧ������ѡ������һ������,Ȼ����ɾ����ť��ֱ��ɾ��*"));
		panelDel.add(ppanelDel);
		panelDel.add(pppanelDel);
		panelDel.setLayout(new GridLayout(2, 1));
		panelDel.setBorder(new TitledBorder("ɾ��ѧ��:"));

		JPanel panelSet = new JPanel();
		panelSet.add(new JLabel("������ѧ���Ż�����:"));
		panelSet.add(jtfSetSidName = new JTextField(20));
		panelSet.add(new JLabel("��ѧ��:"));
		panelSet.add(jtfSetSid = new JTextField(5));
		panelSet.add(new JLabel("������:"));
		panelSet.add(jtfSetName = new JTextField(20));
		panelSet.add(new JLabel("���Ա�:"));
		panelSet.add(jckSetSex = new JComboBox(sex));
		panelSet.add(new JLabel("������:"));
		panelSet.add(jckSetAge = new JComboBox(age));
		panelSet.setLayout(new GridLayout(7, 2, 25, 50));
		panelSet.setBorder(new TitledBorder("�޸�ѧ����Ϣ:"));
		panelSet.add(jbSetSummit = new JButton("�ύ"));
		panelSet.add(jbSetCancel = new JButton("ȡ��"));

		JPanel panel = new JPanel();
		// panel.add(jb0 = new JButton("ˢ��"));
		panel.add(jb1 = new JButton("���ѧ��"));
		panel.add(jb4 = new JButton("���ѡ��"));
		panel.add(jb2 = new JButton("ɾ��ѧ��"));
		panel.add(jb3 = new JButton("�޸�ѧ��"));
		panel.setLayout(new GridLayout(4, 1, 5, 10));
		panel.setBorder(new TitledBorder("������:"));

		JPanel jpanelAddSc = new JPanel();
		jpanelAddSc.add(new JLabel("��ǰѧ��: ", JLabel.RIGHT));
		jpanelAddSc.add(jlAddScName);
		jpanelAddSc.add(new JLabel("ѧ����:"));
		jpanelAddSc.add(jtfAddScSid = new JTextField(5));
		jpanelAddSc.add(new JLabel("�γ̺�:"));
		jpanelAddSc.add(jtfAddScCid = new JTextField(3));
		jpanelAddSc.add(new JLabel("�ɼ�:"));
		jpanelAddSc.add(jtfAddScgrade = new JTextField(20));
		jpanelAddSc.add(jbAddScSummit = new JButton("�ύ"));
		jpanelAddSc.add(jbAddScCancel = new JButton("ȡ��"));
		jpanelAddSc.setLayout(new GridLayout(7, 2, 25, 50));
		jpanelAddSc.setBorder(new TitledBorder("���ѡ��"));

		panelAll = new JPanel(card);
		panelAll.add(jsp, "All");
		panelAll.add(panelAdd, "Add");
		panelAll.add(panelDel, "Del");
		panelAll.add(panelSet, "Set");
		panelAll.add(jpanelAddSc, "Sc");

		jlStudentCount.setHorizontalAlignment(JLabel.LEFT);
		this.add(panelAll, BorderLayout.CENTER);
		this.add(panel, BorderLayout.EAST);
		this.add(jlStudentCount, BorderLayout.SOUTH);

		jtfAddScSid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Student student = new Student();
				try {
					student = userDao.isExistStudent(dbUtil.getCon(), jtfAddScSid.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (student == null)
					jlAddScName.setText("������");
				else {
					try {
						// System.out.println(userDao.selectStudentInfo(dbUtil.getCon(),
						// jtfAddScSid.getText()).toString());
						// System.out.println(userDao.selectStudentInfo(dbUtil.getCon(),
						// jtfAddScSid.getText()));
						jlAddScName.setText(userDao.selectStudentInfo(dbUtil.getCon(), jtfAddScSid.getText()).toString());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				card.show(panelAll, "Add");
				jtfAddSid.requestFocusInWindow();
			}
		});

		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jtable.getSelectedRows().length == 0) {
					card.show(panelAll, "Del");
					jtfDelName.requestFocusInWindow();
				} else {
					if (JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ��ѡ����ѧ����?") == 0)
						try {
							deleteStudentByTable();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				}
			}
		});

		jb3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jtable.getSelectedRow() != -1) {
					jtfSetSidName.setText((String) jtable.getValueAt(jtable.getSelectedRow(), 0));
					card.show(panelAll, "Set");
				} else {
					card.show(panelAll, "Set");
				}
				jtfSetSidName.requestFocusInWindow();
				jtfSetSidName.setText("" + jtable.getValueAt(jtable.getSelectedRow(), 0));
			}
		});

		jb4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (jtable.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "��ֱ���ڱ���ѡ��һ��ѧ������������ѡ�ޡ�����ӣ�");
					return;
				}
				card.show(panelAll, "Sc");
				jlAddScName.setText("ѧ��:" + jtable.getValueAt(jtable.getSelectedRow(), 0) + "   ����:" + jtable.getValueAt(jtable.getSelectedRow(), 1)
						+ "   " + jtable.getValueAt(jtable.getSelectedRow(), 2) + "   " + jtable.getValueAt(jtable.getSelectedRow(), 3) + "��");
				jtfAddScSid.setText((String) jtable.getValueAt(jtable.getSelectedRow(), 0));
			}
		});

		jbAddScSummit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// UserDao userDao = new UserDao();
				// DbUtil dbUtil = new DbUtil();

				String sid = jtfAddScSid.getText().trim();
				String cid = jtfAddScCid.getText().trim();
				String grade = jtfAddScgrade.getText().trim();
				if (sid.isEmpty()) {
					JOptionPane.showMessageDialog(null, "������ѧ���ţ�");
					return;
				}
				if (sid.length() > 5) {
					JOptionPane.showMessageDialog(null, "ѧ���Ź��������������룡\nѧ����ӦС��5���ַ���");
					return;
				}
				Student student = new Student();
				try {
					student = new UserDao().isExistStudent(dbUtil.getCon(), sid);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				if (student == null) {
					JOptionPane.showMessageDialog(null, "��ѧ��������!");
					return;
				}

				if (cid.isEmpty()) {
					JOptionPane.showMessageDialog(null, "������γ̺ţ�");
					return;
				}
				if (cid.length() > 3) {
					JOptionPane.showMessageDialog(null, "�γ̺Ź��������������룡\n�γ̺�ӦС��3���ַ�!");
					return;
				}

				Course course = new Course();
				try {
					course = new UserDao().isExistCourse(dbUtil.getCon(), cid);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				if (course == null) {
					JOptionPane.showMessageDialog(null, "�ÿγ̲�����!");
					return;
				}

				if (grade.isEmpty()) {
					JOptionPane.showMessageDialog(null, "������ɼ���");
					return;
				}

				if (Float.parseFloat(grade) < 0 || Float.parseFloat(grade) > 100) {
					JOptionPane.showMessageDialog(null, "������ 0 ~ 100 ֮�������!");
					return;
				}
				try {
					userDao.addSc(dbUtil.getCon(), sid, cid, Float.parseFloat(grade));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				jtfAddScSid.setText(null);
				jtfAddScCid.setText(null);
				jtfAddScgrade.setText(null);
				refresh();
				card.first(panelAll);
			}
		});

		jbAddScCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jtfAddScSid.setText(null);
				jtfAddScCid.setText(null);
				jtfAddScgrade.setText(null);
				card.first(panelAll);
			}
		});

		jbAddSummit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addStudent();
			}
		});

		jbAddCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jtfAddSid.setText(null);
				jtfAddName.setText(null);
				jckAddSex.setSelectedIndex(0);
				jckAddAge.setSelectedIndex(0);
				card.first(panelAll);
			}
		});

		jbDelSummit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				deleteStudentByPanel();

			}
		});

		jbDelCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jtfDelName.setText(null);
				card.first(panelAll);
			}
		});

		jbSetSummit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String studentinfo = jtfSetSidName.getText().trim();
				if (studentinfo.isEmpty()) {
					JOptionPane.showMessageDialog(null, "������ѧ���Ż�������!");
					return;
				}
				if (jtfSetSid.getText().length() > 5) {
					JOptionPane.showMessageDialog(null, "ѧ���Ź���������������!\nӦ��С��5���ַ�!");
					return;
				}
				if (jtfSetSidName.getText().length() > 20) {
					JOptionPane.showMessageDialog(null, "�����ַ�����������������\n�ܳ���ӦС��20���ַ�");
					return;
				}

				if (jtfSetName.getText().length() > 20) {
					JOptionPane.showMessageDialog(null, "ѧ�����������������³���!\n����ӦС��20���ַ�!");
					return;
				}

				Student student = new Student();
				try {
					student = new UserDao().isExistStudent(dbUtil.getCon(), studentinfo);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				if (student == null) {
					JOptionPane.showMessageDialog(null, "��ѧ��������!");
					return;
				}

				String newSid = jtfSetSid.getText().trim();
				String newName = jtfSetName.getText().trim();
				String newSex = jckSetSex.getSelectedItem().toString();
				int newAge = Integer.parseInt(jckSetAge.getSelectedItem().toString());
				try {
					if (!newSid.isEmpty())
						userDao.setNewStudentSid(dbUtil.getCon(), newSid, studentinfo);
					if (!newName.isEmpty())
						userDao.setNewStudentName(dbUtil.getCon(), newName, studentinfo);
					if (jckSetSex.getSelectedIndex() != 0)
						userDao.setNewStudentSex(dbUtil.getCon(), newSex, studentinfo);
					if (jckSetAge.getSelectedIndex() != 0)
						userDao.setNewStudentAge(dbUtil.getCon(), newAge, studentinfo);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "�޸ĳɹ�!");
				refresh();
				card.first(panelAll);
			}
		});

		jbSetCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jtfSetSidName.setText(null);
				jtfSetSid.setText(null);
				jtfSetName.setText(null);
				card.first(panelAll);
			}
		});

	}

	@SuppressWarnings("unchecked")
	private void refresh() {
		try {
			vector_data = userDao.checkStudent(dbUtil.getCon());
			jtable.updateUI();
			jlStudentCount.setText("��ǰ���й� " + jtable.getRowCount() + " ����¼!");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void deleteStudentByPanel() {
		String studentinfo = jtfDelName.getText().trim();
		if (studentinfo.isEmpty()) {
			JOptionPane.showMessageDialog(null, "������ѧ�Ż�����:");
			return;
		}
		if (jtfDelName.getText().length() > 20) {
			JOptionPane.showMessageDialog(null, "ѧ���Ż���������������������!\nӦ��С��5���ַ�!");
			return;
		}
		Student student = new Student();
		try {
			student = new UserDao().isExistStudent(dbUtil.getCon(), studentinfo);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		if (student == null) {
			JOptionPane.showMessageDialog(null, "��ѧ��������!");
			return;
		}
		try {
			userDao.deleteStudent(dbUtil.getCon(), studentinfo);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "ɾ���ɹ�!");
		jtfDelName.setText(null);
		refresh();
		card.show(panelAll, "All");
	}

	private void deleteStudentByTable() throws Exception {
		int[] rows = jtable.getSelectedRows();
		String values[] = new String[100];
		String valuesName[] = new String[100];

		for (int i = 0; i < rows.length; i++) {
			values[i] = (String) jtable.getValueAt(rows[i], 0);
			valuesName[i] = (String) jtable.getValueAt(rows[i], 1);

			if (userDao.isStudentSeleted(dbUtil.getCon(), values[i]) == true) {
				JOptionPane.showMessageDialog(null, "��ѧ�� �� " + valuesName[i] + " �� ���гɼ�������ɾ����");
				return;
			} else {
				userDao.deleteStudent(dbUtil.getCon(), values[i]);
				JOptionPane.showMessageDialog(null, "ɾ�� �� " + valuesName[i] + " �� �ɹ�!");
			}
		}
		refresh();
	}

	private void addStudent() {
		UserDao userDao = new UserDao();
		DbUtil dbUtil = new DbUtil();

		if (jtfAddSid.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "������ѧ����:");
			return;
		}
		if (jtfAddSid.getText().length() > 5) {
			JOptionPane.showMessageDialog(null, "ѧ���Ź���������������!\nӦ��С��5���ַ�!");
			return;
		}
		if (jtfAddName.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "����������:");
			return;
		}
		if (jtfAddName.getText().length() > 20) {
			JOptionPane.showMessageDialog(null, "ѧ�����������������³���!\n����ӦС��20���ַ�!");
			return;
		}

		Student student = new Student();
		try {
			student = new UserDao().isExistStudent(dbUtil.getCon(), jtfAddSid.getText().trim());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		if (student != null) {
			JOptionPane.showMessageDialog(null, "��ѧ���Ѵ���!");
			return;
		}

		if (jckAddAge.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "��ѡ��ѧ������!");
			return;
		}

		try {
			userDao.addStudent(dbUtil.getCon(), jtfAddSid.getText().trim(), jtfAddName.getText().trim(), jckAddSex.getSelectedItem().toString(),
					Integer.parseInt(jckAddAge.getSelectedItem().toString()));

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		jtfAddSid.setText(null);
		jtfAddName.setText(null);
		jckAddSex.setSelectedIndex(0);
		jckAddAge.setSelectedIndex(0);
		refresh();
		card.first(panelAll);
	}
}
