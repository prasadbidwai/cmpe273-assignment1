package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.*;


public class ReviewsDto extends LinksDto {
	
	private List<Review> reviews = new ArrayList<Review>();

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
}
