package com.jpmorgan.codingassignment.supersimplestocks.model;

import java.math.BigDecimal;

/**
 * StockRecord
 *
 * @author Ilja Cisers
 */
public class StockRecord {

    public enum StockType {Common, Preferred}

    private String symbol;
    private StockType stockType;
    private BigDecimal parValue;
    private BigDecimal lastDividend;
    private BigDecimal fixedDividend;

    private StockRecord() {}

    public String getSymbol() {
        return symbol;
    }

    public StockType getStockType() {
        return stockType;
    }

    public BigDecimal getParValue() {
        return parValue;
    }

    public BigDecimal getLastDividend() {
        return lastDividend;
    }

    public BigDecimal getFixedDividend() {
        return fixedDividend;
    }

    public boolean hasFixedDivident() {
        return stockType == StockType.Common;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockRecord that = (StockRecord) o;

        if (!symbol.equals(that.symbol)) return false;
        return stockType == that.stockType;
    }

    @Override
    public int hashCode() {
        int result = symbol.hashCode();
        result = 31 * result + stockType.hashCode();
        return result;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private StockRecord result = new StockRecord();

        private Builder() {}

        public Builder withSymbol(String symbol) {
            result.symbol = symbol;
            return this;
        }

        public Builder withStockType(StockType stockType) {
            result.stockType = stockType;
            return this;
        }

        public Builder withParValue(BigDecimal parValue) {
            result.parValue = parValue;
            return this;
        }

        public Builder withLastDivident(BigDecimal lastDividend) {
            result.lastDividend = lastDividend;
            return this;
        }

        public Builder withFixedDivident(BigDecimal fixedDividend) {
            result.fixedDividend = fixedDividend;
            return this;
        }

        public StockRecord build() {
            return result;
        }
    }
}
