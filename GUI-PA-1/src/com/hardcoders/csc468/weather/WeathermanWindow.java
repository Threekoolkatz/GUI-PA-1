package com.hardcoders.csc468.weather;

import java.util.Date;

/**
 *
 * @author Mack Smith
 */
public class WeathermanWindow extends javax.swing.JFrame {

    /**
     * Creates new form WeathermanWindow
     */
    public WeathermanWindow() {
        initComponents();
        
        lineGraph.addDataPoint(new SimpleWeatherDataPoint(new Date(100), 10.0).getTemperatureAsDataPoint());
        lineGraph.addDataPoint(new SimpleWeatherDataPoint(new Date(250), 10.0).getTemperatureAsDataPoint());
        lineGraph.addDataPoint(new SimpleWeatherDataPoint(new Date(275), 12.0).getTemperatureAsDataPoint());
        lineGraph.addDataPoint(new SimpleWeatherDataPoint(new Date(325), 8.0).getTemperatureAsDataPoint());
        lineGraph.addDataPoint(new SimpleWeatherDataPoint(new Date(350), 10.0).getTemperatureAsDataPoint());
        lineGraph.addDataPoint(new SimpleWeatherDataPoint(new Date(500), 10.0).getTemperatureAsDataPoint());
        
        lineGraph.setDomainLowerBound(new Date(50));
        lineGraph.setDomainUpperBound(new Date(550));
        lineGraph.setRangeUpperBound(15.0);
        lineGraph.setRangeLowerBound(5.0);
        
        lineGraph.redraw();
    }
    
