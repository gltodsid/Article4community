package pack.spring.basic.tblBoard;

import java.util.List;
import java.util.Map;

public interface BoardService {

	boolean insertBoard(Map<String, Object> map);

	int getMaxNum();
	
	List<Map<String, Object>> selectBoard(Map<String, Object> searchMap);
	
	int selectListCount(Map<String, Object> searchMap);
	
	int updateReadCount(int numParam);
	
	Map<String, Object> selectRead(int numParam);
	
	int updateBoard(Map<String, Object>map);
	
	int deleteBoard(int numParam);
	
	/* int updateAfterDelete(Map<String, Object> map); */
	
	int updateBeforeReply(Map<String, Object> map);
	
	int insertReply(Map<String, Object>map);
}
