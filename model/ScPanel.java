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
		vector_head.add("ѧ����");
		vector_head.add("�γ̺�");
		vector_head.add("�ɼ�");
		vector_data = userDao.checkSc(dbUtil.getCon());
		DefaultTableModel dtm = new DefaultTableModel(vector_data, vector_head);
		jtable = new JTable();
		jtable.setModel(dtm);

		jsp = new JScrollPane(jtable);
		jsp.setBorder(new TitledBorder("ѡ�ޱ�"));

		refresh();

		JPanel jpanelDel = new JPanel();
		jpanelDel.add(new JLabel("ѧ����:"));
		jpanelDel.add(jtfDelSid = new JTextField(20));
		jpanelDel.add(new JLabel("�γ̺�:"));
		jpanelDel.add(jtfDelCid = new JTextField(10));
		jpanelDel.add(jbDelSummit = new JButton("�ύ"));
		jpanelDel.add(jbDelCancel = new JButton("ȡ��"));
		jpanelDel.add(new JLabel("*��ֱ����ѡ�ޱ���ѡ������һ������*"));// �հױ�ǩ���޹��ܣ�ʹɾ��ҳ���������и����롣
		jpanelDel.add(new JLabel("*Ȼ����ɾ����ť��ֱ��ɾ��*"));// �հױ�ǩ���޹��ܣ�ʹɾ��ҳ���������и����롣
		jpanelDel.add(new JLabel());// �հױ�ǩ���޹��ܣ�ʹɾ��ҳ���������и����롣
		jpanelDel.setLayout(new GridLayout(7, 2, 25, 50));
		jpanelDel.setBorder(new TitledBorder("ɾ��ѡ��"));

		JPanel jpanelSet = new JPanel();
		jpanelSet.add(new JLabel("ѧ����:"));
		jpanelSet.add(jtfSetSid = new JTextField(10));
		jpanelSet.add(new JLabel("�γ̺�:"));
		jpanelSet.add(jtfSetCid = new JTextField(10));
		jpanelSet.add(new JLabel("�³ɼ�:"));
		jpanelSet.add(jtfSetgrade = new JTextField(20));
		;
		jpanelSet.add(jbSetSummit = new JButton("�ύ"));
		jpanelSet.add(jbSetCancel = new JButton("ȡ��"));
		jpanelSet.setLayout(new GridLayout(7, 2, 25, 50));
		jpanelSet.setBorder(new TitledBorder("�޸�ѡ��"));

		JPanel panel = new JPanel();
		// panel.add(jb0 = new JButton("ˢ��"));
		// panel.add(jb1 = new JButton("���ѡ��"));
		panel.add(new JLabel("��ѧ��ҳ��\n���ѡ��"));
		panel.add(jb0 = new JButton("ˢ��"));
		panel.add(jb2 = new JButton("ɾ��ѡ��"));
		panel.add(jb3 = new JButton("�޸�ѡ��"));
		panel.setLayout(new GridLayout(4, 1, 5, 10));
		panel.setBorder(new TitledBorder("������:"));

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
					if (JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ��ѡ����ѧ��ѡ�޳ɼ���?") == 0)
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
					JOptionPane.showMessageDialog(null, "�γ̺Ź��������������룡\n�γ̺�ӦС��5���ַ���");
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

				try {
					userDao.deleteSc(dbUtil.getCon(), sid, cid);
				} catch (Exception e2) {
				}
				JOptionPane.showMessageDialog(null, "ɾ���ɹ�!");
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
					JOptionPane.showMessageDialog(null, "�γ̺Ź��������������룡\n�γ̺�ӦС��5���ַ���");
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
			jlScCount.setText("��ǰ���й� " + jtable.getRowCount() + " ����¼!");
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
		JOptionPane.showMessageDialog(null, "ɾ���ɹ�!");
		refresh();

	}

}
