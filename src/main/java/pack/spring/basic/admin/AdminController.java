package pack.spring.basic.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pack.spring.basic.member.MemberService;

@Controller
public class AdminController {

	@Autowired
	AdminService adminService;
	@Autowired
	MemberService memberService;
	
	private ModelAndView accLvCheck(HttpServletRequest res) {
		
		HttpSession session=res.getSession();
		String accLv="";
		if(session.getAttribute("accLv")!=null) {
			accLv=session.getAttribute("accLv").toString();
		}
		if(accLv.equals("2")||accLv.equals("1")) {
			return null;
		}
		ModelAndView mav=new ModelAndView();
		mav.addObject("msg","관리자만 들어오실수 있습니다");
		mav.addObject("url","/index");
		mav.setViewName("common/message");
		return mav;
		
	}
	
	@RequestMapping(value = "/adminPage", method = RequestMethod.GET)
	public ModelAndView adminPage(@RequestParam String gnbParam, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		if(accLvCheck(request)!=null)	return accLvCheck(request);
		
		HttpSession session = request.getSession();

		String uId = (String) session.getAttribute("uId");

		
		mav.addObject("uId", uId);
		mav.addObject("gnbParam", gnbParam);
		mav.setViewName("admin/adminPage");

		return mav;
	}
	
	@RequestMapping(value = "/memberList", method = RequestMethod.GET)
	public ModelAndView boardListPage(HttpServletRequest request) {
		
		if(accLvCheck(request)!=null)	return accLvCheck(request);
		
		///////////////////////페이징 관련 속성 값 시작///////////////////////////
		//페이징(Paging) = 페이지 나누기를 의미함
		int totalRecord = 0;        // 전체 데이터 수(DB에 저장된 row 개수)
		int numPerPage = 5;    // 페이지당 출력하는 데이터 수(=게시글 숫자)
		int pagePerBlock = 5;   // 블럭당 표시되는 페이지 수의 개수
		int totalPage = 0;           // 전체 페이지 수
		int totalBlock = 0;          // 전체 블록수

		/*  페이징 변수값의 이해 
		totalRecord=> 200     전체레코드
		numPerPage => 10
		pagePerBlock => 5
		totalPage => 20
		totalBlock => 4  (20/5 => 4)
		 */

		int nowPage = 1;          // 현재 (사용자가 보고 있는) 페이지 번호
		int nowBlock = 1;         // 현재 (사용자가 보고 있는) 블럭

		int start = 0;     // DB에서 데이터를 불러올 때 시작하는 인덱스 번호
		int end = 5;     // 시작하는 인덱스 번호부터 반환하는(=출력하는) 데이터 개수 
		// select * from T/N where... order by ... limit 5, 5;
		// 데이터가 6개   1~5
		//                  인덱스번호 5이므로 6번 자료를 의미  5개

		//게시판 검색 관련소스
		String keyWord = ""; // 
		String keyField = ""; // DB의 컬럼명
		if (request.getParameter("keyWord") != null) {
			keyField = request.getParameter("keyField");
			keyWord = request.getParameter("keyWord");
		}

		System.out.println("keyWord = " + keyWord + ", keyField = " + keyField);

		if (request.getParameter("nowPage") != null) {
			nowPage = Integer.parseInt(request.getParameter("nowPage"));
			start = (nowPage * numPerPage) - numPerPage;   // 2 페이지라면 start 5
			end = numPerPage;                               //  2 페이지라고 하더라도 end 5
		}

		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("keyWord", keyWord);
		searchMap.put("keyField", keyField);
		searchMap.put("start", start);
		searchMap.put("end", end);

		List<Map<String, Object>>vList = adminService.selectAllMember(searchMap);
		System.out.println("리스트사이즈"+vList.size());
		totalRecord = adminService.selectListCount(searchMap);

		
		/*
		select * from tblBoard order by num desc      limit 10, 10;
		데이터가 100개   =>   num :  100  99   98    97 ... 91 |  90     ....  2  1   
		         start, end :   0    1    2     3....   9      10
		페이지당 출력할 데이터 수 10개
		현재 페이지 1페이지라면    => 1페이지의 출력결과   100 ~ 91
		2페이지(= nowPage가 2라는 의미)   90~81
		3페이지    80~71
		 */

		/* totalRecord = bMgr.getTotalCount(keyField, keyWord); */

		//전체 데이터 수 반환

		totalPage = (int)Math.ceil((double)totalRecord/numPerPage);
		nowBlock = (int)Math.ceil((double)nowPage/pagePerBlock);
		totalBlock = (int)Math.ceil((double)totalPage/pagePerBlock);

		///////////////////////페이징 관련 속성 값 끝///////////////////////////

		int pageStart = (nowBlock - 1 ) * pagePerBlock + 1;
		// 26개 자료기준
		// 현재 기준 numPerPage : 5;    // 페이지당 출력 데이터 수
		//            pagePerBlock : 5;  //  블럭당 페이지 수
		//            nowBlock : 현재블럭
		//            totalBlock : 전체블럭
		//  -------------------------------------------------
		//            totalRecord : 26    totalPage : 6
		// 적용결과  nowBlock : 1  =>   pageStart : 1   pageEnd : 5
		//            nowBlock : 2  =>   pageStart : 6   pageEnd : 6( = totalPage)
		//
		int pageEnd = (nowBlock < totalBlock) ? pageStart + pagePerBlock - 1 :  totalPage;

		String prnType = "";
		if (keyWord.equals("null") || keyWord.equals("")) {
			prnType = "전체 회원목록";
		} else {
			prnType = "검색 결과";
		}

		
		ModelAndView mav = new ModelAndView();

		mav.addObject("prnType", prnType);
		mav.addObject("totalRecord", totalRecord);
		mav.addObject("nowPage", nowPage);
		mav.addObject("totalPage", totalPage);
		mav.addObject("keyWord", keyWord);
		mav.addObject("keyField", keyField);
		mav.addObject("pageStart", pageStart);
		mav.addObject("pageEnd", pageEnd);
		mav.addObject("totalBlock", totalBlock);
		mav.addObject("nowBlock", nowBlock);

		mav.addObject("vList", vList);

		mav.setViewName("admin/list");

		return mav;
	}
	
