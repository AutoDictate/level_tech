package com.level.tech.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResponseDTO {

    private List<?> data;

    private Integer currentPage;

    private Long totalItems;

    private Integer totalPages;

}
