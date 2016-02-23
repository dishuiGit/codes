package com.bizoss.trade.${table};

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import com.bizoss.frame.QueryList;
import com.bizoss.frame.SysMap;
import com.bizoss.frame.TradeInterf;
import com.bizoss.frame.dao.DbmInterf;
import com.bizoss.frame.util.ConstantsUtil;
import com.bizoss.frame.util.PackToList;
import com.bizoss.frame.exp.ApplicationException;
import org.apache.commons.beanutils.BeanUtils;

public class ${table?cap_first}Info implements TradeInterf{
	
	private QueryList queryList = new QueryList();

	public SysMap insert(DbmInterf dbmMgr, SysMap map) {
		${table?cap_first}_prop ${table} = new ${table?cap_first}_prop();
		String result_code = "0";
		try {
			//封装属性
			BeanUtils.populate(${table}, map.getMap());
			dbmMgr.update("insert${table?cap_first}", ${table});
		} catch (Exception e) {
			e.printStackTrace();
			result_code = "1";
		}
		map.setString("result_code", result_code);
		return map;
	}

	/**
	 * 删除
	 */
	public SysMap delete(DbmInterf dbmMgr, SysMap map) {
		String pkid_string = map.getString("id");
		String result_code = "0";
		try {
			delete(dbmMgr, pkid_string);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		map.setString("result_code", result_code);
		return map;
	}
	public void delete(DbmInterf dbmMgr,String pkid) throws ApplicationException{
		dbmMgr.update("delete${table?cap_first}", pkid);
	}
	/**
	 * 更新
	 */
	public SysMap update(DbmInterf dbmMgr, SysMap map) {
		${table?cap_first} ${table} = new ${table?cap_first}();
		String result_code = "0";
		try {
			//封装属性
			BeanUtils.populate(${table}, map.getMap());
			dbmMgr.update("update${table?cap_first}", ${table});
		} catch (Exception e) {
			result_code = "1";
		}
		map.setString("result_code", result_code);
		return map;
	}
	/**
	 * 分页查询
	 */
	public List getListByPage(Object obj, int start, int limit) {
		Map ${table}Map = (Hashtable)obj;
		if(start == 0){
			start = 0;
		}else{
			start = (start - 1) * limit;
		}
		${table}Map.put("start", start);
		${table}Map.put("limit", limit);
		return queryList.getList("getListBy${table?cap_first}Page", ${table}Map);

	}
	
	/**
	 * 查询所有
	 * @param obj
	 * @param flag  only|all
	 * @return
	 */
	public List<? extends ${table?cap_first}> getListByPage(${table?cap_first}_prop obj,String flag) {
		if(obj.getStart()==null){
			obj.setStart(0);
		}
		if(obj.getLimit()==null||obj.getLimit()==0){
			obj.setLimit(1000);
		}
		List tmp_l = queryList.getList("getListBy${table?cap_first}Page", obj);
		if (ConstantsUtil.ALL.equals(flag)) {
			return packObj(tmp_l);
		} else if (ConstantsUtil.ONLY.equals(flag)) {
			return PackToList.packObj(tmp_l, ${table?cap_first}_prop.class);
		}
		return null;
	}
	/**
	 * 查询总数
	 */
	public int getCountByObj(Object obj) {
		Map ${table}Map = (Hashtable)obj;
		List list = queryList.getList("getCountByObj${table?cap_first}", ${table}Map);
		int count = 0;
		if(list!=null && list.size()>0){
			Hashtable newsMap = (Hashtable)list.get(0);
			if(newsMap.get("count")!=null && !("").equals(newsMap.get("count").toString())){
				count = Integer.parseInt(newsMap.get("count").toString());
			}
		}
		return count;
	}
	/**
	 * 根据${table?cap_first}查找总数
	 */
	public Integer getCountBy${table?cap_first}(${table?cap_first} ${table}){
		List list = queryList.getList("getCountBy${table?cap_first}", ${table});
		int count = 0;
		if(list!=null && list.size()>0){
			Hashtable newsMap = (Hashtable)list.get(0);
			if(newsMap.get("count")!=null && !("").equals(newsMap.get("count").toString())){
				count = Integer.parseInt(newsMap.get("count").toString());
			}
		}
		return count;
	}
	/**
	 * 根据主键id查询${table?cap_first}信息
	 */
	public List getListByPk(String id) {
		return queryList.getList("get${table?cap_first}ByPk",id);
	}
	/**
	 * 主键id查询${table?cap_first}信息
	 * 标识flag查单一${table}还是关联${table}的所有信息
	 * @param obj
	 * @param flag  only|all
	 * @return
	 */
	public ${table?cap_first} get${table?cap_first}ByObj(${table?cap_first} obj, String flag) {
		List tmp_l = queryList.getList("getListBy${table?cap_first}Page", obj);
		if (ConstantsUtil.ALL.equals(flag)) {
			List<? extends ${table?cap_first}> tmp_nl = packObj(tmp_l);
			if (tmp_nl != null && tmp_nl.size() > 0) {
				return tmp_nl.get(0);
			}
		} else if (ConstantsUtil.ONLY.equals(flag)) {
			List<? extends ${table?cap_first}> tmp_nl = PackToList.packObj(tmp_l, ${table?cap_first}_prop.class);
			if (tmp_nl != null && tmp_nl.size() > 0) {
				return tmp_nl.get(0);
			}
		}
		return null;
	}
	/**
	 * 封装List中的元素到对象
	 */
	//保存list
	public List<? extends ${table?cap_first}> packObj(Object objList){
		List<${table?cap_first}_prop> mlq_cList = new ArrayList<${table?cap_first}_prop>();
		List obj_list = (List)objList;
		if(obj_list!=null && obj_list.size()>0){
			for(int i=0;i<obj_list.size();i++){
				${table?cap_first}_prop ${table}_prop = new ${table?cap_first}_prop();
				try {
					Hashtable tmp_map = (Hashtable)obj_list.get(i);
					BeanUtils.populate(${table}_prop, tmp_map);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mlq_cList.add(${table}_prop);
			}
		}
		return mlq_cList;
	}
}