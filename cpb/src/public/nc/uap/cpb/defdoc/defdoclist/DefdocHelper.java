package nc.uap.cpb.defdoc.defdoclist;

import java.lang.reflect.Field;

import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.RowSet;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.vo.pub.SuperVO;

public class DefdocHelper {
	public static void putVOstoDS(SuperVO[] vos,Dataset ds,Class voclass){
		ds.clear();
//		Field[]fields = voclass.getFields();
//		for(SuperVO vo:vos){
//			Row emptyrow = ds.getEmptyRow();
//			for(Field field:fields){
//				if(ds.nameToIndex(field.getName().toLowerCase())!=-1){
//					emptyrow.setValue(ds.nameToIndex(field.getName().toLowerCase()),vo.getAttributeValue(field.getName().toLowerCase()) );
//				}
//			}
//			ds.addRow(emptyrow);
//		}
		try{
			ds.getCurrentRowSet().getPaginationInfo().setPageSize(20);
			
		}catch(Exception e){
			
		}
		new SuperVO2DatasetSerializer().serialize(vos, ds,Row.STATE_NORMAL);
		
	}
}
