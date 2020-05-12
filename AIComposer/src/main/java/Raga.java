import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Raga {
	static List<String> songs = new ArrayList();
	static HashMap<String, String> swaras = new HashMap<String, String>();

	enum RagaName{
		ABHOGI,MOHANAM,SHUDHADHANYASI;
	}

	static String identifyRaga(String raganame){
		for (RagaName r:RagaName.values()){
			if (raganame.equalsIgnoreCase(r.toString()))
				return r.toString().toLowerCase();
		}
		return RagaName.ABHOGI.toString().toLowerCase();
	}

	public static void loadRaga(String raganame) {
		JSONObject ragaObject = readJson(raganame+".json");

		JSONObject swarasObject = (JSONObject) ragaObject.get("swaras");
		for (Object entry : swarasObject.keySet())
			swaras.put(entry.toString(),swarasObject.get(entry.toString()).toString());

		JSONArray songsObject = (JSONArray) ragaObject.get("songs");
		for (Object songObj: songsObject ) {
			StringBuilder songbuilder = new StringBuilder();
			JSONArray song = (JSONArray) songObj;
			for(Object line:song)
				songbuilder.append(line.toString());
			songs.add(songbuilder.toString());
		}
	}

	public static String getSong() {
		StringBuilder out = new StringBuilder();
		for (String s:songs)
			out.append(s);
		return out.toString();
	}

	public static JSONObject readJson(String filename)
	{
		JSONParser jsonParser = new JSONParser();
		JSONObject raga = new JSONObject();

		try(InputStream in= Raga.class.getResourceAsStream(filename))
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			Object obj = jsonParser.parse(reader);
			raga = (JSONObject) obj;
			// contains multiple songs. each list is a song, with elements as lines
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return raga;
	}

}