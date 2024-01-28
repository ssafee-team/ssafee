package coffee.ssafy.ssafee.common;

import org.apiguardian.api.API;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@API(status = API.Status.INTERNAL)
@ControllerAdvice
public class ExceptionHandler implements ProblemHandling {
}
