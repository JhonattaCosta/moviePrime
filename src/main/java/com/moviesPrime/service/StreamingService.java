package com.moviesPrime.service;

import com.moviesPrime.controller.request.StreamingRequest;
import com.moviesPrime.entity.Streaming;
import com.moviesPrime.repository.StreamingRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreamingService {

    private final StreamingRepository repository;

    public List<Streaming> findAll(){
        return repository.findAll();
    }

    public Streaming saveStreaming(Streaming streaming){
        return repository.save(streaming);
    }

    public Optional<Streaming> findStreamingId(Long id){
        return  repository.findById(id);
    }

    public Optional<Streaming> updateStreaming(Long id, @Valid Streaming updateStreaming){
        Optional<Streaming> optStreaming = repository.findById(id);
        if (optStreaming.isPresent()){
            Streaming streaming = optStreaming.get();
            streaming.setName(updateStreaming.getName());

            repository.save(streaming);
            return Optional.of(streaming);
        }
        return Optional.empty();
    }

    public void deleteStreamingId(Long id){
        repository.deleteById(id);
    }

}
