package board.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import board.entity.dto.BoardEntity;
import board.entity.dto.BoardFileEntity;

// jpa에서 제공하는 curdRepository 상속
public interface JpaBoardRepository extends CrudRepository<BoardEntity, Integer> {
	
	//게시글 번호로 정렬하여 전체 게시글 조회
	List<BoardEntity> findAllByOrderByBoardIdxDesc();
	
	//첨부파일 정보 조회
	@Query("SELECT file FROM BoardFileEntity file WHERE board_idx = :boardIdx AND idx = :idx")
	BoardFileEntity findBoardFile(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM BoardFileEntity file WHERE board_idx = :boardIdx AND idx = :idx")
	void deleteBoardFile(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
}
