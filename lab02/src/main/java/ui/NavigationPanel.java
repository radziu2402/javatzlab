package ui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class NavigationPanel extends JPanel {
    private JTree tree;

    public NavigationPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 600));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Measurement Data");
        DefaultMutableTreeNode campaign1 = new DefaultMutableTreeNode("07.03.2023");
        root.add(campaign1);
        campaign1.add(new DefaultMutableTreeNode("0001_10:00"));
        campaign1.add(new DefaultMutableTreeNode("0002_11:00"));

        tree = new JTree(root);
        add(new JScrollPane(tree), BorderLayout.CENTER);
    }
}
