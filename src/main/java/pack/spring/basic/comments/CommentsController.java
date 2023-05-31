package pack.spring.basic.comments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pack.spring.basic.member.MemberService;

@Controller
public class CommentsController {

	@Autowired
	CommentsService commentsService;
	
	@Autowired
	MemberService memberService;
	
	/*
	 * @RequestMapping(value = "/commentsWrite", method = RequestMethod.GET) public
	 * ModelAndView commentsWrite() { ModelAndView mav = new ModelAndView();
	 * 
	 * mav.setViewName("comments/commentsWrite");
	 * 
	 * return mav; }
	 */
	
	@RequestMapping(value = "/comments", method = RequestMethod.POST)
	@ResponseBody
	public int commentsWriteProc(@RequestParam Map<String, Object> map) {
		
		int ref = commentsService.getMaxNum() + 1;
		map.put("ref", ref);
		
		int cnt = commentsService.insertComments(map);
		
		return cnt;
	}
	
	@RequestMapping(value = "/commentsList", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> commentsListPage(@RequestParam Map<String,Object> map) {

		Map<String, Object> searchMap = new HashMap<String, Object>();

		String tableNum = map.get("tableNum").toString();
		String tableType = map.get("tableType").toString();
		
		searchMap.put("tableNum", tableNum);
		searchMap.put("tableType", tableType);
		
		List<Map<String, Object>>vList = commentsService.selectBoard(searchMap);

		return vList;
	}
	
	@RequestMapping(value = "/replyComment", method = RequestMethod.POST)
	@ResponseBody
	public int replyComment(@RequestParam Map<String,Object> map) {
		
		int maxPos = commentsService.selectMaxPos(map);
		
		map.put("maxPos", maxPos);
		
		int cnt = commentsService.updateBeforeReply(map);
		
		int result=0;
		
		//commentsService
		
		result = commentsService.insertCommentsReply(map);
		
		return result;
	}
	
	
}
