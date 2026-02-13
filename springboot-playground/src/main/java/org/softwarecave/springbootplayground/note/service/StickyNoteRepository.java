package org.softwarecave.springbootplayground.note.service;

import org.softwarecave.springbootplayground.note.model.StickyNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickyNoteRepository extends JpaRepository<StickyNote, Long> {
}
