package cn.dishui.core.service.service;

import java.util.Map;

public interface StaticPageService {
	/**
	 * 存入项目目录
	 * @param root
	 * @param id
	 */
	public void index(Map<String, Object> root, Integer id);
	/**
	 * 存入图片服务器
	 * @param root
	 * @param id
	 */
	public void index2(Map<String, Object> root, Integer id);
}
