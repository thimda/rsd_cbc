package nc.uap.ctrl.tpl.qry;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.ddc.IBizObjStorage;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.log.LfwLogger;
import nc.vo.pub.core.ObjectNodeVO;

public class QueryPlanController {
	private static final String STORAGE_CLASS_NAME = "nc.bs.pub.quertytemplate.MyFavoritesStorage";
	public void loadQueryPlan(DataLoadEvent e){
		String SQLStr = getLoadSQL();
		if(SQLStr == null)
			return;
		Dataset ds = e.getSource();
		IBizObjStorage storage = (IBizObjStorage) NCLocator.getInstance().lookup(IBizObjStorage.class.getName());
		// ���ҳ����ݿ�������SQL���������нڵ㡣
		try{
			ObjectNodeVO[] objNodeVOs = storage.loadAllObjectNodeData(null, STORAGE_CLASS_NAME, SQLStr);
			if(objNodeVOs != null){
				for(ObjectNodeVO obj : objNodeVOs){
					Row row = ds.getEmptyRow();
					ds.addRow(row);
					row.setString(0, obj.getId());
					row.setString(1, obj.getGuid());
					row.setString(2, obj.getParentguid());
					row.setString(3, obj.getDisplay());
					row.setString(4, obj.getKind());
					row.setString(5, obj.getNote());
				}
			}
		}
		//���쳣��������׳�
		catch(Exception ex){
			LfwLogger.error(ex.getMessage(), ex);
		}
	}
	
	private String getLoadSQL() {
//		TemplateInfo templateInfo = null;//
		StringBuffer loadSelectSQL= new StringBuffer("select kind,guid,id,display,parentguid, ");
		//loadSelectSQL.append(StringUtil.getUnionStr(MyFavoritesNode.PRIVATE_FLDS, ",",""));
		loadSelectSQL.append(" from pub_myfavorite where 1=1");
//		//if(m_selfLoadSQL==null || m_selfLoadSQL.trim().length()==0) {
//			//<li>1.���Templateid��Ϊ��,���ܽڵ�žͲ���������,��Ϊ��������ǿ繦�ܽڵ��ģ��
//			//<li>2.����ͨ���Զ������õ�������Templateid�ǿ���Ϊ�յ�!��ʱ�����õ���node_code
//			//<li>3.�������TempinfoΪ�գ���鲻���κζ���			
//			
//		if(templateInfo == null) {
//			loadSelectSQL.append(" 1=0 ");
//		}
//		else {
//			String tmpSQLSegment ="";
//			tmpSQLSegment = templateInfo.getTemplateId()==null ? " templateid is null " : "templateid = '" + templateInfo.getTemplateId() + "'";			
//			loadSelectSQL.append(tmpSQLSegment);
//			
//			if(templateInfo.getTemplateId()==null) {//see <li>2
//				tmpSQLSegment = templateInfo.getFunNode()==null ? " node_code is null ":"node_code = '" + templateInfo.getFunNode() + "'";
//				loadSelectSQL.append(" and "+ tmpSQLSegment);			
//			}
//			
//			//�û�IDΪ�յ��ղؼУ�Ϊ��˾����һ�������				
//			tmpSQLSegment = templateInfo.getUserid()==null ? " cuserid is null ":"( cuserid is null or cuserid = '" + templateInfo.getUserid() + "')";
//			loadSelectSQL.append(" and "+ tmpSQLSegment);
//			
//			
//			//��֯PKΪ�յ��ղؼУ�Ϊ�繫˾����һ�������
//			tmpSQLSegment = templateInfo.getPk_Org()==null ? " pk_org is null ":"( pk_org is null or pk_org = '" + templateInfo.getPk_Org() + "')";
//			loadSelectSQL.append(" and "+ tmpSQLSegment);	
//			
//			//���orgtype==null���򲻿���֮.....����Ĭ�ϰ�orgtypeΪ�յ�Ҳ��ΪĬ�ϵĲ����
//			if(templateInfo.getOrgType()!=null){
//				tmpSQLSegment = "(orgtypecode is null or orgtypecode = '" + templateInfo.getOrgType()+"')";
//				loadSelectSQL.append(" and "+ tmpSQLSegment);
//			}
////				loadSelectSQL.append(" order by ts desc");
//		}			
//		}
//		else 
//			loadSelectSQL.append(m_selfLoadSQL);
		
		return loadSelectSQL.toString();
	}
}
