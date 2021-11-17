package board.restBoardApi.dto;

import lombok.Data;

@Data
public class RestFileApiDto {
	private int idx;
	private int boardIdx;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;
}
