package com.archiveapp;

import com.labs.archiveutility.Archiver;
import com.labs.archiveutility.MD5Checksum;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ArchiveView extends JPanel {
    private final JTextField txtSourceFiles;
    private final JTextField txtZipFileDestination;
    private final JTextField txtZipFileName;

    public ArchiveView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        txtSourceFiles = new JTextField(20);
        JButton btnChooseFiles = new JButton("Choose Source Files");
        btnChooseFiles.addActionListener(e -> chooseFiles());


        txtZipFileDestination = new JTextField(System.getProperty("user.home") + "\\Desktop",20);
        txtZipFileName = new JTextField("archive.zip", 20);
        JButton btnChooseZip = new JButton("Choose Zip Destination");
        btnChooseZip.addActionListener(e -> chooseZipDestination());

        JButton btnArchive = new JButton("Archive and Calculate MD5");
        btnArchive.addActionListener(e -> archiveAndCalculateMD5());

        setupDragAndDrop(btnChooseFiles, files -> {
            String joinedPaths = String.join("; ", files);
            txtSourceFiles.setText(joinedPaths);
        });

        setupDragAndDrop(btnChooseZip, files -> {
            if (!files.isEmpty()) {
                txtZipFileDestination.setText(files.get(0));
            }
        });

        add(createRowPanel(new JLabel("Source Files:"), txtSourceFiles, btnChooseFiles));
        add(createRowPanel(new JLabel("Zip Destination:"), txtZipFileDestination, btnChooseZip));
        add(createRowPanel(new JLabel("Zip File Name:"), txtZipFileName, null));
        add(createRowPanel(btnArchive));
    }

    private JPanel createRowPanel(JComponent... components) {
        JPanel row = new JPanel();
        for (JComponent component : components) {
            if (component != null) row.add(component);
        }
        return row;
    }

    private void setupDragAndDrop(JButton button, Consumer<List<String>> handleDroppedFiles) {
        new DropTarget(button, new DropTargetListener() {
            public void dragEnter(DropTargetDragEvent dtde) {
            }

            public void dragOver(DropTargetDragEvent dtde) {
            }

            public void dropActionChanged(DropTargetDragEvent dtde) {
            }

            public void dragExit(DropTargetEvent dte) {
            }

            public void drop(DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    Transferable transferable = dtde.getTransferable();
                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        List<File> droppedFiles = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                        List<String> filePaths = droppedFiles.stream().map(File::getAbsolutePath).toList();
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

    private void chooseFiles() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home"), "Desktop"));
        fileChooser.setMultiSelectionEnabled(true);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            String[] filePaths = Arrays.stream(files).map(File::getAbsolutePath).toArray(String[]::new);
            txtSourceFiles.setText(String.join("; ", filePaths));
        }
    }


    private void chooseZipDestination() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + FileSystems.getDefault().getSeparator() + "Desktop"));
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            txtZipFileDestination.setText(selectedFile.getAbsolutePath());
        }
    }

    private void archiveAndCalculateMD5() {
        String sourcePaths = txtSourceFiles.getText().trim();
        String zipFilePath = txtZipFileDestination.getText().trim();
        String zipFileName = txtZipFileName.getText().trim();

        if (sourcePaths.isEmpty() || zipFilePath.isEmpty() || zipFileName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields before archiving.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String fullPath = zipFilePath + File.separator + zipFileName;
            String[] paths = sourcePaths.split(";\\s+");
            Archiver.pack(Arrays.asList(paths), fullPath);
            MD5Checksum.createMD5ChecksumFile(fullPath);
            JOptionPane.showMessageDialog(this, "Archiving and MD5 calculation completed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during archiving/MD5 calculation: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}
