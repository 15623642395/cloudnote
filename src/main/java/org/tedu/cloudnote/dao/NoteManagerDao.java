package org.tedu.cloudnote.dao;

import java.util.List;

import org.tedu.cloudnote.entity.Note;
import org.tedu.cloudnote.entity.User;

public interface NoteManagerDao {
	public User findUserAndBooks1(String userId);
	public User findUserAndBooks(String userId);
	public List<Note> findAllNoteAndUser();
	public Note findNoteAndUser1(String noteId);
	public Note findNoteAndUser(String noteId);
}
