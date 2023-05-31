package pack.spring.basic.tblZipcode;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZipcodeServiceImp implements ZipcodeService{
	
	@Autowired
	ZipcodeDAO zipcodeDao;
	
	@Override
	public List<Map<String, Object>> selectAll(String area3) {
	
		return zipcodeDao.selectAll(area3);
	}

}
