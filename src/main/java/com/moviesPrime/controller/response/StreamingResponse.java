package com.moviesPrime.controller.response;

import lombok.Builder;

@Builder
public record StreamingResponse(Long id, String name) {
}
