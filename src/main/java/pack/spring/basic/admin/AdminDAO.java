package pack.spring.basic.admin;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Repository
public class AdminDAO {

	@Autowired
	SqlSessionTemplate sessionTemplate;
	
	public List<Map<String, Object>> selectAllMember(Map<String, Object> map){
		return this.sessionTemplate.selectList("admin.select_allMember", map);
	}
	
	public int selectListCount(Map<String, Object> searchMap){
		return this.sessionTemplate.selectOne("admin.select_listCount", searchMap);
	}
	
	public int memberEdit(@RequestParam Map<String, Object> pageMap) {
		return this.sessionTemplate.update("admin.update_modMember", pageMap);
	}
}
