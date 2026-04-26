package om.logitrack.api.infra;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class TratadorDeErro {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> error400(MethodArgumentNotValidException exception){
        var erros = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<DadosErroSimples> tratarErro404(EntityNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErroSimples(exception.getMessage()));
    }
    @ExceptionHandler(RegraDeNegocio.class)
    public ResponseEntity<DadosErroSimples> erroRegraDeNegocio(RegraDeNegocio regraDeNegocio){
        return ResponseEntity.badRequest().body(new DadosErroSimples(regraDeNegocio.getMessage()));
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<DadosErroSimples> tratarErro401(BadCredentialsException exception){
        log.warn("Tentativa de acesso negada {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new DadosErroSimples("Email ou Senha incorretos"));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<DadosErroSimples> tratarErro500(Exception exception){
        log.warn("Erro interno inesperado", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DadosErroSimples("Error Interno"));
    }
    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError fieldError){
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
    private record DadosErroSimples(String mensagem){}
}
