package org.tedu.cloudnote.dao;

import java.util.List;

import org.tedu.cloudnote.entity.Share;

public interface ShareDao {
	public List<Share> findLikeTitle(String title);
	public Share findByNoteId(String noteId);
	public void save(Share share);
}
