package pack.spring.basic.tblZipcode;

import java.util.List;
import java.util.Map;

public interface ZipcodeService {
	
	List<Map<String,Object>>selectAll(String area3);
	
	
}
