package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import models.Card.Rank;
import models.Card.Suit;

public class Player {
	private ArrayList<Card> cards;
	private Map<Rank, Integer> cardSet;
	private String name;
	private HandRankking handRank;
	public enum HandRankking {
		ROYAL_FLUSH("Royal flush"),
		STRAIGHT_FLUSH("straight flush"),
		FOUR_OF_A_KIND("four of a kind"),
		FULL_HOUSE("full house"),
		FLUSH("flush"),
		STRAIGHT("straight"),
		THREE_OF_A_KIND("three of a kind"),
		TWO_PAIR("two pair"),
		ONE_PAIR("one pair"),
		HIGH_CARD("high card");
		
		private final String text;
	    private HandRankking(final String text) {
	        this.text = text;
	    }
	    @Override
	    public String toString() {
	        return text;
	    }
	};
	
	public Player(String name) {
		super();
		this.name = name;
		this.cards = new ArrayList<Card>();
		this.cardSet = new HashMap<Rank, Integer>();
		this.handRank = null;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Card> getCards(){
		return cards;
	}
	public void setCard(Card card) {
		this.cards.add(card);
	}
	public HandRankking getHandRank() {
		return handRank;
	}
	
	public void evaluateCard(){
		Collections.sort(this.cards, new CustomComparator(this.cards));
		setCardSet();
		boolean isStraight = isStraight();
		boolean isFlush = isFlush();
		if(isStraight && isFlush){
			handRank = (cards.get(0).getRank() == Card.Rank.ACE)? HandRankking.ROYAL_FLUSH : HandRankking.STRAIGHT_FLUSH;
		}else if(is4kind()){
			handRank = HandRankking.FOUR_OF_A_KIND;
		}else if(isFullHouse()){
			handRank = HandRankking.FULL_HOUSE;
		}else if(isFlush){
			handRank = HandRankking.FLUSH;
		}else if(isStraight){
			handRank = HandRankking.STRAIGHT;
		}else if(isThreeOfAKind()){
			handRank = HandRankking.THREE_OF_A_KIND;
		}else if(isTwoPair()){
			handRank = HandRankking.TWO_PAIR;
		}else if(isOnePair()){
			handRank = HandRankking.ONE_PAIR;
		}else{
			handRank = HandRankking.HIGH_CARD;
		}
	}
	private void setCardSet(){
		for (Card card : cards) {
			int count = (cardSet.get(card.getRank())==null)?1:cardSet.get(card.getRank())+1;
			cardSet.put(card.getRank(), count);
		}
	}
	private boolean isOnePair(){
		for (Rank key : cardSet.keySet()) {
			if(cardSet.get(key) == 2) return true;
		}
		return false;
	}
	private boolean isTwoPair(){
		int first=0;
		for (Rank key : cardSet.keySet()) {
			if(first == 0){
				first = cardSet.get(key);
			}else{
				if(first == 2 && cardSet.get(key) == 2) return true;
			}
		}
		return false;
	}
	private boolean isThreeOfAKind(){
		for (Rank key : cardSet.keySet()) {
			if(cardSet.get(key) == 3) return true;
		}
		return false;
	}
	private boolean isFullHouse(){
		if(cardSet.size()!=2) return false;
		int first=0;
		for (Rank key : cardSet.keySet()) {
			if(first == 0){
				first = cardSet.get(key);
			}else{
				if(first == 3 && cardSet.get(key) == 2) return true;
			}
		}
		return false;
	}
	private boolean is4kind(){
		Card focusCard = cards.get(0);
		int countSame = 0;
		for(int i=1;i<cards.size();i++){
			Card card = cards.get(i);
			if(focusCard.getRank().ordinal() == card.getRank().ordinal()){
				countSame++;
			}else{
				countSame = 0;
				focusCard = card;
			}
		}
		if(countSame == 4){
			return true;
		}else{
			return false;
		}
	}
	private boolean isFlush(){
		Set<Suit> suits = EnumSet.noneOf(Suit.class);
		for (Card card : cards) {
			suits.add(card.getSuit());
		}
		if(suits.size() == 1){
			return true;
		}else{
			return false;
		}
	}
	private boolean isStraight(){
		Card firstCard = cards.get(0);
		for(int i=1;i<cards.size();i++){
			Card card = cards.get(i);
			if(firstCard.getRank().ordinal()-card.getRank().ordinal() != 1){
				return false;
			}
			firstCard = card;
		}
		return true;
	}
}
