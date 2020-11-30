package fi.haagahelia.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

//JUnit 5 tests with import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
//JUnit 4 tests with import org.junit.Test;
//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;


import fi.haagahelia.bookstore.web.BookController;
import fi.haagahelia.bookstore.web.UserController;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class BookstoreApplicationTests {
	
	@Autowired
	private BookController bookController;
	
	@Autowired
	private UserController userController;

	@Test
	public void contexLoads() throws Exception {
		assertThat(bookController).isNotNull();
		assertThat(userController).isNotNull();
	}

}
