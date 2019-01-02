package org.tedu.cloudnote.dao;

import java.util.List;
import java.util.Map;

import org.tedu.cloudnote.entity.Note;

public interface NoteDao {
	public int deleteNotes(String[] ids);
	public int dynamicUpdate(Note note);
	public List<Note> findNotes(
		Map<String, Object> params);
//	public int updateBookId(Note note);
//	public int updateStatus(String noteId);
	public void save(Note note);
//	public int update(Note note);
	public Note findById(String noteId);
	public List<Note> findByBookId(String bookId);
}
