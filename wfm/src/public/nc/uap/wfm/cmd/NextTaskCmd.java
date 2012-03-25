package nc.uap.wfm.cmd;
import nc.uap.wfm.exception.WfmServiceException;
public class NextTaskCmd implements ICommand<Void> {
	public Void execute() throws WfmServiceException {
		ICommandService commandService = new CommandService();
		/**
		 * ��ˮ������
		 */
		commandService.execute(new GenFrmNumBillCmd());
		/**
		 * �������ʵ��״̬
		 */
		commandService.execute(new CheckProInsStateCmd());
		/**
		 * ����ָ�ɵ���Ϣ
		 */
		commandService.execute(new SaveAssignInfoCmd());
		/**
		 * ��ɱ�������
		 */
		commandService.execute(new CompleteTaskCmd());
		/**
		 * �ʵ�����
		 */
		if (commandService.execute(new CheckHumActInsStateCmd()).booleanValue()) {
			/**
			 * ��ɱ����
			 */
			commandService.execute(new CompleteHumActInsCmd());
			/**
			 *������������һ��
			 */
			commandService.execute(new SignalCmd());
		}
		return null;
	}
}
