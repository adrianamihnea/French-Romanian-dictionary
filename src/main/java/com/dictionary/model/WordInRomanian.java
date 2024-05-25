package com.dictionary.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({"wordInRomanian"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WordInRomanian {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // Exclude ID from serialization
    private Long id;

    private String wordInRomanian;

    @ManyToOne
    @JoinColumn(name = "word_in_french_id")
    @JsonBackReference
    private WordInFrench wordInFrench;
}
