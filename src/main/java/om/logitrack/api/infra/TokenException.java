package om.logitrack.api.infra;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
    public TokenException(String mensagem, Throwable throwable){
        super(mensagem, throwable);
    }
}
