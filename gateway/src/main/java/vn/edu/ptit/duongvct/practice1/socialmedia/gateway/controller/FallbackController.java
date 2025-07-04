package vn.edu.ptit.duongvct.practice1.socialmedia.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class FallbackController {
    @GetMapping("/fallback/storage")
    public ResponseEntity<List<String>> storageFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Storage service is unavailable, please try after sometime"));
    }
}
