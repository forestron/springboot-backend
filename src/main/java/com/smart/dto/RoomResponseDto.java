package com.smart.dto;

import java.util.List;

import lombok.Data;

@Data
public class RoomResponseDto {
  private List<RoomDto> roomDtoList;
  
  private Integer totalPages;
  private Integer pageNumber;
}
