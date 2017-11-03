package br.com.eye.messaing.interceptor;

import io.prometheus.client.Counter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class RequestCounterInterceptor extends HandlerInterceptorAdapter {

	private static final String REQ_PARAM_TIMING = "timing";

	// @formatter:off
	private static final Counter requestTotal = Counter.build()
			.name( "http_requests_total" )
			.labelNames( "method", "handler", "status" )
			.help( "Http Request Total" ).register();
	// @formatter:on

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute( REQ_PARAM_TIMING, System.currentTimeMillis() );
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
			throws Exception {

		String handlerLabel = handler.toString();

		if (handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			handlerLabel = method.getDeclaringClass().getSimpleName() + "." + method.getName();
		}

		requestTotal.labels( request.getMethod(), handlerLabel, Integer.toString( response.getStatus() ) ).inc();
	}
}
