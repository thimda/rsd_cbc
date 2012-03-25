package nc.uap.wfm.cmd;
import nc.uap.wfm.exception.WfmServiceException;
public class StartProInsCmd implements ICommand<Void> {
	public Void execute() throws WfmServiceException {
		ICommandService commandService = new CommandService();
		/**
		 * ��ˮ������
		 */
		commandService.execute(new GenFrmNumBillCmd());
		/**
		 * ��������ʵ��
		 */
		commandService.execute(new CreateProInsCmd());
		/**
		 * ����ָ�ɵ���Ϣ
		 */
		commandService.execute(new SaveAssignInfoCmd());
		/**
		 * ������������
		 */
		commandService.execute(new CreateStartTaskCmd());
		/**
		 *���������ߵ���һ����ڵ�
		 */
		commandService.execute(new SignalCmd());
		return null;
	}
}
