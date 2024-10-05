package in.ineuron.factory;

import in.ineuron.dao.IStudentDao;
import in.ineuron.dao.StudentDaoImpl;

public class StudentDaoFactory {
	private static IStudentDao studentDaoImpl;
	
	private StudentDaoFactory() {
		
	}
	
	public static IStudentDao getStudentDao() {
		if(studentDaoImpl==null) {
			studentDaoImpl = new StudentDaoImpl();
		}
		return studentDaoImpl;
	}
}
