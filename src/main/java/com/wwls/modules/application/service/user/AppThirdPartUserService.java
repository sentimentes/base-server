
package com.wwls.modules.application.service.user;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wwls.common.config.Global;
import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.common.utils.FtpUtil;
import com.wwls.common.utils.HttpImageUtils;
import com.wwls.common.utils.IdGen;
import com.wwls.common.utils.MD5Utils;
import com.wwls.modules.application.dao.user.AppMainUserDao;
import com.wwls.modules.application.dao.user.AppThirdPartUserDao;
import com.wwls.modules.application.entity.user.AppMainUser;
import com.wwls.modules.application.entity.user.AppThirdPartUser;
import com.wwls.modules.electricpower.dao.whitelist.ElWhiteListDao;
import com.wwls.modules.electricpower.entity.whitelist.ElWhiteList;
import com.wwls.modules.labourunion.entity.whitelist.LuWorkersWhitelist;


/**
 * 应用第三方用户Service
 * @author chenbaichuan
 * @version 2016-06-23
 */
@Service
@Transactional(readOnly = true)
public class AppThirdPartUserService extends CrudService<AppThirdPartUserDao, AppThirdPartUser> {
	 
	@Autowired
    private AppMainUserDao appMainUserDao;
	
	@Autowired
    private ElWhiteListDao elWhiteListDao;
	
	public AppThirdPartUser get(String id) {
		return super.get(id);
	}
	
	public List<AppThirdPartUser> findList(AppThirdPartUser appThirdPartUser) {
		return super.findList(appThirdPartUser);
	}
	
	public Page<AppThirdPartUser> findPage(Page<AppThirdPartUser> page, AppThirdPartUser appThirdPartUser) {
		return super.findPage(page, appThirdPartUser);
	}
	
	@Transactional(readOnly = false)
	public void save(AppThirdPartUser appThirdPartUser) {
		super.save(appThirdPartUser);
	}
	
	
	
	@Transactional(readOnly = false)
	public void delete(AppThirdPartUser appThirdPartUser) {
		super.delete(appThirdPartUser);
	}
	
	@Transactional(readOnly = false)
	public AppThirdPartUser getUserByOpenId(AppThirdPartUser appThirdPartUser){
	
		return dao.getUserByOpenId(appThirdPartUser);
	}
	
	@Transactional(readOnly = false)
	public AppThirdPartUser getUserByUnionId(AppThirdPartUser appThirdPartUser){
		 
		return dao.getUserByUnionId(appThirdPartUser);
	}
	
	
	
	
	
	/**
	 * 使用apenid和unionids更新数据
	 */
	/*@Transactional(readOnly = false)
	public void updateByUnionId(AppThirdPartUser appThirdPartUser,LuWorkersWhitelist luWorkersWhitelist) {
		dao.updateByUnionId(appThirdPartUser);
		
		//更新用户数据
		AppMainUser appMainUser = new AppMainUser();
		appMainUser.setId(appThirdPartUser.getMuserId());
		appMainUser.setPhone(appThirdPartUser.getPhone());
		appMainUser.setClientId(appThirdPartUser.getClientId());
		appMainUser.setHeadImage(appThirdPartUser.getHeadImage());
		appMainUser.setName(luWorkersWhitelist.getName());
		appMainUser.setGender(luWorkersWhitelist.getGender());
		AppMainUser mUser = appMainUserDao.get(appThirdPartUser.getMuserId());
		if(mUser.getOfficeId()==null){
			appMainUser.setOfficeId(appThirdPartUser.getOfficeId());
		}
		appMainUser.setUpdateDate(new Date());
		appMainUserDao.saveInformation(appMainUser);
		
	}*/
	
