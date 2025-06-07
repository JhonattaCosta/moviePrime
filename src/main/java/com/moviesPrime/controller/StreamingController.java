package com.moviesPrime.controller;

import com.moviesPrime.controller.request.StreamingRequest;
import com.moviesPrime.controller.response.StreamingResponse;
import com.moviesPrime.entity.Streaming;
import com.moviesPrime.mapper.StreamingMapper;
import com.moviesPrime.service.StreamingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/moviesprime/streaming")
@RequiredArgsConstructor
public class StreamingController {
    private final StreamingService streamingService;

    @GetMapping
    public ResponseEntity<List<StreamingResponse>> getAllStream(){
        return ResponseEntity.ok(streamingService.findAll()
                .stream()
                .map(StreamingMapper::toStreamingResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponse> getStreamId(@PathVariable Long id){
        return streamingService.findStreamingId(id)
                .map(streaming -> ResponseEntity.ok(StreamingMapper.toStreamingResponse(streaming)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StreamingResponse> saveStream(@Valid @RequestBody StreamingRequest request){
        Streaming savedStream = streamingService.saveStreaming(StreamingMapper.toStreaming(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(StreamingMapper.toStreamingResponse(savedStream));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StreamingResponse> updateStream(@PathVariable Long id,@Valid @RequestBody StreamingRequest request){
        return streamingService.updateStreaming(id, StreamingMapper.toStreaming(request))
                .map(streaming -> ResponseEntity.ok(StreamingMapper.toStreamingResponse(streaming)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStreamId(@PathVariable Long id){
        Optional<Streaming> optStreaming = streamingService.findStreamingId(id);
        if(optStreaming.isPresent()){
            streamingService.deleteStreamingId(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }



}
