package me.zwoosks.spamDetector.mySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;

import me.zwoosks.spamDetector.Main;

public class TableCreators {
	
	private Main plugin;
	
	public TableCreators(Main plugin) {
		this.plugin = plugin;
	}
	
	public void createTables() {
		FileConfiguration config = plugin.getConfig();
		String port = config.getString("port");
		String db = config.getString("database");
		String host = config.getString("host");
		
		String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
		String username = config.getString("username");
		String password = config.getString("password");
		
		Connection conn = dbConnection.getConnection(url, username, password);
		createBanTable(conn);
		createLogTable(conn);
		createBansLogTable(conn);
		
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private boolean createBanTable(Connection con) {
		try {
			Connection connection = con;
			PreparedStatement create = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `bans`(`nameOrUUID` VARCHAR(255) NOT NULL,`reason` TEXT(65535) NOT NULL,PRIMARY KEY (`nameOrUUID`))");
			create.executeUpdate();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean createLogTable(Connection conn) {
		try {
			Connection connection = conn;
			PreparedStatement create = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `logs` (`id` INT NOT NULL AUTO_INCREMENT,`playerName` VARCHAR(45) NOT NULL,`illegalWord` VARCHAR(45) NOT NULL,`text` VARCHAR(100) NOT NULL,`whereDidHeWrite` VARCHAR(45) NOT NULL,PRIMARY KEY (`id`))");
			create.executeUpdate();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean createBansLogTable(Connection conn) {
		try {
			Connection connection = conn;
			PreparedStatement create = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `bans_log` (`nameOrUUID` VARCHAR(100) NOT NULL,`illegalWord` VARCHAR(45) NOT NULL,`illegalText` MEDIUMTEXT NOT NULL,`whereDidHeWrite` VARCHAR(45) NOT NULL,PRIMARY KEY (`nameOrUUID`))");
			create.executeUpdate();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}