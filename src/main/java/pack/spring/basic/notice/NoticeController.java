package pack.spring.basic.notice;

import java.io.File;
import java.io.IOException;
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

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import pack.spring.basic.member.MemberService;
import pack.spring.basic.tblBoard.UtilMgr;

@Controller
public class NoticeController {

	@Autowired
	NoticeService noticeService;
	@Autowired
	MemberService memberService;


	private static final String SAVEFOLER = "D:/openAPI/silsp/p04_STS3_Spring_Legacy/Community_Migration/src/main/webapp/WEB-INF/views/noticeUpload";
	private static String encType = "UTF-8";
	private static int maxSize = 5 * 1024 * 1024;


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

	@RequestMapping(value="/noticeList", method=RequestMethod.GET)
	public ModelAndView noticeList(HttpServletRequest request) {
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

		List<Map<String, Object>>vList = noticeService.selectNotice(searchMap);
		System.out.println("리스트사이즈"+vList.size());
		totalRecord = noticeService.selectListCount(searchMap);


	

		totalPage = (int)Math.ceil((double)totalRecord/numPerPage);
		nowBlock = (int)Math.ceil((double)nowPage/pagePerBlock);
		totalBlock = (int)Math.ceil((double)totalPage/pagePerBlock);

		///////////////////////페이징 관련 속성 값 끝///////////////////////////

		int pageStart = (nowBlock - 1 ) * pagePerBlock + 1;
		
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

		mav.setViewName("notice/noticeList");

		return mav;
	}

	@RequestMapping(value = "/noticeWrite", method = RequestMethod.GET)
	public ModelAndView noticeWrite(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		if(accLvCheck(request)!=null)	return accLvCheck(request);

		HttpSession session = request.getSession();
		String uId = (String) session.getAttribute("uId");

		Map<String, Object> map = memberService.selectMypage(uId);
		String uName = map.get("uName").toString();

		mav.addObject("uId", uId);
		mav.addObject("uName", uName);
		mav.setViewName("notice/noticeWrite");

		return mav;
	}
	
