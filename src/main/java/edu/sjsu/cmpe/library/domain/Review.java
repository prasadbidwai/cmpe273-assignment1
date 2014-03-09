package edu.sjsu.cmpe.library.domain;

import java.util.Random;

public class Review {

	private int id;
	private int rating;
	private String comment;
	
	public Review(){
		Random random = new Random();
		this.id = random.nextInt(100);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
