package org.Climify.mariaDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.MqttLib.openhab.Channel;
import org.MqttLib.openhab.ControlType;
import org.MqttLib.openhab.DeviceUpdate;
import org.MqttLib.openhab.DidControlItem;
import org.MqttLib.openhab.DidControlThing;
import org.MqttLib.openhab.InboxDevice;
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
	
	public void saveInbox(List<InboxDevice> inboxDevices, String raspberryPiUID) {
		for (InboxDevice inboxDevice: inboxDevices) {
			insertInboxDevice(inboxDevice, raspberryPiUID);
		}
	}
	
	public void clearInbox(String raspberryPiUID) {
		removeInbox(raspberryPiUID);
	}
	
	public void handleControlItem(DidControlItem didControlItem, String raspberryPiUID) {
		if (didControlItem.controlType == ControlType.ADD) {
			insertItem(didControlItem.item);
			insertLink(new Link(didControlItem.uid, didControlItem.channelUID));
		}
		else {
			removeItem(didControlItem.uid);
		}
	}
	
	public void handleControlThing(DidControlThing didControlThing, String raspberryPiUID) {
		if (didControlThing.controlType == ControlType.ADD) {
			insertThing(didControlThing.thing, raspberryPiUID);
		}
		else {
			//Order is important.
			removeItemsFromThing(didControlThing.uid);
			removeChannelsFromThing(didControlThing.uid);
			removeThing(didControlThing.uid);
		}
	}
	
	public List<String> getItemNamesFromThing(String thingUID) {
		String sql = "SELECT items.Name FROM Items as items  INNER JOIN Things as t  INNER JOIN ThingsChannels as tc  INNER JOIN Links as links  WHERE t.UID = ? AND tc.ThingUID = t.UID  AND links.ChannelUID = tc.ChannelUID  AND items.Name = links.ItemName";
		
		List<String> itemNames = new ArrayList<String>();
		
		try {
		    PreparedStatement ps = connection.prepareStatement(sql);
		    ps.setString(1, thingUID);
            ResultSet result = ps.executeQuery();
            while(result.next())
            {
            	System.out.println("Found getItemNames: " + result.getString(1));
            	
            	String itemName = result.getString("Name");
            	itemNames.add(itemName);
            }
        } catch (SQLException e) {
		    e.printStackTrace();
        }
		
		return itemNames;
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

	private void insertInboxDevice(InboxDevice inboxDevice, String raspberryPiUID) {
		String sql = "INSERT IGNORE INTO Inbox(BridgeUID, Flag, Label, ThingUID, ThingTypeUID, RaspberryPiUID)"
				+ " VALUES(?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, inboxDevice.bridgeUID);
			ps.setString(2, inboxDevice.flag);
			ps.setString(3, inboxDevice.label);
			ps.setString(4, inboxDevice.thingUID);
			ps.setString(5, inboxDevice.thingTypeUID);
			ps.setString(6, raspberryPiUID);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void removeInbox(String raspberryPiUID) {
		String sql = "DELETE FROM Inbox WHERE RaspberryPiUID = ?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, raspberryPiUID);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void removeThing(String uid) {
		String sql = "DELETE FROM Things WHERE Things.UID = ?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, uid);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void removeChannelsFromThing(String thingUID) {
		String sql = "DELETE Channels FROM Channels INNER JOIN ThingsChannels WHERE ThingsChannels.ThingUID = ? AND Channels.UID = ThingsChannels.ChannelUID";
	
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, thingUID);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void removeItemsFromThing(String thingUID) {
		String sql = "DELETE items FROM Items as items INNER JOIN Things as t INNER JOIN ThingsChannels as tc INNER JOIN Links as links WHERE t.UID = ? AND tc.ThingUID = t.UID AND links.ChannelUID = tc.ChannelUID AND items.Name = links.ItemName";
	
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, thingUID);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void removeItem(String itemName) {
		String sql = "DELETE Items from Items WHERE Items.Name = ?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, itemName);
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
}
