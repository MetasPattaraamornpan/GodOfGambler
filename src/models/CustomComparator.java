package models;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import models.Card.Rank;

public class CustomComparator implements Comparator<Card> {
	HashMap<String, Integer> countRank;
	public CustomComparator(ArrayList<Card> cards) {
		// TODO Auto-generated constructor stub
		countRank = new HashMap<String, Integer>();
		for (Rank rank : Card.Rank.values()) {
			countRank.put(rank.name(), 0);
		}
		for (Card card : cards) {
			countRank.put(card.getRank().name(), countRank.get(card.getRank().name())+1);
		}
	}

	@Override
	public int compare(Card c1, Card c2) {
    	return (c2.getRank().ordinal()+(countRank.get(c2.getRank().name()) * 10)) - (c1.getRank().ordinal()+(countRank.get(c1.getRank().name()) * 10));
    }
}
