package com.jpmorgan.codingassignment.supersimplestocks;

import com.jpmorgan.codingassignment.supersimplestocks.model.StockRecord;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.jpmorgan.codingassignment.supersimplestocks.model.StockRecord.StockType.Common;
import static com.jpmorgan.codingassignment.supersimplestocks.model.StockRecord.StockType.Preferred;

/**
 * TestDataHelper
 *
 * @author Ilja Cisers
 */
final class TestDataHelper {

    static final Map<String, StockRecord> TEST_STOCK_RECORDS = new HashMap<String, StockRecord>() {
        {
            put("TEA",StockRecord.builder()
                    .withSymbol("TEA")
                    .withStockType(Common)
                    .withLastDivident(BigDecimal.valueOf(0))
                    .build());
            put("ALE",StockRecord.builder()
                    .withSymbol("ALE")
                    .withStockType(Common)
                    .withParValue(BigDecimal.valueOf(60))
                    .withLastDivident(BigDecimal.valueOf(23))
                    .build());
            put("POP",StockRecord.builder()
                    .withSymbol("POP")
                    .withStockType(Common)
                    .withParValue(BigDecimal.valueOf(100))
                    .withLastDivident(BigDecimal.valueOf(8))
                    .build());
            put("JOE",StockRecord.builder()
                    .withSymbol("JOE")
                    .withStockType(Common)
                    .withParValue(BigDecimal.valueOf(250))
                    .withLastDivident(BigDecimal.valueOf(13))
                    .build());
            put("GIN", StockRecord.builder()
                    .withSymbol("GIN")
                    .withStockType(Preferred)
                    .withParValue(BigDecimal.valueOf(100))
                    .withLastDivident(BigDecimal.valueOf(8))
                    .withFixedDivident(BigDecimal.valueOf(0.02))
                    .build());
        }
    };

}
