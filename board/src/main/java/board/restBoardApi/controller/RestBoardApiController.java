package board.restBoardApi.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import board.board.dto.BoardDto;
import board.board.dto.FileDto;
import board.board.service.BoardService;
//import board.common.FileUtils;
import org.apache.commons.io.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class RestBoardApiController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardService boardService;
	
	//Board List
	@GetMapping(value="/api/board")
	public List<BoardDto> openBoardList() throws Exception {
		
		return boardService.selectBoardList();
	}
	
	//Board Write Form
//	@GetMapping(value="/api/board/write")
//    public ModelAndView openBoardWrite() throws Exception {
//		ModelAndView mv = new ModelAndView("/apiBoard/write");
//		
//		return mv;
//    }
	
	//Board Detail view
	@GetMapping(value="/api/board/{boardIdx}")
	public BoardDto openBoardDetail(@PathVariable int boardIdx) throws Exception {
		
		return boardService.selectBoardDetail(boardIdx);
	}
	
	//TODO 나중에 하나로 합치기
	//Board Insert contents
	@RequestMapping(value="/api/board/write", method=RequestMethod.POST)
	public void insertBoard(@RequestBody BoardDto board) throws Exception {
		
		boardService.insertBoard(board, null);
	}
	
	//Board Update
	@PutMapping(value="/api/board/{boardIdx}")
	public String updateBoard(@RequestBody BoardDto board) throws Exception {
		boardService.updateBoard(board);
		return "redirect:/api/board";
	}
	
	//Board Delete
	@DeleteMapping(value="/api/board/{boardIdx}")
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
		boardService.deleteBoard(boardIdx);
		return "redirect:/api/board";
	}

	//File Information
	//HttpServletRequest는 사용자로부터 들어오는 모든 요청 정보를 담고있다면
	//HttpServletResponse는 사용자에게 전달할 데이터를 담고 있음(전달할 결과값을 만들거나 변경 가능)
//	@GetMapping(value="board/file")
//	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception {
//		FileDto boardFile = boardService.selectFileInfo(idx, boardIdx);
//		if(ObjectUtils.isEmpty(boardFile) == false) {
//			String fileName = boardFile.getOriginalFileName();
//			
//			//조회된 파일의 정보 중 storedFilePath값을 이용해 실제 저장되어 있는 파일을 읽어온 후 byte[]형태로 변환
//			//fileUtils는 org.apache.commons.io패키지의 fileUtils사용
//			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
//			
//			//response의 헤더에 contentType, 크기 및 형태 설정. 파일은 반드시 UTF-8로 인코딩
//			response.setContentType("application/octet-stream");
//			response.setContentLength(files.length);
//			response.setHeader("Content-Disposition", "attachment; fileName=\""+ URLEncoder.encode(fileName, "UTF-8")+"\";");
//			
//			//앞에서 읽어온 파일의 정보의 바이트 배열 데이터를 작성
//			response.getOutputStream().write(files);
//			//response의 버퍼를 정리하고 닫아줌.
//			response.getOutputStream().flush();
//			response.getOutputStream().close();
//		}
//	}
	
	//Delete File
//	@DeleteMapping(value="/api/board/file")
//	public String deleteBoardFile(@RequestParam int idx, @RequestParam int boardIdx) throws Exception {
//		boardService.deleteBoardFile(idx, boardIdx);
//		
//		return "redirect:/api/board/"+boardIdx;
//	}
}
