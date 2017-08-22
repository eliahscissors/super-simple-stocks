package com.jpmorgan.codingassignment.supersimplestocks.service;

import com.jpmorgan.codingassignment.supersimplestocks.model.StockRecord;
import com.jpmorgan.codingassignment.supersimplestocks.model.TradeRecord;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.jpmorgan.codingassignment.supersimplestocks.model.TradeRecord.TradeType;

/**
 * SimpleStockExchangeService
 *
 * @author Ilja Cisers
 */
public class SimpleStockExchangeService implements StockExchangeService {

    private class StockPrice {
        BigDecimal tickerPrice;
        BigDecimal marketPrice;
    }

    private BigDecimal allShareIndex;
    private Set<TradeRecord> tradeLedger;
    private Map<StockRecord, StockPrice> stockPrices;

    public SimpleStockExchangeService() {
        allShareIndex = new BigDecimal(BigInteger.ZERO, SCALING_FACTOR);
        tradeLedger = Collections.synchronizedSet(new HashSet<>());
        stockPrices = Collections.synchronizedMap(new HashMap<>());
    }

    private BigDecimal calculateStockPrice(StockRecord stockRecord) {
        BigDecimal result = null;
        List<TradeRecord>  tradeRecords = tradeLedger.stream()
                .filter(it -> it.getStockRecord().equals(stockRecord)).collect(Collectors.toList());
        if (!tradeRecords.isEmpty()) {
            BigDecimal tradedVolume = tradeRecords.stream()
                    .map(it -> it.getStockPrice().multiply(new BigDecimal(it.getTradeUnits())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigInteger tradedUnits = tradeRecords.stream()
                    .map(TradeRecord::getTradeUnits)
                    .reduce(BigInteger.ZERO, BigInteger::add);
            result = tradedVolume.divide(new BigDecimal(tradedUnits), SCALING_FACTOR, ROUNDING_MODE);
        }
        return result;
    }

    private BigDecimal calculateAllShareIndex() {
        BigDecimal result = null;
        int recordCount = stockPrices.values().size();
        if (recordCount > 0) {
            result = stockPrices.values().stream()
                    .map(it -> it.marketPrice)
                    .reduce(BigDecimal.ONE, BigDecimal::multiply);
            result = BigDecimal.valueOf(Math.pow(result.doubleValue(), 1.0 / recordCount))
                    .setScale(SCALING_FACTOR, ROUNDING_MODE);
        }
        return result;
    }

    @Override
    public BigDecimal getStockPrice(StockRecord stockRecord) {
        return stockPrices.containsKey(stockRecord)
                ? stockPrices.get(stockRecord).marketPrice : null;
    }

    @Override
    public BigDecimal getTickerPrice(StockRecord stockRecord) {
        return stockPrices.containsKey(stockRecord)
                ? stockPrices.get(stockRecord).tickerPrice : null;
    }

    @Override
    public BigDecimal getAllShareIndex() {
        return allShareIndex;
    }

    @Override
    public void recordTrade(StockRecord stockRecord, TradeType tradeType, BigDecimal stockPrice, BigInteger tradeVolume) {
        if (!stockPrices.containsKey(stockRecord)) {
            stockPrices.put(stockRecord, new StockPrice());
        }
        StockPrice priceRecord = stockPrices.get(stockRecord);
        TradeRecord tradeRecord = TradeRecord.builder()
            .withStockRecord(stockRecord)
            .withTradeType(tradeType)
            .withStockPrice(stockPrice)
            .withTradeUnits(tradeVolume)
            .withDateTime(ZonedDateTime.now())
            .build();
        tradeLedger.add(tradeRecord);
        priceRecord.tickerPrice = stockPrice;
        priceRecord.marketPrice = calculateStockPrice(stockRecord);
        allShareIndex = calculateAllShareIndex();
    }
}
