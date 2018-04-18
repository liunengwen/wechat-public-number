package com.newland.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public abstract class BaseService<T> {
	
	@Autowired
	public Mapper<T> mapper;
	
	/**
	 * 根据ID查询
	 * author 刘能文
	 * @param id
	 * @return
	 */
	public T queryById(Long id){
		return this.mapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 查询所有
	 * author 刘能文
	 * @return
	 */
	public List<T> queryAll(){
		return this.mapper.select(null);
	}
	
	/**
	 * 条件查询一条数据
	 * author 刘能文
	 * @param t
	 * @return
	 */
	public T queryOne(T record){
		return this.mapper.selectOne(record);
	}
	
	/**
	 * 条件查询批量数据
	 * author 刘能文
	 * @param record
	 * @return
	 */
	public List<T> queryListByWhere(T record){
		return this.mapper.select(record);
	}
	
	/**
	 * 条件查询批量数据
	 * author 刘能文
	 * @param record
	 * @return
	 */
	public List<T> queryListByWhere(Map<String,Object> condition, Class<T> clazz, String orderByClause){
		Example example = new Example(clazz);
		Criteria criteria = example.createCriteria();
		for (String key : condition.keySet()) {
				criteria.andEqualTo(key, condition.get(key));
		}
		if(StringUtils.isNoneBlank(orderByClause)){
			example.setOrderByClause(orderByClause);
		}
		List<T> list = this.mapper.selectByExample(example);
		return list;
	}
	
	/**
	 * 条件分页查询
	 * author 刘能文
	 * @param page
	 * @param rows
	 * @param record
	 * @return
	 */
	public PageInfo<T> queryPageListByWhere(Integer page, Integer rows, T record){
		PageHelper.startPage(page, rows);
		return new PageInfo<T>(this.mapper.select(record));
	}
	
	/**
	 * 条件分页查询,
	 * @param page
	 * @param rows
	 * @param condition
	 * @param clazz
	 * @param orderByClause
	 * @return
	 */
	public PageInfo<T> queryPageListByWhere(Integer page, Integer rows, Map<String,Object> condition, Class<T> clazz, String orderByClause) {
		PageHelper.startPage(page, rows);
		Example example = new Example(clazz);
		Criteria criteria = example.createCriteria();
		for (String key : condition.keySet()) {
				criteria.andEqualTo(key, condition.get(key));
		}
		if(StringUtils.isNoneBlank(orderByClause)){
			example.setOrderByClause(orderByClause);
		}
		List<T> list = this.mapper.selectByExample(example);
		return new PageInfo<T>(list);
	}
	
	/**
	 * 普通新增数据方法 
	 * author 刘能文
	 * @param record
	 * @return
	 */
	public int save(T record){
		return this.mapper.insert(record);
	}
	
	/**
	 * 属性为NULL时新增数据SQL语句不含该字段
	 * author 刘能文
	 * @param record
	 * @return
	 */
	public Integer saveSelective(T record){
		return this.mapper.insertSelective(record);
	}
	
	/**
	 * 普通更新方法
	 * author 刘能文
	 * @param record
	 * @return
	 */
	public Integer update(T record){
		return this.mapper.updateByPrimaryKey(record);
	}
	
	/**
	 * 只更新不为NULL的字段
	 * author 刘能文
	 * @param record
	 * @return
	 */
	public Integer updateSelective(T record){
		return this.mapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 根据ID删除记录
	 * author 刘能文
	 * @param id
	 * @return
	 */
	public Integer deleteById(Long id){
		return this.mapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 批量删除
	 * author 刘能文
	 * @param values
	 * @param property
	 * @param clazz
	 * @return
	 */
	public Integer deleteByIds(List<Object> values,String property,Class<T> clazz){
		Example example = new Example(clazz);
		example.createCriteria().andIn(property, values);
		return this.mapper.deleteByExample(example);
	}
	
	/**
	 * 条件删除
	 * author 刘能文
	 * @param record
	 * @return
	 */
	public Integer deleteByWhere(T record){
		return this.mapper.delete(record);
	}
	
	/**
	 * 查询一条数据，如果有多条只返回一条
	 * @param record
	 * @return
	 */
	public T queryUnique(T record){
		List<T> list = this.mapper.select(record);
		if(list!= null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 *根据条件更新数据 选择性的更新不为null的字段 
	 * 
	 */
	public Integer updateByWhereSelective( T record ,Class<T> clazz ,Map<String,Object> conditions) {
        Example example = new Example(clazz);
        Criteria criteria = example.createCriteria();
        for (Entry<String, Object> entry : conditions.entrySet()) {
        	criteria.andEqualTo(entry.getKey(), entry.getValue());
		}
        return this.mapper.updateByExampleSelective(record, example);
	}
	
	/**
	 *根据id更新数据 选择性的更新不为null的字段 
	 * 
	 */
	public Integer updateByIdSelective( T record ,Class<T> clazz ,Object id) {
        Example example = new Example(clazz);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        return this.mapper.updateByExampleSelective(record, example);
	}
	
	public List<T> queryBillNmListByWhere(Map<String,Object> condition, Class<T> clazz, String orderByClause){
		Example example = new Example(clazz);
		Criteria criteria = example.createCriteria();
		for (String key : condition.keySet()) {
			if("total".equals(key)){
				criteria.andLessThan(key, condition.get(key));
			}if("pushStatus".equals(key)){
				criteria.andEqualTo(key, condition.get(key));
			}
		}
		if(StringUtils.isNoneBlank(orderByClause)){
			example.setOrderByClause(orderByClause);
		}
		List<T> list = this.mapper.selectByExample(example);
		return list;
	}
	
	public List<T> queryQrCodeListByWhere (Map<String,Object> condition, Class<T> clazz, String orderByClause){
		Example example = new Example(clazz);
		Criteria criteria = example.createCriteria();
		
		for (String key : condition.keySet()) {
			criteria.andEqualTo(key, condition.get(key));
		}
		criteria.andBetween("quaryCount", "0", "4");
		
		if(StringUtils.isNoneBlank(orderByClause)){
			example.setOrderByClause(orderByClause);
		}
		List<T> list = this.mapper.selectByExample(example);
		return list;
	}
	
	
}
