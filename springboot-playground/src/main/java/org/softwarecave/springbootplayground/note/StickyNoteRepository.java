package org.softwarecave.springbootplayground.note;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StickyNoteRepository extends JpaRepository<StickyNote, Long> {
}
