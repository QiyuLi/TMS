package tms.core.service.teacher.impl;

import tms.base.service.BaseServiceImpl;
import tms.core.dao.student.PreviewDao;
import tms.core.dao.student.StudentDao;
import tms.core.dao.teacher.ThesisDao;
import tms.core.model.student.Preview;
import tms.core.model.student.Student;
import tms.core.model.teacher.Thesis;
import tms.core.service.teacher.ThesisService;
import tms.core.util.AssignStatus;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

@Service
@Transactional(readOnly = true)
public class ThesisServiceImpl extends BaseServiceImpl implements ThesisService {

	@Resource
	private ThesisDao thesisDao;

	@Resource
	private PreviewDao previewDao;

	@Resource
	private StudentDao studentDao;

	@Override
	public List<Thesis> getThesises() {
		List<Thesis> thesises = Lists.newArrayList(thesisDao.findAll());

		for (Thesis t : thesises) {
			String majorRestrict = t.getMajorRestrict();
			String[] ms = majorRestrict.split(",");
			List<String> majors = Arrays.asList(ms);
			t.setMajorRes(majors);
		}

		logger.debug("获取课题数量：{}", thesises.size());
		return thesises;
	}

	@Override
	public Thesis getThesis(Long thesisId) {
		Assert.notNull(thesisId);

		Thesis t = thesisDao.findOne(thesisId);
		String majorRestrict = t.getMajorRestrict();
		String[] ms = majorRestrict.split(",");
		List<String> majors = Arrays.asList(ms);
		t.setMajorRes(majors);

		return t;
	}

	@Override
	public List<Thesis> getUnassignedThesises() {
		List<Thesis> thesises = Lists.newArrayList(getThesises());
		Iterator<Thesis> it = thesises.iterator();
		while (it.hasNext()) {
			Thesis t = it.next();
			if (StringUtils.isNotBlank(t.getAssign()))//如果已经分配，则去除
				it.remove();
		}
		logger.debug("获取未分配课题数量：{}", thesises.size());
		logger.debug("课题：{}", StringUtils.join(thesises, ','));

		return thesises;
	}

	@Override
	public List<Student> getUnassignedThesisesStudents() {
		List<Student> students = Lists.newArrayList(studentDao.findAll());
		Iterator<Student> it = students.iterator();
		while (it.hasNext()) {
			Student s = it.next();
			if (s.getThesisId() != 0L)
				it.remove();
		}
		logger.debug("未分配学生数量：{}", students.size());

		return students;
	}

	@Override
	public List<Thesis> getUnassignedAndUnchoosedThesises(String studentLoginName) {
		Assert.notNull(studentLoginName);

		Student s = studentDao.findByLoginName(studentLoginName);
		Long studentId = s.getId();
		List<Preview> previews = previewDao.findByStudentId(studentId);

		List<Thesis> thesises = Lists.newArrayList(getUnassignedThesises());
		Iterator<Thesis> it = thesises.iterator();
		while (it.hasNext()) {
			Thesis t = it.next();
			for (Preview p : previews) {
				if (p.getThesisId() == t.getId()) {
					it.remove();
					break;
				}
			}
		}
		logger.debug("获取未分配且未Select课题数量：{}", thesises.size());

		return thesises;
	}

	@Override
	public boolean isAssigned(String studentLoginName) {
		Student s = studentDao.findByLoginName(studentLoginName);
		if (s.getThesisId() != 0L)
			return true;
		return false;
	}

	@Override
	public List<Thesis> getAssignedThesises() {
		List<Thesis> theses = Lists.newArrayList(thesisDao.findAll());
		Iterator<Thesis> it = theses.iterator();
		while (it.hasNext()) {
			Thesis t = it.next();
			if (StringUtils.isBlank(t.getAssign()))
				it.remove();
		}

		return theses;
	}

	@Override
	public List<Student> getAssignedThesisesStudents() {
		List<Thesis> these = getAssignedThesises();

		List<Student> students = Lists.newArrayList();
		for (Thesis t : these) {
			Long thesisId = t.getId();
			Student s = studentDao.findByThesisId(thesisId);
			students.add(s);
		}

		return students;
	}

	@Override
	@Transactional(readOnly = false)
	public Thesis assignThesis(Long thesisId, Long studentId, AssignStatus assignStatus) {
		Student s = studentDao.findOne(studentId);
		if (s.getThesisId() != 0)
			return null;
		s.setThesisId(thesisId);

		Thesis t = thesisDao.findOne(thesisId);
		t.setAssign(assignStatus.getValue());

		return t;
	}

	@Override
	@Transactional(readOnly = false)
	public Thesis unassignThesisByStudent(Long studentId) {
		Student s = studentDao.findOne(studentId);
		Long thesisId = s.getThesisId();
		if (thesisId == 0)
			return null;
		s.setThesisId(0L);

		Thesis t = thesisDao.findOne(thesisId);
		t.setAssign(AssignStatus.NULL.getValue());

		return t;
	}

	@Override
	@Transactional(readOnly = false)
	public Thesis unassignThesis(Long thesisId) {
		Student s = studentDao.findByThesisId(thesisId);
		Long studentId = s.getId();

		return unassignThesisByStudent(studentId);
	}
}
