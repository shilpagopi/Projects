import java.util.HashMap;

public class Node {
	private final String note;
	private Integer count = 0;
	private final HashMap<String, Node> children;
	private Integer sum = 0;
	private Integer restSum = 0;
	
	Node(String name) {
		this.note = name;
		this.children = new HashMap<String, Node>();
	}

	String getNote() {
		return note;
	}

	void addChild(String note) {
		Node child = new Node(note);
		children.put(child.getNote(), child);
	}

	void removeChild(String note) {
		children.remove(note);
	}

	Node getChild(String note) {
		return children.get(note);
	}

	HashMap<String, Node> getChildren() {
		return children;
	}

	public Integer getCount() {
		return count;
	}
	
	public void setCount(Integer num) {
		count = num;
	}

	public void incrementCount() {
		count++;
	}

	public Integer getSum() {
		return sum;
	}

	public void incrementSum() {
		this.sum++;
	}

	public Integer getRestSum() {
		return restSum;
	}

	public void incrementRestSum() {
		this.restSum++;
	}
}