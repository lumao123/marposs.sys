package com.young.dao;

import org.hibernate.criterion.Criterion;
import com.young.Pager;

import java.io.Serializable;
import java.util.List;

/**
 * 公共DAO抽象接口
 * Created by liuzl on 2016/2/3.
 */
public interface BaseDao<T, PK extends Serializable> {
    /**
     * 保存实体
     * 包括添加和修改
     *
     * @param t 实体对象
     */
    public void saveOrUpdate(T t);

    /**
     * 更新实体
     * 可用于添加、修改、删除操作
     *
     * @param hql    更新的HQL语句
     * @param params 参数,可有项目或多项目,代替Hql中的"?"号
     */
    public void update(final String hql, final Object... params);

    /**
     * 删除实体
     *
     * @param t 实体对象
     */
    public void delete(T t);

    /**
     * 删除实体
     *
     * @param entityClass 实体类名
     * @param id          实体的ID
     */
    public void delete(Class<T> entityClass, PK id);

    /**
     * 单查实体
     *
     * @param entityClass 实体类名
     * @param id          实体的ID
     * @return 实体对象
     */
    @SuppressWarnings("unchecked")
    public T get(Class<T> entityClass, PK id);

    /**
     * 查询全部记录列表
     *
     * @param entityClass  实体类名
     * @param propertyName 排序的参照属性
     * @param isAsc        排序方式
     * @param criterions   查询条件,可为0项或任意多项目
     * @return 记录List集
     */
    public List<T> findAll(final Class<T> entityClass, final String propertyName, final boolean isAsc, final Criterion... criterions);

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
    public List<T> findByCriteria(final Class<T> entityClass, final String propertyName, final boolean isAsc, final int firstResult, final int maxResults, final Criterion... criterions);

    /**
     * 查询总记录数
     *
     * @param entityClass 实体类名
     * @param criterions  查询条件,可有0项或任意多项
     * @return 总记录数
     */
    public int findCountsByCriteria(final Class<T> entityClass, final Criterion... criterions);

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
    public Pager<T> findForPager(final Class<T> entityClass, final String propertyName, final boolean isAsc, final int firstResult, final int maxResults, final Criterion... criterions);

    /**
     * 根据属性值查询列表
     *
     * @param entityClass  实体类名
     * @param propertyName 属性名
     * @param value        属性值
     * @return List列表
     */
    public List<T> findByProperty(Class<T> entityClass, String propertyName, Object value);

    /**
     * 根据属性值查询单个对象
     *
     * @param entityClass  实体类名
     * @param propertyName 属性名
     * @param value        属性值
     * @return 实体对象
     */
    @SuppressWarnings("unchecked")
    public T findUniqueByProperty(final Class<T> entityClass, final String propertyName, final Object value);

    /**
     * 根据属性值查询实体是否存在
     *
     * @param entityClass  实体类名
     * @param propertyName 参照的属性名
     * @param value        属性值
     * @return 存在则返回true, 不存在则返回false
     */
    public boolean isPropertyExist(final Class<T> entityClass, final String propertyName, final Object value);

    /**
     * @param hql    查询语句
     *               用法如：可用于登录验证时，根据用户名、密码等信息查询用户
     * @param params 参数数组,代替HQL中的"?"号,可有0项目或多项
     * @return 唯一实体，返回null则表示不存在配置的实体
     */
    @SuppressWarnings("unchecked")
    public T findUniqueByHql(final String hql, final Object... params);

    /**
     * 按HQL条件查询列表
     *
     * @param hql    查询语句,支持连接查询和多条件查询
     * @param params 参数数组,代替hql中的"?"号
     * @return 结果集List
     */
    @SuppressWarnings("unchecked")
    public List<T> findByHql(String hql, Object params);

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
    public Pager<T> findForPagerByHql(final int firstResult, final int maxResults, final String hql, final Object... params);
}
