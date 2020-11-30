package fi.haagahelia.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

//JUnit 5 tests with import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;

//import org.junit.Test and org.junit.runner.RunWith used with JUnit 4
//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit4.SpringRunner;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;
import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.domain.CategoryRepository;
import fi.haagahelia.bookstore.domain.User;
import fi.haagahelia.bookstore.domain.UserRepository;

//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
@DataJpaTest
public class BookRepositoryTest {
	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);
	
	@Autowired
	//private MockMvc mockMvc;
	private BookRepository bookRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	// BookRepository tests
	
	@Test
    public void findByTitleReturnsAuthor() {
		log.info("Running findByTitleReturnsAuthor");
        List<Book> books = bookRepository.findByTitle("A Farewell to Arms");
        System.out.println("FIND");
        
        assertThat(books).hasSize(1);
		assertThat(books.get(0).getAuthor()).isEqualTo("Ernest Hemingway");
    }
	
	// 
	@Test
    public void findByIsbnReturnsBook() {
    	log.info("Running findByIsbnReturnsBook");
        List<Book> books = bookRepository.findByIsbn("1232323-21");
        
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("A Farewell to Arms");
    }
	
	@Test
    public void createNewBook() {
		log.info("Running createNewBook");
        Book book = new Book("Killing Floor", "Lee Child", 1997, "0-593-04143-7", 15.00, new Category("Thriller"));
        bookRepository.save(book);
        // System.out.println("INSERT");
        assertThat(book.getId()).isNotNull();
    } 
	
	@Test
	public void deleteBook() {
		log.info("Test delete book");
		List<Book> book = bookRepository.findByTitle("A Farewell to Arms");
		
		if(book != null) {
			bookRepository.deleteById(book.get(0).getId());
		}
	}
	
	// CategoryRepository tests
	@Test
	public void findByCategory() {
		log.info("Running findByCategory");
		List<Category> categories = categoryRepository.findByName("Fiction");
		assertThat(categories.get(0).getName()).isEqualTo("Fiction");
	}
	
	@Test
	public void createNewCategory() {
		log.info("test creating a new category");
		Category category = new Category("Science Fiction");
		categoryRepository.save(category);
		assertThat(category.getCategoryid()).isNotNull();
		//categoryRepository.deleteById(category.getCategoryid());
	}
	
	@Test
	public void deleteCategory() {
		log.info("Test deletion of not used category 'FoobarCategory' ");
		List<Category> category = categoryRepository.findByName("FoobarCategory");		
		if(category != null) {
			categoryRepository.deleteById(category.get(0).getCategoryid());
		}
	}

	
	// UserRepository tests
	
	@Test
	public void findByUser() {
		log.info("Running findByUser");
		User users = userRepository.findByUsername("user");
		assertThat(users.getRole()).isEqualTo("USER");
	}	
	
	@Test
	public void createNewUser() {
		log.info("Running createNewUser");
		User user = new User("foobar", "$2a$10$Gdr0sij9l1OW.aqtlrkpC.UDQ91uh9tEL4bdmWhT/ZTixsdx57NL.", "foobar@foo.bar", "USER");
		userRepository.save(user);
		assertThat(user.getId()).isNotNull();
	}
	
	@Test
	public void deleteUser() {
		log.info("delete user test");
		User user = userRepository.findByUsername("johndoe");
		log.info("test finding johndoe");
		if(user != null) {
			userRepository.delete(user);
		}
	}
	

}
