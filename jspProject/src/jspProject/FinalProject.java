package jspProject;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FinalProject {

	public static final Boolean DEBUG = false;
	final static DataHandler handler = new DataHandler();

	public static void main(String[] args) throws SQLException {

		Scanner keyboard = new Scanner(System.in);
		boolean running = true;
		while (running) {

			System.out.println("\nWELCOME TO THE JOB-SHOP ACCOUNTING DATABASE SYSTEM\n");
			System.out.println("Please enter a number to the corresponding task:\n");
			System.out.println("(1) Enter a new customer");
			System.out.println("(2) Enter a new department");
			System.out.println(
					"(3) Enter a new assembly with its customer-name, assembly-details, assembly-id, and dateordered");
			System.out.println(
					"(4) Enter a new process-id and its department together with its type and information relevant the type");
			System.out.println(
					"(5) Create a new account and associate it with the process, assembly, or department to which it is applicable");
			System.out.println(
					"(6) Enter a new job, given its job-no, assembly-id, process-id, and date the job commenced ");
			System.out.println(
					"(7) At the completion of a job, enter the date it completed and the information relevant to the of job");
			System.out.println(
					"(8) Enter a transaction-no and its sup-cost and update all the costs (details) of the affected accounts by adding sup-cost to their current values of details");
			System.out.println("(9) Retrieve the cost incurred on an assembly-id ");
			System.out.println(
					"(10) Retrieve the total labor time within a department for jobs completed in the department during given date");
			System.out.println(
					"(11) Retrieve the processes through which a given assembly-id has passed so far and the department responsible for each process ");
			System.out.println(
					"(12) Retrieve the jobs (together with their type information and assembly-id) completed during given date in a given department");
			System.out.println("(13) Retrieve the customers (in name order) whose category is in a given range");
			System.out.println("(14) Delete all cut-jobs whose job-no is in a given range");
			System.out.println("(15) Change the color of a given paint job");
			System.out.println("(16) enter the input file name to insert new costumer");
			System.out.println("(17) Retrieve the customers");
			System.out.println("(18) Quit");

			String choice = keyboard.nextLine();
			String[] parsedInput;
			String inputs;

			if (choice.equals("1")) { // query 1
				System.out.println(
						"Please enter your input in the following format:\nCustomer name, Customer Address, Category(1-10)");
				inputs = keyboard.nextLine();
				parsedInput = inputs.split(", ");
				handler.addCustomer(parsedInput[0], parsedInput[1], Integer.parseInt(parsedInput[2]));

			} else if (choice.equals("2")) {// query 2
				System.out.println(
						"Please enter your input in the following format:\nDepartment Number, Department Data");
				inputs = keyboard.nextLine();
				parsedInput = inputs.split(", ");
				handler.addDepartment(Integer.parseInt(parsedInput[0]), parsedInput[1]);
			} else if (choice.equals("3")) {// query 3
				System.out.println(
						"Please enter your input in the following format:\nassembly ID, date order(yy-mm-dd), assembly detail, customer name");
				inputs = keyboard.nextLine();
				parsedInput = inputs.split(", ");
				handler.addAssembly(Integer.parseInt(parsedInput[0]), parsedInput[1], parsedInput[2], parsedInput[3]);
			} else if (choice.equals("4")) {// query 4
				System.out.println("please enter type of the process (fit, paint or cut)");
				String userInput = keyboard.nextLine();
				if (userInput.equals("fit")) {
					System.out.println(
							"Please enter your input in the following format:\n Process ID, Process data, fit type, department number");
					inputs = keyboard.nextLine();
					parsedInput = inputs.split(", ");
					handler.addProcess(Integer.parseInt(parsedInput[0]), parsedInput[1],
							Integer.parseInt(parsedInput[3]));
					handler.addFit(Integer.parseInt(parsedInput[0]), parsedInput[2]);
				}
				if (userInput.equals("paint")) {
					System.out.println(
							"Please enter your input in the following format:\n Process ID, Process data, paint type, painting method, department number");
					inputs = keyboard.nextLine();
					parsedInput = inputs.split(", ");
					handler.addProcess(Integer.parseInt(parsedInput[0]), parsedInput[1],
							Integer.parseInt(parsedInput[4]));
					handler.addPaint(Integer.parseInt(parsedInput[0]), parsedInput[2], parsedInput[3]);
				}
				if (userInput.equals("cut")) {
					System.out.println(
							"Please enter your input in the following format:\n Process ID, Process data, cut type, machine type, department number");
					inputs = keyboard.nextLine();
					parsedInput = inputs.split(", ");
					handler.addProcess(Integer.parseInt(parsedInput[0]), parsedInput[1],
							Integer.parseInt(parsedInput[4]));
					handler.addCut(Integer.parseInt(parsedInput[0]), parsedInput[2], parsedInput[3]);
				}

			} else if (choice.equals("5")) {// query 5
				System.out.println("please enter type of the account (assembly, department or process)");
				String userInput = keyboard.nextLine();
				if (userInput.equals("assembly")) {
					System.out.println(
							"Please enter your input in the following format:\n Account number, Date established(yy-mm-dd), Cost detail1, Assembly ID");
					inputs = keyboard.nextLine();
					parsedInput = inputs.split(", ");
					handler.addAccount(Integer.parseInt(parsedInput[0]), parsedInput[1]);
					handler.addAssemblyAccount(Integer.parseInt(parsedInput[0]), parsedInput[2],
							Integer.parseInt(parsedInput[3]));
				}
				if (userInput.equals("department")) {
					System.out.println(
							"Please enter your input in the following format:\n Account number, Date established(yy-mm-dd), Cost detail2, Department number");
					inputs = keyboard.nextLine();
					parsedInput = inputs.split(", ");
					handler.addAccount(Integer.parseInt(parsedInput[0]), parsedInput[1]);
					handler.addDepartmentAccount(Integer.parseInt(parsedInput[0]), parsedInput[2],
							Integer.parseInt(parsedInput[3]));
				}
				if (userInput.equals("process")) {
					System.out.println(
							"Please enter your input in the following format:\n Account number, Date established(yy-mm-dd), Cost detail1, Process ID");
					inputs = keyboard.nextLine();
					parsedInput = inputs.split(", ");
					handler.addAccount(Integer.parseInt(parsedInput[0]), parsedInput[1]);
					handler.addProcessAccount(Integer.parseInt(parsedInput[0]), parsedInput[2],
							Integer.parseInt(parsedInput[3]));
				}

			} else if (choice.equals("6")) {// query 6
				System.out.println(
						"Please enter your input in the following format:\nJob number, Date commenced(yy-mm-dd), Process ID, Assembly ID");
				inputs = keyboard.nextLine();
				parsedInput = inputs.split(", ");
				handler.addJob(Integer.parseInt(parsedInput[0]), parsedInput[1], "", "unknown",
						Integer.parseInt(parsedInput[2]), Integer.parseInt(parsedInput[3]));
			} else if (choice.equals("7")) {// query 7
				System.out.println("please enter type of the job (cut, paint or fit)");
				String userInput = keyboard.nextLine();
				if (userInput.equals("cut")) {
					System.out.println(
							"Please enter your input in the following format:\nJob number, Machine type, Amount of time machine used, Material used, Labor time, Date completed(yy-mm-dd)");
					inputs = keyboard.nextLine();
					parsedInput = inputs.split(", ");
					handler.updateJob(Integer.parseInt(parsedInput[0]), parsedInput[5], "cut");
					handler.addCutJob(Integer.parseInt(parsedInput[0]), parsedInput[1],
							Integer.parseInt(parsedInput[2]), parsedInput[3], Integer.parseInt(parsedInput[4]));
				}

				if (userInput.equals("paint")) {
					System.out.println(
							"Please enter your input in the following format:\nJob number, Color, Volume, Labor time, Date completed(yy-mm-dd)");
					inputs = keyboard.nextLine();
					parsedInput = inputs.split(", ");
					handler.updateJob(Integer.parseInt(parsedInput[0]), parsedInput[4], "paint"); // job_number, color,
																									// volume,
																									// labor_time
					handler.addPaintJob(Integer.parseInt(parsedInput[0]), parsedInput[1],
							Integer.parseInt(parsedInput[2]), Integer.parseInt(parsedInput[3]));
				}
				if (userInput.equals("fit")) {
					System.out.println(
							"Please enter your input in the following format:\nJob number, Labor time, Date completed(yy-mm-dd)");
					inputs = keyboard.nextLine();
					parsedInput = inputs.split(", ");
					handler.updateJob(Integer.parseInt(parsedInput[0]), parsedInput[2], "fit"); // job_number, color,
																								// volume, labor_time
					handler.addFitJob(Integer.parseInt(parsedInput[0]), Integer.parseInt(parsedInput[1]));
				}

			} else if (choice.equals("8")) {// query 8
				System.out.println(
						"Please enter your input in the following format:\nTransaction number, Sup cost, Account number, account type (eg: assembly, department, process)");
				inputs = keyboard.nextLine();
				parsedInput = inputs.split(", ");
				if (parsedInput[3].equals("assembly")) {
					handler.addCostTransaction(Integer.parseInt(parsedInput[0]), Integer.parseInt(parsedInput[1]),
							Integer.parseInt(parsedInput[2]));
					handler.updateAssemblyAccount(Integer.parseInt(parsedInput[2]), Integer.parseInt(parsedInput[1]));
				}
				if (parsedInput[3].equals("department")) {
					handler.addCostTransaction(Integer.parseInt(parsedInput[0]), Integer.parseInt(parsedInput[1]),
							Integer.parseInt(parsedInput[2]));
					handler.updateDepartmentAccount(Integer.parseInt(parsedInput[2]), Integer.parseInt(parsedInput[1]));
				}
				if (parsedInput[3].equals("process")) {
					handler.addCostTransaction(Integer.parseInt(parsedInput[0]), Integer.parseInt(parsedInput[1]),
							Integer.parseInt(parsedInput[2]));
					handler.updateProcessAccount(Integer.parseInt(parsedInput[2]), Integer.parseInt(parsedInput[1]));
				}
			} else if (choice.equals("9")) {// query 9
				System.out.println("please enter the assemblyId");
				int assemblyID = keyboard.nextInt();
				final ResultSet resultSet = handler.retreiveCostIncurredOnAssemblyID(assemblyID);

				System.out.print("Cost incured on assemblyID (" + assemblyID + ") = \n");
				while (resultSet.next()) {
					System.out.println(String.format("%d ", resultSet.getInt(1)));
				}
			} else if (choice.equals("10"))// query 10
			{
				System.out.println("not implemented");
			}

			else if (choice.equals("11")) {// query 11
				System.out.println(
						"please enter the date the assembly ordered in following format:\nDate ordered(yy-mm-dd)");
				inputs = keyboard.nextLine();
				final ResultSet resultSet = handler.querryEleven(inputs);

				System.out.println("Process passed before " + inputs + " are displayed below:");
				while (resultSet.next()) {
					System.out.println(String.format("%d | %s | %d |%s ", resultSet.getInt(1), resultSet.getString(2),
							resultSet.getInt(3), resultSet.getString(4)));
				}

			}

			else if (choice.equals("12")) {// query 12
				System.out.println(
						"please enter the date the job completed and department number in following format:\nDete completed(yy-mm-dd), Department Number");
				inputs = keyboard.nextLine();
				parsedInput = inputs.split(", ");
				final ResultSet resultSet = handler.querryTwelve(parsedInput[0], Integer.parseInt(parsedInput[1]));

				System.out.println(
						"the jobs (together with their type information and assembly-id) completed during a\r\n" + ""
								+ parsedInput[0] + " and departmetnt " + Integer.parseInt(parsedInput[1])
								+ " are displayed below:");
				while (resultSet.next()) {
					System.out.println(String.format("%d | %s | %d ", resultSet.getInt(1), resultSet.getString(2),
							resultSet.getInt(3)));
				}

			}

			else if (choice.equals("13")) {// query 13
				System.out.println(
						"please enter the category range in the following format:\nFirst number, Second number");
				inputs = keyboard.nextLine();
				parsedInput = inputs.split(", ");
				final ResultSet resultSet = handler.retreiveCustomers(Integer.parseInt(parsedInput[0]),
						Integer.parseInt(parsedInput[1]));

				System.out.println("Customer with category between " + Integer.parseInt(parsedInput[0]) + " and "
						+ Integer.parseInt(parsedInput[1]) + " are displayed below:");
				while (resultSet.next()) {
					System.out.println(String.format("%s | %s | %d ", resultSet.getString(1), resultSet.getString(2),
							resultSet.getInt(3)));
				}

			}

			else if (choice.equals("14")) {// query 14
				System.out.println("please enter the your input in the following format:\nFirst number, Second number");
				inputs = keyboard.nextLine();
				parsedInput = inputs.split(", ");
				handler.deleteCutJob(Integer.parseInt(parsedInput[0]), Integer.parseInt(parsedInput[1]));
				handler.deleteJob(Integer.parseInt(parsedInput[0]), Integer.parseInt(parsedInput[1]));
			}

			else if (choice.equals("15")) {// query 15
				System.out.println(
						"please enter the account number and color to change in the following format:\nJob number, color");
				inputs = keyboard.nextLine();
				parsedInput = inputs.split(", ");
				handler.updatePaintJob(Integer.parseInt(parsedInput[0]), parsedInput[1]);

			}

			else if (choice.equals("16")) {// query 16
				System.out.println("please enter a file name to insert into customer");
				String fileName = keyboard.nextLine();
				try {
					handler.readFile(fileName);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			else if (choice.equals("17")) {// query 17
				System.out.println("please enter a file name to write customer");
				String fileName = keyboard.nextLine();
				System.out.println("Enter a category range (1 to 10) to retrieve a customer Ex: 2, 4");
				inputs = keyboard.nextLine();
				parsedInput = inputs.split(", ");

				try {
					handler.writeFile(fileName, Integer.parseInt(parsedInput[0]), Integer.parseInt(parsedInput[1]));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (choice.equals("18")) {// query 18
				running = false;
			} else {
				System.out.println("Invalid argument.");
			}
		}

		System.out.println("Goodbye!");
	}

}