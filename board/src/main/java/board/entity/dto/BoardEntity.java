package board.entity.dto;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity			// 해당 클래스가 jpa의 엔티티임을 나타냅니다. 엔티티 클래스는 테이블과 매핑됩니다.
@Table(name="t_jpa_board")
@NoArgsConstructor
@Data
public class BoardEntity {
	
	// id : pk, generatedvalue : 기본키 생성전략(mysql : auto increment, oracle : sequence 생성)
	// nullable=false : not null
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int boardIdx;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String contents;
	
	@Column(nullable = false)
	private int hitCnt = 0;
	
	@Column(nullable = false)
	private String creatorId;
	
	@Column(nullable = false)
	private LocalDateTime createdDate = LocalDateTime.now();
	
	private String updatedId;
	
	private LocalDateTime updatedDate;
	
	// 1:N관계 나타내는 JPA어노테이션. 하나의 게시글은 첨부파일이 없거나 1개 이상을 가질 수 있음
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	// 릴레이션 관계가 있는 테이블의 컬럼 지정
	@JoinColumn(name="board_idx")
	private Collection<BoardFileEntity> fileList;
	
}
