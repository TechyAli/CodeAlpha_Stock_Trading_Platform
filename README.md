# CodeAlpha_Stock_Trading_Platform

# Stock Trading GUI Platform

## 📌 Overview
**StockTradingGUI** is a simple **Java Swing-based stock trading simulator**.  
It allows users to view live-updating stock prices, buy/sell stocks, track their portfolio, and view transaction history — all in a graphical interface.

## ✨ Features
- **Live Price Updates** — Stock prices for AAPL, GOOG, and TSLA update every 3 seconds.
- **Buy & Sell Stocks** — Enter stock symbol and quantity, then click **Buy** or **Sell**.
- **Portfolio View** — Displays your holdings and remaining cash.
- **Transaction History** — Shows all buy and sell transactions with timestamps.
- **Error Handling** — Alerts if you have insufficient funds or insufficient stock to sell.

## 🖥️ GUI Layout
- **Top Panel**: Stock market table with symbols and current prices.
- **Middle Panel**: Input fields for `Symbol` and `Quantity` with Buy/Sell buttons.
- **Bottom Left**: Portfolio display (stocks owned and available cash).
- **Bottom Right**: Transaction history.

## 🚀 How to Run
### **Requirements**
- Java Development Kit (**JDK 8** or higher)

### **Steps**
1. Save the file as `StockTradingGUI.java`.
2. Open a terminal in the file directory.
3. Compile the program:
   javac StockTradingGUI.java
4. Run the program:
   java StockTradingGUI

###🛠️ **How It Works**

1. When launched, the application shows the stock market table with initial prices:
   AAPL: $150
   GOOG: $2800
   TSLA: $700

2. Prices change every 3 seconds to simulate market movement.

3. Enter the stock symbol and quantity:
   Click Buy to purchase (if enough cash).
   Click Sell to sell (if enough shares).

4. Portfolio and transaction history update instantly after each trade.

###📸 Example Usage
```Initial State
  Portfolio:
  Cash: $10000.00

  Transactions:
  (no transactions yet)
```

```After Buying 5 AAPL @ $151.23
  Portfolio:
  AAPL: 5 shares @ $151.23
  Cash: $9243.85

  Transactions:
  [Date] - BUY 5 of AAPL @ $151.23
```

###📄 License
This project is for educational/demo purposes and is not intended for real trading.

-By Syed Masood Ali


