package cn.itcast.erp.auth.base;

import java.util.List;

public interface BaseDao<T> {
	public void save(T t);

	public void update(T t);
	
	public T get(Long uuid);

	public List<T> getAll();

	public List<T> getAll(BaseQueryModel bqm);

	public Integer getCount(BaseQueryModel bqm);

	public List<T> getAll(BaseQueryModel bqm, Integer pageNum,
			Integer pageCount);

	public void delete(T t);
}
