package board.entity.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.entity.dto.BoardEntity;
import board.entity.dto.BoardFileEntity;
import board.entity.service.JpaBoardService;

@Controller
public class JpaBoardController {
	
	@Autowired
	private JpaBoardService jpaBoardService;
	
	
	@RequestMapping(value="/jpa/board", method = RequestMethod.GET)
	public ModelAndView openBoardList() throws Exception {
		ModelAndView mv = new ModelAndView("/jpa/list");
		
		List<BoardEntity> list = jpaBoardService.selectBoardList();
		mv.addObject("list", list);
		
		return mv;
		
	}
	
	@RequestMapping(value="/jpa/board/write", method=RequestMethod.GET)
	public String openBoardWrite() throws Exception {
		return "/jpa/write";
	}
	
	@RequestMapping(value="/jpa/board/write", method=RequestMethod.POST)
	public String insertBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		int hitCnt = board.getHitCnt();
        
        jpaBoardService.saveBoard(board, multipartHttpServletRequest, hitCnt);
		return "redirect:/jpa/board";
	}
	
	@RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.GET)
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
		ModelAndView mv = new ModelAndView("/board/jpaBoardDetail");
		
		BoardEntity board = jpaBoardService.selectBoardDetail(boardIdx);
		mv.addObject("board", board);
		
		return mv;
	}
	
	@RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.PUT)
	public String updateBoard(BoardEntity board) throws Exception {
		int hitCnt = board.getHitCnt();
        
        jpaBoardService.saveBoard(board, null, hitCnt + 1);
		return "redirect:/jpa/board";
	}
	
	//@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.DELETE)
	@DeleteMapping(value="/jpa/board/{boardIdx}")
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
		jpaBoardService.deleteBoard(boardIdx);
		return "redirect:/jpa/board";
	}
	
	@RequestMapping(value="/jpa/board/file", method=RequestMethod.GET)
	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception {
		BoardFileEntity boardFile = jpaBoardService.selectBoardFileInformation(idx, boardIdx);
		if(ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	
	@RequestMapping(value="/jpa/board/file", method=RequestMethod.DELETE)
	public String deleteBoardFile(@RequestParam int idx, @RequestParam int boardIdx) throws Exception {
		jpaBoardService.deleteBoardFile(idx, boardIdx);
		
		return "redirect:/jpa/board/"+boardIdx;
	}
	
}
