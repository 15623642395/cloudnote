package org.tedu.cloudnote.service;

import org.tedu.cloudnote.util.NoteResult;

public interface NoteService {
	public NoteResult loadNoteContent(String noteId);
	public NoteResult deleteNotes(String ids);
	public NoteResult loadManagerNote(
		String title,String status,
		String begin,String end);
	public NoteResult viewShareNote(String noteId);
	public NoteResult searchShareNote(String keyword);
	public NoteResult shareNote(String noteId);
	public NoteResult moveNote(String noteId,String bookId);
	public NoteResult deleteNote(String noteId);
	public NoteResult addNote(
		String userId,String bookId,String noteTitle);
	public NoteResult updateNote(
		String noteId,String noteTitle,String noteBody);
	public NoteResult loadDetail(String noteId);
	public NoteResult loadBookNotes(String bookId);
}
