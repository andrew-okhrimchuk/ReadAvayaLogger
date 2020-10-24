package main.web.DTO;

import lombok.Getter;
import lombok.Setter;

import main.entity.Calls;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Getter
@Setter
@RequestScope
public class DTO_Padding {

    long totalPageCount;
    long currentIndex;
    long beginIndex;
    long endIndex;
    long totalElements;
    List<Integer> pageNumbers;


    public void init(Page<Calls> callsPage) {
        this.totalPageCount = callsPage.getTotalPages();
        this.currentIndex = callsPage.getNumber() + 1;
        this.beginIndex = Math.max(1, currentIndex - callsPage.getContent().size());
        this.endIndex = Math.min(beginIndex + 5, totalPageCount);
        this.totalElements = callsPage.getTotalElements();
        setPageNumbersList(callsPage);
    }

    private void setPageNumbersList(Page<Calls> callsPage) {
        int totalPages = callsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            setPageNumbers(pageNumbers);
        }
    }
}
