package net.geant.autobahn.testplatform.configuration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import net.geant.autobahn.intradomain.IntradomainTopology;

public class DomainConfiguration {

	private int port;
	private int securePort;
	private String host;
	
	private MyProperties defaultConf;
	private MyProperties dmProps;
	private IncrementalProperties incremental;
	private IntraTopologyBuilder2 builder = null;
	
	public DomainConfiguration(MyProperties defaultConf,
			IntraTopologyBuilder2 builder, IncrementalProperties incremental) {
		
		this.port = builder.getDomain().getTcpPort();
		this.securePort = builder.getDomain().getSecureTcpPort();
		this.host = builder.getDomain().getHost();
		this.defaultConf = defaultConf;
		this.incremental = incremental;
		this.builder = builder;
		
		InputStream is = this.getClass().getResourceAsStream("/default/dm.properties");
		
		dmProps = new MyProperties(is);
		dmProps = updateDmProperties(dmProps);
	}
	
	public void writeConfigurationFiles() throws IOException {
		String destDir = incremental.getHome() + "/etc";
		File dir = new File(destDir);

		if(!dir.exists()) {
			if(!dir.getParentFile().exists())
				dir.getParentFile().mkdir();
			
			boolean success = dir.mkdir();
			if(!success)
				throw new IOException("Unable to create directory: " + destDir);
		}

		// Save public Properties
		MyProperties.savePropertiesFile(builder.getPublicIds(), new File(dir,
				"public_ids.properties"));

		// IDM properties
		InputStream is = this.getClass().getResourceAsStream(
				"/default/idm.properties");
		MyProperties idm = new MyProperties(is);
		updateIdmProperties(idm);
		idm.save(new File(dir, "idm.properties"));

		// DM properties
		is = this.getClass().getResourceAsStream("/default/dm.properties");
		MyProperties dm = new MyProperties(is);
		updateDmProperties(dm);
		dm.save(new File(dir, "dm.properties"));

		// Calendar properties
		is = this.getClass()
				.getResourceAsStream("/default/calendar.properties");
		MyProperties cal = new MyProperties(is);
		cal.save(new File(dir, "calendar.properties"));

		// Services properties
		is = this.getClass()
				.getResourceAsStream("/default/services.properties");
		MyProperties services = new MyProperties(is);
		services.setProperty("server.port", Integer.toString(port));
		services.setProperty("server.securePort", Integer.toString(securePort));
		services.save(new File(dir, "services.properties"));

		// Framework properties
		is = this.getClass().getResourceAsStream(
				"/default/framework.properties");
		MyProperties frame = new MyProperties(is);
		frame.setProperty("framework.port", incremental.getFrameworkPort());
		frame.setProperty("framework.password", "pass");
		frame.save(new File(dir, "framework.properties"));

		// TA properties
		is = this.getClass().getResourceAsStream("/default/ta.properties");
		MyProperties ta = new MyProperties(is);
		updateTaProperties(ta);
		ta.save(new File(dir, "ta.properties"));
	}
	
	public void copyDistribution(File src, Long timestamp) throws IOException {
		String toDir = incremental.getHome();
		File dest = new File(toDir);
		
		if(dest.exists()) {
			deleteDirectory(dest);
		}
		
		if(!dest.getParentFile().exists())
			dest.getParentFile().mkdir();
			
		if(!dest.mkdir()) {
			throw new IOException("Unable to create directory: " + toDir);
		}
		
		try {
			if(dest.exists() && src.exists()) {
				File dstLibDir = new File(dest, "lib");
				dstLibDir.mkdir();
				
				File libDir = new File(src, "lib");

				copy(libDir, dstLibDir);
				copy(new File(src, "etc"), new File(dest, "etc"));
				
				copy(new File(src, "start.bat"), 
						new File(dest, "start.bat"));
				copy(new File(src, "log4j.properties"), 
						new File(dest, "log4j.properties"));

				writeTimestamp(new File(dest, "stamp.tmp"), timestamp);
				
				System.out.println("Autobahn instance is located in: " + dest);
			} else {
				System.out.println("Directories don't exist...");
			}
		} catch(IOException e) {
			System.out.println("Exception while copying the distribution: " + e.getMessage());
		}
	}
	
