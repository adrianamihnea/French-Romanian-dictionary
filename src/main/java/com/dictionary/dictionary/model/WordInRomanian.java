package com.dictionary.dictionary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WordInRomanian {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String wordInRomanian;
    @ManyToOne
    @JoinColumn(name = "word_in_french_id")
    private WordInFrench wordInFrench;
}
