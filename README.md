# GUI-PA-1

Introduction
Weather impacts many aspects of our daily lives: where we choose to live, the activities we
participate in, how we dress, the food we eat, conversation topics, etc. Sophisticated personal
weather stations are available for home use at reasonable prices, and can be connected to webbased
worldwide live weather station networks. Weather station hardware includes sensors for
temperature, humidity, barometric pressure, wind speed and direction, rainfall, and other
weather-related measurements. This data may be logged to a computer for analysis and display.
Problem
Write a Java Swing program to display weather station data in graphical form. The display
format is up to you, but should resemble the graphs available on most Internet weather sites (see
screen shots below). Allow the user to plot any of the following measurements: temperature,
humidity, barometric pressure, wind speed, UV index, and rainfall. Provide controls to toggle
(zoom) between daily, weekly, monthly, and yearly displays, and move (pan) from one
day/week/month/year to the next. Clicking (or hovering) on a specific data point should bring up
a message box with all the available information about that particular time point. Provide menu
options to compute the following weather statistics:
 daily/weekly/monthly/yearly mean (average) temperature
 daily/weekly/monthly/yearly high/low temperatures, with date/time of occurrence
 daily/weekly/monthly/yearly mean (average) wind speed
 daily/weekly/monthly/yearly maximum wind speed (gust), with date/time of occurrence
 daily/weekly/monthly/yearly prevailing wind direction
 daily/weekly/monthly/yearly rainfall
Weather station data has been logged every few minutes for several years at SDSM&T. This data
has been converted to XML format, and stored in files named with the year and month of data
collection, using the format YYYY-MM.xml (e.g., 2015-10.xml for October 2015). XML weather
tags include:
 date – MM/DD/YY
 time – HH:MM followed by A (AM) or P (PM)
 temperature – in degrees Farenheit
 humidity – relative percent humidity
 barometer – barometric pressure, in inches of mercury
 windspeed – in mph
 winddirection – one of 16 compass directions (N, NNE, NE, etc.)
 windgust – highest wind speed since last measurement, in mph
 windchill – computed from temperature and wind speed
 heatindex – computed from temperature, humidity, wind speed, etc.
 uvindex – UV radiation dose, in arbitrary units from 0 (lowest) to 15 (highest)
 rainfall – amount of precipitation since last measurement, to nearest 0.01 inch
Not all tags need be present for every time point. By default, your program should access all
weather data files in the current directory. Allow the user to specify other weather data
directories with a file dialog.