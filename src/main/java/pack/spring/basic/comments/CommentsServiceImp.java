package pack.spring.basic.comments;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsServiceImp implements CommentsService{

	@Autowired
	CommentsDAO commentsDao;

	@Override
	public int getMaxNum() {
		
		return commentsDao.getMaxNum();
	}

	@Override
	public int insertComments(Map<String, Object> map) {
		return commentsDao.insertComments(map);
	}

	@Override
	public List<Map<String, Object>> selectBoard(Map<String, Object> searchMap) {
		return commentsDao.selectBoard(searchMap);
	}

	@Override
	public int insertCommentsReply(Map<String, Object> map) {
		return commentsDao.insertCommentsReply(map);
	}

	@Override
	public int updateBeforeReply(Map<String, Object> map) {
		return commentsDao.updateBeforeReply(map);
	}

	@Override
	public int selectMaxPos(Map<String, Object> map) {
		return commentsDao.selectMaxPos(map);
	}
	

}
