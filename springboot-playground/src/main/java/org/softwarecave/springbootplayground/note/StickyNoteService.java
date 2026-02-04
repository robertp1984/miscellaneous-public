package org.softwarecave.springbootplayground.note;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.json.JsonMapper;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class StickyNoteService {

    private final JsonMapper jsonMapper;
    private final StickyNoteRepository stickyNoteRepository;
    private final StickyNoteLinkRepository stickyNoteLinkRepository;

    public StickyNoteService(JsonMapper jsonMapper, StickyNoteRepository stickyNoteRepository,
                             StickyNoteLinkRepository stickyNoteLinkRepository) {
        this.jsonMapper = jsonMapper;
        this.stickyNoteRepository = stickyNoteRepository;
        this.stickyNoteLinkRepository = stickyNoteLinkRepository;
    }

    public Collection<StickyNote> getStickyNotes() {
        return stickyNoteRepository.findAll();
    }

    public StickyNote getStickyNoteById(Long stickyNoteId) throws NoSuchStickyNoteException {
        if (stickyNoteId == null) {
            throw new NoSuchStickyNoteException("No sticky note id provided");
        }
        Optional<StickyNote> stickyNote = stickyNoteRepository.findById(stickyNoteId);
        return stickyNote.orElseThrow(() -> new NoSuchStickyNoteException("No sticky note found for ID " + stickyNoteId));
    }

    public StickyNote addStickyNote(StickyNote stickyNote) {
        if (stickyNote.getId() != null) {
            throw new IllegalArgumentException("Sticky note id already exists");
        }
        boolean linkHasId = stickyNote.getStickyNoteLinks()
                .stream()
                .anyMatch(stickyNoteLink -> stickyNoteLink.getId() != null);
        if (linkHasId) {
            throw new IllegalArgumentException("Sticky note link id already exists");
        }
        return stickyNoteRepository.save(stickyNote);
    }

    public StickyNote updateStickyNote(StickyNote stickyNote) {
        if (stickyNoteRepository.existsById(stickyNote.getId())) {
            return stickyNoteRepository.save(stickyNote);
        } else {
            throw new NoSuchStickyNoteException("No sticky note found for ID " + stickyNote.getId());
        }
    }

    public void deleteStickyNote(Long stickyNoteId) {
        if (stickyNoteId == null) {
            throw new NoSuchStickyNoteException("No sticky note id provided");
        }
        if (stickyNoteRepository.existsById(stickyNoteId)) {
            stickyNoteRepository.deleteById(stickyNoteId);
        } else {
            throw new NoSuchStickyNoteException("No sticky note found for ID " + stickyNoteId);
        }
    }

//    public StickyNote patchSickyNote(Long id, StickyNote stickyNote) {
//        StickyNote originalStickyNote = stickyNotes.get(id);
//        if (originalStickyNote == null) {
//            throw new NoSuchStickyNoteException("No sticky note found for ID " + id);
//        }
//        if (stickyNote.getId() != null && stickyNote.getId().equals(id)) {
//            throw new IllegalArgumentException("Sticky note IDs does not match");
//        }
//
//        // FIXME: this does not work
//        StickyNote updatedStickyNote = jsonMapper.updateValue(stickyNote, originalStickyNote);
//        stickyNotes.put(id, updatedStickyNote);
//
//        return originalStickyNote;
//    }
}
