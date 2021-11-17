package board.restBoardApi.service;

import java.util.List;
import java.io.File;
import java.util.Iterator;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.util.CollectionUtils;

import board.board.dto.BoardDto;
import board.board.dto.FileDto;
import board.board.mapper.BoardMapper;
import board.common.FileUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RestBoardApiServiceImpl implements RestBoardApiService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	//list
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
	}
	//insert
	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
			Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
			String name;
			while(iterator.hasNext()) {
				name = iterator.next();
				log.debug("file tag name : " + name);
				List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
				for(MultipartFile multipartFile : list) {
					log.debug("start file information");
					log.debug("file name : " + multipartFile.getOriginalFilename());
					log.debug("file size : "+ multipartFile.getSize());
					log.debug("file content type " + multipartFile.getContentType());
					log.debug("end file information. \n");
				}
			}
		}
		boardMapper.insertBoard(board);
		
		List<FileDto> fileList = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(fileList) == false) {
			boardMapper.insertBoardFileList(fileList);
		}
	}
	//view
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		//int i = 10/0;
		boardMapper.updateHitCount(boardIdx);
		
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		List<FileDto> fileList = boardMapper.selectFileList(boardIdx);
		board.setFileList(fileList);
		
		return board;
	}
	// Info File
	@Override
	public FileDto selectFileInfo(int idx, int boardIdx) throws Exception {
		return boardMapper.selectFileInfo(idx, boardIdx);
	}
	//update
	@Override
	public void updateBoard(BoardDto board) throws Exception {
		boardMapper.updateBoard(board);
	}
	//delete
	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		boardMapper.deleteBoard(boardIdx);
	}
	//Delete File
	@Override
	public void deleteBoardFile(int idx, int boardIdx) throws Exception {
		boardMapper.deleteBoardFile(idx, boardIdx);
	}
}
