package com.wwls.modules.sys.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wwls.common.persistence.Page;
import com.wwls.common.utils.DateUtils;
import com.wwls.common.web.BaseController;
import com.wwls.modules.sys.entity.Log;
import com.wwls.modules.sys.service.LogService;

/**
 * 日志Controller
 * @version 2013-6-2
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/log")
public class LogController extends BaseController {

	@Autowired
	private LogService logService;
	
	private List<String> xAxisData;
	private Map<String,List<Double>> yAxisData;
	private Map<String,Integer> yAxisIndex;
	
	@RequiresPermissions("sys:log:view")
	@RequestMapping(value = {"list", ""})
	public String list(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Log> page = logService.findPage(new Page<Log>(request, response), log); 
        model.addAttribute("page", page);
		return "modules/sys/logList";
	}
	

	
	@RequiresPermissions("sys:log:view")
	@RequestMapping(value = "index")
	public String index(Log log,HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Log> list = logService.findList(log);
		Log log1 = logService.getTotalCount(log);
		if(log1!=null){
			log.setTotalCount(log1.getTotalCount());
		}else{
			log.setTotalCount(0);
		}
        model.addAttribute("list", list);
		//x轴数据
		request.setAttribute("xAxisData", getxAxisData());
		//y轴数据
		request.setAttribute("yAxisData", getyAxisData(log));
		//Y轴双轴情况下的位置定位
		request.setAttribute("yAxisIndex", getyAxisIndex());
		String timeSlot = DateUtils.formatDate(log.getBeginDate(),"yyyy-MM-dd")+" 至 "+DateUtils.formatDate(log.getEndDate(),"yyyy-MM-dd");
		request.setAttribute("timeSlot", timeSlot);//时间段
		
		return "modules/sys/bar/bar";
	}
	
	public List<String> getxAxisData(){
		xAxisData = new ArrayList<String>();
		xAxisData.add("登录异常 ");
		xAxisData.add("越权访问");
		xAxisData.add("一般异常");
		xAxisData.add("IP异常");
		xAxisData.add("正常日志");
		return xAxisData;
	}
	
	public Map<String,List<Double>> getyAxisData(Log log){
		yAxisData = new HashMap<String,List<Double>>();
		List<Double> data1 = new ArrayList<Double>();
		// 1、根据条件获取到登录异常的次数
		Log log1 = logService.getLoginCount(log);
		int totalCount = log.getTotalCount();
		DecimalFormat df = new DecimalFormat("#.###");
		double d1 = 0;
		double d2 = 0;
		double d3 = 0;
		double d4 = 0;
		double d5 = 0;
		if(log1!=null){
			d1 = Double.parseDouble(df.format((float)log1.getLoginCount()/totalCount));
		}
		 
		// 2、根据条件获取到越权访问的次数
		Log log2 = logService.getYqfwCount(log);
		if(log2!=null){
			d2 = Double.parseDouble(df.format((float)log2.getYqfwCount()/totalCount));
		}

		// 3、根据条件获取到一般异常的次数
		Log log3 = logService.getYbycCount(log);
		if(log3!=null){
			d3 = Double.parseDouble(df.format((float)log3.getYbycCount()/totalCount));
		}
		// 4、根据条件获取到IP异常的次数
		Log log4 = logService.getIpCount(log);
		if(log4!=null){
			d4 = Double.parseDouble(df.format((float)log4.getIpCount()/totalCount));
		}
		// 5、根据条件获取到正常日志的次数
		Log log5 = logService.getZcCount(log);
		if(log5!=null){
			d5 = Double.parseDouble(df.format((float)log5.getZcCount()/totalCount));
		}
		
		data1.add(d1);
		data1.add(d2);
		data1.add(d3);
		data1.add(d4);
		data1.add(d5);
		yAxisData.put("柱图", data1);
		
		return yAxisData;
	}
	
	public Map<String,Integer> getyAxisIndex(){
		yAxisIndex = new HashMap<String,Integer>();
		yAxisIndex.put("柱状一", 0);
		return yAxisIndex;
	}
}
