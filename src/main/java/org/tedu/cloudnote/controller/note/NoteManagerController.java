package org.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tedu.cloudnote.service.NoteService;
import org.tedu.cloudnote.util.NoteResult;

@Controller
@RequestMapping("/note")
public class NoteManagerController {
	@Resource
	private NoteService noteService;
	
	@RequestMapping("/load.do")
	@ResponseBody
	public NoteResult showNote(String noteId){
		NoteResult result = 
			noteService.loadNoteContent(noteId);
		return result;
	}
	
	@RequestMapping("/manager.do")
	@ResponseBody
	public NoteResult execute(
		String title,String status,
		String begin,String end){
		NoteResult result = 
			noteService.loadManagerNote(
			title, status, begin, end);
		return result;
	}
	
}
