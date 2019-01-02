package org.tedu.cloudnote.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tedu.cloudnote.dao.NoteDao;
import org.tedu.cloudnote.dao.NoteManagerDao;
import org.tedu.cloudnote.dao.ShareDao;
import org.tedu.cloudnote.entity.Note;
import org.tedu.cloudnote.entity.Share;
import org.tedu.cloudnote.util.NoteResult;
import org.tedu.cloudnote.util.NoteUtil;

@Service("noteService")
@Transactional
public class NoteServiceImpl implements NoteService{
	@Resource
	private NoteDao noteDao;
	@Resource
	private ShareDao shareDao;
	
	public NoteResult loadBookNotes(String bookId) {
		List<Note> list = 
			noteDao.findByBookId(bookId);
		NoteResult result = new NoteResult();
		result.setData(list);
		result.setStatus(0);
		result.setMsg("加载笔记列表成功");
		return result;
	}

	public NoteResult loadDetail(String noteId) {
		Note note = noteDao.findById(noteId);
		NoteResult result = new NoteResult();
		result.setData(note);
		result.setStatus(0);
		result.setMsg("加载笔记内容成功");
		return result;
	}

	public NoteResult updateNote(
			String noteId, String noteTitle,
			String noteBody) {
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_note_title(noteTitle);
		note.setCn_note_body(noteBody);
		note.setCn_note_last_modify_time(
			System.currentTimeMillis());
		noteDao.dynamicUpdate(note);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("保存笔记成功");
		return result;
	}

	public NoteResult addNote(
		String userId, String bookId, String noteTitle) {
		Note note = new Note();
		note.setCn_user_id(userId);
		note.setCn_notebook_id(bookId);
		note.setCn_note_title(noteTitle);
		String noteId = NoteUtil.createId();
		note.setCn_note_id(noteId);
		note.setCn_note_body("");
		note.setCn_note_status_id("1");//normal
		note.setCn_note_type_id("1");//normal
		Long time = System.currentTimeMillis();
		note.setCn_note_create_time(time);
		note.setCn_note_last_modify_time(time);
		noteDao.save(note);
		//返回结果
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("创建笔记成功");
		result.setData(noteId);//笔记ID
		return result;
	}

	public NoteResult deleteNote(String noteId) {
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_note_status_id("2");
		noteDao.dynamicUpdate(note);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("删除笔记成功");
		return result;
	}

	public NoteResult moveNote(String noteId, String bookId) {
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_notebook_id(bookId);
		noteDao.dynamicUpdate(note);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("笔记转移成功");
		return result;
	}

	public NoteResult shareNote(String noteId) {
		NoteResult result = new NoteResult();
		//检查是否重复分享
		Share has_share = 
			shareDao.findByNoteId(noteId);
		if(has_share != null){
			result.setStatus(1);
			result.setMsg("该笔记已分享过");
			return result;
		}
		//分享操作
		Share share = new Share();
		String shareId = NoteUtil.createId();
		share.setCn_share_id(shareId);
		share.setCn_note_id(noteId);
		//查询cn_note
		Note note = noteDao.findById(noteId);
		share.setCn_share_title(
			note.getCn_note_title());
		share.setCn_share_body(
			note.getCn_note_body());
		//执行cn_share添加
		shareDao.save(share);
		//返回NoteResult
		result.setStatus(0);
		result.setMsg("分享笔记成功");
		return result;
	}

	public NoteResult searchShareNote(
			String keyword) {
		String title = "%";
		if(keyword!=null&&!"".equals(keyword)){
			title = "%"+keyword+"%";
		}
		List<Share> list = 
			shareDao.findLikeTitle(title);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setData(list);
		result.setMsg("搜索成功");
		return result;
	}

	public NoteResult viewShareNote(String noteId) {
		Share share = 
			shareDao.findByNoteId(noteId);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("加载笔记信息成功");
		result.setData(share);
		return result;
	}

	public NoteResult loadManagerNote(
			String title, String status,
			String begin, String end) {
		Map<String,Object> params = 
			new HashMap<String, Object>();
		//标题条件处理
		if(title!=null && !"".equals(title)){
			String keyword = "%"+title+"%";
			params.put("title", keyword);
		}
		//状态条件处理,选项不为"全部"时追加
		if(status!=null && !"0".equals(status)){
			params.put("status", status);
		}
		//开始日期条件处理
		System.out.println(begin);
		if(begin!=null&&!"".equals(begin)){
			try {
				Long beginTime = 
					NoteUtil.convertDateStringToLong(begin);
				params.put("beginTime", beginTime);
			} catch (Exception e) {
			}
		}
		//结束日期条件处理
		System.out.println(end);
		if(end!=null&&!"".equals(end)){
			try {
				Long endTime = 
					NoteUtil.convertDateStringToLong(end);
				params.put("endTime", endTime);
			} catch (Exception e) {
			}
		}
		//执行dao查询
		List<Note> list = 
			noteDao.findNotes(params);
		//封装成NoteResult格式
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("搜索成功");
		result.setData(list);
		return result;
	}

	public NoteResult deleteNotes(String ids) {
		NoteResult result = new NoteResult();
		if(ids!=null&&!"".equals(ids)){
			String[] noteIds = ids.split(";");
			int rows = noteDao.deleteNotes(noteIds);
			result.setStatus(0);
			result.setMsg("删除了"+rows+"个笔记");
		}else{
			result.setStatus(1);
			result.setMsg("删除失败");
		}
		return result;
	}
	@Resource
	private NoteManagerDao noteManagerDao;
	
	public NoteResult loadNoteContent(String noteId) {
		Note note = noteManagerDao.findNoteAndUser(noteId);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("查询成功");
		result.setData(note);
		return result;
	}

}
