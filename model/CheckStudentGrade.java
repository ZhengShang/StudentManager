package model;

import java.awt.BorderLayout;
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

public class CheckStudentGrade extends JPanel {

	private JButton jb1, jb2;
	private JTextField jtf;

	@SuppressWarnings("rawtypes")
	private Vector vector_data, vector_head;
	private JScrollPane jspTable;
	private JTable jtable = new JTable();

	@SuppressWarnings("unchecked")
	public CheckStudentGrade() {
		this.add(jspTable = new JScrollPane(jtable), BorderLayout.CENTER);
		JPanel jspFunction = new JPanel();
		jspFunction.add(new JLabel("����ѧ���Ż�����:"));
		jspFunction.add(jtf = new JTextField("11001", 7));
		jspFunction.add(jb1 = new JButton("�鿴"));
		jspFunction.add(jb2 = new JButton("�鿴����ѧ���ɼ�"));
		jspFunction.setLayout(new GridLayout(4, 1, 5, 5));
		this.add(jspFunction, BorderLayout.EAST);
		
		

		vector_head = new Vector<String>();
		vector_head.add("ѧ����");
		vector_head.add("����");
		vector_head.add("�Ա�");
		vector_head.add("����");
		vector_head.add("�γ���");
		vector_head.add("ѧ��");
		vector_head.add("����");
		jspTable.setBorder(new TitledBorder("ѧ���ɼ�"));

		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String studentinfo = jtf.getText().trim();
				if (studentinfo.isEmpty()) {
					JOptionPane.showMessageDialog(null, "������ѧ���Ż�����!");
					return;
				}
				
				Student student = new Student();
				try {
					student = new UserDao().isExistStudent(new DbUtil().getCon(), studentinfo);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				if (student == null) {
					JOptionPane.showMessageDialog(null, "��ѧ��������!");
					return;
				}

				UserDao userDao = new UserDao();
				DbUtil dbUtil = new DbUtil();
				try {
					vector_data = userDao.checkSingleStudent(dbUtil.getCon(), studentinfo);
					DefaultTableModel dtm = new DefaultTableModel(vector_data, vector_head);
					jtable.setModel(dtm);
//					((DefaultTableModel) jtable.getModel()).fireTableDataChanged();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserDao userDao = new UserDao();
				DbUtil dbUtil = new DbUtil();
				try {

					vector_data = userDao.checkAllStudent(dbUtil.getCon());

					DefaultTableModel dtm = new DefaultTableModel(vector_data, vector_head);
					jtable.setModel(dtm);
//					((DefaultTableModel) jtable.getModel()).fireTableDataChanged();

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.toString());
				}
			}
		});

	}
}

