package pack.spring.basic.tblBoard;

public class PagingVO {
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

	public PagingVO(int nowPage, int totalRecord, int numPerPage, int pagePerBlock) {
		
	}
}
