package com.chetan.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationResponseDTO {

	private Boolean isAccepted;
	private Long messageId;
	
}
