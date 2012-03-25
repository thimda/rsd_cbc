package nc.uap.wfm.history;
import nc.uap.lfw.core.comp.IWebPartContentFetcher;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.jsp.uimeta.UIMeta;
public class WebPartContentFetcherImpl implements IWebPartContentFetcher {
	public String fetchBodyScript(UIMeta um, PageMeta pm, LfwWidget view) {
		return "";
	}
	public String fetchHtml(UIMeta um, PageMeta pm, LfwWidget view) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<div style='border:1px solid #A0B3C6;width:440px;height:275px;top:5px;overflow-y:auto'>");
		for (int i = 0; i < 10; i++) {
			buffer.append("<div style=''> ");
			buffer.append("<div style='display:inline-block;float:left'><img src='/portal/pagemeta/public/widgetpool/pubview_history/regular_smile.gif' style='height:22px'></div>");
			buffer.append("<div style='display:inline-block;float:left;margin-top:5px'><font style='font-weight:bold;'>人力资源部:</font><font>王娜</font></div>");
			buffer.append("<div style='float:right;margin-top:5px'><font>2012-12-12 08:08:08</font></div>");
			buffer.append("</div>");
			buffer.append("<div style='clear:both'> ");
			buffer.append("<div style='float:left;margin-left:20px;'>");
			buffer.append("<font style='color:red;font-weight:bold'>不批准</font><font style='margin-left:15px'>好好干好好干好好干好好干好好干好好干好好干好好干好好干好好干好好干好好干好好干好好干好好干</font>");
			buffer.append("</div>");
			buffer.append("</div>");
			buffer.append("<div style='clear:both;height:12px'></div>");
		}
		buffer.append("</div>");
		return buffer.toString();
	}
}
