package com.kimsoran.app.board;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService service;

    public BoardController(BoardService service) {
        this.service = service;
    }

    @PostMapping
    public Board add(@RequestBody Board board, Authentication auth) {
        return service.save(board, auth.getName());
    }

    @GetMapping
    public List<Board> list(Authentication auth) {
        return service.getBoards(auth.getName());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
