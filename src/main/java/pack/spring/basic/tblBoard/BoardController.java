package pack.spring.basic.tblBoard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import pack.spring.basic.member.MemberService;

@Controller
public class BoardController {
	@Autowired
	BoardService boardService;

	@Autowired
	MemberService memberService;

	private static final String SAVEFOLER = "D:/openAPI/silsp/p04_STS3_Spring_Legacy/Community_Migration/src/main/webapp/WEB-INF/views/fileUpload";
	private static String encType = "UTF-8";
	private static int maxSize = 5 * 1024 * 1024;

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public ModelAndView boardWrite(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		String uId = (String) session.getAttribute("uId");

		Map<String, Object> map = memberService.selectMypage(uId);
		String uName = map.get("uName").toString();

		mav.addObject("uId", uId);
		mav.addObject("uName", uName);
		mav.setViewName("bbs/write");

		return mav;
	}

	@RequestMapping(value = "/writeProc", method = RequestMethod.POST)
	public ModelAndView boardWriteProc(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		int ref = boardService.getMaxNum() + 1;

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
		map.put("uName", multi.getParameter("uName"));
		map.put("subject", multi.getParameter("subject"));
		map.put("content", content);
		map.put("ref", ref);
		map.put("ip", multi.getParameter("ip"));
		map.put("fileName", fileName);
		map.put("fileSize", fileSize);


		boolean bool = boardService.insertBoard(map);

		String msg = "글 등록에 실패했습니다";
		String url = "/write";

		if (bool) {
			msg = "글이 등록되었습니다";
			url = "/list";
		}

		ModelAndView mav = new ModelAndView();

		mav.addObject("url", url);
		mav.addObject("msg", msg);
		mav.setViewName("common/message");

		return mav;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView boardListPage(HttpServletRequest request) {

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

		List<Map<String, Object>>vList = boardService.selectBoard(searchMap);
		System.out.println("리스트사이즈"+vList.size());
		totalRecord = boardService.selectListCount(searchMap);

		
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

		mav.setViewName("bbs/list");

		return mav;
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public ModelAndView boardReadPage(HttpServletRequest request) {

		// 검색어 수신 시작
		int numParam = Integer.parseInt(request.getParameter("num"));
		String keyField = request.getParameter("keyField");
		String keyWord = request.getParameter("keyWord");
		// 검색어 수신 끝
		// 현재 페이지 돌아가기 소스 시작
		String nowPage = request.getParameter("nowPage");
		// 현재 페이지 돌아가기 소스 끝

		boardService.updateReadCount(numParam);

		Map<String,Object>map =boardService.selectRead(numParam);

		String fUnit = "Bytes";
		int fileSize=0;
		if(map.get("fileSize")!=null) {
			fileSize=Integer.valueOf(map.get("fileSize").toString());
		}
		if(fileSize > 1024) {
			fileSize /= 1024;	
			fUnit = "KBytes";
		}
		
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
		
		map.put("tableType", "board");
		
		HttpSession session = request.getSession();
		session.setAttribute("sessionMap", map);

		ModelAndView mav = new ModelAndView();

		mav.addObject("map", map);
		
		mav.setViewName("bbs/read");

		return mav;
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView modifyPage(@RequestParam Map<String, Object> map) {
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("map",map);
		mav.setViewName("bbs/modify");
		
		return mav;
	}
	
	@RequestMapping(value = "/modifyProc", method = RequestMethod.POST)
	public ModelAndView modifyBoard(@RequestParam Map<String, Object> map) {
		
		int cnt=boardService.updateBoard(map);
		
		String msg="DB처리중 오류가 발생했습니다./n문제가 계속되면 관리자에게 연락바랍니다."; 
		String url="/modify";
		
		if(cnt>0) {
			msg="글 수정 완료하였습니다!";
			url="/read?nowPage="+map.get("nowPage")
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
	
	@RequestMapping(value = "/deleteBoard", method = RequestMethod.GET)
	public ModelAndView deleteBoard(@RequestParam Map<String,Object>map) {
		String num=map.get("num").toString();
		
		Map<String, Object> selectMap = boardService.selectRead(Integer.parseInt(num));
		
//		String fileName="";
//		if(selectMap.get("fileName")!=null) {
//			fileName=selectMap.get("fileName").toString();
//			String fileUrl=SAVEFOLER+"/"+fileName;
//			
//			File file = new File(fileUrl);
//			
//			if(file.exists()) {
//				file.delete();
//				
//			}
//		}
		
		int cnt = boardService.deleteBoard(Integer.parseInt(num));
//		int cnt2 = boardService.updateAfterDelete(selectMap);
		
		String msg = "삭제 실패!!";
		String url = "/read?nowPage="+map.get("nowPage")
		+"&num="+map.get("num")+"&keyField="+map.get("keyField")
		+"&keyWord="+map.get("keyWord");
		
		if(cnt>0) {
			msg="삭제 완료!";
			url="/list?nowPage="+map.get("nowPage")
			+"&keyField="+map.get("keyField")
			+"&keyWord="+map.get("keyWord");
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("msg", msg);
		mav.addObject("url", url);
		mav.setViewName("common/message");
		
		return mav;
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView boardReply(@RequestParam Map<String,Object> map, HttpSession session) {
		
		String replyId = (String)session.getAttribute("uId");
		String replyName = (String)memberService.selectMypage(replyId).get("uName");
		
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("replyId", replyId);
		mav.addObject("replyName", replyName);
		mav.setViewName("bbs/reply");
		
		return mav;
	}
	
	@RequestMapping(value = "/replyProc", method = RequestMethod.POST)
	public ModelAndView boardReplyProc(@RequestParam Map<String,Object> map, HttpSession session) {
		
		int upBRCnt = boardService.updateBeforeReply(map);
		
		int repInsCnt = boardService.insertReply(map);
		
		
		String url = "", msg = "";
		
		if(repInsCnt > 0) { 
			url = "/list?nowPage="+map.get("nowPage")
				+"&keyField="+map.get("keyField")
				+"&keyWord="+map.get("keyWord");
			
			msg = "답변글 등록 완료";
		} else {
			url = "javascript:history.back()";
			msg = "답변글 등록에 실패했습니다";
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("msg", msg);
		mav.addObject("url", url);
		mav.setViewName("common/message");
		
		return mav;
	}
	@GetMapping(value = "/downloadFile")
	public void downloadFile(HttpServletResponse response,@RequestParam(value="filename") String filename) {
			
		String fileName = SAVEFOLER + File.separator + filename; // 다운로드할 파일 매개변수명 일치
		File file=new File(fileName);
		
		response.setContentType("text/html;charset=utf-8");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename="+filename);
		
		OutputStream os = null;
		try {
			os = response.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		FileInputStream fis=null;
		try {
			fis=new FileInputStream(file);
			
			FileCopyUtils.copy(fis,os);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		finally {
			if(fis!=null)
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		}
	
	
}
