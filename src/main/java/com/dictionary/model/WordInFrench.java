package com.dictionary.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
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
@JsonPropertyOrder({"wordInFrench", "translations"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WordInFrench {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // Exclude ID from serialization
    private Long id;
    private String wordInFrench;
    @OneToMany(mappedBy = "wordInFrench", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WordInRomanian> translations;
}
