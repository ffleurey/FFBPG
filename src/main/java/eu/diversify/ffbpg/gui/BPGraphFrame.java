/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.ffbpg.gui;

import org.thingml.rtcharts.swing.*;
import eu.diversify.ffbpg.*;
import eu.diversify.ffbpg.utils.FileUtils;
import java.awt.Color;
import java.io.File;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author ffl
 */
public class BPGraphFrame extends javax.swing.JFrame {

    protected GraphBuffer nblinks = new GraphBuffer(10); // Empty graph
    protected GraphBuffer appsize = new GraphBuffer(10); // Empty graph
    protected GraphBuffer srvuse = new GraphBuffer(10); // Empty graph
    protected GraphBuffer extinction = new GraphBuffer(10); // Empty graph

    protected BPGraph graph;

    protected BPGraphAppTableModel apptablemodel = new BPGraphAppTableModel();

    public void setBPGraph(BPGraph g, boolean scale_graphs) {
        graph = g;
        apptablemodel.setBPGraph(g);
        jTextFieldBPgraph.setText(g.toString());
        
        ((BPMatrixGraph)jPanelMatrix).setBPGraph(g);

        int max = 0;
        int[] l = g.applicationsServicesCountsDistribution();
        appsize.setGraphData(l);
        if (scale_graphs) {
            max = 0;
            for (int v : l) {
                if (v > max) {
                    max = v;
                }
            }
            ((GraphPanel) jPanel1).setYmax((max / 5 + 1) * 5);
            ((GraphPanel) jPanel1).setYminor(((max) / 20 + 1) * 5);
            ((GraphPanel) jPanel1).setXminor(10);
        }
        ((GraphPanel) jPanel1).redrawGraph();

        l = g.servicesDistributionInApplications();
        srvuse.setGraphData(l);
        if (scale_graphs) {
            max = 0;
            for (int v : l) {
                if (v > max) {
                    max = v;
                }
            }
            ((GraphPanel) jPanel2).setYmax((max / 5 + 1) * 5);
            ((GraphPanel) jPanel2).setYminor(((max) / 20 + 1) * 5);
            ((GraphPanel) jPanel2).setXminor(10);
        }
        ((GraphPanel) jPanel2).redrawGraph();

        l = g.applicationsLinksCountsDistribution();
        nblinks.setGraphData(l);
        if (scale_graphs) {
            max = 0;
            for (int v : l) {
                if (v > max) {
                    max = v;
                }
            }
            ((GraphPanel) jPanel3).setYmax((max / 5 + 1) * 5);
            ((GraphPanel) jPanel3).setYminor(((max) / 20 + 1) * 5);
            ((GraphPanel) jPanel3).setXminor(10);
        }
        ((GraphPanel) jPanel3).redrawGraph();

        extinction.resetBuffer();
        ((GraphPanel) jPanelExt).redrawGraph();

        jLabelRIndex.setText("");
    }

    public void computeExtinctionSeq(int n, boolean export) {
        ExtinctionSequence[] seqs = graph.performRandomExtinctionSequences(n);
        double[] ext = ExtinctionSequence.averageExtinctionSequences(seqs);
        double result = ExtinctionSequence.averageRobustnessIndex(ext) * 100;
        jLabelRIndex.setText(String.format("%1$,.2f", result) + "%");
        int[] l = new int[ext.length];
        for (int i = 0; i < l.length; i++) {
            l[i] = (int) ext[i];
        }

        extinction.setGraphData(l);
        ((GraphPanel) jPanelExt).setYmax(graph.getApplications().size());
        ((GraphPanel) jPanelExt).setYminor(((graph.getApplications().size()) / 20 + 1) * 5);
        ((GraphPanel) jPanelExt).setXminor(10);
        ((GraphPanel) jPanelExt).redrawGraph();
        
        if (export) {
            File folder = getDataFolder();
            ExtinctionSequence.writeGNUPlotScriptForAll(seqs, folder, graph.getName() + "_" +n+"_extinctions");
        }
    }

    List<BPGraph> collection = null;
    int collection_index = 0;

    public void setBPGraphCollection(List<BPGraph> list, int index, String parameters) {
        collection = list;
        collection_index = index;
        setBPGraph(collection.get(collection_index), true);
        jTextAreaParams.setText(parameters);
    }

    JFileChooser chooser = new JFileChooser();
    Preferences prefs = Preferences.userRoot().node(this.getClass().getName());

    /**
     * Creates new form BPGraphFrame
     */
    public BPGraphFrame() {
        initComponents();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        jTextFieldFolder.setText(prefs.get("DataFolder", ""));
        File folder = new File(jTextFieldFolder.getText());
    }

