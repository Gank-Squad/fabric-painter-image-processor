package processing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import processing.colors.Colors;

public class ImageToInstructions {

	public static final String instructionDirectoryAppension = "_InstructionSet";
	
	/**
	 * converts the given image to raw instructions for the painting mod
	 * @param image image to be converted
	 * @param targetDirectory location for all the instructions to be put
	 * @return TRUE if successful, FALSE if unsuccessful
	 */
	public static boolean convertImageToInstructions(BufferedImage image, File targetDirectory)
	{
		if (!targetDirectory.exists())
		{
			targetDirectory.mkdir();
		}
		else
		{
			if (!targetDirectory.isDirectory())
				return false;
		}
		
		System.out.println(targetDirectory.exists());
		
		final int xPanels = image.getWidth() / 32;
		final int yPanels = image.getHeight() / 32;
		final String delimiter = ",";
		
		int counter = 0;
		
		for (int yPanel = 0; yPanel < yPanels; yPanel++)
		{
			for (int xPanel = 0; xPanel < xPanels; xPanel++)
			{
				
				Map<String, Integer> counts = new HashMap<String, Integer>();
				FileWriter writer = null;
				try
				{
					writer = new FileWriter(targetDirectory.getAbsolutePath() + File.separator + counter + ".csv");
					
					for (int y = 0; y < 32; y++)
					{
						String line = "";
						
						for (int x = 0; x < 31; x++)
						{
							String c = Colors.HexToColor(image.getRGB(x + 32 * xPanel, y + 32 * yPanel)).toString(); 
							line += c.toString() + delimiter;
							
							if (counts.containsKey(c))
								counts.put(c, counts.get(c) + 1);
							else
								counts.put(c, 1);
						}
						
						String c = Colors.HexToColor(image.getRGB(31 + 32 * xPanel, y + 32 * yPanel)).toString();
						line += c + "\n";
						
						if (counts.containsKey(c))
							counts.put(c, counts.get(c) + 1);
						else
							counts.put(c, 1);
						
						writer.write(line);
					}
					writer.close();
					
					
					// create a new file, that has all the color info Nc.csv
					writer = new FileWriter(targetDirectory.getAbsolutePath() + File.separator + counter + "c.csv");
					List<Entry<String, Integer>> list = new LinkedList<>(counts.entrySet());

			        // Sorting the list based on values
					Map<String,Integer> sorted = sortByValue(counts, false);
					
					for (Map.Entry<String, Integer> entry : sorted.entrySet())
					{
						writer.write(entry.getKey() + "," + entry.getValue() + "\n");
					}

					writer.close();
					
					
					counter++;
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
					return false;
				}
				finally
				{
					if (writer != null)
						try {
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
				
				
			}
		}
		
		return true;
	}
	
	
	// code stolen from https://stackoverflow.com/questions/8119366/sorting-hashmap-by-values
    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap, final boolean order)
    {
        List<Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }
	
}