	/**
	 * 根据openId获取用户信息（针对工会用户使用）
	 * @param appThirdPartUser
	 * @return
	 */
	public AppThirdPartUser getLuUserByOpenId(AppThirdPartUser appThirdPartUser){

		return 	dao.getLuUserByOpenId(appThirdPartUser);

	}
	
	
	/**
	 * 根据主用户id获取第三方用户的信息
	 * @param appThirdPartUser
	 * @return
	 */
	public AppThirdPartUser getUserInfoById(AppThirdPartUser appThirdPartUser) {
		return dao.getUserInfoById(appThirdPartUser);
	}
	
	
	/**
	 * 根据主用户手机号获取第三方用户的信息
	 * @param appThirdPartUser
	 * @return
	 */
	public List<AppThirdPartUser> getByPhone(AppThirdPartUser appThirdPartUser) {
		return dao.getByPhone(appThirdPartUser);
	}
	
	
	/**
	 * 根据id修改用户数据
	 */
	@Transactional(readOnly = false)
	public void updateById(AppThirdPartUser appThirdPartUser,LuWorkersWhitelist luWorkersWhitelist) {
		dao.updateById(appThirdPartUser);
		
		// 更新用户数据
		AppMainUser appMainUser = new AppMainUser();
		appMainUser.setId(appThirdPartUser.getMuserId());
		appMainUser.setName(luWorkersWhitelist.getName());
		appMainUser.setGender(luWorkersWhitelist.getGender());
		appMainUser.setPhone(appThirdPartUser.getPhone());
		appMainUser.setUpdateDate(new Date());
		appMainUserDao.saveInformation(appMainUser);
	}
	
	
	
	
	
	/**
	 * 党建登录用户数据添加
	 * @param appThirdPartUser
	 */
	@Transactional(readOnly = false)
	public void pbInsert(AppThirdPartUser appThirdPartUser) {
		AppMainUser gmu = new AppMainUser();
		String token = MD5Utils.MD5(IdGen.uuid());
		gmu.preInsert();
		gmu.setNickName(appThirdPartUser.getNickName());
		gmu.setName(appThirdPartUser.getName());
		gmu.setGender(appThirdPartUser.getGender());
		gmu.setAccesstoken(token);
		gmu.setPhone(appThirdPartUser.getPhone());
		gmu.setOfficeId(appThirdPartUser.getOfficeId());
		gmu.setGrade("1");
		gmu.setWhiteListId(appThirdPartUser.getWhiteListId());
		gmu.setGradeName("LV1");
		gmu.setClientId(appThirdPartUser.getClientId());
		gmu.setLoginType("2");//第三方登录
		gmu.setChannelId(appThirdPartUser.getChannelId());
		gmu.setPublicUserId(appThirdPartUser.getPublicUserId());
 		 if(StringUtils.isNotBlank(appThirdPartUser.getHeadImage())){
		 String headImageUrl=	HttpImageUtils.getImgFromUrl(appThirdPartUser.getHeadImage());
		if(StringUtils.isNotBlank(headImageUrl)){
			File file = new File(Global.getConfig("web.upload.path")+headImageUrl);
			 InputStream input;
			try {
				input = new FileInputStream(file);
				 FtpUtil.uploadFileSimple("headImage", file.getName(), input);
					logger.debug("获取的图像地址是"+headImageUrl);
					gmu.setHeadImage("headImage/"+file.getName());
			} catch (FileNotFoundException e) {
				logger.info("抽取用户图片出错");
			}	 
			
		}
       } 
		gmu.setGrade("1");
		gmu.setGradeName("LV1");
		gmu.setWx("0");
		appMainUserDao.insert(gmu);
		appThirdPartUser.setMuserId(gmu.getId());
		appThirdPartUser.setId(IdGen.uuid());
		appThirdPartUser.setCreateDate(new Date());
		appThirdPartUser.setUpdateDate(new Date());
		dao.insert(appThirdPartUser);
		
		//注册完成后更新白名单的注册状态
		ElWhiteList elWhiteList = new ElWhiteList();
		elWhiteList.setId(appThirdPartUser.getWhiteListId());
		elWhiteListDao.updateById(elWhiteList);
	}
	
	
	/**
	 * 根据id修改用户数据--电力用户
	 */
	@Transactional(readOnly = false)
	public void updateByIdOfEl(AppThirdPartUser appThirdPartUser,ElWhiteList elWhiteList) {
		dao.updateById(appThirdPartUser);
		
		// 更新用户数据
		AppMainUser appMainUser = new AppMainUser();
		appMainUser.setId(appThirdPartUser.getMuserId());
		appMainUser.setName(elWhiteList.getName());
		appMainUser.setGender(elWhiteList.getGender());
		appMainUser.setPhone(elWhiteList.getPhone());
		appMainUser.setUpdateDate(new Date());
		appMainUserDao.saveInformation(appMainUser);
	}
	
}