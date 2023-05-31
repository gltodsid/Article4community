package pack.spring.basic.tblZipcode;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ZipcodeDAO {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public List<Map<String,Object>>selectAll(String area3){
		return this.sqlSessionTemplate.selectList("zipcode.selectAll", area3);
	}
}
