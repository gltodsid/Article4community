package pack.spring.basic.notice;

import java.util.List;
import java.util.Map;

public interface NoticeService {
	
	
	int insertNotice(Map<String, Object> map);
	
	List<Map<String, Object>> selectNotice(Map<String, Object> searchMap);
	
	int selectListCount(Map<String, Object> searchMap);
	
	int updateReadCount(int numParam);
	
	Map<String, Object> selectRead(int numParam);
	
	int updateNotice(Map<String, Object>map);
	
	int deleteNotice(int numParam);
}
