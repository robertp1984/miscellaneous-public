package org.softwarecave.springbootplayground.note.web.converter;

import org.softwarecave.springbootplayground.note.model.StickyNote;
import org.softwarecave.springbootplayground.note.web.StickyNoteDTO;
import org.softwarecave.springbootplayground.note.web.StickyNoteLinkDTO;

import java.util.ArrayList;
import java.util.List;

public class StickyNoteConverter {
    public StickyNoteDTO convertToDTO(StickyNote note) {
        StickyNoteLinkConverter linkConverter = new StickyNoteLinkConverter(note);
        List<StickyNoteLinkDTO> links = new ArrayList<>();
        if (note.getStickyNoteLinks() != null) {
            note.getStickyNoteLinks().stream().map(linkConverter::convertToDTO).forEach(links::add);
        }

        return new StickyNoteDTO(note.getId(), note.getTitle(), note.getBody(), note.getType(), links, note.getCreated());
    }

    public StickyNote convertToEntity(StickyNoteDTO noteDTO) {
        StickyNote note = new StickyNote(noteDTO.getId(), noteDTO.getTitle(), noteDTO.getBody(), noteDTO.getType(), new ArrayList<>(), noteDTO.getCreated());

        StickyNoteLinkConverter linkConverter = new StickyNoteLinkConverter(note);
        if (noteDTO.getStickyNoteLinks() != null) {
            noteDTO.getStickyNoteLinks().stream().map(linkConverter::convertToEntity).forEach(e -> note.getStickyNoteLinks().add(e));
        }
        return note;
    }
}
