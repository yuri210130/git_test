package board.board.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class BoardController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardService boardService;
	
	//Board List
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception {
		log.debug("openBoardList");
		ModelAndView mv = new ModelAndView("/board/list");
		
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		
		return mv;
	}
	
	//Board Write Form
	@RequestMapping("/board/openBoardWrite.do")
    public String openBoardWrite() throws Exception {
		
        return "/board/write";
    }
	
	//Board Detail view
	@RequestMapping("/board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception {
		ModelAndView mv = new ModelAndView("/board/view");
		
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board", board);
		
		return mv;
	}
	
	//File Information
	//HttpServletRequest는 사용자로부터 들어오는 모든 요청 정보를 담고있다면
	//HttpServletResponse는 사용자에게 전달할 데이터를 담고 있음(전달할 결과값을 만들거나 변경 가능)
	@RequestMapping("board/downloadBoardFile.do")
	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception {
		FileDto boardFile = boardService.selectFileInfo(idx, boardIdx);
		if(ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			
			//조회된 파일의 정보 중 storedFilePath값을 이용해 실제 저장되어 있는 파일을 읽어온 후 byte[]형태로 변환
			//fileUtils는 org.apache.commons.io패키지의 fileUtils사용
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			//response의 헤더에 contentType, 크기 및 형태 설정. 파일은 반드시 UTF-8로 인코딩
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\""+ URLEncoder.encode(fileName, "UTF-8")+"\";");
			
			//앞에서 읽어온 파일의 정보의 바이트 배열 데이터를 작성
			response.getOutputStream().write(files);
			//response의 버퍼를 정리하고 닫아줌.
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	
	//TODO 나중에 하나로 합치기
	//Board Insert contents
	@RequestMapping("/board/insertBoard.do")
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardService.insertBoard(board, multipartHttpServletRequest);
		return "redirect:/board/openBoardList.do";
	}
	
	//Board Update
	@RequestMapping("/board/updateBoard.do")
	public String updateBoard(BoardDto board) throws Exception {
		boardService.updateBoard(board);
		return "redirect:/board/openBoardList.do";
	}
	
	//Board Delete
	@RequestMapping("/board/deleteBoard.do")
	public String deleteBoard(int boardIdx) throws Exception {
		boardService.deleteBoard(boardIdx);
		return "redirect:/board/openBoardList.do";
	}
	
	//Delete File
	@RequestMapping("/board/deleteBoardFile.do")
	public String deleteBoardFile(@RequestParam int idx, @RequestParam int boardIdx) throws Exception {
		boardService.deleteBoardFile(idx, boardIdx);
		
		return "redirect:/board/openBoardDetail.do?boardIdx="+boardIdx;
	}
}
