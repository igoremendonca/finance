package br.com.jiva.finance.web.controller.to;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GraphTO {
    private String label;
    private Double value;
}
