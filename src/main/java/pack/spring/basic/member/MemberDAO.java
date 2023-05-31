package pack.spring.basic.member;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public int insert(Map<String, Object> map) throws DuplicateKeyException{
		return this.sqlSessionTemplate.insert("member.insert", map);
	}
	
	public Map<String,Object>selectLogin(Map<String, Object> map){
		return this.sqlSessionTemplate.selectOne("member.select_login", map);
	}
	
	public int selectIdCheck(String uId) {
		return this.sqlSessionTemplate.selectOne("member.select_idCheck", uId);
	}
	
	public Map<String, Object> selectMypage(String uId){
		return this.sqlSessionTemplate.selectOne("member.select_myPage", uId);
	}
	
	public int updateModMember(Map<String, Object> map) {
		return this.sqlSessionTemplate.update("member.update_modMember", map);
	}
	
	public int deleteMember(String uId) {
		return this.sqlSessionTemplate.delete("member.delete_member", uId);
	}
	
	public List<Map<String, Object>> selectMyBoard(Map<String, Object> searchMap){
		return this.sqlSessionTemplate.selectList("member.select_myBoard", searchMap);
	}
	
	public int selectMyBoardCount(Map<String, Object> searchMap){
		return this.sqlSessionTemplate.selectOne("member.select_myBoardCount", searchMap);
	}
	
	public int selectPwCheck(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectOne("member.select_pwCheck", map);
	}
	
	public int updateModPw(Map<String, Object>map) {
		return this.sqlSessionTemplate.update("member.update_modPw", map);
	}
}
