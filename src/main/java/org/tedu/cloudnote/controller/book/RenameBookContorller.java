package org.tedu.cloudnote.controller.book;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tedu.cloudnote.service.BookService;
import org.tedu.cloudnote.util.NoteResult;

@Controller
@RequestMapping("/book")
public class RenameBookContorller {
	@Resource
	private BookService bookService;

	/**
	 * 重命名笔记本
	 * @param userId
	 * @return
	 */
	@RequestMapping("/renameBook.do")
	@ResponseBody
	public NoteResult execute(String bookId, String bookName) {
		NoteResult result = bookService.renameBooks(bookId, bookName);
		return result;
	}

}
