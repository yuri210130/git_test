package board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
// entityscan : backpackages로 지정된 패키지 하위에 jpa엔티티(@entity 어노테이션이 설정된 도메인 클래스들) 검색
@EntityScan(
			// Jsr310JpaConverters 클래스를 등록하면 java 8날짜 및 시간 관련 클래스 사용에 문제가 발생하지 않습니다.
			basePackageClasses = {Jsr310JpaConverters.class},
			basePackages = {("board")}
		)
/** 
 SpringBootApplication은 @SpringBootApplication, @ComponentScan, @EnableAutoConfiguration 세 개의 어노테이션으로 구성 
  그 중 EnableAutoConfiguration은 스프링부트의 자동 구성을 사용할 때 exclude를 이용해 특정 자동구성을 사용하지 않도록 할 수 있다.
 **/
@SpringBootApplication(exclude= {MultipartAutoConfiguration.class})
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}
