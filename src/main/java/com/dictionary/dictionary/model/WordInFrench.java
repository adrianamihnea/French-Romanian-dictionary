package com.dictionary.dictionary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class WordInFrench {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String wordInFrench;
    @OneToMany(mappedBy = "wordInFrench", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WordInRomanian> translations;
}
