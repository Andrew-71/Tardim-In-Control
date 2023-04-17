package su.a71.tardim_ic.tardim_ic.tardim_dock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.swdteam.tardim.main.Config;
import com.swdteam.tardim.main.Tardim;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;
import com.swdteam.tardim.tardim.TardimSaveHandler;
import com.swdteam.tardim.tardim.TardimIDMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class DockManager {
    private static Map<String, DockData> DOCK_DATA = new HashMap<>();
    static Gson gson = new Gson();
    public static MinecraftServer server;


    public DockManager() {
    }

    public static DockData getDock(String name) {
        return DOCK_DATA.get(name);
    }

    public static String addDock(DockData dockData) {
        int new_id = DOCK_DATA.size();
        while (DOCK_DATA.containsKey(Integer.toString(new_id))) {
            System.out.println(new_id + "Was taken so we try another");
            new_id++;
        }
        System.out.println("PICKED ID: " + new_id);

        DOCK_DATA.put(Integer.toString(new_id), dockData);
        return Integer.toString(new_id);
    }

    public static void removeDock(String name) {
        DOCK_DATA.remove(name);
    }

    public static void updateDock(String name, DockData dockData) {
        DOCK_DATA.put(name, dockData);
    }

    public static void load() throws Exception {
        File file = new File(server.getWorldPath(LevelResource.ROOT) + "/tardim_ic/dock_map.json");

        // Check if file exists
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(new HashMap<Integer, DockData>()));
            writer.close();
            return;
        }

        Type typeOfDockMap = new TypeToken<Map<Integer, DockData>>() { }.getType();

        JsonReader reader = new JsonReader(new FileReader(file));
        String json = gson.fromJson(reader, String.class);
        DOCK_DATA = gson.fromJson(json, typeOfDockMap);

        System.out.println("Loaded TARDIM: IC docks");
    }

    public static void save() throws Exception {
        File file = new File(server.getWorldPath(LevelResource.ROOT) + "/tardim_ic/dock_map.json");

        // Check if file exists
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        Gson gson = new Gson();
        String json = gson.toJson(DOCK_DATA);
        JsonWriter writer = new JsonWriter(new FileWriter(file));
        writer.jsonValue(json);
        writer.close();

        System.out.println("Saved TARDIM: IC docks");
    }

    public static void clearCahce() {
        DOCK_DATA.clear();
    }
}
