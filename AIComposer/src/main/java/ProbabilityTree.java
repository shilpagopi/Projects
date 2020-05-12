import java.util.Map.Entry;
import java.util.Random;

public class ProbabilityTree {
	static Node P1tree = new Node("root");

	public static void setP1(String song) {
		String mathra[] = song.split(" ");
		if (mathra.length < 1)
			return;

		String note = mathra[0];
		String note1;
		int j;
		for (int i = 1; i < mathra.length; i++) {
			j = i;
			while (i < mathra.length && mathra[i].equals("-"))
				i++;
			if (i - j > 0) //Rest Note
				note1 = (i - j) + "R";
			else //Music Note
				note1 = mathra[i];

			if (P1tree.getChild(note) == null) {
				P1tree.addChild(note);
			}
			if (P1tree.getChild(note).getChild(note1) == null) {
				P1tree.getChild(note).addChild(note1);
			}
			P1tree.getChild(note).getChild(note1).incrementCount();

			if (i == j) {
				P1tree.incrementSum();
				P1tree.getChild(note).incrementSum();
				note = note1;
			} else { // rest note
				P1tree.incrementRestSum();
				P1tree.getChild(note).incrementRestSum();
			}
		}
		setP1Rest();
	}
	
	public static void setP1Rest() {
		for (Entry<String, Node> n1 : P1tree.getChildren().entrySet()) {
			n1.getValue().addChild("0R");
			n1.getValue().getChild("0R").setCount(n1.getValue().getSum()-n1.getValue().getRestSum());
		}
	}
	public static void printP1() {
		System.out.println("P1tree: sum " + P1tree.getSum() + ", rest sum "
				+ P1tree.getRestSum());
		for (Entry<String, Node> n1 : P1tree.getChildren().entrySet()) {
			String sysout = n1.getKey();
			System.out.println(sysout + ": sum " + n1.getValue().getSum()
					+ ", rest sum " + n1.getValue().getRestSum());
			for (Entry<String, Node> n2 : n1.getValue().getChildren()
					.entrySet()) {
				System.out.println(sysout + ":" + n2.getKey() + ":"
						+ n2.getValue().getCount());
			}
		}
	}

	public static String generateNextNote(String note) {
		if (P1tree.getChild(note) == null)
			return null;
		Integer total = P1tree.getChild(note).getSum();
		String nextnote = null;
		Float nextnoteScore = (float) 0;
		Float score = (float) 0;

		Float w1 = (float) 0.3;
		Float w2 = (float) 0.7;

		for (Entry<String, Node> n : P1tree.getChild(note).getChildren()
				.entrySet()) {
			if (!n.getKey().contains("R")) {
				Float randomScore = (float) new Random().nextInt(100) / 100;
				score = (w1 * n.getValue().getCount() / total) + w2
						* randomScore;
				//System.out.println(n.getKey() + ":" + score + "," + randomScore);

				if (score > nextnoteScore) {
					nextnoteScore = score;
					nextnote = n.getKey();
				}
			}
		}
		return nextnote;
	}
	
	public static String generateNextRestNote(String note) {
		if (P1tree.getChild(note) == null)
			return null;
		Integer total = P1tree.getChild(note).getSum();
		String nextnote = null;
		Float nextnoteScore = (float) 0;
		Float score = (float) 0;

		Float w1 = (float) 0.3;
		Float w2 = (float) 0.7;

		for (Entry<String, Node> n : P1tree.getChild(note).getChildren()
				.entrySet()) {
			if (n.getKey().contains("R")) {
				Float randomScore = (float) new Random().nextInt(100) / 100;
				score = (w1 * n.getValue().getCount() / total) + w2
						* randomScore;
				//System.out.println(n.getKey() + ":" + score + "," + randomScore);

				if (score > nextnoteScore) {
					nextnoteScore = score;
					nextnote = n.getKey();
				}
			}
		}
		return nextnote;
	}

}