package in.ineuron.dao;

import in.ineuron.dto.Student;

public interface IStudentDao {
	String save(Student student);
	Student findById(Integer sid);
	String updateById(Student student);
	String deleteById(Integer sid);
}
