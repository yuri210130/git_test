package board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor{
	//컨트롤러가 실행되기 전 수행
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("==================== START ====================");
        log.debug(" Request URI \t: " + request.getRequestURI());
        return true;
    }
	//컨트롤러가 실행된 후 수행
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("==================== END ====================");
    }
}
