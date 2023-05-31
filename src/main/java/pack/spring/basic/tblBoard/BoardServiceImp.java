package pack.spring.basic.tblBoard;

import java.util.List;
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImp implements BoardService{
	

	@Autowired
	BoardDAO boardDao;
	
	@Override
	public boolean insertBoard(Map<String, Object>map) {
		int affectRowcnt;
		try {
			affectRowcnt = this.boardDao.insertBoard(map);
			if(affectRowcnt==1) {
				return true;
			}else {
				return false;
			}
		}catch(DuplicateKeyException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int getMaxNum() {
		return boardDao.getMaxNum();
	}

	@Override
	public List<Map<String, Object>> selectBoard(Map<String, Object> searchMap) {
		return boardDao.selectBoard(searchMap);
	}

	@Override
	public int selectListCount(Map<String, Object> searchMap) {
	
		return boardDao.selectListCount(searchMap);
	}

	@Override
	public int updateReadCount(int numParam) {
		
		return boardDao.updateReadCount(numParam);
	}

	@Override
	public Map<String, Object> selectRead(int numParam) {
		
		return boardDao.selectRead(numParam);
	}

	@Override
	public int updateBoard(Map<String, Object> map) {
		
		return boardDao.updateBoard(map);
	}

	@Override
	public int deleteBoard(int numParam) {
		return boardDao.deleteBoard(numParam);
	}
	
//	@Override
//	public int updateAfterDelete(Map<String, Object> map) {
//		return boardDao.updateAfterDelete(map);
//	}

	@Override
	public int updateBeforeReply(Map<String, Object> map) {
		return boardDao.updateBeforeReply(map);
	}

	@Override
	public int insertReply(Map<String, Object> map) {
		return boardDao.insertReply(map);
	}
	
}
