package org.Climify.mariaDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.MqttLib.openhab.Channel;
import org.MqttLib.openhab.DeviceUpdate;
import org.MqttLib.openhab.Item;
import org.MqttLib.openhab.Link;
import org.MqttLib.openhab.Thing;

/**
 * Responsible for communicating with the MariaDB on the VM
 * @author nch
 *
 */
public class MariaDBCommunicator {

	Connection connection;
	
	public void connect() throws SQLException {
		connection = DriverManager.getConnection("jdcb:mariadb://localhost:3306", "root", "totallysecurepassword");
		Statement statement = connection.createStatement();
		statement.execute("USE Climafy3");
	}

	public void close() throws SQLException {
		connection.close();
	}
	
	public void saveRaspberryPi(String raspberryPiUID, Integer locationID) {
		insertRaspberryPi(raspberryPiUID, locationID);
	}
	
	public void saveDeviceUpdate(DeviceUpdate deviceUpdate, String raspberryPiUID) {
		for(Item item: deviceUpdate.getItems()) {
			insertItem(item);
		}
		
		for(Thing thing: deviceUpdate.getThings()) {
			insertThing(thing, raspberryPiUID);
		}
		
		for(Link link: deviceUpdate.getLinks()) {
			insertLink(link);
		}
		
		
	}
	
	private void insertRaspberryPi(String raspberryPiUID, Integer locationID) {
		String sql = "INSERT INTO RaspberryPis(UID, LocationID)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, raspberryPiUID);
			ps.setInt(2, locationID);
			ps.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void insertItem(Item item) {
		String sql = "INSERT INTO Items(Name, Category) VALUES(?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, item.name);
			ps.setString(2, item.category);
			ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void insertLink(Link link) {
		String sql = "INSERT INTO Links(ItemName, ChannelUID)"
					+ " VALUES(?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, link.itemName);
			ps.setString(2, link.channelUID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void insertThing(Thing thing, String raspberryPiUID) {
		String sql = "INSERT INTO Things(Label, BridgeUID, UID, ThingTypeUID, Status, StatusDetail, StatusDescription, RaspberryPiUID)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, thing.label);
			ps.setString(2, thing.bridgeUID);
			ps.setString(3, thing.uid);
			ps.setString(4, thing.thingTypeUID);
			ps.setString(5, thing.statusInfo.status);
			ps.setString(6, thing.statusInfo.statusDetail);
			ps.setString(7, thing.statusInfo.description);
			ps.setString(8, raspberryPiUID);
			ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (Channel channel: thing.channels) {
			insertChannel(channel, thing.uid);
		}
	}
	
	private void insertChannel(Channel channel, String thingUID) {
		String sql = "INSERT INTO Channels(UID, ID, ChannelTypeUID, ItemType, Kind, Label, Description)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, channel.uid);
			ps.setString(2, channel.id);
			ps.setString(3, channel.channelTypeUID);
			ps.setString(4, channel.itemType);
			ps.setString(5, channel.kind);
			ps.setString(6, channel.label);
			ps.setString(7, channel.description);
			ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		insertChannelThingsRelationsship(channel.uid, thingUID);
	}
	
	private void insertChannelThingsRelationsship(String channelUID, String thingUID) {
		String sql = "INSERT INTO ThingsChannels(ChannelUID, ThingUID)"
					+ " VALUES(?, ?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, channelUID);
			ps.setString(2, thingUID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
