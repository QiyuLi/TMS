package tms.core.web.admin;

import tms.base.web.BaseController;
import tms.core.model.student.StudentClass;
import tms.core.model.student.StudentMajor;
import tms.core.service.student.StudentClassService;
import tms.core.service.student.StudentMajorService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminStudentMajorClassManageController extends BaseController{

	@Resource
	private StudentMajorService studentMajorServicce;
	
	@Resource
	private StudentClassService studentClassService;
	
	@RequestMapping(value = {"/studentMajorClassManage"})
	public String showStudentMajorClassManage(Model model){
		return getContent(model);
	}
	
	@RequestMapping(value = {"/studentMajorManage/add"},method = RequestMethod.POST)
	public String addStudentMajor(StudentMajor studentMajor,Model model){
		
		logger.debug("新增学生major：{}",studentMajor.toString());
		
		boolean success = false;
		if(studentMajorServicce.addOrUpdateStudentMajor(studentMajor) != null){
			success = true;
		}
		model.addAttribute("success", success);
		return getContent(model);
	}
	
	@RequestMapping(value = {"/studentMajorManage/del/{studentMajorId}"})
	public String delStudentMajor(@PathVariable("studentMajorId")Long studentMajorId,Model model){
		boolean success = false;
		if(studentMajorServicce.delStudentMajor(studentMajorId) != null){
			success = true;
		}
		model.addAttribute("success", success);
		
		return getContent(model);
	}
	
	@RequestMapping(value = {"/studentClassManage/add"},method = RequestMethod.POST)
	public String addStudentClass(StudentClass studentClass,Model model){
		logger.debug("新增学生class：{}",studentClass.toString());
		
		boolean success = false;
		if(studentClassService.addOrUpdateStudentClass(studentClass) != null){
			success = true;
		}
		model.addAttribute("success", success);
		return getContent(model);
	}
	
	@RequestMapping(value = {"/studentClassManage/del/{studentClassId}"})
	public String delStudentClass(@PathVariable("studentClassId")Long studentClassId,Model model){
		boolean success = false;
		if(studentClassService.delStudentClass(studentClassId) != null){
			success = true;
		}
		model.addAttribute("success", success);
		
		return getContent(model);
	}
	
	private String getContent(Model model){
		List<StudentMajor> studentMajors = studentMajorServicce.getStudentMajors();
		model.addAttribute("studentMajors", studentMajors);
		
		List<StudentClass> studentClasses = studentClassService.getStudentClasses();
		model.addAttribute("studentClasses", studentClasses);
		
		return "/admin/studentMajorClassManage";
	}
}
