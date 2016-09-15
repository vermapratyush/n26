package com.n26.model;

/**
 * Created by pratyushverma on 9/15/16.
 */
public class AggregateSum {
    private Double sum;

    public AggregateSum() {
    }

    public AggregateSum(Double sum) {
        this.sum = sum;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
