package org.softwarecave.springbootplayground.note.web;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.softwarecave.springbootplayground.note.model.StickyNoteLink;
import org.softwarecave.springbootplayground.note.model.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StickyNoteDTO {
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @NotNull
    private Type type;

    private List<StickyNoteLinkDTO> stickyNoteLinks = new ArrayList<>();

    @PastOrPresent
    @NotNull
    private LocalDateTime created;
}
