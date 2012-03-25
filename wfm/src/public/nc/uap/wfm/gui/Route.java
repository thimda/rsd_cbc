package nc.uap.wfm.gui;
/**
 * 2011-5-25 обнГ02:38:21 limingf
 */
public class Route extends Element {
	private String routeStyle = "0";
	private String source;
	private String target;
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getRouteStyle() {
		return routeStyle;
	}
	public void setRouteStyle(String routeStyle) {
		this.routeStyle = routeStyle;
	}
}
