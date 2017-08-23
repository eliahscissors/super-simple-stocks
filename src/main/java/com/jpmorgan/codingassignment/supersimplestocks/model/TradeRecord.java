package com.jpmorgan.codingassignment.supersimplestocks.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * TradeRecord
 *
 * @author Ilja Cisers
 */
public class TradeRecord {

    public enum TradeType {SELL, BUY}

    private StockRecord stockRecord;
    private TradeType tradeType;
    private BigDecimal stockPrice;
    private BigInteger tradeUnits;
    private LocalDateTime tradeTime;

    private TradeRecord() {}

    public StockRecord getStockRecord() {
        return stockRecord;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public BigInteger getTradeUnits() {
        return tradeUnits;
    }

    public LocalDateTime getTradeTime() {
        return tradeTime;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeRecord that = (TradeRecord) o;

        if (!stockRecord.equals(that.stockRecord)) return false;
        if (tradeType != that.tradeType) return false;
        return stockPrice.equals(that.stockPrice) && tradeUnits.equals(that.tradeUnits) && tradeTime.equals(that.tradeTime);
    }

    @Override
    public int hashCode() {
        int result = stockRecord.hashCode();
        result = 31 * result + tradeType.hashCode();
        result = 31 * result + stockPrice.hashCode();
        result = 31 * result + tradeUnits.hashCode();
        result = 31 * result + tradeTime.hashCode();
        return result;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private TradeRecord result = new TradeRecord();

        private Builder() {}

        public Builder withStockRecord(StockRecord stockRecord) {
            result.stockRecord = stockRecord;
            return this;
        }

        public Builder withTradeType(TradeType tradeType) {
            result.tradeType = tradeType;
            return this;
        }

        public Builder withStockPrice(BigDecimal stockPrice) {
            result.stockPrice = stockPrice;
            return this;
        }

        public Builder withTradeUnits(BigInteger tradeUnits) {
            result.tradeUnits = tradeUnits;
            return this;
        }

        public Builder withTradeTime(LocalDateTime dateTime) {
            result.tradeTime = dateTime;
            return this;
        }

        public TradeRecord build() {
            if (result.stockRecord == null) {
                throw new IllegalStateException("Trade stock record is not defined");
            }
            if (result.tradeType == null) {
                throw new IllegalStateException("Trade type is not defined");
            }
            if (result.stockPrice == null || result.stockPrice.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalStateException("Trade stock price is not positive");
            }
            if (result.tradeUnits == null || result.tradeUnits.compareTo(BigInteger.ZERO) <= 0) {
                throw new IllegalStateException("Traded units is not positive");
            }
            if (result.tradeTime == null) {
                throw new IllegalStateException("Trade time is not defined");
            }
            return result;
        }
    }
}
