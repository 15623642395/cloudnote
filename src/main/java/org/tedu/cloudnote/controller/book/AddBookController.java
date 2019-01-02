package org.tedu.cloudnote.controller.book;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tedu.cloudnote.service.BookService;
import org.tedu.cloudnote.util.NoteResult;

@Controller
@RequestMapping("/book")
public class AddBookController {
	@Resource
	private BookService bookService;

	/**
	 * 添加笔记本
	 * @param bookName
	 * @param userId
	 * @return
	 */
	@RequestMapping("/add.do")
	@ResponseBody
	public NoteResult execute(String bookName, String userId) {
		NoteResult result = bookService.addBook(bookName, userId);
		return result;
	}

}
