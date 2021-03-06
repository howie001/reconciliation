
package com.froad.recon.importfile.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.comon.dao.impl.DBException;
import com.froad.comon.dao.impl.HibernateBaseDao;
import com.froad.comon.idgenerator.GeneratorHelp;
import com.froad.comon.util.Logger;
import com.froad.comon.util.SqlUtil;
import com.froad.recon.importfile.dao.IpointsDAO;
import com.froad.recon.importfile.model.Ipoints;


/**
 * 积分对账数据表
 * @author zhangjwei
 * @created 2015-06-16 11:07:28
 * @version 1.0
 */

public class IpointsDAOImpl extends HibernateBaseDao implements  IpointsDAO {
	private final static Logger logger = Logger.getLogger(IpointsDAOImpl.class);
	
	/**
	 * @Title: inser 
	 * @Description: 插入单条数据
	 * @param ipoints  ipoints 对象
	 * @return Ipoints   返回插入完成的 ipoints对象
	 */
	@SuppressWarnings("unchecked")
	public Ipoints insert(Ipoints ipoints) throws DBException {
		String id=GeneratorHelp.generateIpoints();
		if (id!=null&&!"".equals(id)){
	        ipoints.setTransferId(id);
		}
		super.save(ipoints);
		return ipoints;
	}
	
	/**
	 * @Title: batchInser 
	 * @Description: 插入多条数据 
	 * @param listIpoints 插入数据的list    
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void batchInser (List<Ipoints> lists) throws DBException  {
		if (lists==null|| lists.size()<=0){
			return;
		}
		for (int i = 0; i < lists.size(); i++) {
			String id=GeneratorHelp.generateIpoints();
			Ipoints item=lists.get(i);
			if (id!=null&&!"".equals(id)){
		        item.setTransferId(id);
				
				super.save(item);
			}
		}
	}
	
	/**
	 * @Title: delete
	 * @Description: 根据对象删除
	 * @param objId void    
	 */
	@SuppressWarnings("unchecked")
	public void delete(Ipoints ipoints) throws DBException {
		super.delete(ipoints);
	}
	
	/**
	 * @Title: batchDelete 
	 * @Description: 根据 根据对象集合删除数据
	 * @param lists id集合   
	 */
	@SuppressWarnings("unchecked")
	public void batchDelete(List<Ipoints> lists) throws DBException  {
		if (lists==null|| lists.size()<=0){
			return;
		}
		for (int i = 0; i < lists.size(); i++) {
			Ipoints item=lists.get(i);
			super.delete(item);
		}
	}
	
	/**
	 * @Title: update 
	 * @Description: 跟新一条数据 ipoints
	 * @param ipoints  ipoints对象
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void update(Ipoints ipoints) throws DBException {
		super.update(ipoints);
	}
	
	/**
	 * @Title: batchUpdate
	 * @Description: 跟新多条数据 ipoints集合
	 * @param listIpoints  ipoints集合  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void batchUpdate(List<Ipoints> lists) throws DBException {
		if (lists==null|| lists.size()<=0){
			return;
		}
		for (int i = 0; i < lists.size(); i++) {
			Ipoints item=lists.get(i);
			super.update(item);
		}
		
	}
	/**
	 * @Title: getById
	 * @Description: 根据编号查询一条数据
	 * @param objID 编号
	 * @return Ipoints    ipoints 对象
	 * @throws
	 */
	public Ipoints getById(String objID) {
		return  (Ipoints)super.getHibernateTemplate().get(Ipoints.class, objID);
	}
	
	/**
	 * @Title: getAll
	 * @Description: 查询所有的ipoints数据
	 * @return List    返回一个ipoints的集合
	 * @throws
	 */
	public List getAll(){
		return  getHibernateTemplate().findByExample(new Ipoints());
	}
	
	/**
	 * @Title: getList
	 * @Description: 根据 ipoints对象 查询符合的数据
	 * @param ipoints ipoints对象
	 * @return List    返回一个结合。 
	 * @throws
	 */
	public List getList(Ipoints ipoints) {
		return  getHibernateTemplate().findByExample(ipoints);
	}
	
	/**
	 * @Description: 根据 以paramsMap中key为字段名value为值， 查询符合条件的记录
	 * @param paramsMap 
	 * @param pageNum  页号
	 * @param pageSize 页大小
	 * @return
	 * @throws DBException
	 */
	public List<Ipoints> selectForPagin(Map<String, Object> paramsMap, Integer pageNum, Integer pageSize) 
			throws DBException{
		List<Object> params =new ArrayList<Object>();
		String hql= createHql(paramsMap, params, "list");
		List<Ipoints> list = this.findByPaged(hql, params.toArray(), pageNum, pageSize);
		if (list == null || list.size() == 0) {
			return new ArrayList<Ipoints>();
		}
		return list;
	}
	
	/**
	 * @Description: 根据 以paramsMap中key为字段名value为值， 总条数
	 * @param paramsMap 
	 * @param pageNum  页号
	 * @param pageSize 页大小
	 * @return
	 * @throws DBException
	 */
	public Integer selectForPaginCount(Map<String, Object> paramsMap) throws DBException{
		List<Object> params =new ArrayList<Object>();
		String hql= createHql(paramsMap, params, "count");
		List list = this.findByHql(hql, params.toArray());
		if (list == null || list.isEmpty()) {
			return 0;
		}
		return  Integer.parseInt(list.get(0).toString());
	}
	
	/***根据参数封装 HQL语句**/
	private String createHql(Map<String, Object> paramsMap,
			List<Object> params, String type) {
		if(paramsMap==null){
			paramsMap=new HashMap<String, Object>();
		}
		StringBuffer hql=new StringBuffer();
		if ("list".equals(type)) {
		} else {
			hql.append("select count(*) ");
		}
		hql.append("from Ipoints t where 1=1");
		
		SqlUtil.appendResearchConditionMap(paramsMap, hql, params);
		if ("list".equals(type)) {
			hql.append(" order by id desc");
		}
		logger.info("createHql:" +hql.toString()+"==params:"+params.toString());
		return hql.toString();
	}
}
