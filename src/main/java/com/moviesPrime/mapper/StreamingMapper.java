package com.moviesPrime.mapper;


import com.moviesPrime.controller.request.StreamingRequest;
import com.moviesPrime.controller.response.StreamingResponse;
import com.moviesPrime.entity.Streaming;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamingMapper {

    public static Streaming toStreaming(StreamingRequest categoryRequest){
        return Streaming
                .builder()
                .name(categoryRequest.name())
                .build();
    }

    public static StreamingResponse toStreamingResponse(Streaming streaming){
        return StreamingResponse
                .builder()
                .id(streaming.getId())
                .name(streaming.getName())
                .build();

    }
}
