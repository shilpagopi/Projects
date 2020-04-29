import java.util.HashMap;
import java.util.Map.Entry;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;
import org.jfugue.theory.ChordProgression;

public class MainPlay {
	static HashMap<Integer, String> rests = new HashMap<Integer, String>();
    static String lastnote = "s";

	public static void main(String[] args) {
		setRests();
		Player player = new Player();
        String raganame = "abhogi";
        int duration = 16;
		Raga.loadRaga(raganame);
		String song = Raga.getSong();

		//Raga.setSwaras(raganame);
		//String song = Raga.getSong(raganame);
		ProbabilityTree.setP1(song);
		//ProbabilityTree.printP1();
		//player.play("V0 I[PICCOLO] "+translate(generateSong("g")));
		//player.play("V0 I[VIOLIN] "+translate(generateSong(lastnote)));		
		//player.play("V0 I[GUITAR] "+translate(generateSong(lastnote)));
		//player.play("V0 I[ACCORDIAN] "+translate(generateSong(lastnote)));
		//player.play("V0 I[SITAR] "+translate(generateSong(lastnote)));
		
		Rhythm rhythm = new Rhythm()
        .addLayer("O..oO...O..oOO..")
        .addLayer("..S...S...S...S.")
        .addLayer("````````````````")
        .addLayer("...............+");
		//player.play(new Pattern(translate(song)),rhythm.getPattern().repeat(4));
		
		//player.play(new Pattern("I[PICCOLO] "+translate(generateSong("g"))),rhythm.getPattern().repeat(4));	
		player.play(new Pattern("I[SITAR] "+translate(generateSong(lastnote,duration))),rhythm.getPattern().repeat(duration/16));
		//player.play("C D E -");
		// player.play("Eq Fq Eq Dq Cq Cq G4q Cq Dq Eh -");
		// player.play("Eq Fq Eq Dq Cq Cq Gq Gq Aq Gh -");
		// player.play("Ch Dq Eh - Ch Dq Eh -");
		//player.play("Dq Dq Dq Fq Eq Dq Cq");		 
	    //new Player().play(rhythm.getPattern().repeat(2));
	   
	    //player.play(new Pattern("C D E"), rhythm);

	    //new Player().play(new ChordProgression("I IV vi V").eachChordAs("$!i $!i Ri $!i"), new Rhythm().addLayer("..X...X...X...XO"));
	}

	public static String generateSong(String testnote,int duration) {
		String generatedSong = testnote;
		String nextnote;
		int nextRestnote;

		for (int songlength = 0; songlength < duration; songlength++) {
			nextnote = ProbabilityTree.generateNextNote(testnote);
			nextRestnote = Integer.parseInt(ProbabilityTree
					.generateNextRestNote(testnote).replace("R", ""));
			songlength += nextRestnote;
			while (nextRestnote > 0) {
				generatedSong += " -";
				nextRestnote--;
			}
			generatedSong += " " + nextnote;
			testnote = nextnote;
		}
		System.out.println(generatedSong);
		lastnote = testnote;
		return generatedSong;
	}

	private static String translate(String song) {
		String translated = "";
		String mathra[] = song.split(" ");

		int blankCount = 0;
		for (int i = 0; i < mathra.length; i++) {
			String note = mathra[i];
			if (note.equals("-")) {
				blankCount++;
			} else {
				if (blankCount > 0 && translated.length() == 0) {
					translated = "R";
					blankCount--;
				}
				if (translated.length() != 0)
					translated += rests.get(blankCount) + " ";
				translated += Raga.swaras.get(note);
				blankCount = 0;
			}
		}
		translated += rests.get(blankCount);
		return translated;
	}

	private static void setRests() {
		 rests.put(0, "i");
		 rests.put(1, "q"); 
		 rests.put(2, "q Ri");
		 rests.put(3, "h");
	}
}