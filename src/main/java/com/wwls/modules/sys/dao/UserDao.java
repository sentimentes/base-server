package com.wwls.modules.sys.dao;

import java.util.List;
import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.sys.entity.User;

/**
 * 用户DAO接口
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);
 
	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	
	
	/**
	 * 根据白名单id物理删除系统用户数据
	 * @param user
	 * @return
	 */
	public int physicsDelete(User user);
	
	
	/**
	 * 根据用户信息更新用户的登录状态
	 * @param user
	 * @return
	 */
	public int updateStatus(User user);
	
	/**
	 * 根据用户ID更新用户登录的初始ip
	 * @param user
	 * @return
	 */
	public int updateStartLoginIp(User user);
	
	/**
	 * 根据用户ID和访问ip获取到用户的信息
	 * @param id
	 * @return
	 */
	public User getIdOrIp(User user);
	
	
	/**
	 * 更新全部用户的ip
	 * @param user
	 */
	public int updateAllIp(User user);
	
	/**
	 * 根据ip获取用户信息
	 * @param loginName
	 * @return
	 */
	public List<User> getByIp(User user);
	
	
	/**
	 * 更新用户状态
	 * @param user
	 * @return
	 */
	public int updateLoginFlag(User user);
}
