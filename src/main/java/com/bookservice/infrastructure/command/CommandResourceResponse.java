package com.bookservice.infrastructure.command;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommandResourceResponse {

	String msg;

	LocalDateTime msgTime;
}
