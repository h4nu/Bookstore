package fi.haagahelia.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.domain.CategoryRepository;
import fi.haagahelia.bookstore.domain.User;
import fi.haagahelia.bookstore.domain.UserRepository;
import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;



@SpringBootApplication
public class BookstoreApplication {
	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	@Bean
	public CommandLineRunner bookDemo(BookRepository repository, CategoryRepository crepository, UserRepository urepository) {
		return (args) -> {
			log.info("save a couple of categories");
			
			crepository.save(new Category("Fiction"));
			crepository.save(new Category("Satire"));
			crepository.save(new Category("FoobarCategory"));
			// crepository.save(new Category("Thriller"));
			
			// lists categories created
			for (Category category : crepository.findAll()) {
				if(category == null) { 
				log.info("empty db");
				
				} else {
				log.info(category.getName().toString());	
				}
			}
			
			
			log.info("save a couple of books");
			// these generates the books multiple times
			repository.save(new Book("A Farewell to Arms", "Ernest Hemingway", 1929, "1232323-21", 10.00, crepository.findByName("Fiction").get(0)));
			repository.save(new Book("Animal Farm", "George Orwell", 1945, "2212343-5", 15.00, crepository.findByName("Satire").get(0)));
			log.info("fetch all books");
			for (Book book : repository.findAll()) {
				log.info(book.toString());
			}
			// Create users: admin/admin user/user
			
			/*
			User user1 = new User("user", "$2a$10$KkBleYKXVS.U72vsMeAgmeIMyl7Z985YNETMrZicCoLUH4QnYfkNm", "user@foo.bar", "USER");
			User user2 = new User("admin", "$2a$10$7TmD6APcS5O08dkAnf340e.H0JzlN3j8iffXhiSGsq6eKFx6S8BlS", "admin@foo.bar", "ADMIN");
			User user3 = new User("johndoe", "$2a$10$mrFtIoSfeBQE9EPwgoRUYeXBp.CbkX4Xw6OinxKooc3rewtxDhz8C", "john@doe.bar", "USER");
			urepository.save(user1);
			urepository.save(user2);
			urepository.save(user3);
			*/
			// These are already generated once to heroku-postgresql so these needs to be commented out...
			// however, the test will fail if johndoe will not be created manually
			urepository.save(new User("user", "$2a$10$KkBleYKXVS.U72vsMeAgmeIMyl7Z985YNETMrZicCoLUH4QnYfkNm", "user@foo.bar", "USER") );
            urepository.save(new User("admin", "$2a$10$7TmD6APcS5O08dkAnf340e.H0JzlN3j8iffXhiSGsq6eKFx6S8BlS", "admin@foo.bar", "ADMIN"));
            urepository.save(new User("johndoe", "$2a$10$mrFtIoSfeBQE9EPwgoRUYeXBp.CbkX4Xw6OinxKooc3rewtxDhz8C", "john@doe.bar", "USER"));
            log.info("Create users");
            for (User user : urepository.findAll()) {
				log.info(user.getUsername().toString());
			}

		};
	}

}
