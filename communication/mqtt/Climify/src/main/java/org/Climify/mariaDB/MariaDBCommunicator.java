package org.Climify.mariaDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.MqttLib.openhab.Channel;
import org.MqttLib.openhab.DeviceUpdate;
import org.MqttLib.openhab.Item;
import org.MqttLib.openhab.Link;
import org.MqttLib.openhab.Option;
import org.MqttLib.openhab.Thing;

/**
 * Responsible for communicating with the MariaDB on the VM
 * @author nch
 *
 */
public class MariaDBCommunicator {

	Connection connection;


	public void connect() throws SQLException {
		try {
			Class.forName( "org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306", "root", "totallysecurepassword");
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
		String sql = "INSERT IGNORE INTO RaspberryPis(UID, LocationID)"
					+ " VALUES(?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, raspberryPiUID);
			ps.setObject(2, 1, java.sql.Types.INTEGER);
			ps.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertItem(Item item) {
		String sql = "INSERT INTO Items(Name, Category, Minimum, Maximum, Step, Pattern, ReadOnly) VALUES(?,?,?,?,?,?,?)"
				     + " ON DUPLICATE KEY UPDATE"
				     + " Category=Values(Category),"
				     + " Minimum=Values(Minimum),"
				     + " Maximum=Values(Maximum),"
				     + " Step=Values(Step),"
				     + " Pattern=Values(Pattern),"
				     + " ReadOnly=Values(ReadOnly)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, item.name);
			ps.setString(2, item.category);
			if (item.stateDescription != null) {
				ps.setObject(3, item.stateDescription.minimum, java.sql.Types.INTEGER);
				ps.setObject(4, item.stateDescription.maximum, java.sql.Types.INTEGER);
				ps.setObject(5, item.stateDescription.step, java.sql.Types.INTEGER);
				ps.setObject(6, item.stateDescription.pattern, java.sql.Types.VARCHAR);
				ps.setObject(7, item.stateDescription.readOnly, java.sql.Types.BOOLEAN);
			}
			else {
				ps.setNull(3,java.sql.Types.INTEGER);
				ps.setNull(4,java.sql.Types.INTEGER);
				ps.setNull(5,java.sql.Types.INTEGER);
				ps.setNull(6,java.sql.Types.VARCHAR);
				ps.setNull(7,java.sql.Types.BOOLEAN);
			}

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (item.stateDescription != null) {
			for (Option option: item.stateDescription.options) {
				insertItemOption(item.name, option);
			}
		}
	}

	private void insertItemOption(String itemName, Option option) {
		String sql = "INSERT IGNORE INTO ItemOption(ItemName, Value, Label) VALUES (?,?,?)";

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, itemName);
			ps.setString(2, option.value);
			ps.setString(3, option.label);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertLink(Link link) {
		String sql = "INSERT IGNORE INTO Links(ItemName, ChannelUID)"
					+ " VALUES(?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, link.itemName);
			ps.setString(2, link.channelUID);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void insertThing(Thing thing, String raspberryPiUID) {
		String sql = "INSERT IGNORE INTO Things(Label, BridgeUID, UID, ThingTypeUID, Status, StatusDetail, StatusDescription, RaspberryPiUID)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			System.out.println("Thing UID = " + thing.UID + " ");
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, thing.label);
			ps.setString(2, thing.bridgeUID);
			ps.setString(3, thing.UID);
			ps.setString(4, thing.thingTypeUID);
			ps.setString(5, thing.statusInfo.status);
			ps.setString(6, thing.statusInfo.statusDetail);
			ps.setString(7, thing.statusInfo.description);
			ps.setString(8, raspberryPiUID);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Channel channel: thing.channels) {
			insertChannel(channel, thing.UID);
		}
	}

	private void insertChannel(Channel channel, String thingUID) {
		String sql = "INSERT IGNORE INTO Channels(UID, ID, ChannelTypeUID, ItemType, Kind, Label, Description)"
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
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		insertChannelThingsRelationsship(channel.uid, thingUID);
	}

	private void insertChannelThingsRelationsship(String channelUID, String thingUID) {
		String sql = "INSERT IGNORE INTO ThingsChannels(ChannelUID, ThingUID)"
					+ " VALUES(?, ?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, channelUID);
			ps.setString(2, thingUID);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<List<String>> getRulesBySensorID(String sensorID) {
        String SensorID = "";
        String Operator = "";
        String Value = "";
        String Action = "";
        String ActuatorID = "";
		List<List<String>> results = new ArrayList<List<String>>();
		String sql = "SELECT * FROM Rule WHERE SensorID = ?";
		try {
		    PreparedStatement ps = connection.prepareStatement(sql);
		    ps.setString(1, sensorID);
            ResultSet result = ps.executeQuery();
            while(result.next())
            {
            	System.out.println("Found rule with ID: " + result.getString(1));
            	List<String> rs = new ArrayList<String>();
            	SensorID = result.getString(2);
				Operator = result.getString(3);
				Value = result.getString(4);
				Action = result.getString(5);
				ActuatorID = result.getString(6);
				rs.add(SensorID);
				rs.add(Operator);
				rs.add(Value);
				rs.add(Action);
				rs.add(ActuatorID);
				results.add(rs);
            }
        } catch (SQLException e) {
		    e.printStackTrace();
        }
        return results;
	}

	public  List<String> getRaspberryPis(){
		String sql1 = "SELECT RaspberryPis.UID FROM RaspberryPis";
		List<String> RPis = new ArrayList<String>();
		try {
			PreparedStatement ps1 = connection.prepareStatement(sql1);
			ResultSet result1 = ps1.executeQuery();

			while(result1.next()) {
				String RPiID = result1.getString(1);
				RPis.add(RPiID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return RPis;

	}


	public List<String> getSensorsByRaspberryPi(String RPi) {

		List<String> RPiSensors = new ArrayList<String>();

		try {
		String sql2 = "SELECT items.Name FROM Items as items " +
				"INNER JOIN RaspberryPis as rp " +
				"INNER JOIN Things as t " +
				"INNER JOIN ThingsChannels as tc " +
				"INNER JOIN Links as links " +
				"WHERE" +
				"t.RaspberryPiUID = rp.UID " +
				"AND tc.ThingUID = t.UID " +
				"AND links.ChannelUID = tc.ChannelUID " +
				"AND items.Name = links.ItemName" +
				"AND t.RaspberryPiUID = ?";

		PreparedStatement ps2 = connection.prepareStatement(sql2);
		ps2.setString(1, RPi);
		ResultSet result2 = ps2.executeQuery();

		while(result2.next()) RPiSensors.add(result2.getString(1));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return RPiSensors;
	}

	public List<List<String	>> getSesorsForRaspberryPis(){

		List<List<String>> sensors = new ArrayList<List<String>>();

		List<String> RPis = getRaspberryPis();
		for(int i = 0; i < RPis.size(); i++){
			sensors.add(getSensorsByRaspberryPi(RPis.get(i)));
		}

		return sensors;
	}


}
