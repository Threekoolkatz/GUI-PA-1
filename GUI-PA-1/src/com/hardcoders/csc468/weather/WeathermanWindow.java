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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static java.util.Calendar.*;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Displays the view for the graph and data to reside in.
 * It handles fetching the data from a file open command and passing that data
 * to the graph. Pretty much the main file of the program.
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
                setCalculatedData();
            }
        });
        
        lineGraph.redraw();
    }
    
    /**
     * Checks which mode the graph is in (Days, Weeks, Months, Years)
     *  Then displays the calculations based on the selected point.
     */
    public void setCalculatedData()
    {
        List<AverageData.CalculatedAverageWeatherData> tempList;
        WeatherDataPoint dataPoint = lineGraph.getSelectedDataPoint();
        Calendar currentDataPointCalendar = Calendar.getInstance();
        currentDataPointCalendar.setTime(dataPoint.getTimestamp());
        Calendar dateInListCalendar = Calendar.getInstance();
        
        if (dayTab.isSelected())
        {
            tempList = dataCruncher.getDailyCalculations();
            for(AverageData.CalculatedAverageWeatherData tempPoint : tempList){
                dateInListCalendar.setTime(tempPoint.getHighTemperature().getTimestamp());
                if(dateInListCalendar.get(DAY_OF_MONTH)== 
                        currentDataPointCalendar.get(DAY_OF_MONTH) &&
                        dateInListCalendar.get(YEAR)== 
                        currentDataPointCalendar.get(YEAR) &&
                        dateInListCalendar.get(MONTH)== 
                        currentDataPointCalendar.get(MONTH)){
                    //Set Data Now
                    displayCalculatedData(tempPoint);
                }
                
            }
        }
        else if (weekTab.isSelected()){
            tempList = dataCruncher.getWeeklyCalculations();
            for(AverageData.CalculatedAverageWeatherData tempPoint : tempList){
                dateInListCalendar.setTime(tempPoint.getHighTemperature().getTimestamp());
                if(dateInListCalendar.get(WEEK_OF_YEAR)== 
                        currentDataPointCalendar.get(WEEK_OF_YEAR) &&
                        dateInListCalendar.get(YEAR)== 
                        currentDataPointCalendar.get(YEAR)){
                    //Set Data Now
                    displayCalculatedData(tempPoint);
                }
            }
        }
        else if (monthTab.isSelected()){
            tempList = dataCruncher.getMonthlyCalculations();
            for(AverageData.CalculatedAverageWeatherData tempPoint : tempList){
                dateInListCalendar.setTime(tempPoint.getHighTemperature().getTimestamp());
                if(dateInListCalendar.get(MONTH)== 
                        currentDataPointCalendar.get(MONTH) &&
                        dateInListCalendar.get(YEAR)== 
                        currentDataPointCalendar.get(YEAR)){
                    //Set Data Now
                    displayCalculatedData(tempPoint);
                }
            }
        }
        else if (yearTab.isSelected()){
            tempList = dataCruncher.getYearlyCalculations();
            for(AverageData.CalculatedAverageWeatherData tempPoint : tempList){
                dateInListCalendar.setTime(tempPoint.getHighTemperature().getTimestamp());
                if(dateInListCalendar.get(YEAR)== 
                        currentDataPointCalendar.get(YEAR)){
                    //Set Data Now
                    displayCalculatedData(tempPoint);
                }
            }
        }
        else
            displayCalculatedData(dataCruncher.getAllDataValues(dataPoints));
    }
    
    /**
     * Uses the data from displayPoint to changed the labels related to
     *  the calculated data
     * @param displayPoint 
     */
    public void displayCalculatedData(AverageData.CalculatedAverageWeatherData displayPoint){
        if (displayPoint == null) return;
        
        Double tempVal = displayPoint.getAverageTemperature();
        averageTempVal.setText(tempVal.toString());
        
        String tempString;
        
        if( displayPoint.getHighTemperature() != null && 
                displayPoint.getHighTemperature().getTemperature() != null){
            tempString = (displayPoint.getHighTemperature().getTemperature().toString());
            tempString += " " + displayPoint.getHighTemperature().getTimestamp().toString();
            highTempVal.setText(tempString);
        }
        
        if( displayPoint.getLowTemperature() != null && 
                displayPoint.getLowTemperature().getTemperature() != null){
            tempString = (displayPoint.getLowTemperature().getTemperature().toString());
            tempString += " " + displayPoint.getLowTemperature().getTimestamp().toString();
            lowTempVal.setText(tempString);
        }
        
        tempVal = displayPoint.getAverageWindSpeed();
        averageWindSpeedVal.setText(tempVal.toString());
        
        if( displayPoint.getMaxWindGust() != null &&
                displayPoint.getMaxWindGust().getWindGust() != null)
        {
            tempString = (displayPoint.getMaxWindGust().getWindGust().toString());
            tempString += " " + displayPoint.getMaxWindGust().getTimestamp().toString();
            maxWindSpeedVal.setText(tempString);
        }
        
        tempString = (displayPoint.getPrevailingWindDirection().toString());
        prevailingWindDirectionVal.setText(tempString);
        
        tempVal = (displayPoint.getTotalRainFall());
        totalRainfallVal.setText(tempVal.toString());
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
        dateTabs = new javax.swing.ButtonGroup();
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
        dayTab = new javax.swing.JRadioButton();
        monthTab = new javax.swing.JRadioButton();
        yearTab = new javax.swing.JRadioButton();
        averageTempLabel = new javax.swing.JLabel();
        highTempLabel = new javax.swing.JLabel();
        lowTempLabel = new javax.swing.JLabel();
        maxWindSpeedLabel = new javax.swing.JLabel();
        prevailingWindLabel = new javax.swing.JLabel();
        totalRainfallLabel = new javax.swing.JLabel();
        averageWindSpeedLabel = new javax.swing.JLabel();
        graphPanel = new javax.swing.JPanel();
        lineGraph = new com.hardcoders.csc468.weather.WeathermanLineGraph();
        endDate = new javax.swing.JSpinner();
        startDate = new javax.swing.JSpinner();
        yAxisLabel = new javax.swing.JLabel();
        xAxisLabel = new javax.swing.JLabel();
        weekTab = new javax.swing.JRadioButton();
        averageTempVal = new javax.swing.JLabel();
        highTempVal = new javax.swing.JLabel();
        lowTempVal = new javax.swing.JLabel();
        averageWindSpeedVal = new javax.swing.JLabel();
        maxWindSpeedVal = new javax.swing.JLabel();
        prevailingWindDirectionVal = new javax.swing.JLabel();
        totalRainfallVal = new javax.swing.JLabel();
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

        humidityLabel.setText("Humidity");

        rainfallLabel.setText("Rainfall");

        pressureLabel.setText("Pressure");

        uvLabel.setText("UV Index");

        heatLabel.setText("Heat Index");

        speedLabel.setText("Wind Speed");

        gustLabel.setText("Wind Gust");

        chillLabel.setText("Wind Chill");

        dateTabs.add(dayTab);
        dayTab.setText("Day");
        dayTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dayTabActionPerformed(evt);
            }
        });

        dateTabs.add(monthTab);
        monthTab.setText("Month");
        monthTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthTabActionPerformed(evt);
            }
        });

        dateTabs.add(yearTab);
        yearTab.setText("Year");
        yearTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearTabActionPerformed(evt);
            }
        });

        averageTempLabel.setText("Average Temperature");

        highTempLabel.setText("High Temperature");

        lowTempLabel.setText("Low Temperature");

        maxWindSpeedLabel.setText("Maximum Wind Speed");

        prevailingWindLabel.setText("Prevailing Wind Direction");

        totalRainfallLabel.setText("Rainfall");

        averageWindSpeedLabel.setText("Mean Wind Speed");

        graphPanel.setLayout(new java.awt.BorderLayout());

        lineGraph.setBackground(new java.awt.Color(255, 255, 255));
        lineGraph.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

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
                .addContainerGap(484, Short.MAX_VALUE)
                .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        lineGraphLayout.setVerticalGroup(
            lineGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lineGraphLayout.createSequentialGroup()
                .addGap(0, 221, Short.MAX_VALUE)
                .addGroup(lineGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        graphPanel.add(lineGraph, java.awt.BorderLayout.CENTER);

        yAxisLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yAxisLabel.setText("No Data Selected");
        graphPanel.add(yAxisLabel, java.awt.BorderLayout.LINE_START);

        xAxisLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        xAxisLabel.setText("Time");
        graphPanel.add(xAxisLabel, java.awt.BorderLayout.SOUTH);

        dateTabs.add(weekTab);
        weekTab.setText("Week");
        weekTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weekTabActionPerformed(evt);
            }
        });

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tempLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(averageTempLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(averageTempVal))
                            .addComponent(highTempLabel)
                            .addComponent(lowTempLabel)
                            .addComponent(averageWindSpeedLabel))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(speedLabel)
                            .addComponent(gustLabel)
                            .addComponent(chillLabel))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chillVal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(weekTab))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(gustVal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dayTab))
                            .addComponent(speedVal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yearTab)
                            .addComponent(monthTab))
                        .addGap(45, 45, 45))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(humidityLabel)
                                    .addComponent(rainfallLabel)
                                    .addComponent(pressureLabel)
                                    .addComponent(uvLabel)
                                    .addComponent(heatLabel)
                                    .addComponent(prevailingWindLabel)
                                    .addComponent(totalRainfallLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(prevailingWindDirectionVal)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(highTempVal)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(heatVal)
                                            .addComponent(pressureVal)
                                            .addComponent(rainfallVal)
                                            .addComponent(humidityVal)
                                            .addComponent(tempVal)
                                            .addComponent(uvVal))
                                        .addComponent(lowTempVal))
                                    .addComponent(totalRainfallVal)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(averageWindSpeedVal)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(maxWindSpeedLabel)
                                    .addGap(18, 18, 18)
                                    .addComponent(maxWindSpeedVal))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(averageTempLabel)
                            .addComponent(averageTempVal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(highTempLabel)
                            .addComponent(highTempVal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lowTempLabel)
                            .addComponent(lowTempVal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(averageWindSpeedLabel)
                            .addComponent(averageWindSpeedVal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(maxWindSpeedLabel)
                            .addComponent(maxWindSpeedVal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(prevailingWindLabel)
                            .addComponent(prevailingWindDirectionVal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalRainfallLabel)
                            .addComponent(totalRainfallVal)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(temperatureButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(windSpeedButton)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(dayTab)
                                    .addComponent(monthTab))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(weekTab)
                                    .addComponent(yearTab))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(windGustButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(humidityButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rainfallButton)
                            .addComponent(windChillButton))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pressureButton)
                            .addComponent(heatButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uvButton)))
                .addContainerGap(29, Short.MAX_VALUE))
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
            
            Collections.sort(dataPoints);
            //Testing AverageData class with data here. Might actually be right place for it
            dataCruncher.calculateData(dataPoints);
        }
        else
        {
            System.out.println( "Open command cancelled by user." + "\n" );
        }
    }//GEN-LAST:event_openMenuItemActionPerformed

    /**
     * Action exits the program.
     * @param evt 
     */
    private void menuQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuQuitActionPerformed
        // exit program
        System.exit(0);
    }//GEN-LAST:event_menuQuitActionPerformed

    /**
     * Switches the graph to temperature mode
     * @param evt 
     */
    private void temperatureButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temperatureButtonActionPerformed
        yAxisLabel.setText("Temperature");
        lineGraph.showTemperatureData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_temperatureButtonActionPerformed

    /**
     * Switches the graph to Barometric Pressure mode
     * @param evt 
     */
    private void pressureButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pressureButtonActionPerformed
        yAxisLabel.setText("Pressure");
        lineGraph.showPressureData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_pressureButtonActionPerformed

    /**
     * Switches the graph to display the UV index
     * @param evt 
     */
    private void uvButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uvButtonActionPerformed
        yAxisLabel.setText("UV Index");
        lineGraph.showUVIndexData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_uvButtonActionPerformed

    /**
     * Switches the graph to wind speed mode
     * @param evt 
     */
    private void windSpeedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_windSpeedButtonActionPerformed
        yAxisLabel.setText("Wind Speed");
        lineGraph.showWindSpeedData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_windSpeedButtonActionPerformed

    /**
     * Switches the graph to Wind Chill mode
     * @param evt 
     */
    private void windChillButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_windChillButtonActionPerformed
        yAxisLabel.setText("Wind Chill");
        lineGraph.showWindChillData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_windChillButtonActionPerformed

    /**
     * Switches the graph to humidity mode
     * @param evt 
     */
    private void humidityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_humidityButtonActionPerformed
        yAxisLabel.setText("Humidity");
        lineGraph.showHumidityData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_humidityButtonActionPerformed

    /**
     * Switches the graph to Wind Gust Display
     * @param evt 
     */
    private void windGustButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_windGustButtonActionPerformed
        yAxisLabel.setText("Wind Gust");
        lineGraph.showWindGustData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_windGustButtonActionPerformed

    /**
     * Switches the graph to display rainfall
     * @param evt 
     */
    private void rainfallButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rainfallButtonActionPerformed
        yAxisLabel.setText("Rainfall");
        lineGraph.showPercipitationData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_rainfallButtonActionPerformed

    /**
     * Switches the graph to Show the Head Index
     * @param evt 
     */
    private void heatButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heatButtonActionPerformed
        yAxisLabel.setText("Heat Index");
        lineGraph.showHeatIndexData();
        lineGraph.redraw();
        lineGraph.repaint();
    }//GEN-LAST:event_heatButtonActionPerformed

    /**
     * @Author Mack Smith
     * 
     * Changes the graph scale to one month based on the date in the left spinner
     * 
     * @param evt 
     */
    private void dayTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dayTabActionPerformed
        // TODO add your handling code here:
        xAxisLabel.setText("Time(1 Day Interval)");
        // Had to do a series of conversion to get the date spinner to give the 
        // necessary data type.  Started as object reference, result is a calendar 
        // object
          DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
          String low = date.format(startDate.getValue());
          Calendar min = Calendar.getInstance();

          Date temp = null;
          
        try {
            temp = date.parse(low);
        } catch (ParseException ex) {
            Logger.getLogger(WeathermanWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          min.setTime(temp);          
          
          min.add(Calendar.DAY_OF_MONTH, 1);

          double newLow = 0;
          newLow = (double) min.getTimeInMillis();
          lineGraph.setDomainUpperBound(newLow);
          lineGraph.redraw();
          lineGraph.repaint();
    }//GEN-LAST:event_dayTabActionPerformed

    /**
     * @Author Mack Smith
     * 
     * Changes the graph scale to one month based on the date in the left spinner
     * 
     * @param evt 
     */
    private void monthTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthTabActionPerformed
        // TODO add your handling code here:
          xAxisLabel.setText("Time(1 Month Interval)");
        // Had to do a series of conversion to get the date spinner to give the 
        // necessary data type.  Started as object reference, result is a calendar 
        // object
          DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
          String low = date.format(startDate.getValue());
          Calendar min = Calendar.getInstance();

          Date temp = null;
          
        try {
            temp = date.parse(low);
        } catch (ParseException ex) {
            Logger.getLogger(WeathermanWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          min.setTime(temp);          
          
          min.add(Calendar.MONTH, 1);

          double newLow = 0;
          newLow = (double) min.getTimeInMillis();
          lineGraph.setDomainUpperBound(newLow);
          lineGraph.redraw();
          lineGraph.repaint();
    }//GEN-LAST:event_monthTabActionPerformed

    
    /**
     * Author:  Mack Smith
     * 
     * Changes graph scale to yearly based on the initial date in the left spinner
    */
    private void yearTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearTabActionPerformed
        // TODO add your handling code here:
        xAxisLabel.setText("Time(1 Year Interval)");
        // Had to do a series of conversion to get the date spinner to give the 
        // necessary data type.  Started as object reference, result is a calendar 
        // object
          DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
          String low = date.format(startDate.getValue());
          Calendar min = Calendar.getInstance();

          Date temp = null;
        // parsing the string from the spinner, NetBeans complained without the try catch block
        try {
            temp = date.parse(low);
        } catch (ParseException ex) {
            Logger.getLogger(WeathermanWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          // set the calendar to the parsed date
          min.setTime(temp);          
          
          // add a year to the calendar
          min.add(Calendar.YEAR, 1);

          // cast the calendar to a double by using the getTimeInMillis and redraw
          double newLow = 0;
          newLow = (double) min.getTimeInMillis();
          lineGraph.setDomainUpperBound(newLow);
          lineGraph.redraw();
          lineGraph.repaint();
    }//GEN-LAST:event_yearTabActionPerformed

    private void weekTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weekTabActionPerformed
        // TODO add your handling code here:
        xAxisLabel.setText("Time(1 Week Interval)");
        // Had to do a series of conversion to get the date spinner to give the 
        // necessary data type.  Started as object reference, result is a calendar 
        // object
          DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
          String low = date.format(startDate.getValue());
          Calendar min = Calendar.getInstance();

          Date temp = null;
        // parsing the string from the spinner, NetBeans complained without the try catch block
        try {
            temp = date.parse(low);
        } catch (ParseException ex) {
            Logger.getLogger(WeathermanWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          // set the calendar to the parsed date
          min.setTime(temp);          
          
          // add a year to the calendar
          min.add(Calendar.WEEK_OF_YEAR, 1);

          // cast the calendar to a double by using the getTimeInMillis and redraw
          double newLow = 0;
          newLow = (double) min.getTimeInMillis();
          lineGraph.setDomainUpperBound(newLow);
          lineGraph.redraw();
          lineGraph.repaint();
    }//GEN-LAST:event_weekTabActionPerformed


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
    private javax.swing.JLabel averageTempLabel;
    private javax.swing.JLabel averageTempVal;
    private javax.swing.JLabel averageWindSpeedLabel;
    private javax.swing.JLabel averageWindSpeedVal;
    private javax.swing.JLabel chillLabel;
    private javax.swing.JLabel chillVal;
    private javax.swing.ButtonGroup dataOptions;
    private javax.swing.ButtonGroup dateTabs;
    private javax.swing.JRadioButton dayTab;
    private javax.swing.JMenu editMenu;
    private javax.swing.JSpinner endDate;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JLabel gustLabel;
    private javax.swing.JLabel gustVal;
    private javax.swing.JRadioButton heatButton;
    private javax.swing.JLabel heatLabel;
    private javax.swing.JLabel heatVal;
    private javax.swing.JLabel highTempLabel;
    private javax.swing.JLabel highTempVal;
    private javax.swing.JRadioButton humidityButton;
    private javax.swing.JLabel humidityLabel;
    private javax.swing.JLabel humidityVal;
    private com.hardcoders.csc468.weather.WeathermanLineGraph lineGraph;
    private javax.swing.JLabel lowTempLabel;
    private javax.swing.JLabel lowTempVal;
    private javax.swing.JLabel maxWindSpeedLabel;
    private javax.swing.JLabel maxWindSpeedVal;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuQuit;
    private javax.swing.JRadioButton monthTab;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JRadioButton pressureButton;
    private javax.swing.JLabel pressureLabel;
    private javax.swing.JLabel pressureVal;
    private javax.swing.JLabel prevailingWindDirectionVal;
    private javax.swing.JLabel prevailingWindLabel;
    private javax.swing.JRadioButton rainfallButton;
    private javax.swing.JLabel rainfallLabel;
    private javax.swing.JLabel rainfallVal;
    private javax.swing.JLabel speedLabel;
    private javax.swing.JLabel speedVal;
    private javax.swing.JSpinner startDate;
    private javax.swing.JLabel tempLabel;
    private javax.swing.JLabel tempVal;
    private javax.swing.JRadioButton temperatureButton;
    private javax.swing.JLabel totalRainfallLabel;
    private javax.swing.JLabel totalRainfallVal;
    private javax.swing.JRadioButton uvButton;
    private javax.swing.JLabel uvLabel;
    private javax.swing.JLabel uvVal;
    private javax.swing.JRadioButton weekTab;
    private javax.swing.JRadioButton windChillButton;
    private javax.swing.JRadioButton windGustButton;
    private javax.swing.JRadioButton windSpeedButton;
    private javax.swing.JLabel xAxisLabel;
    private javax.swing.JLabel yAxisLabel;
    private javax.swing.JRadioButton yearTab;
    // End of variables declaration//GEN-END:variables
}
