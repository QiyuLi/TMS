package tms.core.web.root;

import tms.base.web.BaseController;
import tms.core.model.student.Student;
import tms.core.model.student.StudentClass;
import tms.core.model.student.StudentMajor;
import tms.core.service.student.StudentClassService;
import tms.core.service.student.StudentMajorService;
import tms.core.service.student.StudentService;
import tms.core.util.Message;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class StudentRegController extends BaseController {

	@Resource
	private StudentService studentService;

	@Resource
	private StudentMajorService studentMajorService;

	@Resource
	private StudentClassService studentClassService;

	@RequestMapping(value = "/studentReg", method = RequestMethod.GET)
	public String showInfo(Model model) {
		logger.debug("GET-show");

		model.addAttribute("student", new Student());

		//添加major列表
		List<StudentMajor> majors = studentMajorService.getStudentMajors();
		model.addAttribute("majors", majors);

		//添加class列表
		List<StudentClass> stuClasses = studentClassService.getStudentClasses();
		model.addAttribute("stuClasses", stuClasses);

		return "studentReg";
	}

	@RequestMapping(value = "/studentReg", method = RequestMethod.POST)
	public String addStudent(Student student, Model model) {
		logger.debug("POST-addStudent");

		logger.debug("网页获取信息：{}", student);

		//保存
		Message message = new Message();
		boolean success = false;
		if (studentService.regStudent(student, message) != null) {
			success = true;
		}

		if (success) {
			model.addAttribute("successMessage", "Reg success");
			return "login";
		} else {
			model.addAttribute("errorMessage", "Reg fail" + message);
			return showInfo(model);
		}
	}
}
