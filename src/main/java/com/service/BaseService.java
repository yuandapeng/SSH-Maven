package com.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dao.base.BaseDao;
import com.hibernate.GenericsUtils;
import com.hibernate.Page;

@SuppressWarnings("unchecked")
public abstract class BaseService<T> {
	protected  final Logger log = LoggerFactory
			.getLogger(this.getClass());
	
	@Autowired
	protected BaseDao baseDao;
	
	private Class<T> clazz = GenericsUtils.getSuperClassGenricType(this.getClass());
	
	
	
	
	protected Class<T> getClazz() {
		return this.clazz;
	}

	/**
	 * 保存或更新对象
	 * 有id补位空时更新 为空时保存
	 * @param o
	 * @return
	 */
	public T save(T o) {
		this.baseDao.saveOrUpdate(o);
		return o;
	}
	
	protected T saveOrUpdate(T o) {
		return (T) this.baseDao.saveOrUpdate(o);
	}

	
	/**
	 * 合并对象
	 * @param o
	 * @return
	 */
	protected T merge(T o) {
		return (T) this.baseDao.merge(o);
	}
	
	/**
	 * 删除对象
	 * @param o
	 */
	public void delete(T o) {
		this.baseDao.delete(o);
	}
	
	/**
	 * 根据id删除对象
	 * @param id
	 */
	public void deleteById(Integer id) {
		this.baseDao.deleteById(clazz, id);
	}
	
	/**
	 * 根据id load对象
	 * @param id
	 * @return
	 */
	public T load(Integer id) {
		return this.baseDao.load(clazz, id);
	}
	
	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public T get(Integer id) {
		return this.baseDao.get(clazz, id);
	}

	/**
	 * 根据条件获取对象
	 * @param conditionHql
	 * @param params
	 * @return
	 */
	protected T get(String conditionHql, Map<String,Object> params) {
		return this.baseDao.get(clazz, conditionHql, params);
	}
	
	
	/**
	 * 根据条件获取对象集合
	 * @param conditionHql
	 * @param params
	 * @return
	 */
	protected List<T> find(String conditionHql, Map<String, Object> params) {
		return this.baseDao.find(clazz, conditionHql, params);
	}
	
	/**
	 * 根据属性名和属性值查询对象.
	 * 
	 * @return 符合条件的对象列表
	 */
	public List<T> findBy(String propertyName, Object value) {
		return this.baseDao.findBy(clazz, propertyName, value);
	}
	
	/**
	 * 根据属性名和属性值查询对象,带排序参数.
	 */
	protected List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
		return this.baseDao.findBy(clazz, propertyName, value, orderBy, isAsc);
	}
	

	/**
	 * 创建当前类泛型参数的 Criteria
	 * @param criterions
	 * @return
	 */
	protected Criteria createCriteria(Criterion... criterions) {
		return this.baseDao.createCriteria(clazz, criterions);
	}
	
	/**
	 * 创建Criteria对象，带排序字段与升降序字段.
	 * 
	 * @see #createCriteria(Class,Criterion[])
	 */
	protected Criteria createCriteria(String orderBy, boolean isAsc, Criterion... criterions) {
		return this.baseDao.createCriteria(clazz, orderBy, isAsc, criterions);
	}
	
	
	protected Page pagedQuery(Criteria criteria, int pageNo, int pageSize) {
		return this.baseDao.pagedQuery(criteria, pageNo, pageSize);
	}
	
	/**
	 * 分页查询函数，根据entityClass和查询条件参数,排序参数创建默认的<code>Criteria</code>.
	 * 
	 * @param pageNo
	 *            页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	protected Page pagedQuery(int pageNo, int pageSize, String orderBy, boolean isAsc, Criterion... criterions) {
		return this.baseDao.pagedQuery(clazz, pageNo, pageSize, orderBy, isAsc, criterions);
	}
	
	/**
	 * hql 分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param condition 查询条件
	 * @return
	 */
	protected Page pagedQuery(int pageNo, int pageSize, String condition) {
		return this.baseDao.pagedQuery(clazz, condition, pageNo, pageSize);
	}
	
	/**
	 * @param pageNo
	 * @param pageSize
	 * @param condition 查询条件
	 * @param params 参数值
	 * @return
	 */
	protected Page pagedQuery(int pageNo, int pageSize, String condition, Map<String, Object> params) {
		return this.baseDao.pageQuery(clazz, condition, params, pageNo, pageSize);
	}
	
	
}
