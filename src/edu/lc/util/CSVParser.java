package edu.lc.util;

import edu.lc.entity.RealLayerFeatures;
import edu.lc.entity.TrainingLayerFeatures;
import javafx.collections.ObservableList;

import java.io.*;

/** 
 * Parses Observable Lists to CSV
 */
public class CSVParser {
  public static String filename;

  /** 
   * Writes data to CSV file
   * @param list - data 
  */
  public static <T> void list2CSVFile(ObservableList<T> list) {
    try (Writer writer = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(filename), "utf-8"))) {
      for (T item : list)
        writer.write(item.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** 
   * Reading data from CSV file
   * and parsing to Real table rows
   * @param list - data
   */
  public static void CSVFile2RList(ObservableList<RealLayerFeatures> list) {
    try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line = br.readLine();

        while (line != null) {
          String[] data = line.split(",");
          
          list.add(new RealLayerFeatures(
              Integer.parseInt(data[0]),
              Double.parseDouble(data[1]),
              Double.parseDouble(data[2]),
              Double.parseDouble(data[3]),
              Double.parseDouble(data[4])
          ));
          
            line = br.readLine();
        }
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }

  /** 
   * Reading data from CSV file
   * and parsing to Training table rows
   * @param list - data
   */
  public static void CSVFile2TList(ObservableList<TrainingLayerFeatures> list) {
    try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line = br.readLine();

        while (line != null) {
          String[] data = line.split(",");
          
          list.add(new TrainingLayerFeatures(
              Integer.parseInt(data[0]),
              Double.parseDouble(data[1]),
              Double.parseDouble(data[2]),
              Double.parseDouble(data[3]),
              Double.parseDouble(data[4]),
              Integer.parseInt(data[5])
          ));
          
            line = br.readLine();
        }
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
}