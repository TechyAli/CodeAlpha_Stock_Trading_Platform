
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.ArrayList;
import java.util.List; // <--- EXPLICIT List from java.util
import javax.swing.Timer; // <--- EXPLICIT Timer from javax.swing

class Stock {
    String symbol;
    double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public void updatePrice() {
        double change = (Math.random() - 0.5) * 10;
        price = Math.max(1, price + change);
    }

    public String toString() {
        return symbol + ": $" + String.format("%.2f", price);
    }
}

class Transaction {
    String stockSymbol;
    int quantity;
    double price;
    String type;
    Date date;

    public Transaction(String stockSymbol, int quantity, double price, String type) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.date = new Date();
    }

    public String toString() {
        return date + " - " + type + " " + quantity + " of " + stockSymbol + " @ $" + price;
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    List<Transaction> transactions = new ArrayList<>();
    double cash;

    public Portfolio(double initialCash) {
        this.cash = initialCash;
    }

    public void buyStock(Stock stock, int quantity) {
        double totalCost = stock.price * quantity;
        if (cash >= totalCost) {
            holdings.put(stock.symbol, holdings.getOrDefault(stock.symbol, 0) + quantity);
            cash -= totalCost;
            transactions.add(new Transaction(stock.symbol, quantity, stock.price, "BUY"));
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient funds!");
        }
    }

    public void sellStock(Stock stock, int quantity) {
        int currentQty = holdings.getOrDefault(stock.symbol, 0);
        if (currentQty >= quantity) {
            holdings.put(stock.symbol, currentQty - quantity);
            cash += stock.price * quantity;
            transactions.add(new Transaction(stock.symbol, quantity, stock.price, "SELL"));
        } else {
            JOptionPane.showMessageDialog(null, "Not enough stock to sell!");
        }
    }
}

public class StockTradingGUI extends JFrame {
    private final Map<String, Stock> market = new HashMap<>();
    private final Portfolio portfolio = new Portfolio(10000);
    private final DefaultTableModel marketTableModel;
    private final JTextArea portfolioTextArea;
    private final JTextArea transactionTextArea;
    private final Timer priceUpdateTimer;

    public StockTradingGUI() {
        setTitle("Stock Trading Platform");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        market.put("AAPL", new Stock("AAPL", 150));
        market.put("GOOG", new Stock("GOOG", 2800));
        market.put("TSLA", new Stock("TSLA", 700));

        String[] columnNames = {"Symbol", "Price"};
        marketTableModel = new DefaultTableModel(columnNames, 0);
        JTable marketTable = new JTable(marketTableModel);
        refreshMarketTable();

        JPanel controlPanel = new JPanel();
        JTextField symbolField = new JTextField(5);
        JTextField qtyField = new JTextField(5);
        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");

        controlPanel.add(new JLabel("Symbol:"));
        controlPanel.add(symbolField);
        controlPanel.add(new JLabel("Quantity:"));
        controlPanel.add(qtyField);
        controlPanel.add(buyButton);
        controlPanel.add(sellButton);

        buyButton.addActionListener(e -> {
            String symbol = symbolField.getText().toUpperCase();
            int qty;
            try {
                qty = Integer.parseInt(qtyField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity");
                return;
            }
            if (market.containsKey(symbol)) {
                portfolio.buyStock(market.get(symbol), qty);
                refreshPortfolio();
                refreshTransactions();
            } else {
                JOptionPane.showMessageDialog(this, "Stock not found");
            }
        });

        sellButton.addActionListener(e -> {
            String symbol = symbolField.getText().toUpperCase();
            int qty;
            try {
                qty = Integer.parseInt(qtyField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity");
                return;
            }
            if (market.containsKey(symbol)) {
                portfolio.sellStock(market.get(symbol), qty);
                refreshPortfolio();
                refreshTransactions();
            } else {
                JOptionPane.showMessageDialog(this, "Stock not found");
            }
        });

        portfolioTextArea = new JTextArea(10, 30);
        transactionTextArea = new JTextArea(10, 30);
        refreshPortfolio();
        refreshTransactions();

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(new JScrollPane(portfolioTextArea));
        bottomPanel.add(new JScrollPane(transactionTextArea));

        add(new JScrollPane(marketTable), BorderLayout.NORTH);
        add(controlPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        priceUpdateTimer = new Timer(3000, e -> {
            for (Stock stock : market.values()) {
                stock.updatePrice();
            }
            refreshMarketTable();
        });
        priceUpdateTimer.start();
    }

    private void refreshMarketTable() {
        marketTableModel.setRowCount(0);
        for (Stock stock : market.values()) {
            marketTableModel.addRow(new Object[]{stock.symbol, String.format("%.2f", stock.price)});
        }
    }

    private void refreshPortfolio() {
        StringBuilder sb = new StringBuilder("=== Portfolio ===\n");
        for (Map.Entry<String, Integer> entry : portfolio.holdings.entrySet()) {
            String symbol = entry.getKey();
            int qty = entry.getValue();
            double currentPrice = market.get(symbol).price;
            sb.append(symbol).append(": ").append(qty).append(" shares @ $")
              .append(String.format("%.2f", currentPrice)).append("\n");
        }
        sb.append("Cash: $").append(String.format("%.2f", portfolio.cash));
        portfolioTextArea.setText(sb.toString());
    }

    private void refreshTransactions() {
        StringBuilder sb = new StringBuilder("=== Transactions ===\n");
        for (Transaction t : portfolio.transactions) {
            sb.append(t).append("\n");
        }
        transactionTextArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StockTradingGUI().setVisible(true);
        });
    }
}
