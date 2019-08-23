package com.wwls.modules.sys.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wwls.common.config.Global;
import com.wwls.common.persistence.Page;
import com.wwls.common.utils.StringUtils;
import com.wwls.common.web.BaseController;
import com.wwls.modules.sys.entity.Office;
import com.wwls.modules.sys.entity.User;
import com.wwls.modules.sys.service.OfficeService;
import com.wwls.modules.sys.utils.DictUtils;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 机构Controller
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			Office  office =officeService.get(id) ;
			String companyType =	office.getCompanyType();
			if(companyType!=null&&companyType.contains(",")){
				office.setCompanyTypeList(Arrays.asList(companyType.split(",")));
				logger.debug("获取的列表是"+office.getCompanyTypeList());
			}
			return office;
		}else{
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
//        model.addAttribute("list", officeService.findAll());
		return "modules/sys/officeIndex";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"list"})
	public String list(Office office, Model model) {
        model.addAttribute("list", officeService.findList(office));
		return "modules/sys/officeList";
	}
	
	/**
	 * 机构信息修改表单
	 * @param office
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}
		model.addAttribute("office", office);
		return "modules/sys/officeForm";
	}
	
	
	/**
	 * 机构信息添加表单
	 * @param office
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "addForm")
	public String addForm(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}
		
		model.addAttribute("office", office);
		return "modules/sys/officeAddForm";
	}
	
	
	/**
	 * 机构信息修改
	 * @param office
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "updateInfo")
	public String updateInfo(Office office, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if (!beanValidator(model, office)){
			return form(office, model);
		}
		if(office.getCompanyTypeList()!=null){
			StringBuffer sb  = new StringBuffer();
			for(String cType:office.getCompanyTypeList()){
				sb.append(cType);
				sb.append(",");
				
			}
			office.setCompanyType(sb.toString());
			
		}
		office.setSort(office.getSort());
		officeService.save(office);
		
		if(office.getChildDeptList()!=null){
			Office childOffice = null;
			for(String id : office.getChildDeptList()){
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setSort(office.getSort());
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+1));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}
		
		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
		return "redirect:" + adminPath + "/sys/office/list?id="+id+"&parentIds="+office.getParentIds();
	}
	
	/**
	 * 机构信息新增
	 * @param office
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:office:add")
	@RequestMapping(value = "addSave")
	public String addSave(Office office, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if (!beanValidator(model, office)){
			return addForm(office, model);
		}
		if(office.getCompanyTypeList()!=null){
			StringBuffer sb  = new StringBuffer();
			for(String cType:office.getCompanyTypeList()){
				sb.append(cType);
				sb.append(",");
				
			}
			office.setCompanyType(sb.toString());
			
		}
		office.setSort(office.getSort());
		officeService.save(office);
		
		if(office.getChildDeptList()!=null){
			Office childOffice = null;
			for(String id : office.getChildDeptList()){
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setSort(office.getSort());
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+1));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}
		
		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
		return "redirect:" + adminPath + "/sys/office/list?id="+id+"&parentIds="+office.getParentIds();
	}
	
	/**
	 * 删除机构信息
	 * @param office
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:office:del")
	@RequestMapping(value = "delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list";
		}
//		if (Office.isRoot(id)){
//			addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
//		}else{
			officeService.delete(office);
			addMessage(redirectAttributes, "删除机构成功");
//		}
		return "redirect:" + adminPath + "/sys/office/list?id="+office.getParentId()+"&parentIds="+office.getParentIds();
	}

	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	/**
	 * 内容提供商列表
	 * @param office
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "cpList")
	public String cpList(Office office,HttpServletRequest request, HttpServletResponse response, Model model) {
		if(office==null){
			office= new Office();	
		}
		office.setCompanyType("1,");
		Page<Office> page = officeService.findPage(new Page<Office>(request, response), office);
        model.addAttribute("page", page);
		return "modules/content/distribute/cpList";
	}
	
	/**
	 * 运营机构列表
	 * @param office
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "resultList")
	public String resultList(Office office,HttpServletRequest request, HttpServletResponse response, Model model,String types) {
		if(office==null){
		 
			office= new Office();	
			
		}
		office.setParent(UserUtils.getUser().getOffice());
		Page<Office> page = officeService.findPage(new Page<Office>(request, response), office);
        model.addAttribute("page", page);
        if (StringUtils.isBlank(types)) {
        	return "modules/content/distribute/coList";
        }else{
        	return "modules/product/category/coList";
        }
	}
	
	
	/**
	 * 运营机构列表
	 * @param office
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "coList")
	public String coList(Office office,HttpServletRequest request, HttpServletResponse response, Model model) {
		if(office==null){
			office= new Office();	
		}
		office.setCompanyType("2,");
		Page<Office> page = officeService.findPage(new Page<Office>(request, response), office);
        model.addAttribute("page", page);
        	return "modules/content/distribute/coList";
       
	}
	
	
	/**
	 * 公司行政机构列表
	 * @param office
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"pbDepartmentList"})
	public String pbDepartmentList(Office office, Model model) {
		
		List<Office> list = officeService.pbDepartmentList(office);
        model.addAttribute("list", list);
		return "modules/sys/pbDepartmentList";
	}
	
	/**
	 * 部门添加表单
	 * @param office
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "pbDepartmentForm")
	public String pbDepartmentForm(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}
		model.addAttribute("office", office);
		return "modules/sys/pbDepartmentForm";
	}
	
	
	/**
	 * 部门添加保存
	 * @param office
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "officeSave")
	public String officeSave(Office office, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if (!beanValidator(model, office)){
			return pbDepartmentForm(office, model);
		}
		
       //根据获取到的公司名称遍历数据库，有数据的进行有数据的处理，没有的进行无数据的处理
		office.setName(office.getName());//公司名称
		office.setUseable("1");
		Office office1=officeService.getByName(office);
		
		Office  office2 = new Office();
		office2.setDelFlag("0");
        if(office1!=null){
        	addMessage(redirectAttributes, "该部门已存在，请重新添加新的部门！");
    		return "redirect:" + adminPath + "/sys/office/pbDepartmentList";
        }else{
        	officeService.save(office);
        }
		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		return "redirect:" + adminPath + "/sys/office/pbDepartmentList";
	}
	
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeDataOffice")
	public List<Map<String, Object>> treeDataOffice(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Office office = new Office();
		if(office.getCurrentUser().getOffice().getId().equals(Global.getConfig("office_id"))){
			office.setNotId(Global.getConfig("office_id"));
		}else{
			office.setId(Global.getConfig("office_id"));
		}
		List<Office> list = officeService.findOfficeList(office);
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()))){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
}
