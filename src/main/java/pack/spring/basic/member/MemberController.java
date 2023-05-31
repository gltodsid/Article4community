package pack.spring.basic.member;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {

	// 회원약관페이지이동
	@RequestMapping(value = "/agreement", method = RequestMethod.GET)
	public ModelAndView memberAgreement() {
		return new ModelAndView("member/joinAgreement");
	}

	// 메인페이지 이동
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView indexPage() {
		return new ModelAndView("index");
	}

	// 회원가입페이지이동
	@RequestMapping(value = "/member", method = RequestMethod.POST)
	public ModelAndView memberJoin(@RequestParam String vCode) {
		if (vCode == null || vCode.isEmpty()) {
			return new ModelAndView("member/joinAgreement");
		} else {
			return new ModelAndView("member/member");
		}
	}

	@Autowired
	MemberService memberService;

	// 회원가입폼제출
	@RequestMapping(value = "/memberProc", method = RequestMethod.POST)
	public ModelAndView memberJoinPost(@RequestParam Map<String, Object> map,
			@RequestParam(value = "uHobby", required = false) String[] hobby) {

		ModelAndView mav = new ModelAndView();

		System.out.println(map.get("uJob").toString());
		String[] hobbyName = { "인터넷", "여행", "게임", "영화", "운동" };
		char[] hobbyCode = { '0', '0', '0', '0', '0' };
		
		if(hobby!=null) {
			for (int i = 0; i < hobby.length; i++) {
				for (int j = 0; j < hobbyName.length; j++) {
					if (hobby[i].equals(hobbyName[j])) {
						hobbyCode[j] = '1';
					}
				}
			}
		}
		String uHobby = new String(hobbyCode);
		System.out.println("uHobby"+uHobby);
		map.replace("uHobby", uHobby);

		boolean bool = memberService.insert(map);

		String msg = "회원가입 완료!!";
		String url = "/index";
		if (!bool) {
			msg = "회원가입 중 문제가 발생했습니다. 다시 시도해주세요.\n만일 문제가 계속될 경우 고객센터(02-1234-5678)로 연락해주세요.";
			url = "javascript:history.back()";
		}

		mav.addObject("msg", msg);
		mav.addObject("url", url);
		mav.setViewName("common/message");

		return mav;
	}

	// 로그인페이지로 이동
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage() {
		return new ModelAndView("/member/login");
	}

	@RequestMapping(value = "/loginProc", method = RequestMethod.POST)
	public ModelAndView loginMember(@RequestParam Map<String, Object> map, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();

		Map<String, Object> loginMap = memberService.selectLogin(map);

		String msg = "아이디 또는 비밀번호를 확인해주세요";
		String url = "/login";

		if (!(loginMap == null || loginMap.isEmpty())) {

			msg = "어서오세요" + map.get("uId").toString() + " 님";
			url = "/index";

			HttpSession session = request.getSession();
			session.setAttribute("uId", loginMap.get("uId").toString());
			session.setAttribute("uName", loginMap.get("uName").toString());
			session.setAttribute("accLv", loginMap.get("accLv").toString());
		}

		mav.addObject("url", url);
		mav.addObject("msg", msg);
		mav.setViewName("common/message");

		return mav;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request) {

		HttpSession session = request.getSession();
		session.invalidate();

		String msg = "로그아웃 되셨습니다!";
		String url = "/index";

		ModelAndView mav = new ModelAndView();
		mav.addObject("url", url);
		mav.addObject("msg", msg);
		mav.setViewName("common/message");

		return mav;
	}

	@RequestMapping(value = "/idCheck", method = RequestMethod.GET)
	public ModelAndView idCheck(@RequestParam String uId) {

		String result = "";
		String btnName = "";
		String confirmOk = "";

		ModelAndView mav = new ModelAndView();

		if (memberService.selectIdCheck(uId) == 1) {

			result = "이미 존재하는 아이디입니다.";
			btnName = "Id 다시 입력하기";

		} else {

			result = "사용 가능한 아이디입니다.";
			btnName = "Id 사용하기";
			confirmOk = "Y";

		}

		mav.addObject("uId", uId);
		mav.addObject("result", result);
		mav.addObject("btnName", btnName);
		mav.addObject("confirmOk", confirmOk);
		mav.setViewName("member/idCheck");

		return mav;
	}

	@RequestMapping(value = "/myPage", method = RequestMethod.GET)
	public ModelAndView myPage(@RequestParam String gnbParam, HttpServletRequest request) {

		HttpSession session = request.getSession();

		String uId = (String) session.getAttribute("uId");

		ModelAndView mav = new ModelAndView();
		mav.addObject("uId", uId);
		mav.addObject("gnbParam", gnbParam);
		mav.setViewName("member/myPage");

		return mav;
	}
	
	@RequestMapping(value = "/modPwCheck", method = RequestMethod.GET)
	public ModelAndView modPwCheckPage() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("member/modPwCheck");

		return mav;
	}
	
	@RequestMapping(value = "/PwCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modPwCheck(@RequestParam(value="uPw") String uPw,
																	@RequestParam(value="uId") String uId) {

		
		Map<String, Object> map = new HashMap<>();
		
		map.put("uId", uId);
		map.put("uPw", uPw);
		
		int cnt = memberService.selectPwCheck(map);
		
		String msg = "비밀번호가 틀렸습니다";
		String url = "javascript:history.back()";
		String pwChecked = null;

		if(cnt > 0) {
			pwChecked = "checked";
			msg="성공";
			url="/memberMod";
			map.put("pwChecked", pwChecked);
		
		}
		
		map.put("msg", msg);
		map.put("url",url);
		
		return map;
	}
	
	
	
	
	@RequestMapping(value = "/memberMod", method = RequestMethod.POST)
	public ModelAndView modPage(HttpServletRequest request,@RequestParam Map<String,Object> reqMap) {
		String pwChecked=reqMap.get("pwChecked").toString();
		
		if (pwChecked == null || pwChecked.isEmpty()) {
			return new ModelAndView("member/myPage");
		}
		
		HttpSession session = request.getSession();
		
		String uId = (String) session.getAttribute("uId");
		
		Map<String, Object> map = memberService.selectMypage(uId);
		
		String uEmail=map.get("uEmail").toString();
		
		int index = uEmail.indexOf("@");
		
		String uEmail1 = uEmail.substring(0, index);
		String uEmail2 = uEmail.substring(index+1);
		
		String uHobby = map.get("uHobby").toString();
		String[] hobbyCode = uHobby.split("");
		
		
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("uEmail1", uEmail1);
		mav.addObject("uEmail2", uEmail2);
		mav.addObject("hobbyCode", hobbyCode);
		mav.addObject("map", map);
		mav.setViewName("member/memberMod");

		return mav;
	}
	
	
	@RequestMapping(value = "/memberModProc", method = RequestMethod.POST)
	public ModelAndView modMember(@RequestParam Map<String,Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		String uId=(String)session.getAttribute("uId");
		
		map.put("uId", uId);
		
		boolean bool = memberService.updateModMember(map);
		
		String msg="회원정보가 수정되었습니다";
		String url="/index";
		
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
	
	/**
	 * 비밀번호 수정 전 비밀번호 체크페이지
	 * @return
	 */
	@RequestMapping(value = "/modPw", method = RequestMethod.GET)
	public ModelAndView mod() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("member/pwCheck");
		
		return mav;
	}
	
	@RequestMapping(value = "/PwCheck2", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modPwCheck2(@RequestParam(value="uPw") String uPw,
																	@RequestParam(value="uId") String uId) {

		
		Map<String, Object> map = new HashMap<>();
		
		map.put("uId", uId);
		map.put("uPw", uPw);
		
		int cnt = memberService.selectPwCheck(map);
		
		String msg = "비밀번호가 틀렸습니다";
		String url = "javascript:history.back()";
		String pwChecked = null;

		if(cnt > 0) {
			pwChecked = "checked";
			msg="비밀번호 변경 페이지로 이동";
			url="/modPw";
			map.put("pwChecked", pwChecked);
		
		}
		
		map.put("msg", msg);
		map.put("url",url);
		
		return map;
	}
	
	/**
	 * 비밀번호 수정 페이지 이동
	 * @param request
	 * @param reqMap
	 * @return
	 */
	@RequestMapping(value = "/modPw", method = RequestMethod.POST)
	public ModelAndView modPwPage(@RequestParam Map<String,Object> reqMap) {
		String pwChecked=reqMap.get("pwChecked").toString();
		
		if (pwChecked == null || pwChecked.isEmpty()) {
			return new ModelAndView("member/myPage");
		}
		
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("member/modPw");

		return mav;
	}
	
	/**
	 * 비밀번호 수정 처리 컨트롤러
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modPwProc", method = RequestMethod.POST)
	public ModelAndView modPw(@RequestParam Map<String,Object> map, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		String uId=(String)session.getAttribute("uId");
		
		map.put("uId", uId);
		
		int cnt = memberService.updateModPw(map);
		
		String msg="비밀번호 변경 실패!";
		String url="javascript:history.back()";
		
		if (cnt>0) {
			msg = "비밀번호 변경 완료!!";
			url = "/myPage?gnbParam=myPage";
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("msg", msg);
		mav.addObject("url", url);
		mav.setViewName("common/message");
		
		return mav;
	}
	
	@RequestMapping(value = "/memberQuit", method = RequestMethod.GET)
	public ModelAndView deleteMemberPage() {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("member/memberQuit");
		
		return mav;
	}
	
	@RequestMapping(value = "/memberQuit", method = RequestMethod.POST)
	public ModelAndView deleteMember(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		String uId=(String)session.getAttribute("uId");
		
		int cnt = memberService.deleteMember(uId);
		
		String msg="회원탈퇴 완료!!";
		String url="/index";
		
		session.invalidate();
		ModelAndView mav = new ModelAndView();

		mav.addObject("msg", msg);
		mav.addObject("url", url);
		mav.setViewName("common/message");

		return mav;
	}
	
	@RequestMapping(value = "/myBoard", method = RequestMethod.GET)
	public ModelAndView myBoard(HttpServletRequest request) {

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

		HttpSession session = request.getSession();
		String uId = session.getAttribute("uId").toString();
		
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
		searchMap.put("uId", uId);
		searchMap.put("keyWord", keyWord);
		searchMap.put("keyField", keyField);
		searchMap.put("start", start);
		searchMap.put("end", end);

		List<Map<String, Object>>vList = memberService.selectMyBoard(searchMap);
		System.out.println("리스트사이즈"+vList.size());
		totalRecord = memberService.selectMyBoardCount(searchMap);

		
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
			prnType = "전체 게시글";
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

		mav.setViewName("member/myBoard");

		return mav;
	}
	
	
}
