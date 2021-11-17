package board.apiBoard.dto;

import lombok.Data;

@Data
public class ApiFileDto {
	private int idx;
	private int boardIdx;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;
}