	private void writeTimestamp(File f, Long timestamp) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(f));
		out.write("" + timestamp);
		out.close();
	}
	
	public void dropDatabase() {
		String dbName = dmProps.getProperty("db.name");
		
		boolean found = true;
		
		try {
			Connection connection = connectToDatabase(dbName);
			if(connection != null)
				connection.close();
		} catch(SQLException e1) {
			found = false;
		}
		
		if(!found)
			return;
		
		try {
			Connection connection = connectToDatabase("postgres");

			if (connection != null) {
				Statement stmt = connection.createStatement();

				String sql = "DROP DATABASE " + dbName + ";";

				stmt.executeUpdate(sql);
				stmt.close();
				connection.commit();
				connection.close();
			}

		} catch (SQLException e1) {
			System.out.println("Problem with dropping database: " + dbName
					+ " " + e1.getMessage());
		}
	}
	
	public void cleanOldReservations() {
		String dbName = dmProps.getProperty("db.name");
		
		try {
			Connection connection = connectToDatabase(dbName);

			if(connection != null) {
				Statement stmt = connection.createStatement();
			    
		        // Create table called my_table
				String sql = "DELETE FROM domainsids;\n";
				sql += "DELETE FROM pcon_range_constraints;\n";
				sql += "DELETE FROM pcon_range_names;\n";
				sql += "DELETE FROM pcon_bool_constraints;\n";
				sql += "DELETE FROM pcon_bool_names;\n";
				sql += "DELETE FROM reservation;\n";
				sql += "DELETE FROM service;\n";
		        sql += "DELETE FROM glink_to_intrapath;\n";
		        sql += "DELETE FROM intradomain_reservation;\n";
				sql += "DELETE FROM reservation_params;\n";

				sql += "DELETE FROM path_constraint;\n";
				sql += "DELETE FROM domain_constraints;\n";
				sql += "DELETE FROM domainsids;\n";
				sql += "DELETE FROM global_constraints;\n";

		        sql += "DELETE FROM intradomain_path;";
		    
		        stmt.executeUpdate(sql);
		        stmt.close();
		        connection.commit();
		        connection.close();
			}
			
		} catch (SQLException e1) {
			System.out.println("Problem with cleaning database: " + dbName
					+ " " + e1.getMessage());
		}
	}
	
	public void createDatabaseSchema() {
		String dbName = dmProps.getProperty("db.name");
		
		Connection connection = null;
		try {
			connection = connectToDatabase(dbName);
		} catch(SQLException e1) {
			connection = null;
		}
		
		try {
			if(connection == null) {
				System.out.println("Database " + dbName + " not found. Creating...");
				Connection conn = connectToDatabase("postgres");
				Statement stmt = conn.createStatement();
			    
		        // Create table
		        String sql = "CREATE DATABASE " + dbName + ";";
		    
		        stmt.executeUpdate(sql);
		        stmt.close();
		        conn.commit();
		        conn.close();
		        
		        connection = connectToDatabase(dbName);
			}
			
			Statement stmt = connection.createStatement();
			InputStream is = this.getClass().getResourceAsStream("/sql/create_db.sql");
			stmt.executeUpdate(getContents(is));
			
			connection.commit();
			connection.close();
			
		} catch (SQLException e1) {
			System.out.println("Problem with creating database: " + dbName
					+ " " + e1.getMessage());
		}
	}
	
	private String getContents(InputStream is) {
		StringBuffer sb = new StringBuffer();
		
		try {
	        BufferedReader in = new BufferedReader(new InputStreamReader(is));
	        String str;
	        while ((str = in.readLine()) != null) {
	        	if(str.startsWith("--"))
	        		continue;
	            sb.append(str + "\n");
	        }
	        in.close();
	    } catch (IOException e) {
	    }
		
		return sb.toString();
	}
	
	private Connection connectToDatabase(String dbName) throws SQLException {
		
		Connection connection = null;
		
		try {
	        // Load the JDBC driver
	        String driverName = "org.postgresql.Driver";
	        Class.forName(driverName);
	        // Create a connection to the database
	        String serverName = dmProps.getProperty("db.host");
	        
	        String dbPort = dmProps.getProperty("db.port");
	        String url = "jdbc:postgresql://" + serverName + ":" + dbPort + "/" + dbName;
	        String username = dmProps.getProperty("db.user");
	        String password = dmProps.getProperty("db.pass");
	        
	        connection = DriverManager.getConnection(url, username, password);
	        
	    } catch (ClassNotFoundException e) {
	        // Could not find the database driver
	    }
	    
	    return connection;
	}
	
	static public boolean deleteDirectory(File path) {
		if(path.exists()) {
			for (File f : path.listFiles()) {
				if (f.isDirectory()) {
					deleteDirectory(f);
				} else {
					f.delete();
				}
			}
		}
		
		return path.delete();
	}
	
    public static void copy(File src, File dst) throws IOException {
    	
    	if(src.isDirectory()) {
    		if(!dst.exists())
    			dst.mkdir();
    		
    		for(File f : src.listFiles()) {
    			copy(f, new File(dst, f.getName()));
    		}
    		
    		return;
    	}
    	
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
    
        copy(in, out);
        
        in.close();
        out.close();
    }
	
    public static void copy(InputStream in, OutputStream out) throws IOException {
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
    }
    
	private MyProperties updateDmProperties(MyProperties props) {
		props.setProperty("idm.address", buildAutobahnUrl("/dm2idm"));
		props.setProperty("domainName", incremental.getDomainName());
		props.setProperty("topologyabstraction.address", buildAutobahnUrl("/topologyabstraction"));
		props.setProperty("resourcesreservationcalendar.address", buildAutobahnUrl("/resourcesreservationcalendar"));
		props.setProperty("lookuphost", defaultConf.getProperty("lookupservice.address"));
		props = updateDbProperties(props);
		
		return props;
	}

	private MyProperties updateIdmProperties(MyProperties props) {
		props.setProperty("domain", buildAutobahnUrl("/interdomain"));
		props.setProperty("domainName", incremental.getDomainName());
		props.setProperty("dm.address", buildAutobahnUrl("/idm2dm"));
		props.setProperty("lookuphost", defaultConf.getProperty("lookupservice.address"));
		props.setProperty("longitude", Double.toString(builder.getDomain().getLongitude()));
		props.setProperty("latitude", Double.toString(builder.getDomain().getLatitude()));
		props = updateDbProperties(props);
		
		return props;
	}

	private MyProperties updateTaProperties(MyProperties props) {
		props.setProperty("idm.address", buildAutobahnUrl("/dm2idm"));
		props.setProperty("id.nodes", incremental.getNodesRange());
		props.setProperty("id.ports", incremental.getPortsRange());
		props.setProperty("id.links", incremental.getLinksRange());
		props.setProperty("lookuphost", defaultConf.getProperty("lookupservice.address"));
		
		return props;
	}
	
	private MyProperties updateDbProperties(MyProperties props) {
		if(props == null)
			return null;
		
		props.setProperty("db.host", defaultConf.getProperty("default.db-host"));
		props.setProperty("db.port", defaultConf.getProperty("default.db-port"));
		props.setProperty("db.user", defaultConf.getProperty("default.db-user"));
		props.setProperty("db.pass", defaultConf.getProperty("default.db-pass"));
		
		props.setProperty("db.name", incremental.getDbName());

		return props;
	}
	
	public String buildAutobahnUrl(String resource) {
		return "http://" + host + ":" + port + "/autobahn" + resource;
	}
	
	public Long getDistributionTimeStamp() {
		File f = new File(getHome() + "/stamp.tmp");
		if(!f.exists()) {
			return null;
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			return Long.parseLong(br.readLine());
		} catch(IOException e) {
			System.out.println("Unable to read distribution time stamp");
		}
		
		return null;
	}
	
	public String getHome() {
		return incremental.getHome();
	}
	
	public IntradomainTopology getTopology() {
		return builder.getIntradomainTopology();
	}

}
