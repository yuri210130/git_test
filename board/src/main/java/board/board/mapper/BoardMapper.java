package board.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.board.dto.BoardDto;
import board.board.dto.FileDto;

@Mapper
public interface BoardMapper {
	//list
	List<BoardDto> selectBoardList() throws Exception;
	//fileList
	List<FileDto> selectFileList(int boardIdx) throws Exception;
	//Info file
	FileDto selectFileInfo(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
	//insert
	void insertBoard(BoardDto board) throws Exception;
	//insert file
	void insertBoardFileList(List<FileDto> fileList) throws Exception;
	//delete file
	void deleteBoardFile(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
	//view
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	//hitCnt
	void updateHitCount(int boardIdx) throws Exception;
	//update
	void updateBoard(BoardDto board) throws Exception;
	//delete
	void deleteBoard(int boardIdx) throws Exception;
}
