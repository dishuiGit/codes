package cn.itcast.erp.util.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.erp.util.exception.AppException;

public abstract class BaseImpl<T> extends HibernateDaoSupport{
	//entityClass表示泛型的类型
	private Class<T> entityClass;
	
	//对entityClass进行初始化
	public BaseImpl(){
		//1.获取当前实现类的类型
		//当前类型的父类的类型（用于获取泛型类型）
		Type type = getClass().getGenericSuperclass();
		ParameterizedType ptype = (ParameterizedType)type;
		Type[] types = ptype.getActualTypeArguments();
		entityClass = (Class<T>) types[0];
	}
	
	public void save(T t) {
		this.getHibernateTemplate().save(t);
	}

	public void update(T t) {
		try {
			this.getHibernateTemplate().update(t);
		} catch (Exception e) {
			throw new AppException("对不起，服务器已关闭，请稍后再试！",e);
		}
	}

	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}
	
	public T get(Long uuid) {
		return this.getHibernateTemplate().get(entityClass, uuid);
	}
	
	public List<T> getAll() {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		return this.getHibernateTemplate().findByCriteria(dc);
	}

	public List<T> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		doQbc(dc,qm);
		return this.getHibernateTemplate().findByCriteria(dc,(pageNum-1)*pageCount,pageCount);	
	}

	public Integer getCount(BaseQueryModel qm) {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		dc.setProjection(Projections.rowCount());
		doQbc(dc,qm);
		List<Long> temp = this.getHibernateTemplate().findByCriteria(dc);
		return temp.get(0).intValue(); 
	}
	
	protected abstract void doQbc(DetachedCriteria dc,BaseQueryModel qm);

}
