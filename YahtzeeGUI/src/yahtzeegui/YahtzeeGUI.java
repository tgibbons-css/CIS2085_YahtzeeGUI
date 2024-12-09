/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package yahtzeegui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 *
 * @author tgibbons
 */
public class YahtzeeGUI {


    private JFrame frame;
    private JPanel dicePanel, scorePadPanel;
    private JButton rollButton;
    private ArrayList<JButton> diceButtons;
    private ArrayList<Integer> diceValues;
    private Map<String, Integer> scorePad;
    private Random random;

    public Unit12_Yahtzee_SwingGUI() {
        diceButtons = new ArrayList<>();
        diceValues = new ArrayList<>();
        scorePad = new HashMap<>();
        random = new Random();

        // Initialize the dice values
        for (int i = 0; i < 5; i++) {
            diceValues.add(random.nextInt(6) + 1);
        }

        // Initialize the score pad categories with null values (unscored)
        String[] categories = {"1s", "2s", "3s", "4s", "5s", "6s"};
        for (String category : categories) {
            scorePad.put(category, null);
        }

        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Set up the main frame
        frame = new JFrame("Yahtzee with Score Pad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Dice panel to hold the dice buttons
        dicePanel = new JPanel();
        dicePanel.setLayout(new GridLayout(1, 5, 10, 10));
        for (int i = 0; i < 5; i++) {
            JButton diceButton = createDiceButton(i);
            diceButtons.add(diceButton);
            dicePanel.add(diceButton);
        }

        // Score pad panel
        scorePadPanel = new JPanel();
        scorePadPanel.setLayout(new GridLayout(6, 2, 5, 5));
        updateScorePad();

        // Roll button
        rollButton = new JButton("Re-roll Selected Dice");
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reRollSelectedDice();
                updateScorePad();
            }
        });

        // Add components to the frame
        frame.add(dicePanel, BorderLayout.NORTH);
        frame.add(scorePadPanel, BorderLayout.CENTER);
        frame.add(rollButton, BorderLayout.SOUTH);

        // Display the frame
        frame.setSize(500, 400);
        frame.setVisible(true);
    }

    private JButton createDiceButton(int index) {
        JButton button = new JButton(getDiceIcon(diceValues.get(index)));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);

        // Toggle selection on click
        button.addActionListener(e -> toggleDiceSelection(button));

        return button;
    }

    private void toggleDiceSelection(JButton button) {
        if (button.getBackground() == Color.RED) {
            button.setBackground(Color.WHITE); // Deselect
        } else {
            button.setBackground(Color.RED); // Select
        }
    }

    private void reRollSelectedDice() {
        for (int i = 0; i < diceButtons.size(); i++) {
            JButton button = diceButtons.get(i);
            if (button.getBackground() == Color.RED) {
                // Re-roll the dice
                diceValues.set(i, random.nextInt(6) + 1);
                button.setIcon(getDiceIcon(diceValues.get(i)));
                button.setBackground(Color.WHITE); // Deselect after re-rolling
            }
        }
    }

    private Icon getDiceIcon(int value) {
        String imagePath = "/resources/dice" + value + ".png"; // Path relative to the src directory
        return new ImageIcon(getClass().getResource(imagePath));
    }

    private void updateScorePad() {
        scorePadPanel.removeAll();

        // Add each category and its score (or blank if not scored)
        for (Map.Entry<String, Integer> entry : scorePad.entrySet()) {
            JLabel categoryLabel = new JLabel(entry.getKey() + ":");
            JLabel scoreLabel;

            if (entry.getValue() == null) {
                scoreLabel = new JLabel("Blank");
            } else {
                scoreLabel = new JLabel(entry.getValue().toString());
            }

            scorePadPanel.add(categoryLabel);
            scorePadPanel.add(scoreLabel);
        }

        // Refresh the panel
        scorePadPanel.revalidate();
        scorePadPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Unit12_Yahtzee_SwingGUI::new);
    }
}

