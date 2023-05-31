package pack.spring.basic.comments;

import java.util.List;
import java.util.Map;

public interface CommentsService {

	int getMaxNum();
	
	int insertComments(Map<String, Object>map);
	
	List<Map<String, Object>> selectBoard(Map<String, Object> searchMap);
	
	int insertCommentsReply(Map<String, Object>map);
	
	int updateBeforeReply(Map<String, Object> map);
	
	int selectMaxPos(Map<String, Object> map);
}
