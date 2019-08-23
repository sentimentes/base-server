package com.wwls.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wwls.common.constant.DataResult;
import com.wwls.common.constant.HttpConstant;
import com.wwls.common.utils.StringUtils;
import com.wwls.common.web.BaseController;
import com.wwls.modules.sys.entity.Area;
import com.wwls.modules.sys.service.AreaService;

/**
 * 区域Controller
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${apiPath}/sys/area")
public class AreaAPIController extends BaseController {

	@Autowired
	private AreaService areaService;
	

	@RequestMapping(value = {"areaList", ""})
	@ResponseBody
	public DataResult phoneList(@RequestParam(required=false) String extId) {
		DataResult dataresult = new DataResult();
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Area> areaList = areaService.findAll();
		    if(areaList!=null && areaList.size()>0){
		    	for (int i=0; i<areaList.size(); i++){
					Area e = areaList.get(i);
					if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
						Map<String, Object> map = Maps.newHashMap();
						map.put("id", e.getId());
						map.put("pId", e.getParentId());
						map.put("name", e.getName());
						map.put("sort", e.getSort());
						map.put("type", e.getType());
						map.put("updateby", e.getUpdateBy());
						mapList.add(map);
					}
				}
		    	dataresult.setData(mapList);
		    	dataresult.setMsg("成功");
		    	dataresult.setStatus(HttpConstant.SUCCESS);
		    }else{
		    	dataresult.setData(mapList);
		    	dataresult.setMsg("没有对应数据");
		    	dataresult.setStatus(HttpConstant.FAILURE);
		    }
		return dataresult;
	}
	
	
	

	/*@RequiresPermissions("user")*/
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Area> list = areaService.findAll();
		for (int i=0; i<list.size(); i++){
			Area e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
}