    /**
     *  Creates new form WeathermanWindow with a title
    */
    public WeathermanWindow(String title) {
        this();
        this.setTitle(title);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tempButton = new javax.swing.JButton();
        windSpeedButton = new javax.swing.JButton();
        pressureButton = new javax.swing.JButton();
        windGustButton = new javax.swing.JButton();
        humidityButton = new javax.swing.JButton();
        windChillButton = new javax.swing.JButton();
        heatIndexButton = new javax.swing.JButton();
        uvIndexButton = new javax.swing.JButton();
        rainfallButton = new javax.swing.JButton();
        lineGraph = new com.hardcoders.csc468.weather.graph.LineGraph();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tempButton.setText("Temperature");
        tempButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tempButtonActionPerformed(evt);
            }
        });

        windSpeedButton.setText("Wind Speed");
        windSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                windSpeedButtonActionPerformed(evt);
            }
        });

        pressureButton.setText("Pressure");
        pressureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pressureButtonActionPerformed(evt);
            }
        });

        windGustButton.setText("Wind Gust");
        windGustButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                windGustButtonActionPerformed(evt);
            }
        });

        humidityButton.setText("Humidity");
        humidityButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                humidityButtonActionPerformed(evt);
            }
        });

        windChillButton.setText("Wind Chill");
        windChillButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                windChillButtonActionPerformed(evt);
            }
        });

        heatIndexButton.setText("Heat Index");
        heatIndexButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heatIndexButtonActionPerformed(evt);
            }
        });

        uvIndexButton.setText("UV Index");
        uvIndexButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uvIndexButtonActionPerformed(evt);
            }
        });

        rainfallButton.setText("Rainfall");
        rainfallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rainfallButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout lineGraphLayout = new javax.swing.GroupLayout(lineGraph);
        lineGraph.setLayout(lineGraphLayout);
        lineGraphLayout.setHorizontalGroup(
            lineGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        lineGraphLayout.setVerticalGroup(
            lineGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 237, Short.MAX_VALUE)
        );

        fileMenu.setText("File");
        fileMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fileMenuMouseClicked(evt);
            }
        });

        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");
        menuBar.add(editMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tempButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(windSpeedButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(windChillButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(windGustButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(humidityButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(uvIndexButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rainfallButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pressureButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(heatIndexButton)
                .addContainerGap(27, Short.MAX_VALUE))
            .addComponent(lineGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(lineGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tempButton)
                    .addComponent(windSpeedButton)
                    .addComponent(pressureButton)
                    .addComponent(windGustButton)
                    .addComponent(humidityButton)
                    .addComponent(windChillButton)
                    .addComponent(heatIndexButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uvIndexButton)
                    .addComponent(rainfallButton))
                .addGap(83, 83, 83))
        );

        // Here is where i am going to add the new stuff

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * Opens the File menu from the menu bar
     * @param evt the mouse click event
     */
    private void fileMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fileMenuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_fileMenuMouseClicked

    /**
     * The Open action from the File menu
     * @param evt 
     */
    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_openMenuItemActionPerformed

    /**
     * Temperature button action
     * @param evt 
     */
    private void tempButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tempButtonActionPerformed
        // TODO add your handling code here:
        // Switches graph data to temperature
        System.out.println("Switch to temperature");
    }//GEN-LAST:event_tempButtonActionPerformed

    /**
     * Wind Speed button action
     * @param evt 
     */
    private void windSpeedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_windSpeedButtonActionPerformed
        // TODO add your handling code here:
        // Switches graph data to wind speed
        System.out.println("Switch to wind speed");
    }//GEN-LAST:event_windSpeedButtonActionPerformed

    /**
     * Pressure button action
     * @param evt 
     */
    private void pressureButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pressureButtonActionPerformed
        // TODO add your handling code here:
        // Switches graph data to barometric pressure
        System.out.println("Switching to pressure data");
    }//GEN-LAST:event_pressureButtonActionPerformed

    /**
     * Wind gust action
     * @param evt 
     */
    private void windGustButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_windGustButtonActionPerformed
        // TODO add your handling code here:
        // Switch graph data to wind gust data
        System.out.println("Switching to wind gust");
    }//GEN-LAST:event_windGustButtonActionPerformed

    /** 
     * Humidity button action
     * @param evt 
     */
    private void humidityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_humidityButtonActionPerformed
        // TODO add your handling code here:
        // Switch graph data to humidity data
        System.out.println("Switching to humidity");
    }//GEN-LAST:event_humidityButtonActionPerformed

    /**
     * Wind chill button action
     * @param evt 
     */
    private void windChillButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_windChillButtonActionPerformed
        // TODO add your handling code here:
        // Switch graph data to wind chill
        System.out.println("Switching to wind chill data");
    }//GEN-LAST:event_windChillButtonActionPerformed

    
    /**
     * Head index button action
     * @param evt 
     */
    private void heatIndexButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heatIndexButtonActionPerformed
        // TODO add your handling code here:
        // Switch graph data to heat index data
        System.out.println("Switchin to head index data");
    }//GEN-LAST:event_heatIndexButtonActionPerformed

    /**
     * UV index button action
     * @param evt 
     */
    private void uvIndexButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uvIndexButtonActionPerformed
        // TODO add your handling code here:
        // Switch graph data to UV index data
        System.out.println("Switching to UV index data");
    }//GEN-LAST:event_uvIndexButtonActionPerformed

    /**
     * Rainfall button action
     * @param evt 
     */
    private void rainfallButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rainfallButtonActionPerformed
        // TODO add your handling code here:
        // Switch to rainfall data
        System.out.println("Switching to rainfall data");
    }//GEN-LAST:event_rainfallButtonActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WeathermanWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WeathermanWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WeathermanWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WeathermanWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WeathermanWindow("Weatherman App").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton heatIndexButton;
    private javax.swing.JButton humidityButton;
    private com.hardcoders.csc468.weather.graph.LineGraph lineGraph;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JButton pressureButton;
    private javax.swing.JButton rainfallButton;
    private javax.swing.JButton tempButton;
    private javax.swing.JButton uvIndexButton;
    private javax.swing.JButton windChillButton;
    private javax.swing.JButton windGustButton;
    private javax.swing.JButton windSpeedButton;
    // End of variables declaration//GEN-END:variables
}
