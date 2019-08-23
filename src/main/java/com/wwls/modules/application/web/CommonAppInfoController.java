package com.wwls.modules.application.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wwls.common.config.Global;
import com.wwls.common.persistence.Page;
import com.wwls.common.web.BaseController;
import com.wwls.common.utils.FtpUtil;
import com.wwls.common.utils.StringUtils;
import com.wwls.common.utils.ftp.FileUtils;
import com.wwls.modules.application.entity.CommonAppInfo;
import com.wwls.modules.application.service.CommonAppInfoService;

/**
 * 应用信息Controller
 * @author mengyanan
 * @version 2016-06-24
 */
@Controller
@RequestMapping(value = "${adminPath}/application/commonAppInfo")
public class CommonAppInfoController extends BaseController {

	@Autowired
	private CommonAppInfoService commonAppInfoService;
	
	@ModelAttribute
	public CommonAppInfo get(@RequestParam(required=false) String id) {
		CommonAppInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = commonAppInfoService.get(id);
		}
		if (entity == null){
			entity = new CommonAppInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("application:commonAppInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CommonAppInfo commonAppInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CommonAppInfo> page = commonAppInfoService.findPage(new Page<CommonAppInfo>(request, response), commonAppInfo); 
		model.addAttribute("page", page);
		return "modules/application/commonAppInfoList";
	}

	@RequiresPermissions("application:commonAppInfo:view")
	@RequestMapping(value = "form")
	public String form(CommonAppInfo commonAppInfo, Model model) {
		model.addAttribute("commonAppInfo", commonAppInfo);
		return "modules/application/commonAppInfoForm";
	}

	@RequiresPermissions("application:commonAppInfo:edit")
	@RequestMapping(value = "save")
	public String save(CommonAppInfo commonAppInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, commonAppInfo)){
			return form(commonAppInfo, model);
		}
		 if(commonAppInfo.getIsNewRecord()){
			 commonAppInfo.setShopId(commonAppInfo.getCurrentUser().getOffice().getId());
		  }
		 MultipartFile imageTemp =commonAppInfo.getAppTemp();
		 logger.debug("获取的文件是==="+imageTemp.isEmpty());
		 if(imageTemp!=null&&!imageTemp.isEmpty()){
				/****
				 * 文件上传可以参考此处代码
				 ****/
			String filePath = FileUtils.getFilePath();
			 String filename=   FileUtils.getFileName(imageTemp);
			 InputStream inputStream;
			try {
				inputStream =imageTemp.getInputStream();
			boolean tag =	FtpUtil.uploadFileSimple(filePath, filename, inputStream);
			if(tag){
				   String image = filePath + filename;
				   commonAppInfo.setAppImage(image);
				   logger.debug("获取文件的上传路径是====="+image);	 
				   logger.debug("文件上传结果是======"+tag);}
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
		commonAppInfoService.save(commonAppInfo);
		addMessage(redirectAttributes, "保存应用信息成功");
		return "redirect:"+Global.getAdminPath()+"/application/commonAppInfo/?repage";
	}
	
	@RequiresPermissions("application:commonAppInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CommonAppInfo commonAppInfo, RedirectAttributes redirectAttributes) {
		commonAppInfoService.delete(commonAppInfo);
		addMessage(redirectAttributes, "删除应用信息成功");
		return "redirect:"+Global.getAdminPath()+"/application/commonAppInfo/?repage";
	}
	
	/**
	 * 工会党建通用信息添加表单
	 * @param commonAppInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("application:commonAppInfo:view")
	@RequestMapping(value = "commonForm")
	public String commonForm(CommonAppInfo commonAppInfo, Model model) {
		model.addAttribute("commonAppInfo", commonAppInfo);
		return "modules/application/commonForm";
	}

}