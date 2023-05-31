package pack.spring.basic.tblZipcode;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ZipcodeController {
	
	@Autowired
	ZipcodeService zipcodeService;
	
	@RequestMapping(value = "/zipCheck",method = RequestMethod.GET)
	public ModelAndView zipcodeView() {
		
		ModelAndView mav=new ModelAndView();
		mav.setViewName("member/zipCheck");
		
		return mav;
	}
	
	
	@RequestMapping(value = "/zipCheck",method = RequestMethod.POST)
	public ModelAndView selectZipcode(@RequestParam String area3){
		
		ModelAndView mav=new ModelAndView();
		List<Map<String, Object>>list= zipcodeService.selectAll(area3);
		
		mav.addObject("list",list);
		mav.addObject("area3",area3);
		mav.setViewName("member/zipCheck");
		
		return mav;
	}
	
}
