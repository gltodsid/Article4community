package pack.spring.basic.tblBoard;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public int getMaxNum() {
		
		int cnt=0;
		if(this.sqlSessionTemplate.selectOne("board.maxnum")!=null) {
			cnt=this.sqlSessionTemplate.selectOne("board.maxnum");
		}
		return cnt;
	
	}
	
	public int insertBoard(Map<String, Object>map) {
		return this.sqlSessionTemplate.insert("board.insert_board",map);
	}
	
	
	public List<Map<String, Object>> selectBoard(Map<String, Object> searchMap){
		return this.sqlSessionTemplate.selectList("board.select_list", searchMap);
	}
	
	public int selectListCount(Map<String, Object> searchMap){
		return this.sqlSessionTemplate.selectOne("board.select_listCount", searchMap);
	}
	public int updateReadCount(int numParam) {
		return this.sqlSessionTemplate.update("board.update_readCount", numParam);
		
	}
	public Map<String, Object> selectRead(int numParam){
		return this.sqlSessionTemplate.selectOne("board.select_read", numParam);
	}
	
	public int updateBoard(Map<String, Object>map) {
		return this.sqlSessionTemplate.update("board.update_board", map);
	}
	
	public int deleteBoard(int numParam) {
		return this.sqlSessionTemplate.delete("board.delete_board", numParam);
	}
	
//	public int updateAfterDelete(Map<String, Object> map) {
//		return this.sqlSessionTemplate.update("board.update_after_delete", map);
//	}
	
	public int updateBeforeReply(Map<String, Object> map) {
		return this.sqlSessionTemplate.update("board.update_before_reply", map);
	}
	
	public int insertReply(Map<String, Object>map) {
		return this.sqlSessionTemplate.insert("board.insert_reply",map);
	}
}
