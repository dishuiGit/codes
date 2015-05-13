package cn.itcast.erp.auth.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T>{
	private Class<T> clazz = null;
	{
		Type genType = getClass().getGenericSuperclass();   
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();   
		clazz =  (Class)params[0];  
	}
	public void save(T t) {
		this.getHibernateTemplate().save(t);
	}

	public List<T> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	public T get(Long uuid) {
		return this.getHibernateTemplate().get(clazz, uuid);
	}

	public List<T> getAll(BaseQueryModel bqm) {
		
		// QBC条件查询
		DetachedCriteria dc = doQBC(bqm,clazz);
		return this.getHibernateTemplate().findByCriteria(dc);
	}

	

	public Integer getCount(BaseQueryModel bqm) {
		DetachedCriteria dc = doQBC(bqm,clazz);
		dc.setProjection(Projections.rowCount());
		List<Long> count = this.getHibernateTemplate().findByCriteria(dc);
		return count.get(0).intValue();
	}

	public List<T> getAll(BaseQueryModel bqm, Integer pageNum,
			Integer pageCount) {
		DetachedCriteria dc = doQBC(bqm,clazz);
		return this.getHibernateTemplate().findByCriteria(dc,pageNum,pageCount);
	}

	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}

	public abstract DetachedCriteria doQBC(BaseQueryModel bqm, Class<T> entryclass) ;
}