    public File getDataFolder() {
        File folder = new File(jTextFieldFolder.getText());
        if (!folder.exists() || !folder.isDirectory()) {
            if (chooser.showDialog(this, "OK") == JFileChooser.APPROVE_OPTION) {
                if (!chooser.getSelectedFile().exists() || !chooser.getSelectedFile().isDirectory()) {
                    JOptionPane.showMessageDialog(null, "Please select an existing folder.", "Folder not found", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                jTextFieldFolder.setText(chooser.getSelectedFile().getAbsolutePath());
                folder = chooser.getSelectedFile();
            } else {
                return null; // abort
            }
        }
        prefs.put("DataFolder", folder.getAbsolutePath());
        File subfolder = new File(folder, jTextFieldName.getText().replaceAll("\\W+", ""));
        if (!subfolder.exists()) subfolder.mkdirs();
        FileUtils.writeTextFile(subfolder, "Parameters.txt", jTextAreaParams.getText());
        return subfolder;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldBPgraph = new javax.swing.JTextField();
        jButtonnext = new javax.swing.JButton();
        jButtonprev = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldFolder = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new BarGraphPanel(appsize, "Distribution of the application sizes (# required srv)", 0, 100, 25, Color.GREEN);
        jPanel2 = new BarGraphPanel(srvuse, "Distribution of the services usage", 0, 100, 25, Color.ORANGE);
        jPanel3 = new BarGraphPanel(nblinks, "Distribution of the number of links per application", 0, 100, 25, Color.RED);
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldNExt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabelRIndex = new javax.swing.JLabel();
        jCheckBoxExportToFile = new javax.swing.JCheckBox();
        jPanelExt = new BarGraphPanel(extinction, "Average extinction sequence", 0, 100, 25, new java.awt.Color(0, 153, 255));
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaParams = new javax.swing.JTextArea();
        jPanelMatrix = new BPMatrixGraph();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Bipartite Graph Viewer");

        jLabel1.setText("Bi-Partite Graph: ");

        jTextFieldBPgraph.setEditable(false);

        jButtonnext.setText("Next");
        jButtonnext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonnextActionPerformed(evt);
            }
        });

        jButtonprev.setText("Prev");
        jButtonprev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonprevActionPerformed(evt);
            }
        });

        jLabel3.setText("Data Folder:");

        jButton3.setText("Browse...");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setText("Experiment Name:");

        jTextFieldName.setText("Noname");

        jButton2.setText("Export Graphs");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Export Distributions");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldBPgraph)
                            .addComponent(jTextFieldName)
                            .addComponent(jTextFieldFolder))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButtonprev)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonnext))
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldBPgraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonnext)
                    .addComponent(jButtonprev))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jButton3))
                    .addComponent(jTextFieldFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        jTable1.setModel(apptablemodel);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Applications", jPanel8);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Topology", jPanel5);

        jLabel2.setText("Number of sequences:");

        jTextFieldNExt.setText("1");

        jButton1.setText("Run");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabelRIndex.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N

        jCheckBoxExportToFile.setText("Export to file");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNExt, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 339, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelRIndex)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxExportToFile)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldNExt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jCheckBoxExportToFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelRIndex)
                .addContainerGap())
        );

        jPanelExt.setToolTipText("");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelExt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelExt, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Robustness", jPanel6);

        jTextAreaParams.setEditable(false);
        jTextAreaParams.setColumns(20);
        jTextAreaParams.setRows(5);
        jScrollPane2.setViewportView(jTextAreaParams);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Parameters", jPanel9);

        javax.swing.GroupLayout jPanelMatrixLayout = new javax.swing.GroupLayout(jPanelMatrix);
        jPanelMatrix.setLayout(jPanelMatrixLayout);
        jPanelMatrixLayout.setHorizontalGroup(
            jPanelMatrixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 707, Short.MAX_VALUE)
        );
        jPanelMatrixLayout.setVerticalGroup(
            jPanelMatrixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Matrix", jPanelMatrix);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonnextActionPerformed
        if (collection != null && collection_index < collection.size() - 1) {
            collection_index++;
            setBPGraph(collection.get(collection_index), false);
        }
    }//GEN-LAST:event_jButtonnextActionPerformed

    private void jButtonprevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonprevActionPerformed
        if (collection != null && collection_index > 0) {
            collection_index--;
            setBPGraph(collection.get(collection_index), false);
        }
    }//GEN-LAST:event_jButtonprevActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int n = Integer.parseInt(jTextFieldNExt.getText());
        computeExtinctionSeq(n, jCheckBoxExportToFile.isSelected());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        File folder = new File(jTextFieldFolder.getText());
        if (folder.exists() && folder.isDirectory()) {
            chooser.setSelectedFile(folder);
        }
        if (chooser.showDialog(this, "OK") == JFileChooser.APPROVE_OPTION) {
            jTextFieldFolder.setText(chooser.getSelectedFile().getAbsolutePath());
            prefs.put("DataFolder", folder.getAbsolutePath());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        File folder = getDataFolder();
        if (folder == null) return;
        for (BPGraph g : collection) {
            FileUtils.writeTextFile(folder, g.getName() + ".dot", g.exportToGraphViz());
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        File folder = getDataFolder();
        if (folder == null) return;
        
        int n_run = collection.size();
        
        int[][] apps_srv = new int[n_run][];
        int[][] apps_link = new int[n_run][];
        int[][] srv_dist = new int[n_run][];
        double[] robusness = new double[n_run];
        
        for(int i=0; i<n_run; i++) {
            BPGraph g = collection.get(i);
            apps_srv[i] = g.applicationsServicesCountsDistribution();
            apps_link[i] = g.applicationsLinksCountsDistribution();
            srv_dist[i] = g.servicesDistributionInApplications();
            //ExtinctionSequence[] seqs = g.performRandomExtinctionSequences(n_run);
            //robusness[i] = 100 * ExtinctionSequence.averageRobustnessIndex(ExtinctionSequence.averageExtinctionSequences(seqs));
        }
        
        BPGraph.writeGNUPlotScriptForData(srv_dist, folder, "service_usage_dist");
        BPGraph.writeGNUPlotScriptForData(apps_link, folder, "link_per_app_dist");
        BPGraph.writeGNUPlotScriptForData(apps_srv, folder, "app_size_dist");
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonnext;
    private javax.swing.JButton jButtonprev;
    private javax.swing.JCheckBox jCheckBoxExportToFile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelRIndex;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelExt;
    private javax.swing.JPanel jPanelMatrix;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextAreaParams;
    private javax.swing.JTextField jTextFieldBPgraph;
    private javax.swing.JTextField jTextFieldFolder;
    private javax.swing.JTextField jTextFieldNExt;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
}
