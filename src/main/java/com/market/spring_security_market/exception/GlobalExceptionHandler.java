package com.market.spring_security_market.exception;

import com.market.spring_security_market.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * este método se lanza cuando ocurre un error genérico*/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerGenericException(HttpServletRequest request, Exception exception){
        ApiError apiError = new ApiError();
        apiError.setBackenMessage(exception.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setMessage("Error interno en el servidor, vuelva a intentarlo");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    /**
     * este método se lanza cuando no se logra hacer el binding del json hacia el objeto java*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException(HttpServletRequest request,
                                                                    MethodArgumentNotValidException exception){
        ApiError apiError = new ApiError();
        apiError.setBackenMessage(exception.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimeStamp(LocalDateTime.now());
        apiError.setMessage("Error en la petición enviada");
        System.out.println(
                "ERROR validaciones de atributos " + exception.getAllErrors().stream().map(each -> each.getDefaultMessage())
                        .collect(Collectors.toList())
        );
        List<String> mensajes = exception.getAllErrors().stream().map(each -> each.getDefaultMessage())
                .collect(Collectors.toList());

        apiError.setMessages(mensajes);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
