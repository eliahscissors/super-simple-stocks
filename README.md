# Super Simple Stocks

This project provides working source code that 
- for a given stock
  * calculates divident yield
  * calculates P/E Ratio
  * records fact of trade with timestamp, number of traded shares, price and Buy/Sell indicator
  * calculates stock price based on trades recorded in past 15 minutes
- calculates All Share Index using geometric mean of prices for all stocks


|          |Formula        |
|----------|:-------------:|
| Common DY|  Last Dividend / Ticker Price |  
| Preferred DY| (Fixed Dividend x Par Value)/ Ticker Price |
| P/E Ratio | Ticker Price / Dividend |
| Geometric Mean | (p1 x p2 x p3 x ... x pN) ^ 1/N |
| Stock Price |  Sum(Trade Price x Quantity) / Sum (Quantity)|

DISCLAIMER: The code has been prepared for educational and demo purposes and doesn't obtain production quality.  
