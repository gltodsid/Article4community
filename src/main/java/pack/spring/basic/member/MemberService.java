package pack.spring.basic.member;

import java.util.List;
import java.util.Map;

public interface MemberService {

	boolean insert(Map<String, Object> map);
	
	 Map<String,Object>selectLogin(Map<String, Object> map);
	 
	 int selectIdCheck(String uId);
	 
	 Map<String, Object> selectMypage(String uId);
	 
	 boolean updateModMember(Map<String, Object> map);
	 
	 int deleteMember(String uId);
	 
	 List<Map<String, Object>> selectMyBoard(Map<String, Object> searchMap);
	 
	 int selectMyBoardCount(Map<String, Object> searchMap);
	 
	 int selectPwCheck(Map<String, Object> map);
	 
	 int updateModPw(Map<String, Object>map);
}
