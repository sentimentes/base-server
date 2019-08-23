package com.wwls.modules.sys.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.wwls.common.utils.StringUtils;
import com.wwls.modules.sys.utils.PwdUtils;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 表单验证（包含验证码）过滤类

 * @version 2014-5-19
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {
	private Logger logger = LoggerFactory.getLogger(getClass());
	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
	private String messageParam = DEFAULT_MESSAGE_PARAM;
	

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		
		//HttpServletRequest re = (HttpServletRequest) request;
//		logger.info("用户==="+username);
//		logger.info("密码==="+password);
		
		//CookieUtils.setCookie(response, "jeesite.session.id", IdGen.uuid(),"/ec-power-server");
		
		
//		//解密密码
//		String key="abcdefgabcdefg12";
//		String decrypt=null;
//		try {
//			decrypt = RSACoder.aesDecrypt(password,key);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (decrypt==null){
//			decrypt = "";
//		}

		//解密后截取的md5秘闻传给sha-1生成秘闻存数据库
		String pwd = PwdUtils.decryptPsw(password);//整密码串
		String pwdMing = pwd.substring(pwd.indexOf(",")+1);//明文密码
		//String pwdStr = pwd.substring(0,pwd.indexOf(","));//密文密码
		
		//解密加密的用户名
		String pwd1 = PwdUtils.decryptPsw(username);//整密码串
		String pwdMing1 = pwd1.substring(pwd1.indexOf(",")+1);//明文密码
		
		
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
		String captcha = getCaptcha(request);
		boolean mobile = isMobileLogin(request);
		return new UsernamePasswordToken(pwdMing1, pwdMing.toCharArray(), rememberMe, host, captcha, mobile);
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	public String getMobileLoginParam() {
		return mobileLoginParam;
	}
	
	protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }
	
	public String getMessageParam() {
		return messageParam;
	}
	
	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}
	
	@Override
	protected void issueSuccessRedirect(ServletRequest request,
			ServletResponse response) throws Exception {
//		Principal p = UserUtils.getPrincipal();
//		if (p != null && !p.isMobileLogin()){
			 WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
//		}else{
//			super.issueSuccessRedirect(request, response);
//		}
	}

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request, ServletResponse response) {
		String className = e.getClass().getName(), message = "";
		if (IncorrectCredentialsException.class.getName().equals(className)
				|| UnknownAccountException.class.getName().equals(className)){
			message = "用户或密码错误或被锁定, 请重试!";
			//System.out.println("0==="+token);
			UserUtils.clearCache();
			UserUtils.getSubject().logout();
		}
		else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
			message = StringUtils.replace(e.getMessage(), "msg:", "");
			//LogUtils.saveLog(Servlets.getRequest(), null,e,e.getMessage());
		}
		else{
			message = "系统出现点问题，请稍后再试！";
			e.printStackTrace(); // 输出到控制台
			//LogUtils.saveLog(Servlets.getRequest(), null,e,message);
			UserUtils.clearCache();
			UserUtils.getSubject().logout();
		}
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);
        return true;
	}
	
}