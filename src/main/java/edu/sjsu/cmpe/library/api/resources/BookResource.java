package edu.sjsu.cmpe.library.api.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.dto.ReviewsDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
	/** bookRepository instance */
	private final BookRepositoryInterface bookRepository;

	/**
	 * BookResource constructor
	 * 
	 * @param bookRepository
	 *            a BookRepository instance
	 */
	public BookResource(BookRepositoryInterface bookRepository) {
		this.bookRepository = bookRepository;
	}
	/*
    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
	Book book = bookRepository.getBookByISBN(isbn.get());
	BookDto bookResponse = new BookDto(book);
	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),
		"GET"));
	bookResponse.addLink(new LinkDto("update-book",
		"/books/" + book.getIsbn(), "POST"));
	// add more links

	return bookResponse;
    }
	 */
	@POST
	@Timed(name = "create-book")
	public Response createBook(Book request) {
		// Store the new book in the BookRepository so that we can retrieve it.
		Book savedBook = bookRepository.saveBook(request);

		String location = "/books/" + savedBook.getIsbn();
		//BookDto bookResponse = new BookDto(savedBook);
		BookDto bookResponse = new BookDto();
		bookResponse.addLink(new LinkDto("view-book", location, "GET"));
		bookResponse.addLink(new LinkDto("update-book", location, "POST"));
		bookResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
		bookResponse.addLink(new LinkDto("create-review", location+"/reviews", "DELETE"));
		// Add other links if needed

		return Response.status(201).entity(bookResponse).build();
	}

	@GET
	@Path("/{isbn}")
	@Timed(name = "create-book")
	public Response getBook(Book request, @PathParam("isbn") LongParam isbn){
		Book savedBook = bookRepository.getBookByISBN(Long.parseLong(isbn.toString()));
		List<Author> ath = savedBook.getAuthors();
		System.out.println("Author is:"+ath.get(0).getName());

		String location = "/books/" + savedBook.getIsbn();

		BookDto bookResponse = new BookDto(savedBook);

		bookResponse.addLink(new LinkDto("view-book", location, "GET"));
		bookResponse.addLink(new LinkDto("update-book", location, "POST"));
		bookResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
		bookResponse.addLink(new LinkDto("create-review", location+"/reviews", "DELETE"));
		return Response.status(201).entity(bookResponse).build();
	}

	@DELETE
	@Path("/{isbn}")
	@Timed(name = "delete-book")
	public Response deleteBook(@PathParam("isbn") LongParam isbn){
		bookRepository.deleteBookByISBN(Long.parseLong(isbn.toString()));

		LinkDto linkDto = new LinkDto("view-book", "/books", "GET");
		return Response.status(200).entity(linkDto).build();
	}

	@PUT
	@Path("/{isbn}")
	@Timed(name = "update-book")
	public Response updateBook(@QueryParam("status") String status, @PathParam("isbn") LongParam isbn){

		bookRepository.updateBookByISBN(Long.parseLong(isbn.toString()), status);	

		String location = "/books/" + isbn.toString();


		//BookDto bookResponse = new BookDto();
		LinksDto linksDto = new LinksDto();
		linksDto.addLink(new LinkDto("view-book", location, "GET"));

		linksDto.addLink(new LinkDto("update-book", location, "PUT"));
		linksDto.addLink(new LinkDto("delete-book", location, "DELETE"));
		linksDto.addLink(new LinkDto("create-review", location+"/reviews", "POST"));
		if(bookRepository.hasReviews(Long.parseLong(isbn.toString()))){
			linksDto.addLink(new LinkDto("create-review", location+"/reviews", "GET"));
		}

		return Response.status(200).entity(linksDto).build();
	}

	@POST
	@Path("/{isbn}/reviews")
	@Timed(name="create-review")
	public Response createReview(@PathParam("isbn") LongParam isbn, Review review){
		bookRepository.createReview(Long.parseLong(isbn.toString()), review);
		String location = "/books/" + isbn.toString()+"/reviews/"+review.getId();
		
		LinksDto linksDto = new LinksDto();
		linksDto.addLink(new LinkDto("view-review", location, "GET"));
		return Response.status(200).entity(linksDto).build();
	}

	@GET
	@Path("/{isbn}/reviews/{id}")
	@Timed(name="view-review")
	public Response viewReview(@PathParam("isbn") LongParam isbn, @PathParam("id") Integer id){
		
		Review review = bookRepository.viewReview(Long.parseLong(isbn.toString()),id);
		//Book book = bookRepository.getBookByISBN(Long.parseLong(isbn.toString()));
		ReviewDto reviewDto = new ReviewDto(review);
		String location = "/books/" + isbn.toString()+"/reviews/"+ review.getId();
		
		reviewDto.addLink(new LinkDto("view-review", location, "GET"));
		
		return Response.status(200).entity(reviewDto).build();
	}

	@GET
	@Path("/{isbn}/reviews/")
	@Timed(name="view-reviews")
	public Response viewReview(@PathParam("isbn") LongParam isbn){
		List<Review> reviews = bookRepository.getBookByISBN(Long.parseLong(isbn.toString())).getReviews();
		ReviewsDto reviewsDto = new ReviewsDto();
		reviewsDto.setReviews(reviews);
		return Response.status(200).entity(reviewsDto).build();
	}
	
	@GET
	@Path("/{isbn}/author/id")
	@Timed(name="view-authors")
	public Response viewAuthor(@PathParam("isbn") LongParam isbn,@PathParam("id") Integer id){
		List<Author> authors = bookRepository.getBookByISBN(Long.parseLong(isbn.toString())).getAuthors();
		Author myAuthor = null;
		for(Author author: authors){
			if(author.getId() == id){
				myAuthor = author;
				break;	
			}
		}
		AuthorDto authorDto = new AuthorDto(myAuthor);
		authorDto.addLink(new LinkDto("view-author", "/books/"+isbn.toString()+"/authors/"+myAuthor.getId(), "GET"));
		return Response.status(200).entity(authorDto).build();
	}
}	