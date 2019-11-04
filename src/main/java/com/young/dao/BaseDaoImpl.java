package com.young.dao;

import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import com.young.Pager;

import java.io.Serializable;
import java.util.List;

/**
 * 公共DAO抽象接口的实现类
 * Created by liuzl on 2016/2/3.
 */
public class BaseDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport implements BaseDao<T, PK> {

    /**
     * 保存实体
     * 包括添加和修改
     *
     * @param t 实体对象
     */
    public void saveOrUpdate(Object t) {
        Session session = null;
        try {
            session = this.getHibernateTemplate().getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(t);
            tx.commit();
        } catch (Exception e) {
            logger.error("保存或者更新对象报错:", e);
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * 更新实体
     * 可用于添加、修改、删除操作
     *
     * @param hql    更新的HQL语句
     * @param params 参数,可有项目或多项目,代替Hql中的"?"号
     */
    public void update(final String hql, final Object... params) {
        try {
            this.getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session)
                        throws HibernateException {
                    Query query = session.createQuery(hql);
                    for (int i = 0; i < params.length; i++) {
                        query.setParameter(i, params[i]);
                    }
                    query.executeUpdate();
                    return null;
                }
            });
        } catch (Exception e) {
            logger.error("更新实体对象报错:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除实体
     *
     * @param t 实体对象
     */
    public void delete(T t) {
        Session session = null;
        try {
            session = this.getHibernateTemplate().getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.delete(t);
            tx.commit();
        } catch (Exception e) {
            logger.error("删除实体对象报错:", e);
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * 删除实体
     *
     * @param entityClass 实体类名
     * @param id          实体的ID
     */
    public void delete(Class<T> entityClass, PK id) {
        try {
//            this.getHibernateTemplate().delete(get(entityClass,id));
            this.delete(get(entityClass, id));
        } catch (Exception e) {
            logger.error("删除实体对象报错:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 单查实体
     *
     * @param entityClass 实体类名
     * @param id          实体的ID
     * @return 实体对象
     */
    @SuppressWarnings("unchecked")
    public T get(Class<T> entityClass, PK id) {
        try {
            return (T) getHibernateTemplate().get(entityClass, id);
        } catch (Exception e) {
            logger.error("单查实体对象报错:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询全部记录列表
     *
     * @param entityClass  实体类名
     * @param propertyName 排序的参照属性
     * @param isAsc        排序方式
     * @param criterions   查询条件,可为0项或任意多项目
     * @return 记录List集
     */
    public List<T> findAll(final Class<T> entityClass, final String propertyName, final boolean isAsc, final Criterion... criterions) {
        try {
            int firstResult = 0;
            int maxResults = 0;        //设置为0,则表示查询全部记录
            return findByCriteria(entityClass, propertyName, isAsc, firstResult, maxResults, criterions);
        } catch (Exception e) {
            logger.error("查询全部记录列表报错:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询列表
     *
     * @param entityClass  实体类名
     * @param propertyName 排序的参照属性
     * @param isAsc        排序方式,true表示升序,false表示降序,当propertyName赋值为null时,此参数无效
     * @param firstResult  开始记录序号
     * @param maxResults   最大记录数
     * @param criterions   查询条件,可有0项或任意多项目
     * @return 记录List集
     */
    @SuppressWarnings("unchecked")
    public List<T> findByCriteria(final Class<T> entityClass, final String propertyName, final boolean isAsc, final int firstResult, final int maxResults, final Criterion... criterions) {
        try {
            List<T> list = (List<T>) this.getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session)
                        throws HibernateException {
                    Criteria criteria = session.createCriteria(entityClass);
                    //按属性条件查询
                    if (criterions != null && criterions.length > 0 && criterions[0] != null) {
                        for (Criterion queryCriterion : criterions) {
                            criteria.add(queryCriterion);
                        }
                    }
                    //按某个属性排序
                    if (null != propertyName) {
                        if (isAsc) {
                            criteria.addOrder(Order.asc(propertyName));
                        } else {
                            criteria.addOrder(Order.desc(propertyName));
                        }
                    }
                    //用于分页查询
                    if (maxResults != 0) {
                        criteria.setFirstResult(firstResult);
                        criteria.setMaxResults(maxResults);
                    }
                    List<T> list = criteria.list();
                    return list;
                }
            });
            return list;
        } catch (Exception e) {
            logger.error("查询列表报错:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询总记录数
     *
     * @param entityClass 实体类名
     * @param criterions  查询条件,可有0项或任意多项
     * @return 总记录数
     */
    public int findCountsByCriteria(final Class<T> entityClass, final Criterion... criterions) {
        try {
            int totalCounts = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session)
                        throws HibernateException {
                    Criteria criteria = session.createCriteria(entityClass);
                    //按属性条件查询
                    if (criterions != null && criterions.length > 0 && criterions[0] != null) {
                        for (Criterion queryCriterion : criterions) {
                            criteria.add(queryCriterion);
                        }
                    }
                    int totalCounts = criteria.list().size();
                    return totalCounts;
                }
            });
            return totalCounts;
        } catch (Exception e) {
            logger.error("查询总记录数报错:", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * 分页查询
     *
     * @param entityClass  实体类名
     * @param propertyName 排序参照属性
     * @param isAsc        排序方式,true表示升序,false表示降序,当propertyName赋值为null时,此参数无效
     * @param firstResult  开始记录序号
     * @param maxResults   最大记录数
     * @param criterions   查询条件,可为0项或任意多项目
     * @return 封装List和totalCounts的Pager对象
     */
    @SuppressWarnings("unchecked")
    public Pager<T> findForPager(final Class<T> entityClass, final String propertyName, final boolean isAsc, final int firstResult, final int maxResults, final Criterion... criterions) {
        try {
            int totalCounts = findCountsByCriteria(entityClass, criterions);
            List<T> entityList = findByCriteria(entityClass, propertyName, isAsc, firstResult, maxResults, criterions);
            Pager pager = new Pager();
            pager.setTotalCount(totalCounts);
            pager.setList(entityList);
            pager.setOrderBy(propertyName);
            pager.setOrderType(isAsc ? Pager.OrderType.asc : Pager.OrderType.desc);
            pager.setPageSize(maxResults);
            return pager;
        } catch (Exception e) {
            logger.error("分页查询报错:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据属性值查询列表
     *
     * @param entityClass  实体类名
     * @param propertyName 属性名
     * @param value        属性值
     * @return List列表
     */
    public List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) {
        try {
            Criterion criterion = Restrictions.eq(propertyName, value);
            List<T> list = findAll(entityClass, null, true, criterion);
            return list;
        } catch (Exception e) {
            logger.error("根据属性值查询列表报错:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据属性值查询单个对象
     *
     * @param entityClass  实体类名
     * @param propertyName 属性名
     * @param value        属性值
     * @return 实体对象
     */
    @SuppressWarnings("unchecked")
    public T findUniqueByProperty(final Class<T> entityClass, final String propertyName, final Object value) {
        try {
            T t = (T) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session)
                        throws HibernateException {
                    Criteria criteria = session.createCriteria(entityClass).add(Restrictions.eq(propertyName, value));
                    T t = (T) criteria.uniqueResult();
                    return t;
                }
            });
            return t;
        } catch (Exception e) {
            logger.error("根据属性值查询单个对象报错:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据属性值查询实体是否存在
     *
     * @param entityClass  实体类名
     * @param propertyName 参照的属性名
     * @param value        属性值
     * @return 存在则返回true, 不存在则返回false
     */
    public boolean isPropertyExist(final Class<T> entityClass, final String propertyName, final Object value) {
        try {
            boolean isExist = (Boolean) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session)
                        throws HibernateException {
                    Criteria criteria = session.createCriteria(entityClass).add(Restrictions.eq(propertyName, value));
                    boolean isEmpty = criteria.list().isEmpty();
                    return !isEmpty;
                }
            });
            return isExist;
        } catch (Exception e) {
            logger.error("根据属性值查询实体是否存在报错:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param hql    查询语句
     *               用法如：可用于登录验证时，根据用户名、密码等信息查询用户
     * @param params 参数数组,代替HQL中的"?"号,可有0项目或多项
     * @return 唯一实体，返回null则表示不存在配置的实体
     */
    @SuppressWarnings("unchecked")
    public T findUniqueByHql(final String hql, final Object... params) {
        try {

            T t = (T) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session)
                        throws HibernateException {
                    Query query = session.createQuery(hql);
                    for (int i = 0; i < params.length; i++) {
                        query.setParameter(i, params[i]);
                    }
                    T t = (T) query.uniqueResult();
                    return t;
                }
            });
            return t;
        } catch (Exception e) {
            logger.error("@param hql 查询语句报错:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 按HQL条件查询列表
     *
     * @param hql    查询语句,支持连接查询和多条件查询
     * @param params 参数数组,代替hql中的"?"号
     * @return 结果集List
     */
    @SuppressWarnings("unchecked")
    public List<T> findByHql(String hql, Object params) {
        try {
            List list = getHibernateTemplate().find(hql, params);
            return list;
        } catch (Exception e) {
            logger.error("按HQL条件查询列表报错:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 按HQL分页查询
     *
     * @param firstResult 开始记录号
     * @param maxResults  最大记录数
     * @param hql         查询语句,支持连接查询和多条件查询
     * @param params      参数数组,代替餐hql中的"?"号
     * @return 封装List和total的Pager对象
     */
    @SuppressWarnings("unchecked")
    public Pager<T> findForPagerByHql(final int firstResult, final int maxResults, final String hql, final Object... params) {
        try {
            Pager<T> pager = (Pager<T>) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session)
                        throws HibernateException {
                    Query query = session.createQuery(hql);
                    for (int position = 0; position < params.length; position++) {
                        query.setParameter(position, params[position]);
                    }
                    int totalCounts = query.list().size();    //总记录数
                    //用于分页查询
                    if (maxResults > 0) {
                        query.setFirstResult(firstResult);
                        query.setMaxResults(maxResults);
                    }
                    List<T> list = query.list();
                    Pager<T> pager = new Pager<T>();
                    pager.setList(list);
                    pager.setTotalCount(totalCounts);
                    return pager;
                }
            });
            return pager;
        } catch (Exception e) {
            logger.info("按HQL条件查询列表报错:", e);
            throw new RuntimeException(e);
        }
    }
}
