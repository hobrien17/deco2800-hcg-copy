package com.deco2800.hcg.trading;

import com.deco2800.hcg.contexts.UIContext;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class ShopGUI extends JFrame implements UIContext, ActionListener {

    int width, height;

    JButton addStock = new JButton("Add Stock");
    JButton buyStock = new JButton("Buy Stock");
    JButton sellStock = new JButton("Sell Stock");
    JButton exit = new JButton("Exit");

    JPanel panel = new JPanel();
    JPanel menu = new JPanel();

    public ShopGUI(int width, int height) {

        panel.setLayout(layout);
        addButtons;

        setSize(width, height);
        setResizable(null);
        setVisible(true);
        setTitle("Bunnings Local Shopkeep");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        requestFocusWindow();
    }

    private void addButtons() {
        addStock.addActionListener(this);
        buyStock.addActionListener(this);
        sellStock.addActionListener(this);
        exit.addActionListener(this);

        menu.add(addStock);
        menu.add(buyStock);
        menu.add(sellStock);
        menu.add(exit);

        menu.setBackground(colour.BROWN);

        panel.add(menu,"Menu");

        add(panel);
        layout.show(panel,"Menu");

    }

    public void actionPerformed(ActionEvent event){
        Object source = event.getSource();

        if (source == exit){
            hide();
        } else if (source == buyStock) {

        } else if (source == sellStock) {

        }
    }
}
