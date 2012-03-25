package nc.uap.wfm.parse;
import java.io.Reader;
import nc.uap.wfm.exception.WfmServiceException;
import nc.uap.wfm.model.ActorStrategy;
import nc.uap.wfm.model.AssistStrategy;
import nc.uap.wfm.model.CompleteStrategy;
import nc.uap.wfm.model.EndEvent;
import nc.uap.wfm.model.EventShape;
import nc.uap.wfm.model.ExcGat;
import nc.uap.wfm.model.HumAct;
import nc.uap.wfm.model.IncGat;
import nc.uap.wfm.model.MessageStrategy;
import nc.uap.wfm.model.ProDef;
import nc.uap.wfm.model.ProcessDiagram;
import nc.uap.wfm.model.RejectStrategy;
import nc.uap.wfm.model.SequenceFlow;
import nc.uap.wfm.model.SequenceFlowConnector;
import nc.uap.wfm.model.StartEvent;
import nc.uap.wfm.model.TaskShape;
import org.apache.commons.digester.Digester;
import org.apache.commons.io.IOUtils;
public class ProcessParser {
	private static ProcessParser instance = null;
	private ProcessParser() {}
	synchronized public static ProcessParser getInstance() {
		if (instance != null)
			return instance;
		else {
			instance = new ProcessParser();
		}
		return instance;
	}
	public ProDef parse(String prodefxml) throws WfmServiceException {
		if (prodefxml == null || prodefxml.length() == 0) {
			return null;
		}
		Reader reader = null;
		try {
			String xmlpath = "Definitions/Process";
			Digester digester = new Digester();
			reader = new java.io.StringReader(prodefxml);
			digester.setValidating(false);
			/**
			 * �ݹ����
			 */
			int count = 0;
			this.recursSubProcess(digester, xmlpath, count);
			/**
			 * ��ʼ����
			 */
			ProDef proDef = (ProDef) digester.parse(reader);
			/**
			 * ��װ���
			 */
			proDef = ProDefBuilder.assembleProDef(proDef);
			return proDef;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WfmServiceException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(reader);
		}
	}
	public ProcessDiagram parseDiagram(String prodefxml) throws WfmServiceException {
		Reader reader = null;
		try {
			String xmlpath = "Definitions/ProcessDiagram";
			Digester digester = new Digester();
			reader = new java.io.StringReader(prodefxml);
			digester.setValidating(false);
			/**
			 * �ݹ����
			 */
			int count = 0;
			this.recursSubProcessDiagram(digester, xmlpath, count);
			/**
			 * ��ʼ����
			 */
			ProcessDiagram processDiagram = (ProcessDiagram) digester.parse(reader);
			return processDiagram;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WfmServiceException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(reader);
		}
	}
	protected void recursSubProcess(Digester digester, String xmlpath, int count) throws WfmServiceException {
		/**
		 * ���̶�����Ϣ
		 */
		digester.addObjectCreate(xmlpath, ProDef.class.getName());
		digester.addSetProperties(xmlpath);
		/**
		 *��ʼ��Ϣ
		 */
		String str = xmlpath + "/StartEvent";
		digester.addObjectCreate(str, StartEvent.class.getName());
		digester.addSetProperties(str);
		digester.addSetNext(str, "setStart", StartEvent.class.getName());
		digester.addSetNext(str, "addPorts", StartEvent.class.getName());
		/**
		 *������Ϣ
		 */
		str = xmlpath + "/EndEvent";
		digester.addObjectCreate(str, EndEvent.class.getName());
		digester.addSetProperties(str);
		digester.addSetNext(str, "setEnd", EndEvent.class.getName());
		digester.addSetNext(str, "addPorts", EndEvent.class.getName());
		/**
		 * ������Ϣ
		 */
		str = xmlpath + "/SequenceFlow";
		digester.addObjectCreate(str, SequenceFlow.class.getName());
		digester.addSetProperties(str);
		digester.addSetNext(str, "addSequenceFlows", SequenceFlow.class.getName());
		/**
		 * �û��
		 */
		str = xmlpath + "/UserTask";
		digester.addObjectCreate(str, HumAct.class.getName());
		digester.addSetProperties(str);
		digester.addSetNext(str, "addHumanActivitys", HumAct.class.getName());
		digester.addSetNext(str, "addPorts", HumAct.class.getName());
		/**
		 * �����߲���
		 */
		str = xmlpath + "/UserTask/ActorStrategy";
		digester.addObjectCreate(str, ActorStrategy.class.getName());
		digester.addSetProperties(str);
		digester.addSetNext(str, "setActorStrategy", ActorStrategy.class.getName());
		/**
		 * Э�������
		 */
		str = xmlpath + "/UserTask/AssistStrategy";
		digester.addObjectCreate(str, AssistStrategy.class.getName());
		digester.addSetProperties(str);
		digester.addSetNext(str, "setAssistStrategy", AssistStrategy.class.getName());
		/**
		 * ��Ϣ����
		 */
		str = xmlpath + "/UserTask/MessageStrategy";
		digester.addObjectCreate(str, MessageStrategy.class.getName());
		digester.addSetProperties(str);
		digester.addSetNext(str, "setMessageStrategy", MessageStrategy.class.getName());
		/**
		 * ��ɲ���
		 */
		str = xmlpath + "/UserTask/CompleteStrategy";
		digester.addObjectCreate(str, CompleteStrategy.class.getName());
		digester.addSetProperties(str);
		digester.addSetNext(str, "setCompleteStrategy", CompleteStrategy.class.getName());
		/**
		 * ���ز���
		 */
		str = xmlpath + "/UserTask/RejectStrategy";
		digester.addObjectCreate(str, RejectStrategy.class.getName());
		digester.addSetProperties(str);
		digester.addSetNext(str, "setRejectStrategy", RejectStrategy.class.getName());
		// str = xmlpath + "GateWay";
		// digester.addObjectCreate(str, GateWay.class);
		// digester.addSetProperties(str);
		// digester.addSetNext(str, "addGateWay", GateWay.class.getName());
		/**
		 * Ψһ����
		 */
		str = xmlpath + "/ExclusiveGateway";
		digester.addObjectCreate(str, ExcGat.class.getName());
		digester.addSetProperties(str);
		digester.addSetNext(str, "addExclusiveGateways", ExcGat.class.getName());
		digester.addSetNext(str, "addPorts", ExcGat.class.getName());
		/**
		 * ��Ƕ����
		 */
		str = xmlpath + "/InclusiveGateway";
		digester.addObjectCreate(str, IncGat.class.getName());
		digester.addSetProperties(str);
		digester.addSetNext(str, "addInclusiveGateways", IncGat.class.getName());
		digester.addSetNext(str, "addPorts", IncGat.class.getName());
		/**
		 * ������
		 */
		for (int i = 0; i < 1; i++) {
			xmlpath = xmlpath + "/SubProcess";
			/**
			 * ���̶�����Ϣ
			 */
			digester.addObjectCreate(xmlpath, ProDef.class.getName());
			digester.addSetProperties(xmlpath);
			digester.addSetNext(xmlpath, "addSubProcess", ProDef.class.getName());
			/**
			 *��ʼ��Ϣ
			 */
			str = xmlpath + "/StartEvent";
			digester.addObjectCreate(str, StartEvent.class.getName());
			digester.addSetProperties(str);
			digester.addSetNext(str, "setStart", StartEvent.class.getName());
			digester.addSetNext(str, "addPorts", StartEvent.class.getName());
			/**
			 *������Ϣ
			 */
			str = xmlpath + "/EndEvent";
			digester.addObjectCreate(str, EndEvent.class.getName());
			digester.addSetProperties(str);
			digester.addSetNext(str, "setEnd", EndEvent.class.getName());
			digester.addSetNext(str, "addPorts", EndEvent.class.getName());
			/**
			 * ������Ϣ
			 */
			str = xmlpath + "/SequenceFlow";
			digester.addObjectCreate(str, SequenceFlow.class.getName());
			digester.addSetProperties(str);
			digester.addSetNext(str, "addSequenceFlows", SequenceFlow.class.getName());
			/**
			 * �û��
			 */
			str = xmlpath + "/UserTask";
			digester.addObjectCreate(str, HumAct.class.getName());
			digester.addSetProperties(str);
			digester.addSetNext(str, "addHumanActivitys", HumAct.class.getName());
			digester.addSetNext(str, "addPorts", HumAct.class.getName());
			/**
			 * �����߲���
			 */
			str = xmlpath + "/UserTask/ActorStrategy";
			digester.addObjectCreate(str, ActorStrategy.class.getName());
			digester.addSetProperties(str);
			digester.addSetNext(str, "setActorStrategy", ActorStrategy.class.getName());
			/**
			 * ��ɲ���
			 */
			str = xmlpath + "/UserTask/CompleteStrategy";
			digester.addObjectCreate(str, CompleteStrategy.class.getName());
			digester.addSetProperties(str);
			digester.addSetNext(str, "setCompleteStrategy", CompleteStrategy.class.getName());
			/**
			 * ���ز���
			 */
			str = xmlpath + "/UserTask/RejectStrategy";
			digester.addObjectCreate(str, RejectStrategy.class.getName());
			digester.addSetProperties(str);
			digester.addSetNext(str, "setRejectStrategy", RejectStrategy.class.getName());
			/**
			 * ��Ϣ����
			 */
			str = xmlpath + "/UserTask/MessageStrategy";
			digester.addObjectCreate(str, MessageStrategy.class.getName());
			digester.addSetProperties(str);
			digester.addSetNext(str, "setMessageStrategy", MessageStrategy.class.getName());
			/**
			 * Ψһ����
			 */
			str = xmlpath + "/ExclusiveGateway";
			digester.addObjectCreate(str, ExcGat.class.getName());
			digester.addSetProperties(str);
			digester.addSetNext(str, "addExclusiveGateways", ExcGat.class.getName());
			digester.addSetNext(str, "addPorts", ExcGat.class.getName());
			/**
			 * ��Ƕ����
			 */
			str = xmlpath + "/InclusiveGateway";
			digester.addObjectCreate(str, IncGat.class.getName());
			digester.addSetProperties(str);
			digester.addSetNext(str, "addInclusiveGateways", IncGat.class.getName());
			digester.addSetNext(str, "addPorts", IncGat.class.getName());
		}
	}
	protected void recursSubProcessDiagram(Digester digester, String xmlpath, int count) throws WfmServiceException {
		/**
		 * ���̶�����Ϣ
		 */
		digester.addObjectCreate(xmlpath, ProcessDiagram.class.getName());
		digester.addSetProperties(xmlpath);
		digester.addSetProperties(xmlpath, "Width", "width");
		digester.addSetProperties(xmlpath, "Height", "height");
		/**
		 *������Ϣ
		 */
		String str = xmlpath + "/LaneCompartment/TaskShape";
		digester.addObjectCreate(str, TaskShape.class.getName());
		digester.addSetProperties(str);
		digester.addSetNext(str, "addTaskShapes", TaskShape.class.getName());
		digester.addSetProperties(str, "Width", "width");
		digester.addSetProperties(str, "Height", "height");
		/**
		 *�¼���Ϣ
		 */
		str = xmlpath + "/LaneCompartment/EventShape";
		digester.addObjectCreate(str, EventShape.class.getName());
		digester.addSetProperties(str);
		digester.addSetNext(str, "addEventShapes", EventShape.class.getName());
		digester.addSetProperties(str, "Width", "width");
		digester.addSetProperties(str, "Height", "height");
		/**
		 * ������Ϣ
		 */
		str = xmlpath + "/SequenceFlowConnector";
		digester.addObjectCreate(str, SequenceFlowConnector.class.getName());
		digester.addSetProperties(str);
		digester.addSetNext(str, "addSequenceFlowConnectors", SequenceFlowConnector.class.getName());
	}
}
