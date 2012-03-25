package nc.uap.wfm.completesgy;
public interface ICompleteSgy {
	public static final int CompleteSgy_Occupy = 1;//抢占策略
	public static final int CompleteSgy_Countersign= 2;//会签策略
	public static final int CompleteSgy_ByCount = 3;//按数量完成策略
	public static final int CompleteSgy_ByPercent = 4;//按百分比完成策略
}