	@RequestMapping(value = "/noticeWrite", method = RequestMethod.POST)
	public ModelAndView noticeWriteProc(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		MultipartRequest multi = null;

		int fileSize = 0;
		String fileName = null;

		File file = new File(SAVEFOLER);

		if (!file.exists())
			file.mkdirs();

		try {
			//mutipartFile로 바꿀거같음

			multi= new MultipartRequest(request, SAVEFOLER, maxSize, encType, new DefaultFileRenamePolicy());

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (multi.getFilesystemName("fileName")!=null) {
			fileName = multi.getFilesystemName("fileName");
			fileSize = (int) multi.getFile("fileName").length();
		}
		String content = multi.getParameter("content");

		if (multi.getParameter("contentType").equalsIgnoreCase("TEXT")) {
			// ignoreCase, 대소문자 무시, tExt == TEXT => true
			content = UtilMgr.replace(content, "<", "&lt;");
			// a1, a2, a3
			// 입력값 가정 => ABC<p>가나다</p>
			// UtilMgr.replace( ) 실행 후 content에 저장되는 값 ABC&lt;p>가나다&lt;p>
		}


		map.put("uId", multi.getParameter("uId"));
		map.put("writer", multi.getParameter("writer"));
		map.put("title", multi.getParameter("title"));
		map.put("content", content);
		map.put("fileName", fileName);
		map.put("fileSize", fileSize);


		int cnt = noticeService.insertNotice(map);

		String msg = "공지 등록에 실패했습니다";
		String url = "/noticeWrite";

		if (cnt > 0) {
			msg = "공지가 등록되었습니다";
			url = "/noticeList";
		}

		ModelAndView mav = new ModelAndView();

		mav.addObject("url", url);
		mav.addObject("msg", msg);
		mav.setViewName("common/message");

		return mav;
	}
	
	@RequestMapping(value = "/noticeRead", method = RequestMethod.GET)
	public ModelAndView noticeReadPage(HttpServletRequest request) {

		// 검색어 수신 시작
		int numParam = Integer.parseInt(request.getParameter("num"));
		String keyField = request.getParameter("keyField");
		String keyWord = request.getParameter("keyWord");
		// 검색어 수신 끝
		// 현재 페이지 돌아가기 소스 시작
		String nowPage = request.getParameter("nowPage");
		// 현재 페이지 돌아가기 소스 끝

		noticeService.updateReadCount(numParam);

		Map<String,Object>map =noticeService.selectRead(numParam);

		String fUnit = "Bytes";
		int fileSize=0;
		if(map.get("fileSize")!=null) {
			fileSize=Integer.valueOf(map.get("fileSize").toString());
		}
		if(fileSize > 1024) {
			fileSize /= 1024;	
			fUnit = "KBytes";
		}
	 System.out.println(map.get("readCnt").toString());	
		String listBtnLabel = "";
		if(keyWord.equals("null") || keyWord.equals("")) {
			listBtnLabel = "리스트";
		} else {
			listBtnLabel = "검색목록";
		}

		map.put("fUnit", fUnit);
		map.put("nowPage", nowPage);
		map.put("keyField", keyField);
		map.put("keyWord", keyWord);
		map.put("listBtnLabel", listBtnLabel);
		
		map.put("tableType", "notice");
		
		HttpSession session = request.getSession();
		session.setAttribute("sessionMap", map);

		ModelAndView mav = new ModelAndView();

		mav.addObject("map", map);
		
		mav.setViewName("notice/noticeRead");

		return mav;
	}
	
	
	/**
	 * 공지사항 삭제
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/deleteNotice", method = RequestMethod.GET)
	public ModelAndView deleteNotice(@RequestParam Map<String,Object>map) {
		String num=map.get("num").toString();
		
		
		int cnt = noticeService.deleteNotice(Integer.parseInt(num));
//		int cnt2 = noticeService.updateAfterDelete(selectMap);
		
		String msg = "삭제 실패!!";
		String url = "/noticeRead?nowPage="+map.get("nowPage")
		+"&num="+map.get("num")+"&keyField="+map.get("keyField")
		+"&keyWord="+map.get("keyWord");
		
		if(cnt>0) {
			msg="삭제 완료!";
			url="/noticeList?nowPage="+map.get("nowPage")
			+"&keyField="+map.get("keyField")
			+"&keyWord="+map.get("keyWord");
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("msg", msg);
		mav.addObject("url", url);
		mav.setViewName("common/message");
		
		return mav;
	}
	
	
	/**
	 * 수정페이지 이동
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/noticeModify", method = RequestMethod.GET)
	public ModelAndView modifyPage(@RequestParam Map<String, Object> map) {
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("map",map);
		mav.setViewName("notice/noticeModify");
		
		return mav;
	}
	
	
	/**
	 * 수정 처리
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/noticeModifyProc", method = RequestMethod.POST)
	public ModelAndView modifyNotice(@RequestParam Map<String, Object> map) {
		
		int cnt=noticeService.updateNotice(map);
		
		String msg="DB처리중 오류가 발생했습니다./n문제가 계속되면 관리자에게 연락바랍니다."; 
		String url="/noticeModify";
		
		if(cnt>0) {
			msg="글 수정 완료하였습니다!";
			url="/noticeRead?nowPage="+map.get("nowPage")
				+"&num="+map.get("num")+"&keyField="+map.get("keyField")
				+"&keyWord="+map.get("keyWord");
		}
		
//		String url = "/bbs/read.jsp?nowPage="+nowPage;
//		 url += "&num="+upBean.getNum();
//		 url += "&keyField="+keyField;
//		 url += "&keyWord="+keyWord;
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("msg", msg);
		mav.addObject("url", url);
		mav.setViewName("common/message");
		
		
		return mav;
	}
}
