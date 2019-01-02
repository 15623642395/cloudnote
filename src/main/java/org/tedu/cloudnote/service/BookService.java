package org.tedu.cloudnote.service;

import org.tedu.cloudnote.util.NoteResult;

public interface BookService {
	public NoteResult addBook(String bookName, String userId);

	public NoteResult loadUserBooks(String userId);

	public NoteResult renameBooks(String bookId,String bookName);
}
