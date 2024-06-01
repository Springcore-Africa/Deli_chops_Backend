package africa.springCore.delichopsbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/health-check")
@RestController
@RequiredArgsConstructor
public class HealthCheck {

    @GetMapping("")
    public ResponseEntity<String> checkHealth(){
        return ResponseEntity.ok("I'm up and running!!!");
    }
}
