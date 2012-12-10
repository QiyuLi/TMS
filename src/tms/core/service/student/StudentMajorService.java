package tms.core.service.student;

import java.util.List;

import tms.base.service.BaseService;
import tms.core.model.student.StudentMajor;

public interface StudentMajorService extends BaseService {
	/**
	 * 获取学生major列表
	 * @return List<StudentMajor>
	 */
	public List<StudentMajor> getStudentMajors();

	/**
	 * 添加或更新学生major信息
	 * @param studentMajor
	 * @return
	 */
	public StudentMajor addOrUpdateStudentMajor(StudentMajor studentMajor);

	/**
	 * 删除学生major信息
	 * @param studentMajorId majorid
	 * @return
	 */
	public StudentMajor delStudentMajor(Long studentMajorId);
}
