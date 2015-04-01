package model;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class ManagerFrame extends JFrame {
	private String userName;
	JButton jbCheckStudent, jbCheckCourse, jbCheckSc, jbMainframe, jbCheck, jbExit;
	CardLayout card = new CardLayout();
	JPanel cardpanel;

	public ManagerFrame() {
	}

	public ManagerFrame(String userName) throws Exception {
		super();
		this.userName = userName;
		initFrame();
	}

	private void initFrame() throws Exception {

		this.setTitle("学生信息管理系统");
		JLabel jlWelcon = new JLabel("Student Information Manager", JLabel.CENTER);
		jlWelcon.setFont(new Font("Aharoni", Font.BOLD, 24));
		JLabel jlHello = new JLabel();
		jlHello.setHorizontalAlignment(JLabel.RIGHT);
		jlHello.setText("       欢迎你： " + userName + "!");
		jlHello.setForeground(Color.BLUE);
		JPanel panel1 = new JPanel();
		panel1.add(jlWelcon, BorderLayout.CENTER);
		panel1.add(jlHello, BorderLayout.SOUTH);
		this.add(panel1, BorderLayout.NORTH);

		JPanel jpButtons = new JPanel();
		jpButtons.add(jbMainframe = new JButton("主页"));
		jpButtons.add(jbCheck = new JButton("查看学生成绩"));
		jpButtons.add(jbCheckStudent = new JButton("查看学生信息"));
		jpButtons.add(jbCheckCourse = new JButton("查看课程信息"));
		jpButtons.add(jbCheckSc = new JButton("查看选修信息"));
		jpButtons.add(jbExit = new JButton("退出"));
		jpButtons.setLayout(new GridLayout(7, 1, 5, 50));
		jpButtons.setBorder(new TitledBorder("主功能区："));

		cardpanel = new JPanel(card);
		cardpanel.add(new mainPanel(), "mainframe");
		cardpanel.add(new StudentPanel(), "Studentframe");
		cardpanel.add(new CoursePanel(), "Courseframe");
		cardpanel.add(new ScPanel(), "Scframe");
		cardpanel.add(new CheckStudentGrade(), "CheckStudent grade");

		this.add(cardpanel, BorderLayout.CENTER);
		this.add(jpButtons, BorderLayout.WEST);

		this.setSize(870, 660);
		this.setResizable(false);

		jbMainframe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.first(cardpanel);
			}
		});

		jbCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(cardpanel, "CheckStudent grade");
			}
		});

		jbCheckStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(cardpanel, "Studentframe");
			}
		});
		jbCheckSc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(cardpanel, "Scframe");
			}
		});
		jbCheckCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(cardpanel, "Courseframe");
			}
		});

		jbExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "您确定要退出吗?");
				if (result == 0)
					System.exit(0);

			}
		});

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

	}

	class mainPanel extends JPanel {
		Image image = new ImageIcon("resource/078.jpg").getImage();

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (image != null)
				g.drawImage(image, 30, 30, 674, 548, this);
		}
	}

}
