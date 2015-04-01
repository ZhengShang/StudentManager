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
		vector_head.add("�γ̺�");
		vector_head.add("�γ���");
		vector_head.add("ѧ��");

		vector_data = userDao.checkCourse(dbUtil.getCon());
		DefaultTableModel dtm = new DefaultTableModel(vector_data, vector_head);
		jtable = new JTable();
		jtable.setModel(dtm);

		jsp = new JScrollPane(jtable);
		jsp.setBorder(new TitledBorder("�γ̱�"));
		refresh();

		JPanel jpanelAdd = new JPanel();
		jpanelAdd.add(new JLabel("�γ̺�(ID):"));
		jpanelAdd.add(jtfAddCid = new JTextField(5));
		jpanelAdd.add(new JLabel("�γ���:"));
		jpanelAdd.add(jtfAddName = new JTextField(20));
		jpanelAdd.add(new JLabel("�γ�ѧ��:"));
		jckAddCredit = new JComboBox();
		for (int i = 0; i < 10; i++)
			jckAddCredit.addItem(i);
		jpanelAdd.add(jckAddCredit);
		jpanelAdd.add(jbAddSummit = new JButton("�ύ"));
		jpanelAdd.add(jbAddCancel = new JButton("ȡ��"));
		jpanelAdd.setLayout(new GridLayout(7, 2, 25, 50));
		jpanelAdd.setBorder(new TitledBorder("��ӿγ�:"));

		JPanel jpanelDel = new JPanel();
		JPanel jpanelDel2 = new JPanel();
		JPanel jpanelDel3 = new JPanel();
		jpanelDel2.add(new JLabel("�γ̺Ż�����:"));
		jpanelDel2.add(jtfDelCidOrName = new JTextField(20));
		jpanelDel2.add(jbDelSummit = new JButton("�ύ"));
		jpanelDel2.add(jbDelCancel = new JButton("ȡ��"));
		jpanelDel3.add(new JLabel("*��ֱ���ڿγ̱���ѡ������һ������,Ȼ����ɾ����ť��ֱ��ɾ��*"));
		jpanelDel.add(jpanelDel2);
		jpanelDel.add(jpanelDel3);
		jpanelDel.setLayout(new GridLayout(2, 1));
		jpanelDel.setBorder(new TitledBorder("ɾ���γ�:"));

		JPanel jpanelSet = new JPanel();
		jpanelSet.add(new JLabel("�γ̺Ż�����:"));
		jpanelSet.add(jtfSetCidOrName = new JTextField(20));
		jpanelSet.add(new JLabel("�¿γ̺�:"));
		jpanelSet.add(jtfSetCid = new JTextField(3));
		jpanelSet.add(new JLabel("������:"));
		jpanelSet.add(jtfSetName = new JTextField(20));
		jpanelSet.add(new JLabel("��ѧ��:"));
		jckSetCredit = new JComboBox();
		for (int i = 0; i < 10; i++)
			jckSetCredit.addItem(i);
		jpanelSet.add(jckSetCredit);
		jpanelSet.add(jbSetSummit = new JButton("�ύ"));
		jpanelSet.add(jbSetCancel = new JButton("ȡ��"));
		jpanelSet.setBorder(new TitledBorder("�޸Ŀγ���Ϣ:"));
		jpanelSet.setLayout(new GridLayout(7, 2, 25, 50));

		jpanelAll.add(jsp, "All");
		jpanelAll.add(jpanelAdd, "Add");
		jpanelAll.add(jpanelDel, "Del");
		jpanelAll.add(jpanelSet, "Set");

		JPanel panel = new JPanel();
//		panel.add(jb0 = new JButton("ˢ��"));
		panel.add(jb1 = new JButton("��ӿγ�"));
		panel.add(jb2 = new JButton("ɾ���γ�"));
		panel.add(jb3 = new JButton("�޸Ŀγ�"));
		panel.setLayout(new GridLayout(3, 1, 5, 10));
		panel.setBorder(new TitledBorder("������:"));

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
					if (JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ��ѡ���Ŀγ���?") == 0)
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
					JOptionPane.showMessageDialog(null, "������γ̺ţ�");
					return;
				}
				if (jtfAddCid.getText().length() > 3) {
					JOptionPane.showMessageDialog(null, "�γ̺Ź��������������룡\n�γ̺�ӦС��3���ַ���");
					return;
				}
				if (name.isEmpty()) {
					JOptionPane.showMessageDialog(null, "������γ�����");
					return;
				}
				if (jtfAddName.getText().length() > 30) {
					JOptionPane.showMessageDialog(null, "�γ������������������룡\n�γ���ӦС��30���ַ���");
					return;
				}
				if (jckAddCredit.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "��ѡ��ѧ�֣�");
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
					JOptionPane.showMessageDialog(null, "�ÿγ��Ѵ���!");
					return;
				}

				try {
					userDao.addCourse(dbUtil.getCon(), cid, name, credit);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, "��ӳɹ�!");
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
					JOptionPane.showMessageDialog(null, "������γ̺Ż�����");
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
					JOptionPane.showMessageDialog(null, "�ÿγ̲�����!");
					jtfDelCidOrName.setText(null);
					return;
				}

				try {
					// if(userDao.isCourseSelected(dbUtil.getCon(),
					// cidOrName)!=null){
					// JOptionPane.showMessageDialog(null, "�ÿγ�����ѧ��ѡ�ޣ�����ɾ��");
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
					JOptionPane.showMessageDialog(null, "������γ̺Ż�����!");
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
					JOptionPane.showMessageDialog(null, "�ÿγ̲�����!");
					jtfDelCidOrName.setText(null);
					return;
				}

				try {
					if (!newCid.isEmpty()) {
						if (jtfSetCid.getText().length() > 3) {
							JOptionPane.showMessageDialog(null, "�γ̺Ź��������������룡\n�γ̺�ӦС��3���ַ���");
							return;
						}
						userDao.setNewCourseCid(dbUtil.getCon(), newCid, newCidOrName);
					}
					if (!newName.isEmpty()) {
						if (jtfSetName.getText().length() > 30) {
							JOptionPane.showMessageDialog(null, "�γ������������������룡\n�γ���ӦС��30���ַ���");
							return;
						}
						userDao.setNewCourseName(dbUtil.getCon(), newName, newCidOrName);
					}
					if (jckSetCredit.getSelectedIndex() != 0)
						userDao.setNewCourseCredit(dbUtil.getCon(), jckSetCredit.getSelectedIndex(), newCidOrName);

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, "�޸ĳɹ�!");
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
			jlCourseCount.setText("��ǰ���й� " + jtable.getRowCount() + " ����¼!");
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
				JOptionPane.showMessageDialog(null, "�ÿγ� �� " + courseName[i] + " �� ����ѧ��ѡ�ޣ�����ɾ��");
				return;
			} else {
				userDao.deleteCourse(dbUtil.getCon(), values[i]);
				JOptionPane.showMessageDialog(null, " �� " + courseName[i] + " �� ɾ���ɹ�!");
			}
			// System.out.println(userDao.isCourseSelected(dbUtil.getCon(),
			// values[i]));

		}
		refresh();
	}
}