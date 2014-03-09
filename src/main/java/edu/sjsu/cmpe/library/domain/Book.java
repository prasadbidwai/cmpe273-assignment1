package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.sjsu.cmpe.library.dto.LinkDto;


public class Book {
    private long isbn;
    private String title;
    private String pubdate;
    private String language;
    private int numpage;
    private String status;
    private List<Review> reviews = new ArrayList<Review>();
    public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	List<Author> authors = new ArrayList<Author>();
    private List<LinkDto> authorsLinks = new ArrayList<LinkDto>();
    /**
	 * @return the isbn
	 */
	public long getIsbn() {
		return isbn;
	}
	
	@JsonProperty("authors")
	public List<LinkDto> getAuthorsLinks() {
		for(Author author: authors){
			this.authorsLinks.add(new LinkDto("view-author", "/books/"+this.isbn+"/authors/"+author.getId(), "GET"));
		}
		return authorsLinks;
	}

	@JsonIgnore
	public void setAuthorsLinks(List<LinkDto> authorsLinks) {
		this.authorsLinks = authorsLinks;
	}

	@JsonIgnore
	public List<Author> getAuthors() {
		return authors;
	}
	
	@JsonProperty
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the pubdate
	 */
	public String getPubdate() {
		return pubdate;
	}
	/**
	 * @param pubdate the pubdate to set
	 */
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the numpage
	 */
	public int getNumpage() {
		return numpage;
	}
	/**
	 * @param numpage the numpage to set
	 */
	public void setNumpage(int numpage) {
		this.numpage = numpage;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public void addReview(Review review) {
		// TODO Auto-generated method stub
		reviews.add(review);
	}   
    // add more fields here

}
