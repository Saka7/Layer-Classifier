package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import beans.RealLayerFeatures;
import beans.TrainingLayerFeatures;
import javafx.collections.ObservableList;

/** Клас для запису в CSV-файли та парсингу 
 * ObservableList-ів
 */
public class CSVDispatcher {
	public static String filename; // Назва файлу

	/** Запис даних в CSV-файл */
	public static <T> void list2CSVFile(ObservableList<T> list) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "utf-8"))) {
			for (T item : list)
				writer.write(item.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Зчитування даних із CSV-файлу 
	 * і парсинг до типу кортежу таблиці - Реальні дані */
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

	/** Зчитування даних із CSV-файлу 
	 * і парсинг до типу кортежу таблиці - Тренуваліні дані */
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