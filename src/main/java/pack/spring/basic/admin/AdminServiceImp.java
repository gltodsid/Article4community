package pack.spring.basic.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImp implements AdminService{

	@Autowired
	AdminDAO adminDao;

	@Override
	public List<Map<String, Object>> selectAllMember(Map<String, Object> map) {
		return adminDao.selectAllMember(map);
	}
	
	@Override
	public int selectListCount(Map<String, Object> searchMap) {
	
		return adminDao.selectListCount(searchMap);
	}

	@Override
	public int memberEdit(Map<String, Object> pageMap) {
		return adminDao.memberEdit(pageMap);
	}
}
