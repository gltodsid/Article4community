package pack.spring.basic.admin;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

public interface AdminService {

	List<Map<String, Object>> selectAllMember(Map<String, Object> map);
	
	int selectListCount(Map<String, Object> searchMap);
	
	int memberEdit(@RequestParam Map<String, Object> pageMap);
}
