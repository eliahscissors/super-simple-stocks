package com.jpmorgan.codingassignment.supersimplestocks.service;

import com.jpmorgan.codingassignment.supersimplestocks.model.StockRecord;
import com.jpmorgan.codingassignment.supersimplestocks.model.TradeRecord;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.jpmorgan.codingassignment.supersimplestocks.model.StockRecord.StockType;
import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * StockExchangeService
 *
 * @author Ilja Cisers
 */
public interface StockExchangeService {

    int SCALING_FACTOR = 5;
    int ROUNDING_MODE = ROUND_HALF_UP;

    default BigDecimal calculateDividendYield(StockRecord stockRecord, BigDecimal tickerPrice) {
        return stockRecord.getStockType() == StockType.Common
                ? stockRecord.getLastDividend().divide(tickerPrice, SCALING_FACTOR, ROUNDING_MODE)
                : stockRecord.getFixedDividend().multiply(stockRecord.getParValue()).divide(tickerPrice, SCALING_FACTOR, ROUNDING_MODE);
    }

    default BigDecimal calculatePERatio(StockRecord stockRecord, BigDecimal tickerPrice) {
        return stockRecord.getLastDividend().compareTo(BigDecimal.ZERO) > 0
                ? tickerPrice.setScale(SCALING_FACTOR).divide(stockRecord.getLastDividend(), ROUNDING_MODE)
                : BigDecimal.ZERO.setScale(SCALING_FACTOR, ROUNDING_MODE);
    }

    void recordTrade(StockRecord stockRecord, TradeRecord.TradeType tradeType, BigDecimal stockPrice, BigInteger tradeVolume);

    BigDecimal getStockPrice(StockRecord stockRecord);

    BigDecimal getAllShareIndex();

    BigDecimal getTickerPrice(StockRecord stockRecord);
}
