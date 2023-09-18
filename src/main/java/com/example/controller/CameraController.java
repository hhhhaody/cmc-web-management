package com.example.controller;
import com.example.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/camera")
public class CameraController {

    @Autowired
    private CameraService cameraService;

    @GetMapping("/updateVideoInfo")
    public ResponseEntity<String> getCameraList() {
        String cameraListJson = cameraService.getCameraList();
        return ResponseEntity.ok(cameraListJson);
    }

    @GetMapping("/videoStream")
    public ResponseEntity<String> getVideoStream(@RequestParam String indexCode) {
        String videoStreamURL = cameraService.getVideoStream(indexCode);
        return ResponseEntity.ok(videoStreamURL);
    }
}



