package com.archiveapp;

import com.labs.archiveutility.MD5Checksum;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ValidateView extends JPanel {
    private final JTextField txtZipFile;
    private final JTextField txtMd5File;

    public ValidateView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        txtZipFile = new JTextField(20);
        JButton btnChooseZip = new JButton("Choose Zip File");
        btnChooseZip.addActionListener(e -> chooseZipFile());

        txtMd5File = new JTextField(20);
        JButton btnChooseMd5 = new JButton("Choose MD5 File");
        btnChooseMd5.addActionListener(e -> chooseMd5File());

        JButton btnValidate = new JButton("Validate");
        btnValidate.addActionListener(e -> validateFiles());

        setupDragAndDrop(btnChooseZip, files -> {
            if (!files.isEmpty()) {
                txtZipFile.setText(files.get(0));
            }
        });

        setupDragAndDrop(btnChooseMd5, files -> {
            if (!files.isEmpty()) {
                txtMd5File.setText(files.get(0));
            }
        });

        add(createRowPanel(new JLabel("Zip File:"), txtZipFile, btnChooseZip));
        add(createRowPanel(new JLabel("MD5 File:"), txtMd5File, btnChooseMd5));
        add(createRowPanel(btnValidate));
    }

    private JPanel createRowPanel(JComponent... components) {
        JPanel row = new JPanel();
        for (JComponent component : components) {
            if (component != null) row.add(component);
        }
        return row;
    }

    private void setupDragAndDrop(JButton button, Consumer<List<String>> handleDroppedFiles) {
        new DropTarget(button, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    Transferable transferable = dtde.getTransferable();
                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        List<File> droppedFiles = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                        List<String> filePaths = droppedFiles.stream().map(File::getAbsolutePath).collect(Collectors.toList());
                        handleDroppedFiles.accept(filePaths);
                    }
                    dtde.dropComplete(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    dtde.dropComplete(false);
                }
            }
        });
    }

    private void chooseZipFile() {
        setupJFileChooser(txtZipFile);
    }

    private void setupJFileChooser(JTextField txtZipFile) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + FileSystems.getDefault().getSeparator() + "Desktop"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            txtZipFile.setText(selectedFile.getAbsolutePath());
        }
    }

    private void chooseMd5File() {
        setupJFileChooser(txtMd5File);
    }

    private void validateFiles() {
        String zipFilePath = txtZipFile.getText().trim();
        String md5FilePath = txtMd5File.getText().trim();

        if (zipFilePath.isEmpty() || md5FilePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select both ZIP and MD5 files before validation.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            boolean isValid = MD5Checksum.verifyMD5Checksum(zipFilePath, md5FilePath);
            if (isValid) {
                JOptionPane.showMessageDialog(this, "MD5 checksum is valid. The file has not been modified.", "Validation Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "MD5 checksum is invalid. The file may have been modified.", "Validation Failed", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during validation: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}
