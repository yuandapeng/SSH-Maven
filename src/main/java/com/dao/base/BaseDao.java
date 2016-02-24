package com.dao.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.hibernate.BeanUtils;
import com.hibernate.Page;
import com.util.PageUtil;

/**
 * 数据库操作类
 * @author Administrator
 *
 */
@SuppressWarnings({"unchecked","rawtypes","unused"})
@Repository
public class BaseDao {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	@Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 获得当前事物的session
	 * 
	 * @return org.hibernate.Session
	 */
	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	/**
	 * 保存
	 * @param o
	 * @return
	 */
	public Object save(Object o) {
		 this.getCurrentSession().save(o);
		 return o;
	}
	
	
	/**
	 * 更新对象
	 * @param o
	 */
	public void update(Object o) {
		if(o != null) {
			this.getCurrentSession().update(o);
		}
	}
	
	/**
	 * 保存或更新
	 * @param o
	 * @return
	 */
	public Object saveOrUpdate(Object o) {
		this.getCurrentSession().saveOrUpdate(o);
		return o;
	}
	
	/**
	 * 合并
	 * @param o
	 * @return
	 */
	public Object merge(Object o) {
		return this.getCurrentSession().merge(o);
	} 
	
	/**
	 * 物理删除
	 * @param o
	 */
	public void delete(Object o) {
		this.getCurrentSession().delete(o);
	}
	

	public <T> void deleteById(Class<T> clazz, Serializable id) {
		T t = this.get(clazz, id);
		if(t != null) {
			this.getCurrentSession().delete(t);
		}
	}
	
	public <T> T load(Class<T> clazz, Serializable id) {
		return (T) this.getCurrentSession().load(clazz, id);
	}
	
	public <T> T get(Class<T> clazz, Serializable id) {
		return (T) this.getCurrentSession().get(clazz, id);
	}
	
	public Object get(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		List<Object> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	} 
	
