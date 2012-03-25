package nc.uap.wfm.impl;
import java.util.List;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.wfm.itf.IWfmFlwTypeQry;
import nc.uap.wfm.vo.WfmFlwTypeVO;
import org.apache.commons.lang.StringUtils;
public class WfmFlwTypeQry implements IWfmFlwTypeQry {
	
	@Override public WfmFlwTypeVO getFlwTypeVoByPk(String flwTypePk) {
		WfmFlwTypeVO[] flwTypeVos = this.getFlwTypeVoByPks(new String[] { flwTypePk });
		if (flwTypeVos == null || flwTypeVos.length == 0) {
			return null;
		}
		return flwTypeVos[0];
	}
	@SuppressWarnings("unchecked") @Override public WfmFlwTypeVO[] getFlwTypeVoByPks(String[] flwTypePks) {
		PtBaseDAO dao = new PtBaseDAO();
		String flwTypePksStr = StringUtils.join(flwTypePks);
		String sql = "select * from wfm_flwtype where pk_flwtype in('" + flwTypePksStr + "')";
		try {
			List<WfmFlwTypeVO> list = (List<WfmFlwTypeVO>) dao.executeQuery(sql, new BeanListProcessor(WfmFlwTypeVO.class));
			if (list == null || list.size() == 0) {
				return null;
			}
			return list.toArray(new WfmFlwTypeVO[0]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked") @Override public WfmFlwTypeVO getFlwTypeVoByPageId(String pageId) {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "select a.* from wfm_flwtype a, cp_appsnode b where b.id='" + pageId + "' and b.pk_appsnode=a.pageid";
		try {
			List<WfmFlwTypeVO> list = (List<WfmFlwTypeVO>) dao.executeQuery(sql, new BeanListProcessor(WfmFlwTypeVO.class));
			if (list == null || list.size() == 0) {
				return null;
			}
			return list.get(0);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked") @Override public WfmFlwTypeVO[] getSubFlwTypeByParentPk(String parentPk) {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "select * from wfm_flwtype where pk_parent ='" + parentPk + "'";
		try {
			List<WfmFlwTypeVO> list = (List<WfmFlwTypeVO>) dao.executeQuery(sql, new BeanListProcessor(WfmFlwTypeVO.class));
			if (list == null || list.size() == 0) {
				return null;
			}
			return list.toArray(new WfmFlwTypeVO[0]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked") @Override public WfmFlwTypeVO[] getFlwTypeVosByFlowCatePk(String flowCatePk) {
		PtBaseDAO dao = new PtBaseDAO();
		String sql = "select * from wfm_flwtype where pk_flwcat ='" + flowCatePk + "' order by ts";
		try {
			List<WfmFlwTypeVO> list = (List<WfmFlwTypeVO>) dao.executeQuery(sql, new BeanListProcessor(WfmFlwTypeVO.class));
			if (list == null || list.size() == 0) {
				return null;
			}
			return list.toArray(new WfmFlwTypeVO[0]);
		} catch (DAOException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}
}
