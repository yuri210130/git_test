package board.entity.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.entity.dto.BoardEntity;
import board.entity.dto.BoardFileEntity;

public interface JpaBoardService {

	List<BoardEntity> selectBoardList() throws Exception;

	void saveBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest, int hitCnt) throws Exception;

	BoardEntity selectBoardDetail(int boardIdx) throws Exception;

	void deleteBoard(int boardIdx) throws Exception;

	BoardFileEntity selectBoardFileInformation(int idx, int boardIdx) throws Exception;

	void deleteBoardFile(int idx, int boardIdx) throws Exception;
	
	

}
