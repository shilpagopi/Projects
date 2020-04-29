import org.jfugue.player.Player;

public class HelloWorld {
  public static void main(String[] args) {
    Player player = new Player();
    //player.play("C D E F G A B");
    player.play("Eq Fq Eq Dq Cq Cq G4q Cq Dq Eh -");
    player.play("Eq Fq Eq Dq Cq Cq Gq Gq Aq Gh -");
    player.play("Ch Dq Eh - Ch Dq Eh -");
    player.play("Dq Dq Dq Fq Eq Dq Cq");
    
    //Happy Bday
    //player.play("Ci Ci Dq - Cq Fq Eh - Ci Ci Dq - Cq Fq Eh - Ci Ci Gq Fq Eq Dq Cq B4q A4h Ci Ci B4q G4q A4q G4h -");
	
  }
}