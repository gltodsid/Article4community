package pack.spring.basic.comments;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommentsDAO {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	public int getMaxNum() {

		int cnt = 0;
		if (this.sqlSessionTemplate.selectOne("comments.maxnum") != null) {
			cnt = this.sqlSessionTemplate.selectOne("comments.maxnum");
		}
		return cnt;

		
	}
	
	public int insertComments(Map<String, Object>map) {
		return this.sqlSessionTemplate.insert("comments.insert_comments",map);
	}
	
	public List<Map<String, Object>> selectBoard(Map<String, Object> searchMap){
		return this.sqlSessionTemplate.selectList("comments.select_commentsList", searchMap);
	}
	
	public int insertCommentsReply(Map<String, Object>map) {
		return this.sqlSessionTemplate.insert("comments.insert_commentsReply",map);
	} 
	
	public int updateBeforeReply(Map<String, Object> map) {
		return this.sqlSessionTemplate.update("comments.update_before_reply", map);
	}
	
	public int selectMaxPos(Map<String, Object> map) {
		int maxPos = Integer.parseInt(map.get("pos").toString());
		
		if(this.sqlSessionTemplate.selectOne("comments.select_maxPos", map) != null) {
			maxPos = this.sqlSessionTemplate.selectOne("comments.select_maxPos", map);
		}
		
		return maxPos;
	}
}
