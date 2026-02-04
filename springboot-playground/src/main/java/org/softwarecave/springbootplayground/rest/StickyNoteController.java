package org.softwarecave.springbootplayground.rest;

import org.softwarecave.springbootplayground.note.NoSuchStickyNoteException;
import org.softwarecave.springbootplayground.note.StickyNote;
import org.softwarecave.springbootplayground.note.StickyNoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/v1/stickyNotes")
public class StickyNoteController {

    private final StickyNoteService stickyNoteService;

    public StickyNoteController(StickyNoteService stickyNoteService) {
        this.stickyNoteService = stickyNoteService;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<StickyNote> getAllStickyNotes() {
        return stickyNoteService.getStickyNotes();
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StickyNote getStickyNotes(@PathVariable("id") Long id) {
        return stickyNoteService.getStickyNoteById(id);
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public StickyNote addStickyNote(@RequestBody StickyNote stickyNote) {
        return stickyNoteService.addStickyNote(stickyNote);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StickyNote addStickyNote(@PathVariable("id") Long id, @RequestBody StickyNote stickyNote) {
        if (stickyNote.getId() != null && !stickyNote.getId().equals(id)) {
            throw new IllegalArgumentException("StickyNote IDs are not equal");
        }
        stickyNote.setId(id);
        return stickyNoteService.updateStickyNote(stickyNote);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStickyNote(@PathVariable("id") Long id) {
        stickyNoteService.deleteStickyNote(id);
    }

//    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public StickyNote patchStickyNote(@PathVariable("id") Long id, @RequestBody StickyNote stickyNote) {
//        if (stickyNote.getId() != null && !stickyNote.getId().equals(id)) {
//            throw new IllegalArgumentException("StickyNote IDs are not equal");
//        }
//        return stickyNoteService.patchSickyNote(id, stickyNote);
//    }


    @ExceptionHandler
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNoSuchStickyNoteException(NoSuchStickyNoteException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
