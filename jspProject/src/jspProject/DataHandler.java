package jspProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DataHandler {

	private Connection conn;

	// Azure SQL connection credentials
	private String server = "pratik00.database.windows.net";
	private String database = "cs-dsa-4513-sql-db";
	private String username = "pratik123";
	private String password = "******";

	
	
	// Resulting connection string
	final private String url = String.format(
			"jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
			server, database, username, password);

	// Initialize and save the database connection
	private void getDBConnection() throws SQLException {
		if (conn != null) {
			return;
		}

		this.conn = DriverManager.getConnection(url);
	}

	/**
	 * 
	 * @param fileName file name to pass
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public void readFile(String fileName) throws IOException, NumberFormatException, SQLException {
		try {
			String path = "C:\\Users\\pratik\\OneDrive\\Desktop\\cs4513Workspace\\jspProject\\src\\jspProject\\";
			BufferedReader br = new BufferedReader(new FileReader(path + fileName));
			// Read the file

			String strg = br.readLine();
			strg = br.readLine();

			while (strg != null) {
				String[] contains = strg.split(",");
				addCustomer(contains[0], contains[1], Integer.parseInt(contains[2]));

				strg = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Error reading from file!\n");
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param fileName to write in
	 * @param first
	 * @param last
	 * @throws IOException
	 * @throws SQLException
	 */
	public void writeFile(String fileName, int first, int last) throws IOException, SQLException {
		String path = "C:\\Users\\pratik\\OneDrive\\Desktop\\cs4513Workspace\\jspProject\\src\\jspProject\\";
		// String fileContent = "Hello Learner !! Welcome to howtodoinjava.com.";
		BufferedWriter writer = new BufferedWriter(new FileWriter(path + fileName));
		final ResultSet customer = retreiveCustomers(first, last);

		while (customer.next()) {
			String name = customer.getString("customer_name");
			String address = customer.getString("customer_address");
			String cate = customer.getString("category");
			writer.write(name + "  " + address + "  " + cate + "\n");
		}
		writer.close();
	}

	/**
	 * inserts into the customer
	 * 
	 * @param customerName
	 * @param customerAddress
	 * @param category
	 * @return
	 * @throws SQLException
	 */

	public boolean addCustomer(String customerName, String customerAddress, int category) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Customer " + "(customer_name, customer_address, category) " + "VALUES "
				+ "(?, ?, ?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		// Replace the '?' in the above statement with the given attribute values
		stmt.setString(1, customerName);
		stmt.setString(2, customerAddress);
		stmt.setInt(3, category);

		// Execute the query, if only one record is updated, then we indicate success by
		// returning true
		return stmt.executeUpdate() == 1;
	}

	/**
	 * inserts into the department
	 * 
	 * @param departmentNumber
	 * @param departmentData
	 * @return
	 * @throws SQLException
	 */
	public boolean addDepartment(int departmentNumber, String departmentData) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Department " + "(department_number, department_data) " + "VALUES "
				+ "(?, ?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, departmentNumber);
		stmt.setString(2, departmentData);

		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param assemblyID
	 * @param dateOrder
	 * @param assemblyDetail
	 * @param customerName
	 * @return
	 * @throws SQLException
	 */
	public boolean addAssembly(int assemblyID, String dateOrder, String assemblyDetail, String customerName)
			throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Assembly " + "(assembly_ID, date_order, assembly_detail, customer_name) "
				+ "VALUES " + "(?,?,?,?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, assemblyID);
		stmt.setString(2, dateOrder);
		stmt.setString(3, assemblyDetail);
		stmt.setString(4, customerName);

		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param processID
	 * @param processData
	 * @param departmentNumber
	 * @return
	 * @throws SQLException
	 */
	public boolean addProcess(int processID, String processData, int departmentNumber) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Process " + "(process_ID, process_data, department_number) " + "VALUES "
				+ "(?,?,?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, processID);
		stmt.setString(2, processData);
		stmt.setInt(3, departmentNumber);

		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param processID
	 * @param fitType
	 * @return
	 * @throws SQLException
	 */
	public boolean addFit(int processID, String fitType) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Fit " + "(process_ID, fit_type) " + "VALUES " + "(?,?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, processID);
		stmt.setString(2, fitType);

		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param processID
	 * @param paintType
	 * @param paintingMethod
	 * @return
	 * @throws SQLException
	 */
	public boolean addPaint(int processID, String paintType, String paintingMethod) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Paint " + "(process_ID, paint_type, painting_method) " + "VALUES "
				+ "(?,?,?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, processID);
		stmt.setString(2, paintType);
		stmt.setString(3, paintingMethod);

		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param processID
	 * @param cutType
	 * @param machineType
	 * @return
	 * @throws SQLException
	 */
	public boolean addCut(int processID, String cutType, String machineType) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Cut " + "(process_ID, cut_type, machine_type) " + "VALUES " + "(?,?,?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, processID);
		stmt.setString(2, cutType);
		stmt.setString(3, machineType);
		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param accountNumber
	 * @param dateEstablished
	 * @return
	 * @throws SQLException
	 */
	public boolean addAccount(int accountNumber, String dateEstablished) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Account " + "(account_number, date_established) " + "VALUES " + "(?,?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, accountNumber);
		stmt.setString(2, dateEstablished);

		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param accountNumber
	 * @param costDetail1
	 * @param assemblyID
	 * @return
	 * @throws SQLException
	 */
	public boolean addAssemblyAccount(int accountNumber, String costDetail1, int assemblyID) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Assembly_account " + "(account_number, cost_detail1, assembly_ID) "
				+ "VALUES " + "(?,?,?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, accountNumber);
		stmt.setString(2, costDetail1);
		stmt.setInt(3, assemblyID);
		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param accountNumber
	 * @param costDetail2
	 * @param departmentNumber
	 * @return
	 * @throws SQLException
	 */
	public boolean addDepartmentAccount(int accountNumber, String costDetail2, int departmentNumber)
			throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Department_Account " + "(account_number, cost_detail2, department_number) "
				+ "VALUES " + "(?,?,?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, accountNumber);
		stmt.setString(2, costDetail2);
		stmt.setInt(3, departmentNumber);
		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param accountNumber
	 * @param costDetail3
	 * @param processID
	 * @return
	 * @throws SQLException
	 */
	public boolean addProcessAccount(int accountNumber, String costDetail3, int processID) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Process_Account " + "(account_number, cost_detail3, process_ID) "
				+ "VALUES " + "(?,?,?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, accountNumber);
		stmt.setString(2, costDetail3);
		stmt.setInt(3, processID);
		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param jobNumber
	 * @param dateCommenced
	 * @param dateCompleted
	 * @param jobType
	 * @param processID
	 * @param assemblyID
	 * @return
	 * @throws SQLException
	 */
	public boolean addJob(int jobNumber, String dateCommenced, String dateCompleted, String jobType, int processID,
			int assemblyID) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Job "
				+ "(job_number, date_commenced, date_completed, job_type, process_ID, assembly_ID) " + "VALUES "
				+ "(?,?,?,?,?,?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, jobNumber);
		stmt.setString(2, dateCommenced);
		stmt.setString(3, dateCompleted);
		stmt.setString(4, jobType);
		stmt.setInt(5, processID);
		stmt.setInt(6, assemblyID);
		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param jobNumber
	 * @param dateCompleted
	 * @param jobType
	 * @return
	 * @throws SQLException
	 */
	public boolean updateJob(int jobNumber, String dateCompleted, String jobType) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "UPDATE Job SET date_completed = ? , job_type = ? WHERE job_number = ?";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setString(1, dateCompleted);
		stmt.setString(2, jobType);
		stmt.setInt(3, jobNumber);
		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param jobNumber
	 * @param machineType
	 * @param amountOfTime
	 * @param materialUsed
	 * @param laborTime
	 * @return
	 * @throws SQLException
	 */
	public boolean addCutJob(int jobNumber, String machineType, int amountOfTime, String materialUsed, int laborTime)
			throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Cut_Job "
				+ "(job_number, machine_type, amount_of_time, material_used, labor_time) " + "VALUES " + "(?,?,?,?,?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, jobNumber);
		stmt.setString(2, machineType);
		stmt.setInt(3, amountOfTime);
		stmt.setString(4, materialUsed);
		stmt.setInt(5, laborTime);
		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param jobNumber
	 * @param color
	 * @param volume
	 * @param laborTime
	 * @return
	 * @throws SQLException
	 */
	public boolean addPaintJob(int jobNumber, String color, int volume, int laborTime) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Paint_Job " + "(job_number, color, volume, labor_time) " + "VALUES "
				+ "(?,?,?,?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, jobNumber);
		stmt.setString(2, color);
		stmt.setInt(3, volume);
		stmt.setInt(4, laborTime);
		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param jobNumber
	 * @param laborTime
	 * @return
	 * @throws SQLException
	 */
	public boolean addFitJob(int jobNumber, int laborTime) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Fit_Job " + "(job_number, labor_time) " + "VALUES " + "(?,?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, jobNumber);
		stmt.setInt(2, laborTime);
		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param transactionNumber
	 * @param supCost
	 * @param accountNumber
	 * @return
	 * @throws SQLException
	 */
	public boolean addCostTransaction(int transactionNumber, int supCost, int accountNumber) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Cost_Transaction " + "(transaction_number, sup_cost, account_number) "
				+ "VALUES " + "(?,?,?)";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, transactionNumber);
		stmt.setInt(2, supCost);
		stmt.setInt(3, accountNumber);
		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param accountNumber
	 * @param supCost
	 * @return
	 * @throws SQLException
	 */
	public boolean updateAssemblyAccount(int accountNumber, int supCost) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "UPDATE Assembly_Account SET cost_detail1 = cost_detail1 + ?  WHERE account_number = ?";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, supCost);
		stmt.setInt(2, accountNumber);
		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param accountNumber
	 * @param supCost
	 * @return
	 * @throws SQLException
	 */
	public boolean updateDepartmentAccount(int accountNumber, int supCost) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "UPDATE Assembly_Account SET cost_detail2 = cost_detail2 + ?  WHERE account_number = ?";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, supCost);
		stmt.setInt(2, accountNumber);
		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param accountNumber
	 * @param supCost
	 * @return
	 * @throws SQLException
	 */
	public boolean updateProcessAccount(int accountNumber, int supCost) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "UPDATE Process_Account SET cost_detail3 = cost_detail3 + ?  WHERE account_number = ?";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, supCost);
		stmt.setInt(2, accountNumber);
		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param assemblyID
	 * @return
	 * @throws SQLException
	 */
	public ResultSet retreiveCostIncurredOnAssemblyID(int assemblyID) throws SQLException {
		getDBConnection();
		final String sqlQuery = "SELECT cost_detail1 FROM Assembly_Account, Assembly WHERE (Assembly_Account.assembly_ID = Assembly.assembly_ID) AND Assembly.assembly_ID = "
				+ assemblyID + ";";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
		return stmt.executeQuery();
	}

	/**
	 * 
	 * @param dateCommenced
	 * @return
	 * @throws SQLException
	 */
	public ResultSet querryEleven(String dateCommenced) throws SQLException {
		getDBConnection();
		final String sqlQuery = "SELECT process_ID, process_data, department_number, date_order  FROM (SELECT DISTINCT process.process_ID, process_data, department_number, date_order FROM Process, Passes, Assembly WHERE\r\n"
				+ "(process.process_ID = Passes.process_ID) AND \r\n"
				+ "(Assembly.assembly_ID = Passes.assembly_ID) AND\r\n"
				+ "Assembly.date_order <= ?  ) as Temp ORDER BY Temp.date_order;";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
		stmt.setString(1, dateCommenced);
		return stmt.executeQuery();
	}

	/**
	 * 
	 * @param dateCompleted
	 * @param departmentNumber
	 * @return
	 * @throws SQLException
	 */
	public ResultSet querryTwelve(String dateCompleted, int departmentNumber) throws SQLException {
		getDBConnection();
		final String sqlQuery = "SELECT DISTINCT job_number,job_type, process_ID FROM Job WHERE date_completed = ? AND process_ID IN (SELECT process_ID FROM Process WHERE Process.department_number = "
				+ departmentNumber + ");";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
		stmt.setString(1, dateCompleted);
		return stmt.executeQuery();
	}

	/**
	 * 
	 * @param first
	 * @param last
	 * @return
	 * @throws SQLException
	 */
	public ResultSet retreiveCustomers(int first, int last) throws SQLException {
		getDBConnection();
		final String sqlQuery = "SELECT * FROM Customer WHERE category BETWEEN " + first + " AND " + last + ";";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
		return stmt.executeQuery();
	}

	/**
	 * 
	 * @param jobNumber
	 * @param color
	 * @return
	 * @throws SQLException
	 */
	public boolean updatePaintJob(int jobNumber, String color) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "UPDATE Paint_Job SET color = ?  WHERE job_number = ?";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setString(1, color);
		stmt.setInt(2, jobNumber);
		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param firstJobNumber
	 * @param secondJobNumber
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteCutJob(int firstJobNumber, int secondJobNumber) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "DELETE FROM Cut_Job WHERE Job_number >= ? AND job_number <= ?";

		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, firstJobNumber);
		stmt.setInt(2, secondJobNumber);
		return stmt.executeUpdate() == 1;
	}

	/**
	 * 
	 * @param firstJobNumber
	 * @param secondJobNumber
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteJob(int firstJobNumber, int secondJobNumber) throws SQLException {
		getDBConnection(); // Prepare the database connection

		// Prepare the SQL statement
		final String sqlQuery = "DELETE FROM Job WHERE Job_number >= ? AND job_number <= ? AND job_type = 'cut'";

		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

		stmt.setInt(1, firstJobNumber);
		stmt.setInt(2, secondJobNumber);
		return stmt.executeUpdate() == 1;
	}

}
