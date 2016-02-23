

=================${table?cap_first}Info========================================

package com.bizoss.trade.${table};

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import com.bizoss.frame.QueryList;
import com.bizoss.frame.SysMap;
import com.bizoss.frame.TradeInterf;
import com.bizoss.frame.dao.DbmInterf;
import com.bizoss.frame.exp.ApplicationException;

import org.apache.commons.beanutils.BeanUtils;

public class ${table?cap_first}Info implements TradeInterf{
	
	private QueryList queryList = new QueryList();

	public SysMap insert(DbmInterf dbmMgr, SysMap map) {
		${table?cap_first} ${table} = new ${table?cap_first}();
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
	 * 根据主键id查询商品信息
	 */
	public List getListByPk(String id) {
		return queryList.getList("get${table?cap_first}ByPk",id);
	}

	/**
	 * 封装List中的元素到对象
	 */
	//保存list
	public List<${table?cap_first}> packObj(List pList){
		List<${table?cap_first}> psList = new ArrayList<${table?cap_first}>();
		if(pList!=null && pList.size()>0){
			for(int i=0;i<pList.size();i++){
				${table?cap_first} obj = new ${table?cap_first}();
				Hashtable tmp_map = (Hashtable)pList.get(i);
				try {
					BeanUtils.populate(obj, tmp_map);
				} catch (Exception e) {
					e.printStackTrace();
				}
				psList.add(obj);
			}
		}
		return psList;
	}
	
}



=================${table?uncap_first}.xml========================================



----------------------------------------------------------------


