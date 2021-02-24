package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.board.Board;
import com.jpabook.jpashop.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board findBoard(Long boardId) {
        return boardRepository.findOne(boardId);
    }


}
