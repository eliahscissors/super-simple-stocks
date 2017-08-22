package com.jpmorgan.codingassignment.supersimplestocks.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;

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
    private ZonedDateTime dateTime;

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

    public ZonedDateTime getDateTime() {
        return dateTime;
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
        if (!stockPrice.equals(that.stockPrice)) return false;
        if (!tradeUnits.equals(that.tradeUnits)) return false;
        return dateTime.equals(that.dateTime);
    }

    @Override
    public int hashCode() {
        int result = stockRecord.hashCode();
        result = 31 * result + tradeType.hashCode();
        result = 31 * result + stockPrice.hashCode();
        result = 31 * result + tradeUnits.hashCode();
        result = 31 * result + dateTime.hashCode();
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

        public Builder withDateTime(ZonedDateTime dateTime) {
            result.dateTime = dateTime;
            return this;
        }

        public TradeRecord build() {
            return result;
        }
    }
}
