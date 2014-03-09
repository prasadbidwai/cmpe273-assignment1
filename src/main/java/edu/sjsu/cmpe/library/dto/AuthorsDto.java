package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;
import java.util.List;
import edu.sjsu.cmpe.library.domain.*;



public class AuthorsDto {

	private List<Author> author = new ArrayList<Author>();

	/**
	 * @return the author
	 */
	private List<Author> getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	private void setAuthor(List<Author> author) {
		this.author = author;
	}

    
	
}
