package nc.uap.cpb.org.listener;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.crud.ILfwCRUDService;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.core.uif.listener.UifMouseListener;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;


/**
 * 查询操作确定命令基类,实现类需要在子类中实现onclick方法，
 * 2010-8-19 下午02:08:08  limingf
 */

public abstract class QueryOkListener<T extends WebElement> extends UifMouseListener<T>{
	protected String wherePart = "";
	
	public QueryOkListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}
	
	public abstract void onclick(MouseEvent<T> e);
	/**
	 * 
	 * @param vo
	 * @param ds
	 * @param wehreMap 查询条件键值对(字段=值,如name="mr Li")
	 * @param flag  用来设置精确查询还是模糊查询,true:模糊，false：精确
	 */
	public void onDataLoad(SuperVO vo,Dataset ds,Map<String,Object> whereMap,boolean like) {	
		try {
			PaginationInfo pinfo = ds.getCurrentRowSet().getPaginationInfo();
			SuperVO[] vos = null;
			if(!like)
				vos = queryVOsByExact(pinfo, vo,whereMap);
			else
				vos = queryVOsByLike(pinfo,vo,whereMap);
			new SuperVO2DatasetSerializer().serialize(vos, ds, Row.STATE_NORMAL);			
		} 
		catch (LfwBusinessException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException("查询对象出错," + e.getMessage() + ",ds id:" + ds.getId(), "查询过程出现错误");
		}
	}
	/**
	 * @param vo
	 * @param ds
	 */
	public void onDataLoad(SuperVO vo,Dataset ds) {					
		onDataLoad(vo,ds,null,false);		
	}
	/**
	 * @param vo
	 * @param ds
	 */
	public void onDataLoad(SuperVO vo,Dataset ds,boolean like) {					
		onDataLoad(vo,ds,null,like);		
	}
	/**
	 * 按照vo属性或者条件map精确查询
	 * @param pinfo
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	@SuppressWarnings("deprecation")
	protected SuperVO[] queryVOsByExact(PaginationInfo pinfo, SuperVO vo,Map<String,Object> whereMap) throws LfwBusinessException {		
		String sql = "select * from "+vo.getTableName()+" ";	
		StringBuffer notnullsb = new StringBuffer();
		if(whereMap==null){
			String[] attributes = vo.getAttributeNames();
			for(int i=0;i<attributes.length;i++){
			Object value = vo.getAttributeValue(attributes[i]);	
			if(value!=null&&!"".equals(value)){
				//如果是时间截取到天，忽略时，分，秒	
				if(value instanceof UFDateTime)value = ((UFDateTime)value).getDate().toLocalString();
				else if(value instanceof Boolean)value = ((Boolean)value==true?'Y':'N');
				notnullsb.append(attributes[i]).append(" ='").append(value).append("' ").append("and ");
			}
		 }
		}
		else if(whereMap!=null){
			Set<String> attributes = whereMap.keySet();		
			Iterator<String> ite = attributes.iterator();
			while(ite.hasNext()){
				String attribute = ite.next();
				Object value = whereMap.get(attribute);
				if(value!=null&&!"".equals(value)){
					//如果是时间截取到天，忽略时，分，秒	
					if(value instanceof UFDateTime)value = ((UFDateTime)value).getDate().toLocalString();
					else if(value instanceof Boolean)value = ((Boolean)value==true?'Y':'N');
					notnullsb.append(attribute).append(" ='%").append(value).append("' ").append("and ");
				}
			}
		}

		String[] wheres = notnullsb.toString().split("and");
		
		StringBuffer sb = new StringBuffer();
		if(!(wheres.length==1&&"".equals(wheres[0]))){
			for(int i=0;i<wheres.length;i++){
				if(i==0)sb.append(" where ");		
				sb.append(wheres[i]);
				if(i!=wheres.length-1)sb.append(" and ");
			}	
		}
		sql += sb.toString();
		if(sql.indexOf("where")==-1&&getWherePart().length()>0)sql += " where "+getWherePart();
		else sql += getWherePart();
		if(sql.trim().endsWith("and"))
			sql = sql.substring(0, sql.trim().length() - 3);
		SuperVO[] vos = getCrudService().queryVOs(sql, vo.getClass(), null, null);
		return vos;
	}
	/**
	 * 按照vo属性或者条件map模糊查询
	 * @param pinfo
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	@SuppressWarnings("deprecation")
	protected SuperVO[] queryVOsByLike(PaginationInfo pinfo, SuperVO vo,Map<String,Object> whereMap) throws LfwBusinessException {		
		String sql = "select * from "+vo.getTableName()+" ";	
		StringBuffer notnullsb = new StringBuffer();
		if(whereMap==null){
			String[] attributes = vo.getAttributeNames();
			for(int i=0;i<attributes.length;i++){
			Object value = vo.getAttributeValue(attributes[i]);	
			if(value!=null&&!"".equals(value)){
				//如果是时间截取到天，忽略时，分，秒	
				if(value instanceof UFDateTime)value = ((UFDateTime)value).getDate().toLocalString();
				else if(value instanceof Boolean)value = ((Boolean)value==true?'Y':'N');
				notnullsb.append(attributes[i]).append(" like '%").append(value).append("%' ").append("and ");
			}
		 }
		}
		else if(whereMap!=null){
			Set<String> attributes = whereMap.keySet();		
			Iterator<String> ite = attributes.iterator();
			while(ite.hasNext()){
				String attribute = ite.next();
				Object value = whereMap.get(attribute);
				if(value!=null&&!"".equals(value)){
					//如果是时间截取到天，忽略时，分，秒	
					if(value instanceof UFDateTime)value = ((UFDateTime)value).getDate().toLocalString();
					else if(value instanceof Boolean)value = ((Boolean)value==true?'Y':'N');
					notnullsb.append(attribute).append(" like '%").append(value).append("%' ").append("and ");
				}
			}
		}

		String[] wheres = notnullsb.toString().split("and");
		
		StringBuffer sb = new StringBuffer();
		if(!(wheres.length==1&&"".equals(wheres[0]))){
			for(int i=0;i<wheres.length;i++){
				if(i==0)sb.append(" where ");		
				sb.append(wheres[i]);
				if(i!=wheres.length-1)sb.append(" and ");
			}	
		}
		sql += sb.toString();
		if(sql.indexOf("where")==-1&&getWherePart().length()>0)sql += " where "+getWherePart();
		else sql += getWherePart();
		if(sql.trim().endsWith("and"))
			sql = sql.substring(0, sql.trim().length() - 3);
		SuperVO[] vos = getCrudService().queryVOs(sql, vo.getClass(), null, null);
		return vos;
	}
	/**
	 * 按照vo属性精确查询
	 * @param pinfo
	 * @param vo
	 * @return
	 * @throws LfwBusinessException
	 */
	protected SuperVO[] queryVOs(PaginationInfo pinfo, SuperVO vo) throws LfwBusinessException {
		String wherePart = getWherePart();
		SuperVO[] vos =  getCrudService().queryVOs(vo, pinfo, wherePart, null);
		return vos;
	}
	
	protected void setWherePart(String where){
		this.wherePart = where;
	}
	protected String getWherePart(){
		return wherePart;
	}

	protected ILfwCRUDService<SuperVO, AggregatedValueObject> getCrudService() {
		return CRUDHelper.getCRUDService();
	}


}
