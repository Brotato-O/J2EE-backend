package J2EE.com.example.project.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PageResponse<T> {

    private final List<T> content;
    @JsonProperty("page_no")
    private final int pageNo;
    @JsonProperty("page_size")
    private final int pageSize;
    @JsonProperty("total_elements")
    private final long totalElements;
    @JsonProperty("total_pages")
    private final int totalPages;
    @JsonProperty("is_last")
    private final boolean last;
}