package pack.spring.basic.member;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImp implements MemberService {

	@Autowired
	MemberDAO memberDao;

	@Override
	public boolean insert(Map<String, Object> map) {
		int affectRowCnt;
		try {
			affectRowCnt = this.memberDao.insert(map);
			if (affectRowCnt == 1) {
				return true;
			} else {
				return false;
			}
		} catch (DuplicateKeyException e) {

			e.printStackTrace();
			return false;
		}

	}

	@Override
	public Map<String, Object> selectLogin(Map<String, Object> map) {

		return memberDao.selectLogin(map);
	}

	@Override
	public int selectIdCheck(String uId) {

		return memberDao.selectIdCheck(uId);
	}

	@Override
	public Map<String, Object> selectMypage(String uId) {

		Map<String, Object> map = memberDao.selectMypage(uId);

		String email = map.get("uEmail").toString();

		return memberDao.selectMypage(uId);
	}

	@Override
	public boolean updateModMember(Map<String, Object> map) {

		int cnt = memberDao.updateModMember(map);

		if (cnt == 1) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public int deleteMember(String uId) {
		
		return memberDao.deleteMember(uId);
	}
	
	@Override
	public List<Map<String, Object>> selectMyBoard(Map<String, Object> searchMap) {
		return memberDao.selectMyBoard(searchMap);
	}
	
	@Override
	public int selectMyBoardCount(Map<String, Object> searchMap) {
		return memberDao.selectMyBoardCount(searchMap);
	}

	@Override
	public int selectPwCheck(Map<String, Object> map) {
		return memberDao.selectPwCheck(map);
	}

	@Override
	public int updateModPw(Map<String, Object> map) {
		return memberDao.updateModPw(map);
	}

}
