package org.softwarecave.springbootplayground.note;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StickyNoteLinkRepository extends JpaRepository<StickyNoteLink, Long> {
}
