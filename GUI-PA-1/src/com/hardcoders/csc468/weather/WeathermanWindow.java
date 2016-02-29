package com.hardcoders.csc468.weather;

import com.hardcoders.csc468.weather.XMLImport.XmlWeatherDataPoint;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.FILES_AND_DIRECTORIES;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.hardcoders.csc468.weather.graph.Graph;
import com.hardcoders.csc468.weather.graph.InteractiveLineGraph;
import com.hardcoders.csc468.weather.model.WeatherDataPoint;
import java.util.Date;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Mack Smith
 */
public class WeathermanWindow extends javax.swing.JFrame {

    private List<XmlWeatherDataPoint> dataPoints;
    private boolean graphUpdated;
    
    //need to initialize this in WeathermanWindow so that I can pass it data
    //This is for my testing, it can be moved
    //Initialize AverageData.java for necisarry Calculations
    AverageData dataCruncher = new AverageData();
    
    /**
     * Creates new form WeathermanWindow
     */
    public WeathermanWindow() {
        initComponents();

        dataPoints = new ArrayList<XmlWeatherDataPoint>();
        

        
        graphUpdated = false;
                
        startDate.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (graphUpdated) return;
                graphUpdated = true;
                
                lineGraph.setDomainLowerBound((double) ((Date) startDate.getValue()).getTime());
                lineGraph.redraw();
                lineGraph.repaint();
                
                graphUpdated = false;
            }
        });
        endDate.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (graphUpdated) return;
                graphUpdated = true;
                
                lineGraph.setDomainUpperBound((double) ((Date) endDate.getValue()).getTime());
                lineGraph.redraw();
                lineGraph.repaint();
                
                graphUpdated = false;
            }
        });
        lineGraph.addGraphListener(new InteractiveLineGraph.GraphListener() {
            @Override
            public void onGraphChanged(Graph graph) {
                if (graph != lineGraph) return;
                if (graphUpdated) return;
                graphUpdated = true;
                
                if (lineGraph.getDomainLowerBound() != null) {
                    startDate.setValue(new Date((long) lineGraph.getDomainLowerBound().doubleValue()));
                }
                if (lineGraph.getDomainUpperBound() != null) {
                    endDate.setValue(new Date((long) lineGraph.getDomainUpperBound().doubleValue()));
                }
                
                graphUpdated = false;
            }
        });
        lineGraph.addGraphListener(new InteractiveLineGraph.GraphListener() {
            @Override
            public void onGraphChanged(Graph graph) {
                if (graph != lineGraph) return;
                
                WeatherDataPoint dataPoint = lineGraph.getSelectedDataPoint();
                if (dataPoint == null) return;
                
                if (dataPoint.getTemperature() != null) {
                    tempVal.setText(dataPoint.getTemperature().toString());
                }
                if (dataPoint.getHumidity() != null) {
                    humidityVal.setText(dataPoint.getHumidity().toString());
                }
                if (dataPoint.getPressure() != null) {
                    pressureVal.setText(dataPoint.getPressure().toString());
                }
                if (dataPoint.getWindSpeed() != null) {
                    speedVal.setText(dataPoint.getWindSpeed().toString());
                }
                if (dataPoint.getWindGust() != null) {
                    gustVal.setText(dataPoint.getWindGust().toString());
                }
                if (dataPoint.getWindChill() != null) {
                    chillVal.setText(dataPoint.getWindChill().toString());
                }
                if (dataPoint.getHeatIndex() != null) {
                    heatVal.setText(dataPoint.getHeatIndex().toString());
                }
                if (dataPoint.getUVIndex() != null) {
                    uvVal.setText(dataPoint.getUVIndex().toString());
                }
                if (dataPoint.getPercipitation() != null) {
                    rainfallVal.setText(dataPoint.getPercipitation().toString());
                }
            }
        });
        
        /*
        lineGraph.addWeatherDataPoint(new SimpleWeatherDataPoint(new Date(100), 10.0));
        lineGraph.addWeatherDataPoint(new SimpleWeatherDataPoint(new Date(250), 10.0));
        lineGraph.addWeatherDataPoint(new SimpleWeatherDataPoint(new Date(275), 12.0));
        lineGraph.addWeatherDataPoint(new SimpleWeatherDataPoint(new Date(325), 8.0));
        lineGraph.addWeatherDataPoint(new SimpleWeatherDataPoint(new Date(350), 10.0));
        lineGraph.addWeatherDataPoint(new SimpleWeatherDataPoint(new Date(500), 10.0));
        
        lineGraph.setDomainLowerBound(50.0);
        lineGraph.setDomainUpperBound(550.0);
        */
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

        dataOptions = new javax.swing.ButtonGroup();
        temperatureButton = new javax.swing.JRadioButton();
        windSpeedButton = new javax.swing.JRadioButton();
        rainfallButton = new javax.swing.JRadioButton();
        humidityButton = new javax.swing.JRadioButton();
        pressureButton = new javax.swing.JRadioButton();
        windGustButton = new javax.swing.JRadioButton();
        windChillButton = new javax.swing.JRadioButton();
        uvButton = new javax.swing.JRadioButton();
        heatButton = new javax.swing.JRadioButton();
        tempLabel = new javax.swing.JLabel();
        tempVal = new javax.swing.JLabel();
        lineGraph = new com.hardcoders.csc468.weather.WeathermanLineGraph();
        endDate = new javax.swing.JSpinner();
        startDate = new javax.swing.JSpinner();
        humidityLabel = new javax.swing.JLabel();
        rainfallLabel = new javax.swing.JLabel();
        pressureLabel = new javax.swing.JLabel();
        uvLabel = new javax.swing.JLabel();
        heatLabel = new javax.swing.JLabel();
        speedLabel = new javax.swing.JLabel();
        gustLabel = new javax.swing.JLabel();
        chillLabel = new javax.swing.JLabel();
        humidityVal = new javax.swing.JLabel();
        rainfallVal = new javax.swing.JLabel();
        pressureVal = new javax.swing.JLabel();
        uvVal = new javax.swing.JLabel();
        heatVal = new javax.swing.JLabel();
        speedVal = new javax.swing.JLabel();
        gustVal = new javax.swing.JLabel();
        chillVal = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        menuQuit = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dataOptions.add(temperatureButton);
        temperatureButton.setText("Temperature");
        temperatureButton.setToolTipText("Switches graph data to temperature");
        temperatureButton.setBorder(null);
        temperatureButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/temp.png"))); // NOI18N
        temperatureButton.setMaximumSize(new java.awt.Dimension(99, 40));
        temperatureButton.setMinimumSize(new java.awt.Dimension(99, 40));
        temperatureButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/temp-selected.png"))); // NOI18N
        temperatureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temperatureButtonActionPerformed(evt);
            }
        });

        dataOptions.add(windSpeedButton);
        windSpeedButton.setText("Wind Speed");
        windSpeedButton.setToolTipText("Switches graph data to wind speed");
        windSpeedButton.setBorder(null);
        windSpeedButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/windspeed.png"))); // NOI18N
        windSpeedButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/windspeed-selected.png"))); // NOI18N
        windSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                windSpeedButtonActionPerformed(evt);
            }
        });

        dataOptions.add(rainfallButton);
        rainfallButton.setText("Rainfall");
        rainfallButton.setToolTipText("Switches graph data to rainfall");
        rainfallButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/rainfall.png"))); // NOI18N
        rainfallButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/rainfall-selected.png"))); // NOI18N
        rainfallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rainfallButtonActionPerformed(evt);
            }
        });

        dataOptions.add(humidityButton);
        humidityButton.setText("Humidity");
        humidityButton.setToolTipText("Switches graph data to humidity");
        humidityButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/humidity.png"))); // NOI18N
        humidityButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/humidity-selected.png"))); // NOI18N
        humidityButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                humidityButtonActionPerformed(evt);
            }
        });

        dataOptions.add(pressureButton);
        pressureButton.setText("Pressure");
        pressureButton.setToolTipText("Switches graph data to pressure");
        pressureButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/pressure.png"))); // NOI18N
        pressureButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/pressure-selected.png"))); // NOI18N
        pressureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pressureButtonActionPerformed(evt);
            }
        });

        dataOptions.add(windGustButton);
        windGustButton.setText("Wind Gust");
        windGustButton.setToolTipText("Switches graph data to wind gust");
        windGustButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/windgust.png"))); // NOI18N
        windGustButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/windgust-selected.png"))); // NOI18N
        windGustButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                windGustButtonActionPerformed(evt);
            }
        });

        dataOptions.add(windChillButton);
        windChillButton.setText("Wind Chill");
        windChillButton.setToolTipText("Switches graph data to wind chill");
        windChillButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/windchill.png"))); // NOI18N
        windChillButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/windchill-selected.png"))); // NOI18N
        windChillButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                windChillButtonActionPerformed(evt);
            }
        });

        dataOptions.add(uvButton);
        uvButton.setText("UV Index");
        uvButton.setToolTipText("Switches graph data to UV index");
        uvButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/uvindex.png"))); // NOI18N
        uvButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/uvindex-selected.png"))); // NOI18N
        uvButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uvButtonActionPerformed(evt);
            }
        });

        dataOptions.add(heatButton);
        heatButton.setText("Heat Index");
        heatButton.setToolTipText("Switches graph data to heat index");
        heatButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/heatindex.png"))); // NOI18N
        heatButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hardcoders/csc468/weather/icons/heatindex-selected.png"))); // NOI18N
        heatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heatButtonActionPerformed(evt);
            }
        });

        tempLabel.setText("Temperature");

        endDate.setModel(new javax.swing.SpinnerDateModel());
        endDate.setToolTipText("End date");
        endDate.setValue(new Date());

        startDate.setModel(new javax.swing.SpinnerDateModel());
        startDate.setToolTipText("Start date");
        startDate.setValue(new Date());

        javax.swing.GroupLayout lineGraphLayout = new javax.swing.GroupLayout(lineGraph);
        lineGraph.setLayout(lineGraphLayout);
        lineGraphLayout.setHorizontalGroup(
            lineGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lineGraphLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        lineGraphLayout.setVerticalGroup(
            lineGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lineGraphLayout.createSequentialGroup()
                .addGap(0, 157, Short.MAX_VALUE)
                .addGroup(lineGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        humidityLabel.setText("Humidity");

        rainfallLabel.setText("Rainfall");

        pressureLabel.setText("Pressure");

        uvLabel.setText("UV Index");

        heatLabel.setText("Heat Index");

        speedLabel.setText("Wind Speed");

        gustLabel.setText("Wind Gust");

        chillLabel.setText("Wind Chill");

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

        menuQuit.setText("Quit");
        menuQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuQuitActionPerformed(evt);
            }
        });
        fileMenu.add(menuQuit);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");
        menuBar.add(editMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tempLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(humidityLabel)
                            .addComponent(rainfallLabel)
                            .addComponent(pressureLabel)
                            .addComponent(uvLabel)
                            .addComponent(heatLabel))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(heatVal)
                            .addComponent(uvVal)
                            .addComponent(pressureVal)
                            .addComponent(rainfallVal)
                            .addComponent(humidityVal)
                            .addComponent(tempVal))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(speedLabel)
                    .addComponent(gustLabel)
                    .addComponent(chillLabel))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chillVal)
                    .addComponent(gustVal)
                    .addComponent(speedVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 222, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(uvButton)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(temperatureButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(humidityButton))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(windGustButton)
                            .addComponent(windSpeedButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rainfallButton)
                        .addGap(50, 50, 50)
                        .addComponent(windChillButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pressureButton)
                        .addGap(40, 40, 40)
                        .addComponent(heatButton)))
                .addContainerGap())
            .addComponent(lineGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(lineGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tempLabel)
                                    .addComponent(tempVal))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(humidityLabel)
                                    .addComponent(humidityVal))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rainfallLabel)
                                    .addComponent(rainfallVal)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(speedLabel)
                                    .addComponent(speedVal))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(gustLabel)
                                    .addComponent(gustVal))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(chillLabel)
                                    .addComponent(chillVal))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pressureLabel)
                            .addComponent(pressureVal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(uvLabel)
                            .addComponent(uvVal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(heatLabel)
                            .addComponent(heatVal))
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(temperatureButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(windSpeedButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(windGustButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(humidityButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rainfallButton)
                            .addComponent(windChillButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pressureButton)
                            .addComponent(heatButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uvButton)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

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
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(FILES_AND_DIRECTORIES);
        fc.setMultiSelectionEnabled(true);
        FileFilter filter = new FileNameExtensionFilter("Weather files", "xml");
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(openMenuItem);
        
        FilenameFilter xmlFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String lowercaseName = name.toLowerCase();
				if (lowercaseName.endsWith(".xml")) {
					return true;
				} else {
					return false;
				}
			}
        };
        
        
        if ( returnVal == JFileChooser.APPROVE_OPTION )
        {
            XMLImport reader = new XMLImport();
            File[] files = fc.getSelectedFiles();
            List<File> filesForRead = new ArrayList<>();
            //This is where a real application would open the file.
            
            //List though first list of directories - Only directories and xml
            //files will be in the first file list
            for (File file : files) {
                //check if directory
                if (file.isDirectory()){
                    //open directory but only extract xml files
                    File[] moreFiles = (file.listFiles(xmlFilter));
                    //open all xml files and read data into dataPoints
                    for (File moreFile : moreFiles){
                        //System.out.println( moreFile );
                        filesForRead.add(moreFile);
                    }
                }
                else {
                    filesForRead.add(file);
                }
                
                dataPoints = reader.readAll(filesForRead);
            }

            lineGraph.addWeatherDataPoints(dataPoints);
            //System.out.println("right before redraw");
            lineGraph.redraw();
            
            //System.out.println( "Opening: " + files[1].getName() + "." + "\n" );
            
            //Testing AverageData class with data here. Might actually be right place for it
            dataCruncher.calculateData(dataPoints);
            //Line below needs to be removed after testing
            dataCruncher.getAllDataValues(dataPoints);
        }
        else
        {
            System.out.println( "Open command cancelled by user." + "\n" );
        }
    }//GEN-LAST:event_openMenuItemActionPerformed

    
    private void menuQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuQuitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_menuQuitActionPerformed

    private void temperatureButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temperatureButtonActionPerformed
        // TODO add your handling code here:
        lineGraph.showTemperatureData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_temperatureButtonActionPerformed

    private void pressureButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pressureButtonActionPerformed
        // TODO add your handling code here:
        lineGraph.showPressureData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_pressureButtonActionPerformed

    private void uvButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uvButtonActionPerformed
        // TODO add your handling code here:
        lineGraph.showUVIndexData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_uvButtonActionPerformed

    private void windSpeedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_windSpeedButtonActionPerformed
        // TODO add your handling code here:
        lineGraph.showWindSpeedData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_windSpeedButtonActionPerformed

    private void windChillButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_windChillButtonActionPerformed
        // TODO add your handling code here:
        lineGraph.showWindChillData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_windChillButtonActionPerformed

    private void humidityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_humidityButtonActionPerformed
        // TODO add your handling code here:
        lineGraph.showHumidityData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_humidityButtonActionPerformed

    private void windGustButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_windGustButtonActionPerformed
        // TODO add your handling code here:
        lineGraph.showWindGustData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_windGustButtonActionPerformed

    private void rainfallButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rainfallButtonActionPerformed
        // TODO add your handling code here:
        lineGraph.showPercipitationData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_rainfallButtonActionPerformed

    private void heatButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heatButtonActionPerformed
        // TODO add your handling code here:
        lineGraph.showHeatIndexData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_heatButtonActionPerformed


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
    private javax.swing.JLabel chillLabel;
    private javax.swing.JLabel chillVal;
    private javax.swing.ButtonGroup dataOptions;
    private javax.swing.JMenu editMenu;
    private javax.swing.JSpinner endDate;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel gustLabel;
    private javax.swing.JLabel gustVal;
    private javax.swing.JRadioButton heatButton;
    private javax.swing.JLabel heatLabel;
    private javax.swing.JLabel heatVal;
    private javax.swing.JRadioButton humidityButton;
    private javax.swing.JLabel humidityLabel;
    private javax.swing.JLabel humidityVal;
    private com.hardcoders.csc468.weather.WeathermanLineGraph lineGraph;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuQuit;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JRadioButton pressureButton;
    private javax.swing.JLabel pressureLabel;
    private javax.swing.JLabel pressureVal;
    private javax.swing.JRadioButton rainfallButton;
    private javax.swing.JLabel rainfallLabel;
    private javax.swing.JLabel rainfallVal;
    private javax.swing.JLabel speedLabel;
    private javax.swing.JLabel speedVal;
    private javax.swing.JSpinner startDate;
    private javax.swing.JLabel tempLabel;
    private javax.swing.JLabel tempVal;
    private javax.swing.JRadioButton temperatureButton;
    private javax.swing.JRadioButton uvButton;
    private javax.swing.JLabel uvLabel;
    private javax.swing.JLabel uvVal;
    private javax.swing.JRadioButton windChillButton;
    private javax.swing.JRadioButton windGustButton;
    private javax.swing.JRadioButton windSpeedButton;
    // End of variables declaration//GEN-END:variables
}
