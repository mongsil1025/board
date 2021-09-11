package com.community.board.repository;

import com.community.board.domain.Board;
import com.community.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByUser(User user);
}
