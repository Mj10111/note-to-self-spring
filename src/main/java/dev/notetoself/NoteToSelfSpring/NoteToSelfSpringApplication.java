package dev.notetoself.NoteToSelfSpring;

import com.vaadin.flow.server.Command;
import dev.notetoself.NoteToSelfSpring.model.Note;
import dev.notetoself.NoteToSelfSpring.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class NoteToSelfSpringApplication {

	private static final Logger log = LoggerFactory.getLogger(NoteToSelfSpringApplication.class);
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	public static void main(String[] args) {
		SpringApplication.run(NoteToSelfSpringApplication.class);
	}


	@Bean
	public CommandLineRunner loadData(NoteRepository repository){
		return args -> {
			repository.save(new Note("Test note", LocalDateTime.now().format(dtf) , null));
			repository.save(new Note("Second note for testing", LocalDateTime.now().format(dtf), null));
			repository.save(new Note("Today I added some data to my repository", LocalDateTime.now().format(dtf), null));
			repository.save(new Note("I really hope this works", LocalDateTime.now().format(dtf), null));
			repository.save(new Note("A longer note that is longer than usual so that I can see how long this column turns out because this note is so long that it would be longer than the other notes that are not as long because they are short and because they are short they are not as long as this longer note.", LocalDateTime.now().format(dtf), null));
			repository.save(new Note("", LocalDateTime.now().format(dtf), null));


			log.info("Notes found by finalAdd():");
			log.info("-------------------------------");
			for(Note note: repository.findAll()){
				log.info(note.toString());
			}

			log.info("");
		};
	}

}
