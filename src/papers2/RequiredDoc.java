package papers2;

public class RequiredDoc {
	String title;

	String whoNeedIt;

	public RequiredDoc(String title) {
		this.title = title;
		whoNeedIt = "";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWhoNeedIt() {
		return whoNeedIt;
	}

	// I.E. Workers
	public void setWhoNeedIt(String who) {
		whoNeedIt = who;
	}

}
