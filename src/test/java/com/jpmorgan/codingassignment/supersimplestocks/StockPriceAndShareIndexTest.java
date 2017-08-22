package com.jpmorgan.codingassignment.supersimplestocks;

import com.jpmorgan.codingassignment.supersimplestocks.model.TradeRecord;
import com.jpmorgan.codingassignment.supersimplestocks.service.SimpleStockExchangeService;
import com.jpmorgan.codingassignment.supersimplestocks.service.StockExchangeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static com.jpmorgan.codingassignment.supersimplestocks.model.TradeRecord.TradeType;
import static com.jpmorgan.codingassignment.supersimplestocks.TestDataHelper.TEST_STOCK_RECORDS;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * StockPriceAndShareIndexTest
 *
 * @author Ilja Cisers
 */
@RunWith(Parameterized.class)
public class StockPriceAndShareIndexTest {

    private StockExchangeService service;
    private List<TradeRecord> tradeRecords;
    private Map<String, BigDecimal> expectedStockPrices;
    private BigDecimal expectedAllShareIndex;


    public StockPriceAndShareIndexTest(List<TradeRecord> tradeRecords,
                                       Map<String, BigDecimal> expectedStockPrices,
                                       BigDecimal expectedAllShareIndex)
    {
        this.service = new SimpleStockExchangeService();
        this.tradeRecords = tradeRecords;
        this.expectedStockPrices = expectedStockPrices;
        this.expectedAllShareIndex = expectedAllShareIndex;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        List<TradeRecord> tradeRecords = Arrays.asList(
                tradeRecord("POP", "12.00000", "BUY",  "15000"),
                tradeRecord("POP", "10.00000", "BUY",  "10000"),
                tradeRecord("POP", "13.00000", "BUY",  "15000"),
                tradeRecord("TEA", "110.00000", "SELL", "2000"),
                tradeRecord("TEA", "150.00000", "SELL", "2500"),
                tradeRecord("TEA", "200.00000", "BUY",  "1500"),
                tradeRecord("TEA", "250.00000", "BUY",   "500"),
                tradeRecord("ALE", "1200.00000", "BUY",  "900"),
                tradeRecord("ALE", "1195.00000", "BUY",  "850"),
                tradeRecord("ALE", "1216.00000", "BUY",  "700"),
                tradeRecord("JOE", "30000.00000", "BUY",  "10"),
                tradeRecord("JOE", "50000.00000", "BUY",   "3"));
        Map<String, BigDecimal> expectedStockPrices = new HashMap<String, BigDecimal>() {
            {
                put("POP", new BigDecimal(  "11.87500"));
                put("TEA", new BigDecimal( "156.92308"));
                put("ALE", new BigDecimal("1202.83673"));
                put("JOE", new BigDecimal("34615.38462"));
            }
        };
        BigDecimal expectedAllShareIndex = new BigDecimal("527.77531");
        return Arrays.asList(new Object[][]{
                {tradeRecords, expectedStockPrices, expectedAllShareIndex}
        });
    }

    private static TradeRecord tradeRecord(String... args) {
        return TradeRecord.builder()
                .withStockRecord(TEST_STOCK_RECORDS.get(args[0]))
                .withStockPrice(new BigDecimal(args[1]))
                .withTradeType(TradeType.valueOf(args[2]))
                .withTradeUnits(new BigInteger(args[3]))
                .build();
    }

    @Test
    public void testCalculateAllShareIndex() {
        tradeRecords.forEach(it -> service.recordTrade(
                it.getStockRecord(), it.getTradeType(), it.getStockPrice(), it.getTradeUnits()));
        Assert.assertThat(service.getAllShareIndex(), equalTo(expectedAllShareIndex));
    }

    @Test
    public void testCalculateStockPrices() {
        tradeRecords.forEach(it -> service.recordTrade(
                it.getStockRecord(), it.getTradeType(), it.getStockPrice(), it.getTradeUnits()));
        expectedStockPrices.forEach(
                (key, value) -> Assert.assertThat(service.getStockPrice(TEST_STOCK_RECORDS.get(key)), equalTo(value)));
    }
}
