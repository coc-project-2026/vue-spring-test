package com.kimsoran.app.word;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class WordService {

    private final List<Word> words = new ArrayList<>();
    private Long idCounter = 1L;

    public Word save(Word word, String email) {
        word.setId(idCounter++);
        word.setEmail(email);
        words.add(word);
        return word;
    }

    public PageResponse<Word> getWords(int page, int size) {
        int start = page * size;
        int end = Math.min(start + size, words.size());

        List<Word> content = words.subList(start, end);

        int totalPages =
                (int) Math.ceil((double) words.size() / size);

        return new PageResponse<>(content, totalPages);
    }

    public void delete(Long id) {
        words.removeIf(word -> word.getId().equals(id));
    }

    public Word update(Long id, Word newWord) {
        for (Word word : words) {

            if (word.getId().equals(id)) {

                word.setWord(newWord.getWord());
                word.setMeaning(newWord.getMeaning());

                return word;
            }
        }

        throw new RuntimeException("Word not found");
    }
}
