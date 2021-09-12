package com.board;

import com.board.domain.Board;
import com.board.domain.User;
import com.board.domain.enums.BoardType;
import com.board.repository.BoardRepository;
import com.board.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@RestController
@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository) { // 필요한 Bean 을 주입받는다 (생성자를 통해 의존성을 주입)
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
