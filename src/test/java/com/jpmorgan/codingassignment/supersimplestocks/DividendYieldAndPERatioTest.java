package com.jpmorgan.codingassignment.supersimplestocks;

import com.jpmorgan.codingassignment.supersimplestocks.model.StockRecord;
import com.jpmorgan.codingassignment.supersimplestocks.service.StockExchangeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static com.jpmorgan.codingassignment.supersimplestocks.TestDataHelper.TEST_STOCK_RECORDS;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;

/**
 * DividendYieldAndPERatioTest
 *
 * @author Ilja Cisers
 */
@RunWith(Parameterized.class)
public class DividendYieldAndPERatioTest {

    private StockExchangeService service;
    private String stockSymbol;
    private BigDecimal expectedYield;
    private BigDecimal expectedPERatio;

    public DividendYieldAndPERatioTest(StockExchangeService service,
                                       String stockSymbol,
                                       String tickerPrice,
                                       String expectedYield,
                                       String expectedPERatio)
    {
        this.service = service;
        this.stockSymbol = stockSymbol;
        this.expectedYield = new BigDecimal(expectedYield);
        this.expectedPERatio = new BigDecimal(expectedPERatio);
        when(service.getTickerPrice(any(StockRecord.class))).thenReturn(new BigDecimal(tickerPrice));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        StockExchangeService mockService = mock(StockExchangeService.class);
        when(mockService.calculateDividendYield(any(StockRecord.class))).thenCallRealMethod();
        when(mockService.calculatePERatio(any(StockRecord.class))).thenCallRealMethod();
        return Arrays.asList(new Object[][]{
            { mockService, "POP", "12.00000", "0.66667", "1.50000"},
            { mockService, "TEA", "12.00000", "0.00000", "0.00000"},
            { mockService, "ALE", "12.00000", "1.91667", "0.52174"},
            { mockService, "JOE", "12.00000", "1.08333", "0.92308"},
            { mockService, "GIN", "12.00000", "0.16667", "6.00000"},
        });
    }

    @Test
    public void testCalculateDividendYield() {
        Assert.assertThat(
            service.calculateDividendYield(TEST_STOCK_RECORDS.get(stockSymbol)), equalTo(expectedYield)
        );
    }

    @Test
    public void testCalculatePERatio() {
        Assert.assertThat(
            service.calculatePERatio(TEST_STOCK_RECORDS.get(stockSymbol)), equalTo(expectedPERatio)
        );
    }
}
