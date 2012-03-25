package nc.uap.wfm.model;
public class DefaultPortShape implements IPortShape {
	private static final long serialVersionUID = 6793862444659974481L;
	protected String id;
	protected String name;
	protected String x;
	protected String y;
	protected String rx;
	protected String ry;
	protected String Width;
	protected String Height;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getRx() {
		return rx;
	}
	public void setRx(String rx) {
		this.rx = rx;
	}
	public String getRy() {
		return ry;
	}
	public void setRy(String ry) {
		this.ry = ry;
	}
	public String getWidth() {
		return Width;
	}
	public void setWidth(String width) {
		Width = width;
	}
	public String getHeight() {
		return Height;
	}
	public void setHeight(String height) {
		Height = height;
	}	
	
}
