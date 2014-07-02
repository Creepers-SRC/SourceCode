package de.inventivegames.theShip;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Arenas {

	private static File	arenaFile;

	public static void createArenaFile(int arena, World world, Player p) {
		arenaFile = new File("plugins/TheShip/Arenas/" + arena + "/arena.yml");
		YamlConfiguration ArenaFile = YamlConfiguration.loadConfiguration(arenaFile);

		if (!(arenaFile.exists())) {
			try {
				ArenaFile.options().copyDefaults(true);
				ArenaFile.save(arenaFile);
				ArenaFile.addDefault("World", world.getName());
				ArenaFile.createSection("SpawnPoints");
				ArenaFile.createSection("SpawnPoints.lobby");
				ArenaFile.createSection("SpawnPoints.players");
				ArenaFile.createSection("SpawnPoints.prison");
				ArenaFile.createSection("SpawnPoints.prisonOut");

				try {
					ArenaFile.save(arenaFile);
				} catch (IOException e) {
					e.printStackTrace();
				}

				p.sendMessage(TheShip.prefix + "§2Added Arena");

			} catch (IOException e) {
				TheShip.console.sendMessage(TheShip.prefix + "§cCould not create Arena File for Arena §2#" + arena);
				e.printStackTrace();
			}
		} else
			p.sendMessage(TheShip.prefix + "§cArena already exists");

	}

	public static void removeArenaFile(int arena, Player p) {
		arenaFile = new File("plugins/TheShip/Arenas/" + arena + "/arena.yml");
		if (arenaFile.exists()) {
			arenaFile.delete();
			p.sendMessage(TheShip.prefix + "§2Removed Arena");
		} else
			p.sendMessage(TheShip.prefix + "§cArena doesn't exist");

	}

	public static void addSpawnPoint(int arena, String Type, int number, Player p) {
		arenaFile = new File("plugins/TheShip/Arenas/" + arena + "/arena.yml");
		YamlConfiguration ArenaFile = YamlConfiguration.loadConfiguration(arenaFile);

		if (arenaFile.exists()) {
			String type = Type.toLowerCase();
			Location pLoc = p.getLocation();
			double X = pLoc.getX();
			double Y = pLoc.getY() + 0.25;
			double Z = pLoc.getZ();

			if ((type.equals("lobby")) || (type.equals("players")) || (type.equals("prison") || (type.equals("prisonout")))) {
				if(type.equals("prisonout")) {
					type = "prisonOut";
				}
				ArenaFile.set("SpawnPoints." + type + "." + number + ".X", X);
				ArenaFile.set("SpawnPoints." + type + "." + number + ".Y", Y);
				ArenaFile.set("SpawnPoints." + type + "." + number + ".Z", Z);

				try {
					ArenaFile.save(arenaFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				p.sendMessage(TheShip.prefix + "§2Successfully added " + type + " SpawnPoint #" + number + " for Arena #" + arena);
			} else
				p.sendMessage(TheShip.prefix + "§cInvalid Spawn Type");
			p.sendMessage(TheShip.prefix + "§2Available Spawn Types: §alobby §2| §aplayers §2| §aprison §2| §aprisonOut");
		} else
			p.sendMessage(TheShip.prefix + "§cArena doesn't exist");

	}

	public static void createArenaFile(String string, World world, Player p) {
		int arena = Integer.parseInt(string);
		createArenaFile(arena, world, p);

	}

	public static void removeArenaFile(String string, Player p) {
		int arena = Integer.parseInt(string);
		removeArenaFile(arena, p);

	}

	public static void addSpawnPoint(String string, String type, String nbr, Player p) {
		int arena = Integer.parseInt(string);
		int number = Integer.parseInt(nbr);
		addSpawnPoint(arena, type, number, p);

	}

	public static void addSpawnPoint(String string, String type, int number, Player p) {
		int arena = Integer.parseInt(string);
		addSpawnPoint(arena, type, number, p);

	}

	public static boolean exists(int arena) {
		arenaFile = new File("plugins/TheShip/Arenas/" + arena + "/arena.yml");
		if (arenaFile.exists()) {
			return true;
		} else {
			return false;
		}
	}

	// public static void printArenaInfo(Player p, int arena) {
	// arenaFile = new File("plugins/TheShip/Arenas/" + arena + "/arena.yml");
	// YamlConfiguration ArenaFile =
	// YamlConfiguration.loadConfiguration(arenaFile);
	//
	// Boolean lobbySet = ((ArenaFile.get("SpawnPoints.lobby") != null) ? true :
	// false);
	// Boolean playersSet = ((ArenaFile.get("SpawnPoints.players") != null) ?
	// true : false);
	// Boolean lootSet = ((ArenaFile.get("SpawnPoints.loot") != null) ? true :
	// false);
	//
	// p.sendMessage("§2=== TheShip Arena Info ===");
	// p.sendMessage("§2======= Arena #" + arena + " =======");
	// p.sendMessage("§9┌ Lobby Spawnpoint: §7" + lobbySet);
	// p.sendMessage("§9├ Players Spawnpoints: §7" + playersSet);
	// p.sendMessage("§9├ Loot Spawnpoints: §7" + lootSet);
	//
	// p.sendMessage("§9├ World: §7" + ArenaFile.getString("World"));
	//
	// p.sendMessage("§9├ Lobby Spawnpoints:");
	//
	// p.sendMessage("§9│§3  └ 1");
	// p.sendMessage("§9│§b    ├ X: §7" +
	// ArenaFile.getDouble("SpawnPoints.lobby.1.X"));
	// p.sendMessage("§9│§b    ├ Y: §7" +
	// ArenaFile.getDouble("SpawnPoints.lobby.1.Y"));
	// p.sendMessage("§9│§b    └ Z: §7" +
	// ArenaFile.getDouble("SpawnPoints.lobby.1.Z"));
	//
	// p.sendMessage("§9├ Player Spawnpoints:");
	//
	//
	// Set<String> IDs1 =
	// ArenaFile.getConfigurationSection("SpawnPoints.players").getKeys(false);
	// Object[] ids1 = IDs1.toArray();
	// String ID1;
	// int id1;
	// if (IDs1.size() != 0) {
	// ID1 = ids1[IDs1.size() - 1].toString();
	// id1 = Integer.parseInt(ID1) + 1;
	// } else {
	// id1 = 1;
	// }
	// for (int i = 0; i < id1 + 1; i++) {
	// if (ArenaFile.get("SpawnPoints.players." + i) != null) {
	// p.sendMessage("§9│§3  " + (i == id1 ? "└" : "├") + " " + i);
	// p.sendMessage("§9│§b  " + (i == id1 ? " " : "§3│§b") + "  ├ X: §7" +
	// ArenaFile.getDouble("SpawnPoints.players." + i + ".X"));
	// p.sendMessage("§9│§b  " + (i == id1 ? " " : "§3│§b") + "  ├ Y: §7" +
	// ArenaFile.getDouble("SpawnPoints.players." + i + ".Y"));
	// p.sendMessage("§9│§b  " + (i == id1 ? " " : "§3│§b") + "  └ Z: §7" +
	// ArenaFile.getDouble("SpawnPoints.players." + i + ".Z"));
	// }
	// }
	//
	// p.sendMessage("§9└ Loot Spawnpoints:");
	//
	//
	// Set<String> IDs2 =
	// ArenaFile.getConfigurationSection("SpawnPoints.loot").getKeys(false);
	// Object[] ids2 = IDs2.toArray();
	// String ID2;
	// int id2;
	// if (IDs2.size() != 0) {
	// ID2 = ids2[IDs2.size() - 1].toString();
	// id2 = Integer.parseInt(ID2);
	// } else {
	// id2 = 1;
	// }
	// for (int i = 0; i < id2 + 1; i++) {
	// if (ArenaFile.get("SpawnPoints.loot." + i) != null) {
	// p.sendMessage("§3   " + (i == id2 ? "└" : "├") + " " + i);
	// p.sendMessage("§b   " + (i == id2 ? " " : "§3│§b") + "  ├ X: §7" +
	// ArenaFile.getDouble("SpawnPoints.loot." + i + ".X"));
	// p.sendMessage("§b   " + (i == id2 ? " " : "§3│§b") + "  ├ Y: §7" +
	// ArenaFile.getDouble("SpawnPoints.loot." + i + ".Y"));
	// p.sendMessage("§b   " + (i == id2 ? " " : "§3│§b") + "  └ Z: §7" +
	// ArenaFile.getDouble("SpawnPoints.loot." + i + ".Z"));
	// }
	// }
	//
	// }

}
