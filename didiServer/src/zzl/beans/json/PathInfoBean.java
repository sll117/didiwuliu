package zzl.beans.json;

/**
 * ����˾���Ļ�����Ϣ��·�ߵ����˷�
 */
public class PathInfoBean {
	private int id;
	private String nickName;
	private String phone;
	private float price;// �˷ѵ���

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price >= 0 ? price : 0.0f;
	}
}
