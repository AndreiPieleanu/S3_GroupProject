package s3.algorithalliance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3.algorithalliance.domain.reqresp.login.LoginRequest;
import s3.algorithalliance.domain.reqresp.login.LoginResponse;
import s3.algorithalliance.servicelayer.ILoginService;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class LoginController {
    private final ILoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = loginService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }
}
