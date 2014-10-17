package net.karolek.revoguild.manager;

import java.lang.reflect.Field;

import net.karolek.revoguild.manager.types.GuildManager;
import net.karolek.revoguild.utils.Logger;

public class Manager {

	public static GuildManager	GUILD	= null;
		
	public static void load() {
		try {
			for (Field f : Manager.class.getFields()) {
				Class<?> clazz = f.getType();

				if (f.get(null) != null)
					continue;

				if (!clazz.getInterfaces()[0].getSimpleName().equalsIgnoreCase("IManager"))
					continue;

				Object manager = clazz.newInstance();
				f.set(null, manager);

				clazz.getMethod("enable").invoke(manager);
				Logger.info("Manager '" + f.getName().replace("Manager", "") + "' has been correctly enabled!");

			}
		} catch (Exception e) {
			Logger.severe("An error occurred while loading manager!");
			e.printStackTrace();
		}
	}

	public static void unload() {
		try {
			for (Field f : Manager.class.getFields()) {
				Class<?> clazz = f.getType();

				if(f.get(null) == null) continue;
				
				if (!clazz.getInterfaces()[0].getSimpleName().equalsIgnoreCase("IManager"))
					continue;

				Object manager = clazz.newInstance();
				f.set(null, null);

				clazz.getMethod("disable").invoke(manager);
				Logger.info("Manager '" + f.getName().replace("Manager", "") + "' has been correctly disabled!");

			}
		} catch (Exception e) {
			Logger.severe("An error occurred while unloading manager!");
			e.printStackTrace();
		}
	}

}