	@RequestMapping(value = "/memberDetail", method = RequestMethod.GET)
	public ModelAndView modPage(@RequestParam Map<String, Object> pageMap,HttpServletRequest request) {
		if(accLvCheck(request)!=null)	return accLvCheck(request);
		
		
		String uId = pageMap.get("uId").toString();
		
		Map<String, Object> memberMap = memberService.selectMypage(uId);
		
		/*
		 * String uEmail=memberMap.get("uEmail").toString();
		 * 
		 * int index = uEmail.indexOf("@");
		 * 
		 * String uEmail1 = uEmail.substring(0, index); String uEmail2 =
		 * uEmail.substring(index+1);
		 */
		
		String uHobby = "";
		String[] hobbyCode= {"0","0","0","0","0"};
		if(memberMap.get("uHobby")!=null) {
				uHobby=memberMap.get("uHobby").toString();
				hobbyCode = uHobby.split("");
		}
		
		String[] hobbyName = { "인터넷", "여행", "게임", "영화", "운동" };
		
		String hobby="";
		for(int i=0; i<hobbyCode.length;i++) {
			if(hobbyCode[i].equals("1")) {
				if(hobby.length() > 0) hobby += " / ";
				hobby+=hobbyName[i];
			}
		}
		
		
		
		ModelAndView mav = new ModelAndView();
		
//		mav.addObject("uEmail1", uEmail1);
//		mav.addObject("uEmail2", uEmail2);
//		mav.addObject("hobbyCode", hobbyCode);
		mav.addObject("hobby", hobby);	
		mav.addObject("memberMap", memberMap); 	//멤버 상세정보
		mav.addObject("pageMap", pageMap);	//페이지 정보
		mav.setViewName("admin/memberDetail");

		return mav;
	}
	
	@RequestMapping(value = "/memberEdit", method = RequestMethod.GET)
	public ModelAndView memberEdit(@RequestParam Map<String, Object> pageMap,HttpServletRequest request) {
		if(accLvCheck(request)!=null)	return accLvCheck(request);
		
		
//		HttpSession session = request.getSession();
//		
//		String uId = (String) session.getAttribute("uId");
//		
//		Map<String, Object> map = memberService.selectMypage(uId);
		
		String uId = pageMap.get("uId").toString();
		
		Map<String, Object> memberMap = memberService.selectMypage(uId);
			
		
		String uEmail=memberMap.get("uEmail").toString();
		
		int index = uEmail.indexOf("@");
		
		String uEmail1 = uEmail.substring(0, index);
		String uEmail2 = uEmail.substring(index+1);
		
		String uHobby = "";
		String[] hobbyCode= {"0","0","0","0","0"};
		if(memberMap.get("uHobby")!=null) {
			uHobby = memberMap.get("uHobby").toString();
			hobbyCode = uHobby.split("");
		}
		
//		String uHobby = memberMap.get("uHobby").toString();
//		
		
		
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("uEmail1", uEmail1);
		mav.addObject("uEmail2", uEmail2);
		mav.addObject("hobbyCode", hobbyCode);
		mav.addObject("memberMap", memberMap);
		mav.addObject("pageMap", pageMap);
		mav.setViewName("admin/memberEdit");

		return mav;
	}

	@RequestMapping(value = "/memberEdit", method = RequestMethod.POST)
	public ModelAndView memberEditProc(@RequestParam Map<String,Object> map,HttpServletRequest request) {
		
		if(accLvCheck(request)!=null)	return accLvCheck(request);
		
		boolean bool = memberService.updateModMember(map);
		
		String msg="회원정보가 수정되었습니다";
		String url="/memberList";
		
		if (!bool) {
			msg = "회원정보수정 중 문제가 발생했습니다. 다시 시도해주세요.\n만일 문제가 계속될 경우 고객센터(02-1234-5678)로 연락해주세요.";
			url = "javascript:history.back()";
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("msg", msg);
		mav.addObject("url", url);
		mav.setViewName("common/message");
		
		return mav;
	}

	@RequestMapping(value = "/adminQuit", method = RequestMethod.POST)
	public ModelAndView memberQuit(@RequestParam Map<String,Object> map,HttpServletRequest request) {

		
		if(accLvCheck(request)!=null)	return accLvCheck(request);
		
		String uId = map.get("uId").toString();
		
		int cnt = memberService.deleteMember(uId);
		
		String msg="회원탈퇴 완료!!";
		String url="/memberList";
		
		ModelAndView mav = new ModelAndView();

		mav.addObject("msg", msg);
		mav.addObject("url", url);
		mav.setViewName("common/message");

		return mav;
	}
}
