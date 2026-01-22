package com.project.fitness.controller;

import com.project.fitness.dto.ActivityRequest;
import com.project.fitness.dto.ActivityResponse;
import com.project.fitness.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;
//    @PostMapping
//    public ResponseEntity<ActivityResponse>  trackActivity(@RequestBody ActivityRequest request){
//        return ResponseEntity.ok(activityService.trackActivity(request)) ;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ActivityResponse>> getUserActivity(@RequestHeader(value = "X-User-ID") String userId) {
//
//        return ResponseEntity.ok(activityService.getUserActivities(userId));
//    }

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(
            @RequestBody ActivityRequest request
    ) {
        String userId = org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(
                activityService.trackActivity(userId, request)
        );
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivity() {

        String userId = org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(
                activityService.getUserActivities(userId)
        );
    }



}
