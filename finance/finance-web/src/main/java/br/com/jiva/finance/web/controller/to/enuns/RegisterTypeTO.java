package br.com.jiva.finance.web.controller.to.enuns;

import lombok.Getter;

@Getter
public enum RegisterTypeTO {

    EXPENSE ("Despesa"),
    RECIPE ("Receita");

    private String description;
    RegisterTypeTO(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
