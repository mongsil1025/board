package com.community.board;

import com.community.board.domain.Board;
import com.community.board.domain.User;
import com.community.board.domain.enums.BoardType;
import com.community.board.repository.BoardRepository;
import com.community.board.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaMappingTest {
    private final String boardTestTitle = "선미니";
    private final String email = "sunminjsm@gmail.com";

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Before
    public void init() {
        User user = userRepository.save(User.builder()
                                            .name("sunmin")
                                            .password("test")
                                            .email(email)
                                            .createdDate(LocalDateTime.now()).build());
        boardRepository.save(Board.builder()
                                  .title(boardTestTitle)
                                  .subTitle("동영이야~")
                                  .content("미아늉")
                                  .boardType(BoardType.FREE)
                                  .createdDate(LocalDateTime.now())
                                  .user(user).build());
    }

    @Test
    public void 제대로_생성_됐는지_테스트() {
        User user = userRepository.findByEmail(email);
        assertThat(user.getName(), is("sunmin"));
        assertThat(user.getPassword(), is("test"));
        assertThat(user.getEmail(), is(email));

        Board board = boardRepository.findByUser(user);
        assertThat(board.getTitle(), is(boardTestTitle));
        assertThat(board.getSubTitle(), is("동영이야~"));
        assertThat(board.getContent(), is("미아늉"));
        assertThat(board.getBoardType(), is(BoardType.FREE));
    }

}
