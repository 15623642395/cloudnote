package org.tedu.cloudnote.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tedu.cloudnote.dao.BookDao;
import org.tedu.cloudnote.entity.Book;
import org.tedu.cloudnote.util.NoteResult;
import org.tedu.cloudnote.util.NoteUtil;

@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService {
	@Resource
	private BookDao bookDao;

	public NoteResult loadUserBooks(String userId) {
		List<Book> list = bookDao.findByUserId(userId);
		NoteResult result = new NoteResult();
		result.setData(list);
		result.setMsg("加载用户笔记本列表成功");
		result.setStatus(0);
		return result;
	}

	public NoteResult addBook(String bookName, String userId) {
		Book book = new Book();
		String bookId = NoteUtil.createId();
		book.setCn_notebook_id(bookId);
		book.setCn_user_id(userId);
		book.setCn_notebook_name(bookName);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		book.setCn_notebook_createtime(time);
		book.setCn_notebook_desc("");
		book.setCn_notebook_type_id("5");// normal
		bookDao.save(book);

		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("创建笔记本成功");
		result.setData(bookId);// 返回新建笔记本ID
		return result;
	}

	public NoteResult renameBooks(String bookId, String bookName) {
		Book book = new Book();
		book.setCn_notebook_id(bookId);
		book.setCn_notebook_name(bookName);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		book.setCn_notebook_createtime(time);
		int count = 0;
		count = bookDao.renameBooks(book);
		NoteResult result = new NoteResult();
		if (count == 1) {
			result.setStatus(0);
			result.setMsg("修改笔记本名称成功");
		} else {
			result.setStatus(1);
			result.setMsg("修改笔记本名称失败");
		}

		return result;
	}
}
