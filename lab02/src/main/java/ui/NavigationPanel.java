package ui;

import data.DataLoader;
import data.MeasurementCache;
import factories.DataModelFactory;
import model.FileNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;

public class NavigationPanel extends JPanel {
    private final JTree tree;

    public NavigationPanel(String rootFolderPath, DataDisplayPanel dataDisplayPanel, DataLoader dataLoader, DataModelFactory factory) {
        setLayout(new BorderLayout());
        MeasurementCache cache = new MeasurementCache();
        File rootFolder = new File(rootFolderPath);
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootFolder);
        createTree(rootNode, rootFolder);

        tree = new JTree(rootNode);
        expandAllNodes(tree);
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode != null) {
                Object userObject = selectedNode.getUserObject();
                if (userObject instanceof FileNode) {
                    File selectedFile = ((FileNode) userObject).getFile();
                    if (selectedFile.isFile()) {
                        dataDisplayPanel.updateDataPreview(selectedFile.getAbsolutePath(), dataLoader, factory, cache);
                    }
                }
            }
        });

        add(new JScrollPane(tree), BorderLayout.CENTER);
    }

    private void expandAllNodes(JTree tree) {
        int j = tree.getRowCount();
        int i = 0;
        while(i < j) {
            tree.expandRow(i);
            i += 1;
            j = tree.getRowCount();
        }
    }

    private void createTree(DefaultMutableTreeNode node, File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
                node.add(childNode);
                if (file.isDirectory()) {
                    createTree(childNode, file);
                }
            }
        }
    }
}
