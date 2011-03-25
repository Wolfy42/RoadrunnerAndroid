package at.roadrunner.android.setup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;

import at.roadrunner.android.Config;

public class CouchDB {

	public void insertRoadrunnerUser()  {
		
		File iniFile = new File(Config.LOCAL_INI);
				
		URLConnection conn;
		BufferedReader reader = null;
		
		OutputStreamWriter writer = null;
				
		try  {			
			//Read ini.File
			conn = iniFile.toURL().openConnection();
			conn.setDoInput(true);
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			boolean inAdmins = false;
		
			boolean foundRoadrunner = false;
			
			while (line != null)  {
				if (line.equals("[admins]"))  {
					inAdmins = true;
				}
				if (inAdmins && line.contains("roadrunner"))  {
					foundRoadrunner = true;
				}
				if (inAdmins && !foundRoadrunner && !line.equals("[admins]") && line.startsWith("["))  {
					sb.append("roadrunner=roadrunner");
					sb.append("\n");
					inAdmins = false;
					foundRoadrunner = true;
				}
				
				sb.append(line);
				sb.append("\n");
				line = reader.readLine();
			}
			
			if (!foundRoadrunner)  {
				sb.append("roadrunner=roadrunner");
				sb.append("\n");
			}
			
			reader.close();

			if (sb.length() > 0)  {
			
				writer = new OutputStreamWriter(new FileOutputStream(iniFile));
				writer.write(sb.toString());
				writer.flush();
			}
			
		}  catch (IOException e) {
			e.printStackTrace();
		}  finally  {

			if (reader != null)  {
				try  {
					reader.close();
				} catch (IOException e)  {}
			}
			if (writer != null)  {
				try  {
					writer.close();
				} catch (IOException e)  {}
			}		
		}	
	}
	
}