	public Object get(String hql, Map<String,Object> params) {
		Query query = this.createQuery(hql, params);
		List<Object> list = query.list();
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	public <T> T get(Class<T> clazz, String conditionHql, Map<String,Object> params) {
		String hql = "from " + clazz.getName() + " entity " + conditionHql;
		Query query = this.createQuery(hql, params);
		List<T> list = query.list();
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	public List find(String hql, Map<String,Object> params) {
		Assert.hasText(hql);
		Query query = this.createQuery(hql, params);
		return query.list();
	}
	
	
	public <T> List<T> find(Class<T> clazz, String conditionHql, Map<String, Object> params) {
		Query query = this.createQuery(clazz, conditionHql, params);
		return query.list();
	}
	
	public List<Object> find(String hql, Map<String,Object> params, int startIndex, int maxSize) {
		Query query = this.createQuery(hql, params, startIndex, maxSize);
		return query.list();
	}
	
	public List<Object> find(String hql, int startIndex, int maxSize) {
		Query query = this.createQuery(hql, null, startIndex, maxSize);
		return query.list();
	}
	

	
	/**
	 * 根据属性名和属性值查询对象.
	 * 
	 * @return 符合条件的对象列表
	 */
	public <T> List<T> findBy(Class<T> clazz, String propertyName,
			Object value) {
		Assert.hasText(propertyName);
		return createCriteria(clazz, Restrictions.eq(propertyName, value))
				.list();
	}
	
	/**
	 * 根据属性名和属性值查询对象,带排序参数.
	 */
	public <T> List<T> findBy(Class<T> entityClass, String propertyName,
			Object value, String orderBy, boolean isAsc) {
		Assert.hasText(propertyName);
		Assert.hasText(orderBy);
		return createCriteria(entityClass, orderBy, isAsc,
				Restrictions.eq(propertyName, value)).list();
	}
	
	/**
	 * 根据属性名和属性值查询唯一对象.
	 * 
	 * @return 符合条件的唯一对象 or null if not found.
	 */
	public <T> T findUniqueBy(Class<T> entityClass, String propertyName,
			Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass,
				Restrictions.eq(propertyName, value)).uniqueResult();
	}
	
	/**
	 * 创建Criteria对象.
	 * 
	 * @param criterions
	 *            可变的Restrictions条件列表,见{@link #createQuery(String,Object...)}
	 */
	public <T> Criteria createCriteria(Class<T> clazz,
			Criterion... criterions) {
		Criteria criteria = this.getCurrentSession().createCriteria(clazz);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	/**
	 * 创建Criteria对象，带排序字段与升降序字段.
	 * 
	 * @see #createCriteria(Class,Criterion[])
	 */
	public <T> Criteria createCriteria(Class<T> entityClass, String orderBy,
			boolean isAsc, Criterion... criterions) {
		Assert.hasText(orderBy);

		Criteria criteria = createCriteria(entityClass, criterions);

		if (isAsc)
			criteria.addOrder(Order.asc(orderBy));
		else
			criteria.addOrder(Order.desc(orderBy));

		return criteria;
	}
	
	
	/**
	 * 分页查询函数，使用已设好查询条件与排序的<code>Criteria</code>.
	 * 
	 * @param pageNo
	 *            页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page pagedQuery(Criteria criteria, int pageNo, int pageSize) {
		Assert.notNull(criteria);
		pageNo = pageNo == 0 ? 1 : pageNo;
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		CriteriaImpl impl = (CriteriaImpl) criteria;

		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = impl.getProjection();
		List<CriteriaImpl.OrderEntry> orderEntries;
		try {
			orderEntries = (List) BeanUtils.forceGetProperty(impl,
					"orderEntries");
			BeanUtils.forceSetProperty(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		// 执行查询
		int totalCount = ((Long) criteria.setProjection(Projections.rowCount())
				.uniqueResult()).intValue();

		// 将之前的Projection和OrderBy条件重新设回去
		criteria.setProjection(projection);

		// if (projection == null) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		// }

		try {
			BeanUtils.forceSetProperty(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		// 返回分页对象
		if (totalCount < 1)
			return new Page();

		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		while (startIndex >= totalCount) {
			pageNo = pageNo - 1;
			startIndex = Page.getStartOfPage(pageNo, pageSize);
		}
		List list = criteria.setFirstResult(startIndex).setMaxResults(pageSize)
				.list();
		return new Page(startIndex, totalCount, pageSize, list);
	}

	/**
	 * 分页查询函数，根据entityClass和查询条件参数创建默认的<code>Criteria</code>.
	 * 
	 * @param pageNo
	 *            页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page pagedQuery(Class entityClass, int pageNo, int pageSize,
			Criterion... criterions) {
		Criteria criteria = createCriteria(entityClass, criterions);
		return pagedQuery(criteria, pageNo, pageSize);
	}

	/**
	 * 分页查询函数，根据entityClass和查询条件参数,排序参数创建默认的<code>Criteria</code>.
	 * 
	 * @param pageNo
	 *            页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page pagedQuery(Class entityClass, int pageNo, int pageSize,
			String orderBy, boolean isAsc, Criterion... criterions) {
		Criteria criteria = createCriteria(entityClass, orderBy, isAsc,
				criterions);
		return pagedQuery(criteria, pageNo, pageSize);
	}
	
	public Page pagedQuery(String hql, int pageNo, int pageSize) {
		pageNo = pageNo == 0 ? 1 : pageNo;
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		String countHql = "select count(*) " + hql;
		int count = this.count(countHql);
		if(count < 1) {
			return new Page();
		}
		int startIndex = PageUtil.getStartIndex(pageNo, pageSize, count);
		List<Object> list = this.find(hql, startIndex, pageSize);
		return new Page(startIndex, count, pageSize, list);
	}
	
	/**
	 * @param hql eg: from user u where u.userName=:userName
	 * @param params  eg: params.put("userName",userName)
	 * */
	public Page pagedQuery(String hql, int pageNo, int pageSize,Map<String, Object> params) {
		pageNo = pageNo == 0 ? 1 : pageNo;
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		String countHql = "select count(*) " + hql;
		int count = this.count(countHql, params);
		
		if(count < 1) {
			return new Page();
		}
		int startIndex = PageUtil.getStartIndex(pageNo, pageSize, count);
		List<Object> list = this.createQuery(hql, params, startIndex, pageSize).list();
		
		return new Page(startIndex, count, pageSize, list);
	}
	
	
	
	public Page pagedQuery(Class<?> clazz, String condition, int pageNo, int pageSize) {
		String hql = "from " + clazz.getName() + " entity" + condition;
		return this.pagedQuery(hql, pageNo, pageSize);
	} 
	
	public Page pageQuery(Class<?> clazz, String condition, Map<String, Object> params, int pageNo, int pageSize) {
		String  hql = "from " + clazz.getName() + " entity" + condition;
		return this.pagedQuery(hql, pageNo, pageSize, params);
	}
	
	public int count(String hql) {
		Query query = this.createQuery(hql);
		return ((Long) query.uniqueResult()).intValue();
	}
	
	public int count(String hql, Map<String,Object> params) {
		Query query = this.createQuery(hql, params);
		return ((Long) query.uniqueResult()).intValue();
	}

	/**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 * 
	 * @param uniquePropertyNames
	 *            在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 */
	public <T> boolean isUnique(Class<T> entityClass, Object entity,
			String uniquePropertyNames) {
		Assert.hasText(uniquePropertyNames);
		Criteria criteria = createCriteria(entityClass).setProjection(
				Projections.rowCount());
		String[] nameList = uniquePropertyNames.split(",");
		try {
			// 循环加入唯一列
			for (String name : nameList) {
				criteria.add(Restrictions.eq(name,
						PropertyUtils.getProperty(entity, name)));
			}

			// 以下代码为了如果是update的情况,排除entity自身.

			String idName = getIdName(entityClass);

			// 取得entity的主键值
			Serializable id = getId(entityClass, entity);

			// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
			if (id != null)
				criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		return (Integer) criteria.uniqueResult() == 0;
	}
	
	
	public Serializable getId( Class entityClass, Object entity)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Assert.notNull(entity);
		Assert.notNull(entityClass);
		return (Serializable) PropertyUtils.getProperty(entity,
				getIdName(entityClass));
	}

	/**
	 * 取得对象的主键名,辅助函数.
	 */
	public String getIdName( Class clazz) {
		Assert.notNull(clazz);
		ClassMetadata meta = this.sessionFactory.getClassMetadata(clazz);
		Assert.notNull(meta, "Class " + clazz
				+ " not define in hibernate session factory.");
		String idName = meta.getIdentifierPropertyName();
		Assert.hasText(idName, clazz.getSimpleName()
				+ " has no identifier property define.");
		return idName;
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql
				+ " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	
	
	
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = this.getCurrentSession().createQuery(hql);
		for (int i = 0, length = values.length; i < length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}
	
	
	public Query createSQLQuery(String sql, Object... values) {
		Assert.hasText(sql);
		Query query = getCurrentSession().createSQLQuery(sql);
		for (int i = 0, length = values.length; i < length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}
	public Query createSQLQuery(String sql, Map<String,Object> params) {
		Assert.hasText(sql);
		Query query = getCurrentSession().createSQLQuery(sql);
		this.setParameter(query, params);
		return query;
	}
	
	/**
	 * 
	 * @param clazz         表
	 * @param conditionHql  条件
	 * @param params        参数
	 * @return
	 */
	public Query createQuery(Class<?> clazz, String conditionHql, Map<String,Object> params) {
		String hql = "from " + clazz.getName() + " entity " + conditionHql;
		Query query = this.getCurrentSession().createQuery(hql);
		this.setParameter(query, params);
		return query;
	}
	
	
	/**
	 * 
	 * @param hql  查询语句 (from Person where 条件)
	 * @param params 参数
	 * @return
	 */
	public Query createQuery(String hql, Map<String,Object> params) {
		Query query = this.getCurrentSession().createQuery(hql);
		this.setParameter(query, params);
		return query;
	}
	
	
	public Query createQuery(String hql, Map<String,Object> params, int startIndex, int maxSize) {
		Query query = this.getCurrentSession().createQuery(hql);
		this.setParameter(query, params);
		query.setFirstResult(startIndex).setMaxResults(maxSize);
		return query;
	}
	
	private void setParameter(Query query, Map<String,Object> params) {
		if(params != null && !params.isEmpty()) {
			for(String key : params.keySet()) {
				Object val = params.get(key);
				if(val instanceof Collection) {
					query.setParameterList(key, (Collection<Object>) val);
				}else {
					query.setParameter(key, val);
				}
			}
		}
	}

}
