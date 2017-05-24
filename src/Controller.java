import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import models.Card;
import models.Card.Rank;
import models.Card.Suit;
import models.Player;

public class Controller {
	private static final HashMap<String, Rank> RANKS =  new HashMap<String, Rank>() {{
	    put("2",Rank.TWO);
	    put("3",Rank.THREE);
	    put("4",Rank.FOUR);
	    put("5",Rank.FIVE);
	    put("6",Rank.SIX);
	    put("7",Rank.SEVEN);
	    put("8",Rank.EIGHT);
	    put("9",Rank.NINE);
	    put("10",Rank.TEN);
	    put("J",Rank.JACK);
	    put("Q",Rank.QUEEN);
	    put("K",Rank.KING);
	    put("A",Rank.ACE);
	}};
	private static final HashMap<String, Suit> SUITS =  new HashMap<String, Suit>() {{
	    put("D",Suit.DIAMONDS);
	    put("C",Suit.CLUBS);
	    put("H",Suit.HEARTS);
	    put("S",Suit.SPADES);
	}};
	
	public static void main(String[] args) {        
		try(BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+args[0]))) {
			String sLine;
			Player[] player = new Player[2];
            while ((sLine = br.readLine()) != null) {
            	if(sLine.length()<=0)continue;

                player[0] = createPlayer(sLine);
                sLine = br.readLine();
                player[1] = createPlayer(sLine);
                
                compareHandPlayer(player[0], player[1]);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void compareHandPlayer(Player p1, Player p2){
		Player winner = null;
		Card winCard = null;
		p1.evaluateCard();
		p2.evaluateCard();
		if(p1.getHandRank().ordinal() < p2.getHandRank().ordinal()){
			winner = p1;
		}else if(p1.getHandRank().ordinal() > p2.getHandRank().ordinal()){
			winner = p2;
		}else{
			for(int i=0;i<p1.getCards().size();i++){
				if(p1.getCards().get(i).getRank().ordinal() > p2.getCards().get(i).getRank().ordinal()){
					winner = p1;
					winCard = p1.getCards().get(i);
					break;
				}else if(p1.getCards().get(i).getRank().ordinal() < p2.getCards().get(i).getRank().ordinal()){
					winner = p2;
					winCard = p2.getCards().get(i);
					break;
				}
			}
		}
		if(winner != null){
			System.out.print(winner.getName()+" wins. - with "+winner.getHandRank().toString());
			if(winCard != null) System.out.println(": "+winCard.getRank().toString());
			else System.out.println();
		}else{
			System.out.println("Tie.");
		}
	}
	public static Player createPlayer(String str){
		String[] input = str.split(":");
        Player player = new Player(input[0]);
        String[] cards = input[1].split(" ");
        for (int i = 0 ; i < cards.length; i++) {
        	String sCard = cards[i];
        	if(sCard.length() >= 2){
        		Card card = new Card(RANKS.get(sCard.substring(0,sCard.length()-1)), SUITS.get(sCard.substring(sCard.length()-1)));
        		player.setCard(card);
        	}
        }
        return player;
	}

}
