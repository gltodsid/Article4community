package pack.spring.basic.notice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class NoticeServiceImp implements NoticeService{

	@Autowired
	NoticeDAO noticeDao;
	
	@Override
	public int insertNotice(Map<String, Object> map) {
		return noticeDao.insertNotice(map);
	}

	@Override
	public List<Map<String, Object>> selectNotice(Map<String, Object> searchMap) {
		return noticeDao.selectNotice(searchMap);
	}

	@Override
	public int selectListCount(Map<String, Object> searchMap) {
		return noticeDao.selectListCount(searchMap);
	}

	@Override
	public int updateReadCount(int numParam) {
		return noticeDao.updateReadCount(numParam);
	}

	@Override
	public Map<String, Object> selectRead(int numParam) {
		return noticeDao.selectRead(numParam);
	}

	@Override
	public int updateNotice(Map<String, Object> map) {
		return noticeDao.updateNotice(map);
	}

	@Override
	public int deleteNotice(int numParam) {
		return noticeDao.deleteNotice(numParam);
	}

}
