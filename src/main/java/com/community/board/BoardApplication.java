package com.community.board;

import com.community.board.domain.Board;
import com.community.board.domain.User;
import com.community.board.domain.enums.BoardType;
import com.community.board.repository.BoardRepository;
import com.community.board.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@RestController
@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

	@GetMapping
	public String HelloWorld() {
		return "Hello World!";
	}

	@Bean
	public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository) {
		return (args) -> {
			User user = userRepository.save(User.builder()
					.name("sunmin")
					.password("test")
					.email("sunminjsm@gmail.com")
					.createdDate(LocalDateTime.now()).build());

			IntStream.rangeClosed(1, 200).forEach(index -> {
				boardRepository.save(Board.builder()
						.title("내 사과를 받아죠~♥♥♥♥")
						.subTitle("헤헤")
						.content("")
						.boardType(BoardType.FREE)
						.createdDate(LocalDateTime.now())
						.user(user).build());
			});

		};
	}
}
