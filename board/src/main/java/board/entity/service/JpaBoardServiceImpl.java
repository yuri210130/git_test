package board.entity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.entity.dto.BoardFileEntity;
import board.entity.repository.JpaBoardRepository;
import board.entity.dto.BoardEntity;
import board.common.FileUtils;

@Service
public class JpaBoardServiceImpl implements JpaBoardService {
	
	@Autowired
	JpaBoardRepository jpaBoardRepository;
	
	@Autowired
	FileUtils fileUtils;
	
	@Override
	public List<BoardEntity> selectBoardList() throws Exception {
		return jpaBoardRepository.findAllByOrderByBoardIdxDesc();
	}
	
	@Override
	public void saveBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest, int hitCnt) throws Exception {
		board.setCreatorId("admin");
		board.setHitCnt(hitCnt);
		List<BoardFileEntity> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
		
		if(CollectionUtils.isEmpty(list) == false) {
			board.setFileList(list);
		}
		jpaBoardRepository.save(board);
	}
	
	@Override
	public BoardEntity selectBoardDetail(int boardIdx) throws Exception {
		Optional<BoardEntity> optional = jpaBoardRepository.findById(boardIdx);
		
		if(optional.isPresent()) {
			BoardEntity board = optional.get();
			board.setHitCnt(board.getHitCnt()+1);
			jpaBoardRepository.save(board);
			
			return board;
		}
		else {
			throw new NullPointerException();
		}
	}
	
	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		jpaBoardRepository.deleteById(boardIdx);
	}
	
	@Override
	public BoardFileEntity selectBoardFileInformation(int idx, int boardIdx) throws Exception {
		BoardFileEntity boardFile = jpaBoardRepository.findBoardFile(idx, boardIdx);
		return boardFile;
	}
	
	@Override
	public void deleteBoardFile(int idx, int boardIdx) throws Exception {
		jpaBoardRepository.deleteBoardFile(idx, boardIdx);
	}
	
}
