package liu.democacchucnangchocodeweb.dto;

import liu.democacchucnangchocodeweb.entity.Customer;

import java.util.List;

public record CustomerPageResponse(
    List<Customer> customers,
    long totalElements,
    boolean hasMore
) {}
