package nc.uap.cpb.org.impl;

import nc.bs.dao.DAOException;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.cpb.org.itf.ICpPluginBill;
import nc.uap.cpb.org.vos.CpExtension;
import nc.uap.cpb.org.vos.CpExtensionPoint;
import nc.uap.cpb.persist.dao.PtBaseDAO;
import nc.uap.lfw.core.log.LfwLogger;

/**
 * �������ʵ��
 * 
 * 
 */
public class CpPluginBill implements ICpPluginBill {

	@Override
	public void add(CpExtension ex) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(ex);
		} catch (DAOException e) {
			LfwLogger.error("��չ:" + ex.getTitle() + "����ʧ��", e);
			throw new CpbBusinessException(e);
		}

	}

	@Override
	public void add(CpExtension[] exs) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVOs(exs);
		} catch (DAOException e) {
			LfwLogger.error("��չ�鱣��ʧ��", e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public void delete(CpExtension ex) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(ex);
		} catch (DAOException e) {
			LfwLogger.error("��չ���޸�ʧ��", e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public void delete(CpExtension[] exs) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVOArray(exs);
		} catch (DAOException e) {
			LfwLogger.error("��չ��ɾ��ʧ��", e);
			throw new CpbBusinessException(e);
		}

	}

	@Override
	public void update(CpExtension ex) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(ex);
		} catch (DAOException e) {
			LfwLogger.error("��չ�޸�ʧ��", e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public void update(CpExtension[] exs) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVOArray(exs);
		} catch (DAOException e) {
			LfwLogger.error("��չ���޸�ʧ��", e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public void add(CpExtensionPoint ex) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVO(ex);
		} catch (DAOException e) {
			LfwLogger.error("��չ:" + ex.getTitle() + "����ʧ��", e);
			throw new CpbBusinessException(e);
		}

	}

	@Override
	public void add(CpExtensionPoint[] exs) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.insertVOs(exs);
		} catch (DAOException e) {
			LfwLogger.error("��չ�鱣��ʧ��", e);
			throw new CpbBusinessException(e);
		}

	}

	@Override
	public void delete(CpExtensionPoint ex) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVO(ex);
		} catch (DAOException e) {
			LfwLogger.error("��չ���޸�ʧ��", e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public void delete(CpExtensionPoint[] exs) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.deleteVOArray(exs);
		} catch (DAOException e) {
			LfwLogger.error("��չ��ɾ��ʧ��", e);
			throw new CpbBusinessException(e);
		}

	}

	@Override
	public void update(CpExtensionPoint ex) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVO(ex);
		} catch (DAOException e) {
			LfwLogger.error("��չ�޸�ʧ��", e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public void update(CpExtensionPoint[] exs) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.updateVOArray(exs);
		} catch (DAOException e) {
			LfwLogger.error("��չ���޸�ʧ��", e);
			throw new CpbBusinessException(e);
		}
	}

	@Override
	public void clearModule(String moduleName) throws CpbBusinessException {
		PtBaseDAO dao = new PtBaseDAO();
		try {
			dao.executeUpdate("delete from " + (new CpExtension()).getTableName() +" where module =' "+ moduleName +"'");
		} catch (DAOException e) {
			LfwLogger.error("��չ����ʧ��", e);
			throw new CpbBusinessException(e);
		}		
	}
}
