package com.kimsoran.app.word;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/words")
public class WordController {

    private final WordService service;

    public WordController(WordService service) {
        this.service = service;
    }

    @PostMapping
    public Word add(@RequestBody Word word, Authentication auth) {
        return service.save(word, auth.getName());
    }

    @GetMapping
    public PageResponse<Word> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        return service.getWords(page, size);
    }


    @PutMapping("/{id}")
    public Word update(@PathVariable Long id, @RequestBody Word newWord) {
        return service.update(id, newWord);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
