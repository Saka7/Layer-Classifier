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

public class CSVDispatcher {
	public static String filename;

	public static <T> void list2CSVFile(ObservableList<T> list) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "utf-8"))) {

			for (T item : list)
				writer.write(item.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

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
