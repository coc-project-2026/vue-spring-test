package com.kimsoran.app.board;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    private final List<Board> boards = new ArrayList<>();
    private Long idCounter = 1L;

    public Board save(Board board, String email) {
        board.setId(idCounter++);
        board.setEmail(email);
        boards.add(board);
        return board;
    }

    public List<Board> getBoards(String email) {
        return boards.stream()
                .filter(w -> w.getEmail().equals(email))
                .toList();
    }

    public void delete(Long id) {
        boards.removeIf(w -> w.getId().equals(id));
    }
    
}
