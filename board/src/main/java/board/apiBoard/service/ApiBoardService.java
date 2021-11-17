package board.apiBoard.service;

import board.board.dto.BoardDto;
import board.board.dto.FileDto;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ApiBoardService {
	//list
	List<BoardDto> selectBoardList() throws Exception;
	//insert
	void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
	//view
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	//Info File
	FileDto selectFileInfo(int idx, int boardIdx) throws Exception;
	//update
	void updateBoard(BoardDto board) throws Exception;
	//delete
	void deleteBoard(int boardIdx) throws Exception;
	//delete File
	void deleteBoardFile(int idx, int boardIdx) throws Exception;
}
