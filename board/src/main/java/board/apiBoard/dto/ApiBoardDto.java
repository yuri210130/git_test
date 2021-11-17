package board.apiBoard.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ApiBoardDto {
	//게시글 등록
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId;
	private LocalDateTime createdDate;
	private String updaterId;
	private LocalDateTime updateDate;
	private List<ApiFileDto> fileList;
	
}
