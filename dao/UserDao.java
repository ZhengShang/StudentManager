package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.Course;
import model.Student;
import model.User;

public class UserDao {
	@SuppressWarnings("rawtypes")
	Vector<Vector> vector = new Vector<Vector>();

	/**
	 * 用户登录
	 * 
	 * @param con
	 *            数据库连接
	 * @param user
	 *            当前用户
	 * @return
	 * @throws Exception
	 */
	public User login(Connection con, User user) throws Exception {
		User resultUser = null;
		String sql = "select * from t_userinfo where name=? and pwd=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			resultUser = new User();
			resultUser.setUserName(rs.getString("name"));
			resultUser.setPassword(rs.getString("pwd"));
		}
		con.close();
		return resultUser;
	}

	/**
	 * 查看学生表
	 * 
	 * @param con
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector checkStudent(Connection con) throws Exception {
		vector.removeAllElements();// 清空vector里面的所有组件
		String sql = "select * from t_student";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			Vector temp = new Vector();
			temp.add(rs.getString(1));
			temp.add(rs.getString(2));
			temp.add(rs.getString(3));
			temp.add(rs.getInt(4));
			vector.add(temp);
		}
		con.close();
		return vector;
	}

	public String selectStudentInfo(Connection con, String sid) throws Exception {
		String sql = "select * from t_student where sid=?";
		String s = null;
		PreparedStatement pm = con.prepareStatement(sql);
		pm.setString(1, sid);
		ResultSet rs = pm.executeQuery();
		while (rs.next()) {
			s = (rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + "  " + rs.getInt(4));
		}
		return s;
	}

	/**
	 * 查看课程表
	 * 
	 * @param con
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector checkCourse(Connection con) throws Exception {
		vector.removeAllElements();
		String sql = "select * from t_course";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			Vector temp = new Vector();
			temp.add(rs.getString(1));
			temp.add(rs.getString(2));
			temp.add(rs.getFloat(3));
			vector.add(temp);
		}
		con.close();
		return vector;
	}

	/**
	 * 查看选修表
	 * 
	 * @param con
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector checkSc(Connection con) throws Exception {
		vector.removeAllElements();
		String sql = "select * from t_sc";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			Vector temp = new Vector();
			temp.add(rs.getString(1));
			temp.add(rs.getString(2));
			temp.add(rs.getFloat(3));
			vector.add(temp);
		}
		con.close();
		return vector;
	}

	/**
	 * 查看单个学生成绩
	 * 
	 * @param con
	 * @param studentinfo
	 *            当前学生信息，在数据库中查找
	 * @return 返回一个Vector类，用来存贮数据表中的信息。temp存储一行信息，vector存储N个temp，即为整个数据表信息
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector checkSingleStudent(Connection con, String studentinfo) throws Exception {
		vector.removeAllElements();
		String sql = "select t_student.* , t_course.name,t_course.credit,t_sc.grade from t_student,t_course,t_sc where (t_student.sid=t_sc.sid and t_course.cid=t_sc.cid and "
				+ "t_student.sid=?) or (t_student.name=? and t_student.sid=t_sc.sid and t_course.cid=t_sc.cid) order by t_student.sid";
		PreparedStatement pt = con.prepareStatement(sql);
		pt.setString(1, studentinfo);
		pt.setString(2, studentinfo);
		ResultSet rs = pt.executeQuery();

		while (rs.next()) {
			Vector temp = new Vector();
			temp.add(rs.getString(1));
			temp.add(rs.getString(2));
			temp.add(rs.getString(3));
			temp.add(rs.getInt(4));
			temp.add(rs.getString(5));
			temp.add(rs.getFloat(6));
			temp.add(rs.getFloat(7));
			vector.add(temp);
		}
		con.close();
		return vector;
	}

	/**
	 * 查看所有学生成绩
	 * 
	 * @param con
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector checkAllStudent(Connection con) throws Exception {
		vector.removeAllElements();
		String sql = "SELECT t_student.*,t_course.name,t_course.credit,t_sc.grade FROM t_student,t_sc,t_course WHERE t_student.sid=t_sc.sid AND t_course.cid=t_sc.cid";

		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			Vector temp = new Vector();
			temp.add(rs.getString(1));
			temp.add(rs.getString(2));
			temp.add(rs.getString(3));
			temp.add(rs.getInt(4));
			temp.add(rs.getString(5));
			temp.add(rs.getFloat(6));
			temp.add(rs.getFloat(7));
			vector.add(temp);
		}
		con.close();
		return vector;
	}

	/**
	 * 添加学生
	 * 
	 * @param con
	 * @param sid
	 *            学生号
	 * @param name
	 *            学生姓名
	 * @param sex
	 *            性别
	 * @param age
	 *            年龄
	 * @throws Exception
	 */
	public void addStudent(Connection con, String sid, String name, String sex, int age) throws Exception {
		String sql = "insert into t_student values(?,?,?,?)";
		PreparedStatement pt = con.prepareStatement(sql);
		pt.setString(1, sid);
		pt.setString(2, name);
		pt.setString(3, sex);
		pt.setInt(4, age);
		pt.executeUpdate();
		JOptionPane.showMessageDialog(null, "添加成功!");
		con.close();

	}

	/**
	 * 验证当前学生是否存在
	 * 
	 * @param con
	 * @param info
	 *            当前学生信息，包含学生号或者学生姓名
	 * @return
	 * @throws Exception
	 */
	public Student isExistStudent(Connection con, String info) throws Exception {
		String sql = "select * from t_student where sid=? or name=?";
		Student student = null;
		PreparedStatement pm = con.prepareStatement(sql);
		pm.setString(1, info);
		pm.setString(2, info);
		ResultSet rs = pm.executeQuery();
		while (rs.next()) {
			student = new Student();
			student.setSid(rs.getString(1));
			student.setName(rs.getString(2));
		}
		con.close();
		return student;

	}

	/**
	 * 验证当前课程是否存在
	 * 
	 * @param con
	 * @param info
	 *            课程信息，包含课程名或课程号
	 * @return
	 * @throws Exception
	 */
	public Course isExistCourse(Connection con, String info) throws Exception {
		String sql = "select * from t_course where cid=? or name=?";
		Course course = null;
		PreparedStatement pm = con.prepareStatement(sql);
		pm.setString(1, info);
		pm.setString(2, info);
		ResultSet rs = pm.executeQuery();
		while (rs.next()) {
			course = new Course();
			course.setCid(rs.getString(1));
			course.setName(rs.getString(2));
		}
		con.close();
		return course;

	}

	/**
	 * 删除学生
	 * 
	 * @param con
	 * @param studentinfo
	 *            被删除的学生信息
	 * @throws Exception
	 */
	public void deleteStudent(Connection con, String studentinfo) throws Exception {
		String sql2 = "delete from t_student where sid=? or name=?";

		PreparedStatement pm2 = con.prepareStatement(sql2);
		pm2.setString(1, studentinfo);
		pm2.setString(2, studentinfo);
		pm2.executeUpdate();
		pm2.close();
		con.close();
	}

	// public void deleteStudent(Connection con,String values[]) throws
	// Exception {
	// for(int i=0;i<values.length;i++)
	// deleteStudent(con, values[i]);
	// }

	/**
	 * 更新学生的新学号
	 * 
	 * @param con
	 * @param newSid
	 *            新学号
	 * @param studentinfo
	 *            被修改的学生信息，包含学生号或者学生姓名
	 * @throws Exception
	 */
	public void setNewStudentSid(Connection con, String newSid, String studentinfo) throws Exception {
		String sql = "update t_student set sid=? where sid=? or name=?";
		updateStudent(con, sql, newSid, studentinfo);
	}

	public void setNewStudentName(Connection con, String newName, String studentinfo) throws Exception {
		String sql = "update t_student set name=? where sid=? or name=?";
		updateStudent(con, sql, newName, studentinfo);
	}

	public void setNewStudentSex(Connection con, String newSex, String studentinfo) throws Exception {
		String sql = "update t_student set sex=? where sid=? or name=?";
		updateStudent(con, sql, newSex, studentinfo);
	}

	public void setNewStudentAge(Connection con, int newAge, String studentinfo) throws Exception {
		String sql = "update t_student set age=? where sid=? or name=?";
		PreparedStatement pm = con.prepareStatement(sql);
		pm.setInt(1, newAge);
		pm.setString(2, studentinfo);
		pm.setString(3, studentinfo);
		pm.executeUpdate();
		con.close();

	}

	/**
	 * 更新学生信息
	 * 
	 * @param con
	 * @param sql
	 *            更新的sql语句
	 * @param setinfo
	 * @param studentinfo
	 * @throws Exception
	 */
	private void updateStudent(Connection con, String sql, String setinfo, String studentinfo) throws Exception {

		PreparedStatement pm = con.prepareStatement(sql);
		pm.setString(1, setinfo);
		pm.setString(2, studentinfo);
		pm.setString(3, studentinfo);
		pm.executeUpdate();
		con.close();

	}

	/**
	 * 添加课程
	 * 
	 * @param con
	 * @param cid
	 * @param name
	 * @param credit
	 * @throws Exception
	 */
	public void addCourse(Connection con, String cid, String name, float credit) throws Exception {
		String sql = "insert into t_course values (?,?,?)";
		PreparedStatement pm = con.prepareStatement(sql);
		pm.setString(1, cid);
		pm.setString(2, name);
		pm.setFloat(3, credit);
		pm.executeUpdate();
		con.close();

	}

	/**
	 * 删除课程
	 * 
	 * @param con
	 * @param courseinfo
	 * @throws Exception
	 */
	public void deleteCourse(Connection con, String courseinfo) throws Exception {
		String sql = "delete from t_course where cid=? or name=?";
		PreparedStatement pm = con.prepareStatement(sql);
		pm.setString(1, courseinfo);
		pm.setString(2, courseinfo);
		pm.executeUpdate();
		con.close();
	}

	public boolean isStudentSeleted(Connection con, String sid) throws Exception {
		String sql = "select distinct sid from t_sc where sid=?";
		PreparedStatement pm = con.prepareStatement(sql);
		pm.setString(1, sid);
		ResultSet rs = pm.executeQuery();
		boolean flag = rs.next();
		pm.close();
		con.close();
		return flag;

	}

	public boolean isCourseSelected(Connection con, String cid) throws Exception {
		String sql = "select distinct cid from t_sc where cid=?";
		PreparedStatement pm = con.prepareStatement(sql);
		pm.setString(1, cid);
		ResultSet rs = pm.executeQuery();
		boolean flag = rs.next();
		pm.close();
		con.close();
		return flag;
	}

	/**
	 * 设置课程号
	 * 
	 * @param con
	 * @param newCid
	 *            新课程号
	 * @param courseinfo
	 * @throws Exception
	 */
	public void setNewCourseCid(Connection con, String newCid, String courseinfo) throws Exception {
		String sql = "update t_course set cid=? where cid=? or name=?";
		updateCourse(con, sql, newCid, courseinfo);
	}

	public void setNewCourseName(Connection con, String newName, String courseinfo) throws Exception {
		String sql = "update t_course set name=? where cid=? or name=?";
		updateCourse(con, sql, newName, courseinfo);
	}

	public void setNewCourseCredit(Connection con, float newCredit, String courseinfo) throws Exception {
		String sql = "update t_course set credit=? where cid=? or name=?";
		PreparedStatement pm = con.prepareStatement(sql);
		pm.setFloat(1, newCredit);
		pm.setString(2, courseinfo);
		pm.setString(3, courseinfo);
		pm.executeUpdate();
		con.close();
	}

	/**
	 * 更新课程
	 * 
	 * @param con
	 * @param sql
	 *            更新的sql语句
	 * @param setinfo
	 * @param courseinfo
	 * @throws Exception
	 */
	private void updateCourse(Connection con, String sql, String setinfo, String courseinfo) throws Exception {
		PreparedStatement pm = con.prepareStatement(sql);
		pm.setString(1, setinfo);
		pm.setString(2, courseinfo);
		pm.setString(3, courseinfo);
		pm.executeUpdate();
	}

	/**
	 * 添加选修
	 * 
	 * @param con
	 * @param sid
	 * @param cid
	 * @param grade
	 * @throws Exception
	 */
	public void addSc(Connection con, String sid, String cid, float grade) throws Exception {
		String sql = "insert into t_sc values(?,?,?)";
		PreparedStatement pm = con.prepareStatement(sql);
		pm.setString(1, sid);
		pm.setString(2, cid);
		pm.setFloat(3, grade);
		pm.executeUpdate();
		con.close();
		JOptionPane.showMessageDialog(null, "添加成功!");
	}

	/**
	 * 删除选修
	 * 
	 * @param con
	 * @param sid
	 * @param cid
	 * @throws Exception
	 */
	public void deleteSc(Connection con, String sid, String cid) throws Exception {
		String sql = "delete from t_sc where sid=? and cid=?";
		PreparedStatement pm = con.prepareStatement(sql);
		pm.setString(1, sid);
		pm.setString(2, cid);
		pm.executeUpdate();
		con.close();
	}

	/**
	 * 设置选修
	 * 
	 * @param con
	 * @param sid
	 * @param cid
	 * @param grade
	 * @throws Exception
	 */
	public void setSc(Connection con, String sid, String cid, float grade) throws Exception {
		String sql = "update t_sc set grade=? where sid=? and cid=? ";
		PreparedStatement pm = con.prepareStatement(sql);
		pm.setFloat(1, grade);
		pm.setString(2, sid);
		pm.setString(3, cid);
		pm.executeUpdate();
		con.close();
		JOptionPane.showMessageDialog(null, "修改成功!");
	}
}
