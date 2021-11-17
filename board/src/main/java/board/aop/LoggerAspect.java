package board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggerAspect {
	//around : 대상 메소드의 실행 전후 또는 예외 발생 시점에 사용할 수 있는 어노테이션
	//execution은 적용할 메소드를 포인트컷 표현식으로 명시할 때 사용
	@Around("execution(* board..controller.*Controller.*(..)) or execution(* board..service.*Impl.*(..)) or execution(* board..mapper.*Mapper.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();
		if(name.indexOf("Controller") > -1) {
            type = "Controller  \t:  ";
        }
        else if(name.indexOf("Service") > -1) {
            type = "ServiceImpl  \t:  ";
        }
        else if(name.indexOf("Mapper") > -1) {
            type = "Mapper  \t\t:  ";
        }
		//실행되는 메소드의 이름을 이용해 controller등을 구분한 후 실행되는 메소드 이름 출력
		log.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}
	
	/** 
	execution : 가장 대표적이고 강력한 지시자
	execution(void select*(..))
	// 리턴 타입은 void이고, 메소드 이름은 select로 시작하며 파라미터는 0개 이상인 모든 메소드가 호출될 떄
	execution(* board.controller.*())
	// board.controller 패키지 밑 파라미터가 없는 모든 메소드가 호출될 때
	execution(* board.controller.*(..))
	// board.controller 패키지 밑 파라미터가 0개 이상인 모든 메소드가 호출될 때
	execution(* board..select*(*))
	// board 패키지의 모든 하위 패키지에 있는 select로 시작하고 파라미터가 한 개인 모든 메소드가 호출될 때
	execution(* board..select*(*,*))
	// board 패키지의 모든 하위 패키지에 있는 select로 시작하고 파라미터가 두 개인 모든 메소드가 호출될 때
	execution(* board..controller.*Controller.*(..))
	// board 패키지 하위 모든 패키지(..) 중 controller 패키지의 Controller로 끝나는 클래스의 파라미터가 0개 이상인 모든 메소드가 호출될 때
	execution(* board..service.*Impl.*(..))
	// board 패키지 하위 모든 패키지(..) 중 service 패키지의 Impl로 끝나는 클래스의 파라미터가 0개 이상인 모든 메소드가 호출될 때
	execution(* board..mapper.*Mapper.*(..))
	// board 패키지 하위 모든 패키지(..) 중 mapper 패키지의 Mapper로 끝나는 클래스의 파라미터가 0개 이상인 모든 메소드가 호출될 때
	 
	within : 특정 타입에 속하는 메소드를 포인트컷으로 설정
	within(board.service.boardServiceImpl)
	// board.service 패키지 밑에 있는 boardServiceImpl 클래스의 메소드가 호출될 때
	within(board.service.*ServiceImpl)
	// board.service 패키지 밑에 있는 ServiceImpl로 끝나는 클래스의 메소드가 호출될 때 
	
	bean : 스프링의 빈 이름의 패턴으로 포인트컷 설정
	bean(boardServiceImpl)
	// boardServiceImpl이라는 빈의 메소드가 호출될 때
	bean(*ServiceImpl)
	// ServiceImpl로 끝나는 빈의 메소드가 호출될 때
	**/
}
