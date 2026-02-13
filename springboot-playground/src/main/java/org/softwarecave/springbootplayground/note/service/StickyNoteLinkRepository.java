package org.softwarecave.springbootplayground.note.service;

import org.softwarecave.springbootplayground.note.model.StickyNoteLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickyNoteLinkRepository extends JpaRepository<StickyNoteLink, Long> {
}
