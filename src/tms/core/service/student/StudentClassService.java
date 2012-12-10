package tms.core.service.student;

import tms.base.service.BaseService;
import tms.core.model.student.StudentClass;

import java.util.List;

public interface StudentClassService extends BaseService {
	/**
	 * 获取学生major列表
	 * @return List<StudentMajor>
	 */
	public List<StudentClass> getStudentClasses();

	/**
	 * 添加或更新学生class列表
	 * @param studentClass
	 * @return
	 */
	public StudentClass addOrUpdateStudentClass(StudentClass studentClass);

	/**
	 * 删除学生class信息
	 * @param studentClassId
	 * @return
	 */
	public StudentClass delStudentClass(Long studentClassId);
}
