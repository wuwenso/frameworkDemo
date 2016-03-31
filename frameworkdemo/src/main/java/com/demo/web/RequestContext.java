package com.demo.web;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartRequest;

import com.demo.constans.Constants;
import com.demo.entity.auto.SystemMember;
import com.demo.util.RsData;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 来自OSChina的思想
 * @description
 * @author: xwang
 */
public class RequestContext {

	private final static Logger log = LoggerFactory
			.getLogger(RequestContext.class);
	private final static ThreadLocal<RequestContext> contexts = new ThreadLocal<RequestContext>();
	private final static String tmp_path; // 系统可以使用的临时目录
	private static final String UTF_8 = "UTF-8";

	private static String webroot = null;

	private ServletContext context;
	private HttpSession session;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String, Cookie> cookies;
	private RsData rsData;
	private String rs;

	static {
		webroot = getWebrootPath();
		// 上传的临时目录
		tmp_path = webroot + "WEB-INF" + File.separator + "tmp"
				+ File.separator;
		try {
			FileUtils.forceMkdir(new File(tmp_path));
		} catch (IOException excp) {
		}

	}

	private final static String getWebrootPath() {
		String root = RequestContext.class.getResource("/").getFile();
		try {
			root = new File(root).getParentFile().getParentFile()
					.getCanonicalPath();
			root += File.separator;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return root;
	}
	/**
	 * 初始化请求上下文
	 * 
	 * @param ctx
	 * @param req
	 * @param res
	 */
	public static RequestContext begin(ServletContext ctx,
			HttpServletRequest req, HttpServletResponse res) {
		RequestContext rc = new RequestContext();
		rc.context = ctx;
		rc.request = req;
		rc.response = res;
		rc.response.setCharacterEncoding(UTF_8);
		rc.session = req.getSession(false);
		rc.cookies = new HashMap<String, Cookie>();
		Cookie[] cookies = req.getCookies();
		if (cookies != null)
			for (Cookie ck : cookies) {
				rc.cookies.put(ck.getName(), ck);
			}
		contexts.set(rc);
		return rc;
	}
	
	public static RequestContext beginApi(HttpServletRequest req, HttpServletResponse res){
		RequestContext rc = new RequestContext();
		rc.request = req;
		rc.response = res;
		rc.response.setCharacterEncoding(UTF_8);
		contexts.set(rc);
		return rc;
	}
	/**
	 * 返回Web应用的路径
	 * 
	 * @return
	 */
	public static String root() {
		return webroot;
	}
	/**
	 * 获取当前请求的上下文
	 * 
	 * @return
	 */
	public static RequestContext get() {
		return contexts.get();
	}
	
	public void end() {
		this.context = null;
		this.request = null;
		this.response = null;
		this.session = null;
		this.cookies = null;
		this.rs = null;
		this.rsData = null;
		contexts.remove();
	}
	public Locale locale() {
		return request.getLocale();
	}
	public void closeCache() {
		header("Pragma", "No-cache");
		header("Cache-Control", "no-cache");
		header("Expires", 0L);
	}
	public String ip() {
		return request.getRemoteAddr();
	}
	public String uri() {
		return request.getRequestURI();
	}
	public String ctx() {
		return request.getContextPath();
	}
	public void redirect(String uri) throws IOException {
		response.sendRedirect(uri);
	}
	public void forward(String uri) throws ServletException, IOException {
		RequestDispatcher rd = context.getRequestDispatcher(uri);
		rd.forward(request, response);
	}
	public void include(String uri) throws ServletException, IOException {
		RequestDispatcher rd = context.getRequestDispatcher(uri);
		rd.include(request, response);
	}
	public boolean isUpload() {
		return (request instanceof MultipartRequest);
	}
	public static SystemMember getLoginUser(){
		RequestContext ctx=get();
		SystemMember member=null;
		Object obj=ctx.session().getAttribute(Constants.LOGIN_MEMBER_KEY);
		if(null!=obj){
			member=(SystemMember) obj;
		}
		return member;
	}
	/**
	 * 输出信息到浏览器
	 * @param msg
	 * @throws IOException
	 */
	public void print(Object msg) throws IOException {
		if (!UTF_8.equalsIgnoreCase(response.getCharacterEncoding()))
			response.setCharacterEncoding(UTF_8);
		response.getWriter().print(msg);
	}

	public void printXml(Object msg) throws IOException {
		response.setContentType("application/xml");
		print(msg);
	}
	/**
	 * 返回错误的http状态码
	 * 
	 * @param code
	 * @param msg
	 * @throws IOException
	 */
	public void error(int code, String... msg) throws IOException {
		if (msg.length > 0)
			response.sendError(code, msg[0]);
		else
			response.sendError(code);
	}
	public void forbidden() throws IOException {
		error(HttpServletResponse.SC_FORBIDDEN);
	}

	public void notFound() throws IOException {
		error(HttpServletResponse.SC_NOT_FOUND);
	}
	public ServletContext context() {
		return context;
	}
	public HttpSession session() {
		return session;
	}
	public HttpSession session(boolean create) {
		return (session == null && create) ? (session = request.getSession())
				: session;
	}
	public Object sessionAttr(String attr) {
		HttpSession ssn = session();
		return (ssn != null) ? ssn.getAttribute(attr) : null;
	}

	public HttpServletRequest request() {
		return request;
	}

	public byte[] readContentToByte() {
		try {
			return IOUtils.toByteArray(request.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public HttpServletResponse response() {
		return response;
	}
	public Cookie cookie(String name) {
		return cookies.get(name);
	}
	public void cookie(String name, String value, int max_age) {
		Cookie c = new Cookie(name, value); // 设定有效时间 以s为单位
		c.setMaxAge(max_age); // 设置Cookie路径和域名
		c.setPath("/");
		response.addCookie(c);
	}

	public void cookie(String name, String value) {
		Cookie c = new Cookie(name, value); // 设定有效时间 以s为单位
		c.setMaxAge(MAX_AGE); // 设置Cookie路径和域名
		c.setPath("/");
		response.addCookie(c);
	}
	public boolean deleteCookie(String name) {
		if (name != null) {
			Cookie cookie = cookie(name);
			if (cookie != null) {
				cookie.setMaxAge(0);// 如果0，就说明立即删除
				cookie.setPath("/");// 不要漏掉
				response.addCookie(cookie);
				return true;
			}
		}
		return false;
	}
	public String header(String name) {
		return request.getHeader(name);
	}
	public void header(String name, String value) {
		response.setHeader(name, value);
	}

	public void header(String name, int value) {
		response.setIntHeader(name, value);
	}

	public void header(String name, long value) {
		response.setDateHeader(name, value);
	}
	
	public RsData getRsData() {
		return rsData;
	}
	public void setRsData(RsData rsData) {
		this.rsData = rsData;
	}

	public String getRs() {
		return rs;
	}
	public void setRs(String rs) {
		this.rs = rs;
	}
	private final static int MAX_AGE = 86400 * 31; // 最大COOKIE有效期 为一个月
}
