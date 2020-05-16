package main.web.TO;

import lombok.Getter;
import lombok.Setter;

import main.entity.CallsNew;
import org.springframework.context.annotation.Scope;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Getter
@Setter
//@Scope("prototype")
public class TO_Padding {

    long totalPageCount;
    long currentIndex;
    long beginIndex;
    long endIndex;
    long totalElements;
    List<Integer> pageNumbers;


    public void init(Page<CallsNew> callsPage) {
        this.totalPageCount = callsPage.getTotalPages();
        this.currentIndex = callsPage.getNumber() + 1;
        this.beginIndex = Math.max(1, currentIndex - callsPage.getContent().size());
        this.endIndex = Math.min(beginIndex + 5, totalPageCount);
        this.totalElements = callsPage.getTotalElements();
        setPageNumbersList(callsPage);
    }

    private void setPageNumbersList(Page<CallsNew> callsPage) {
        int totalPages = callsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            setPageNumbers(pageNumbers);
        }
    }
}
