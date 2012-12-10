package tms.core.service.admin.impl;

import tms.base.service.BaseServiceImpl;
import tms.core.dao.admin.AdministratorDao;
import tms.core.dao.student.PreviewDao;
import tms.core.dao.student.StudentDao;
import tms.core.model.admin.Admin;
import tms.core.model.student.Preview;
import tms.core.model.student.Student;
import tms.core.model.teacher.Thesis;
import tms.core.service.admin.AdminService;
import tms.core.service.teacher.ThesisService;
import tms.core.util.Message;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional(readOnly = true)
public class AdminServiceImpl extends BaseServiceImpl implements AdminService {

	@Resource
	private AdministratorDao administratorDao;

	@Override
	public List<Admin> getAdministrators() {
		List<Admin> admins = administratorDao.findAll();

		logger.debug("获取Administrator数量：{}", admins.size());
		return admins;
	}

	@Override
	@Transactional(readOnly = false)
	public Admin addAdministrator(Admin administrator) {
		return administratorDao.save(administrator);
	}

	@Override
	@Transactional(readOnly = false)
	public Admin addOrUpdateAdministrator(Admin administrator) {
		Admin _a = administratorDao.findByLoginName(administrator.getLoginName());
		if (_a != null) {
			administrator.setId(_a.getId());
		}

		return administratorDao.save(administrator);
	}

	@Override
	@Transactional(readOnly = false)
	public Admin delAdministrator(Long administratorId) {
		Admin admin = administratorDao.findOne(administratorId);
		administratorDao.delete(admin);
		return admin;
	}

	@Resource
	private ThesisService thesisService;

	@Resource
	private PreviewDao previewDao;

	@Resource
	private StudentDao studentDao;

	@Override
	@Transactional(readOnly = false)
	public boolean autoAssign(Message message) {
		if (!autoAssignRoundOne()) {
			message.getMessages().add("Round 1 Failed");
			return false;
		} else {
			message.getMessages().add("Round 1 Success");
		}

		if (!autoAssignRoundTwo()) {
			message.getMessages().add("Round 2 Failed");
			return false;
		} else {
			message.getMessages().add("Round 2 Success");
		}
		if (!autoAssignRoundThree()) {
			message.getMessages().add("Round 3 Failed");
			return false;
		} else {
			message.getMessages().add("Round 3 Success");
		}
		if (!autoAssignRoundFour()) {
			message.getMessages().add("Round 4 Failed");
			return false;
		} else {
			message.getMessages().add("Round 4 Success");
		}

		return true;
	}

	@Transactional(readOnly = false)
	private boolean autoAssignRoundOne() {
		List<Thesis> theses = thesisService.getUnassignedThesises();
		if (assignByThesis(theses, 1))
			return true;
		return false;
	}

	@Transactional(readOnly = false)
	private boolean autoAssignRoundTwo() {
		List<Thesis> theses = thesisService.getUnassignedThesises();
		if (assignByThesis(theses, 2))
			return true;
		return false;
	}

	@Transactional(readOnly = false)
	private boolean autoAssignRoundThree() {
		List<Thesis> theses = thesisService.getUnassignedThesises();
		if (assignByThesis(theses, 3))
			return true;
		return false;
	}

	@Transactional(readOnly = false)
	private boolean autoAssignRoundFour() {
		List<Thesis> theses = thesisService.getUnassignedThesises();
		Iterator<Thesis> it = theses.iterator();
		while (it.hasNext()) {
			Thesis t = it.next();
			Long thesisId = t.getId();

			List<Student> unassignedStudents = thesisService.getUnassignedThesisesStudents();
			if (unassignedStudents.size() <= 0)
				break;
			int ran = random(0, unassignedStudents.size() - 1);
			Student s = unassignedStudents.get(ran);
			s = studentDao.findOne(s.getId());
			s.setThesisId(thesisId);
		}

		List<Student> unassignedStudents = thesisService.getUnassignedThesisesStudents();
		if (unassignedStudents.size() > 0)
			return false;

		return true;
	}

	@Transactional(readOnly = false)
	private boolean assignByThesis(List<Thesis> theses, int order) {
		Iterator<Thesis> it = theses.iterator();
		while (it.hasNext()) {
			Thesis t = it.next();
			Long thesisId = t.getId();
			List<Preview> _previews = previewDao.findByThesisIdAndSubjectOrder(thesisId, order);
			List<Preview> previews = Lists.newArrayList(_previews);
			if (previews == null || previews.size() == 0)
				continue;

			Iterator<Preview> it_p = previews.iterator();
			while (it_p.hasNext()) {
				Preview p = it_p.next();
				Long studentId = p.getStudentId();
				Student s = studentDao.findOne(studentId);
				if (s.getThesisId() != 0L)
					it.remove();
			}
			if (previews.size() == 0)
				continue;

			int ran = random(0, previews.size() - 1);
			Preview p = previews.get(ran);
			Long studentId = p.getStudentId();
			Student s = studentDao.findOne(studentId);
			s.setThesisId(t.getId());
		}

		return true;
	}

	private int random(int min, int max) {
		int ran = (int) Math.round(Math.random() * (max - min) + min);
		while (ran < min || ran > max) {
			ran = (int) Math.round(Math.random() * (max - min) + min);
		}
		return ran;
	}
}
