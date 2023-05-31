package pack.spring.basic.notice;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDAO {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	public int getMaxNum() {

		int cnt = 0;
		if (this.sqlSessionTemplate.selectOne("notice.maxnum") != null) {
			cnt = this.sqlSessionTemplate.selectOne("notice.maxnum");
		}
		return cnt;

	}

	public int insertNotice(Map<String, Object> map) {
		return this.sqlSessionTemplate.insert("notice.insert_notice", map);
	}
	
	public List<Map<String, Object>> selectNotice(Map<String, Object> searchMap){
		System.out.println("키필드"+searchMap.get("keyField").toString());
		return this.sqlSessionTemplate.selectList("notice.select_list", searchMap);
	}
	
	public int selectListCount(Map<String, Object> searchMap){
		return this.sqlSessionTemplate.selectOne("notice.select_listCount", searchMap);
	}
	public int updateReadCount(int numParam) {
		return this.sqlSessionTemplate.update("notice.update_readCount", numParam);
		
	}
	public Map<String, Object> selectRead(int numParam){
		return this.sqlSessionTemplate.selectOne("notice.select_read", numParam);
	}

	public int updateNotice(Map<String, Object>map) {
		return this.sqlSessionTemplate.update("notice.update_notice", map);
	}
	
	public int deleteNotice(int numParam) {
		return this.sqlSessionTemplate.delete("notice.delete_notice", numParam);
	}
}
