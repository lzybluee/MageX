import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	
	static Vector<String> cards = new Vector<>();
	
	public static void processSet(File file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String str = null;
			while ((str = reader.readLine()) != null) {
				Pattern p = Pattern.compile("new SetCardInfo.*?([a-zA-Z0-9_]+)\\.class");
				Matcher m = p.matcher(str);
				if(m.find()) {
					cards.add(m.group(1));
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		File folder = new File("E:\\MageX\\Project\\MageX\\Mage.Sets\\src\\mage\\sets"); 
		for(File f : folder.listFiles()) {
			processSet(f);
		}
		
		Vector<File> delCards = new Vector<>();
		folder = new File("E:\\MageX\\Project\\MageX\\Mage.Sets\\src\\mage\\cards");
		for(File f : folder.listFiles()) {
			if(f.isDirectory()) {
				for(File c : f.listFiles()) {
					String s = c.getName().replace(".java", "");
					if(!cards.contains(s)) {
						System.out.println(c);
						delCards.add(c);
					}
				}
			}
		}
		
		for(File f : delCards) {
			f.delete();
		}
	}

}
