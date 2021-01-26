package zzl.beans.json;

public class PathBean {
	private String location;
	private float carriage;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getCarriage() {
		return carriage;
	}

	public void setCarriage(float carriage) {
		this.carriage = carriage >= 0 ? carriage : 0.0f;
	}
}
