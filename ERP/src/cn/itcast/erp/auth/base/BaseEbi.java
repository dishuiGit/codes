package cn.itcast.erp.auth.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BaseEbi<T> {

	/**
	 * 保存部门信息
	 * @param dm 部门
	 */
	public void save(T t);
	/**
	 * 删除部门
	 * @param dm 部门
	 */
	public void delete(T t);
	/**
	 * 更新部门信息
	 * @param dm 根据uuid更新
	 */
	
	public void update(T t);
	/**
	 * 查询所有部门
	 * @return 所有部门的集合
	 */
	public List<T> getAll();
	
	/**
	 * 通过uuid查询指定的部门信息
	 * @param uuid 部门编号
	 * @return 部门Model
	 */
	public T getByUuid(Long uuid);
	/**
	 * 通过查询条件查询
	 * @param dqm 查询Model
	 * @return	部门结果集
	 */
	public List<T> getAll(BaseQueryModel bqm);
	/**
	 * 根据查询model 获得结果集总数
	 * @param dqm 
	 * @return 结果集总数
	 */
	public Integer getCount(BaseQueryModel bqm);
	/**
	 * 分页查询
	 * @param dqm
	 * @param i
	 * @param pageCount
	 * @return
	 */
	public List<T> getAll(BaseQueryModel bqm, Integer pageNum, Integer pageCount);
	
}
