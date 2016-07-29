import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import au.com.bytecode.opencsv.CSVReader;

@SuppressWarnings("serial")
public class MainContent extends JInternalFrame implements ActionListener {

	String command = null;
	String command1 = null;
	String command2 = null;
	String command3 = null;
	String command4 = null;
	String command5 = null;

	String taxiString = null;
	String ironString = null;
	String breakfastString = null;
	String wakecallString = null;

	private Connection databaseConnection = null;
	private Statement sqlStatement = null;
	private ResultSet databaseResultSet = null;

	private Container contentContainer;

	private JPanel detailsPanel;
	private JPanel vacanciesPanel;
	private JPanel checkOutPanel;
	private JPanel controlPanel;
	private JPanel custServPanel;
	private JPanel servicesPanel;
	private JPanel custServGroupPanel;
	private JPanel databasePanal;
	private JScrollPane dbContentsPanel1;
	private JScrollPane dbContentsPanel3;
	private JScrollPane dbContentsPanel4;

	private Border guiLineBorder;

	private JLabel vacanciesRoomLabel = new JLabel("Room Number");
	private JLabel vacanciesResRoomLabel = new JLabel();
	private JLabel vacanciesRoomTypeLabel = new JLabel("Room Type");
	private JLabel vacanciesResRoomTypeLabel = new JLabel("");
	private JLabel vacanciesRoomPriceLabel = new JLabel("Room Price");
	private JLabel vacanciesResRoomPriceLabel = new JLabel("");

	private JLabel RoomLabel = new JLabel("Room:");
	private JLabel FirstNameLabel = new JLabel("FirstName:");
	private JLabel LastNameLabel = new JLabel("LastName:");
	private JLabel Address1Label = new JLabel("Address1:");
	private JLabel TownLabel = new JLabel("Town:");
	private JLabel contactNoLabel = new JLabel("Contact Num:");
	private JLabel enterRoomNo = new JLabel("Select Room Number:");
	private JLabel servicesLabel = new JLabel("Extra Service:");
	private JLabel blankLabel = new JLabel(" ");

	private JLabel checkRoomLabel = new JLabel("Room:");
	private JLabel checkFirstNameLabel = new JLabel("FirstName:");
	private JLabel checkLastNameLabel = new JLabel("LastName:");
	private JLabel checkAddress1Label = new JLabel("Address1:");
	private JLabel checkTownLabel = new JLabel("Town:");
	private JLabel checkcontactNoLabel = new JLabel("Contact Number:");
	private JLabel checkRoomTypeLabel = new JLabel("Room Type:");
	private JLabel checkRoomPriceLabel = new JLabel("Room Price:");

	private JLabel resRoomLabel = new JLabel();
	private JLabel resFirstNameLabel = new JLabel();
	private JLabel resLastNameLabel = new JLabel();
	private JLabel resAddress1Label = new JLabel();
	private JLabel resTownLabel = new JLabel();
	private JLabel rescontactNoLabel = new JLabel();
	private JLabel resRoomTypeLabel = new JLabel();
	private JLabel resRoomPriceLabel = new JLabel();
	private JLabel resTempLabel = new JLabel();

	private JTextField RoomTF = new JTextField(10);
	private JTextField FirstNameTF = new JTextField(10);
	private JTextField LastNameTF = new JTextField(10);
	private JTextField Address1TF = new JTextField(10);
	private JTextField TownTF = new JTextField(10);
	private JTextField contactNoTF = new JTextField(10);

	private static QueryTableModelCustomer TableModelCustomer = new QueryTableModelCustomer();
	private static QueryTableModelServices TableModelServices = new QueryTableModelServices();
	private static QueryTableModelRoom TableModelRoom = new QueryTableModelRoom();
	private JTable TableofDBContentsCustomer = new JTable(TableModelCustomer);
	private JTable TableofDBContentsAmine = new JTable(TableModelServices);
	private JTable TableofDBContentsRoom = new JTable(TableModelRoom);

	private JButton updateButton = new JButton("Update");
	private JButton confirmButton = new JButton("Confirm");
	private JButton exportButton = new JButton("Export");
	private JButton deleteButton = new JButton("Delete");
	private JButton clearButton = new JButton("Clear");
	private JButton editButton = new JButton("Edit");

	private JButton homeButton = new JButton("Home");
	private JButton checkInButton = new JButton("Check In");
	private JButton checkOutButton = new JButton("Check Out");
	private JButton servicesButton = new JButton("Customer Services");

	private JButton vacanciesCheckButton = new JButton("Check For Vacancies");
	private JButton checkOutConfirmButton = new JButton("Confirm");
	private JButton checkOutSearchButton = new JButton("Search");

	private JButton whatRoomDDown = new JButton("What Room's");
	private JButton howManyCount = new JButton("How Many");

	private JRadioButton extraServiceTaxi = new JRadioButton("Taxi");
	private JRadioButton extraServiceIron = new JRadioButton("Iron");
	private JRadioButton extraServiceWakeupCall = new JRadioButton("Wakeup Call");
	private JRadioButton extraServiceBreakfast = new JRadioButton("Breakfast");

	private ButtonGroup roomTypeButtonGroup = new ButtonGroup();
	private JRadioButton singleRoom = new JRadioButton("Single");
	private JRadioButton doubleRoom = new JRadioButton("Double");
	private JRadioButton masterRoom = new JRadioButton("Master");

	private ButtonGroup checkServiceGroup = new ButtonGroup();
	private JRadioButton checkServiceTaxi = new JRadioButton("Taxi");
	private JRadioButton checkServiceIron = new JRadioButton("Iron");
	private JRadioButton checkServiceWakeupCall = new JRadioButton("Wakeup Call");
	private JRadioButton checkServiceBreakfast = new JRadioButton("Breakfast");

	private ButtonGroup databaseButtonGroup = new ButtonGroup();
	private JRadioButton roomInfoDatabase = new JRadioButton("Room DB");
	private JRadioButton customerInfoDatabase = new JRadioButton("Customer DB");
	private JRadioButton servicesInfoDatabase = new JRadioButton("Services DB");

	String[] list1 = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", };
	String[] list0 = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", };
	List[] list2 = {};

	private JComboBox<?> myRoomCB = new JComboBox<Object>(list1);
	private JComboBox<?> RoomCB = new JComboBox<Object>(list0);
	private JComboBox<String> vacanciesRoomCB = new JComboBox<String>();
	private JComboBox myServiceCB = new JComboBox();

	JTextPane custDetPane = new JTextPane();

	ButtonGroup servicesButtonGroup = new ButtonGroup();
	JRadioButton yesOptions = new JRadioButton("Yes");
	JRadioButton noOptions = new JRadioButton("No");

	ButtonGroup bigButtonGroup = new ButtonGroup();

	private Font customFont = new Font("Sans Bold", Font.BOLD, 16);
	private Color backgroundColorGray = new Color(167, 173, 186);
	private Color fontColorYellow = new Color(255, 220, 115);

	public MainContent(String aTitle) {
		super(aTitle, false, false, false, false);
		setEnabled(true);

		initiate_db_conn();
		contentContainer = getContentPane();
		contentContainer.setLayout(null);
		contentContainer.setBackground(backgroundColorGray);

		homeButton.setBackground(fontColorYellow);
		checkInButton.setBackground(fontColorYellow);
		checkOutButton.setBackground(fontColorYellow);
		servicesButton.setBackground(fontColorYellow);

		homeButton.setLocation(0, 3);
		checkInButton.setLocation(153, 3);
		checkOutButton.setLocation(306, 3);
		servicesButton.setLocation(456, 3);

		homeButton.setSize(150, 30);
		checkInButton.setSize(150, 30);
		checkOutButton.setSize(150, 30);
		servicesButton.setSize(190, 30);

		homeButton.setFont(customFont);
		checkInButton.setFont(customFont);
		checkOutButton.setFont(customFont);
		servicesButton.setFont(customFont);

		homeButton.addActionListener(this);
		checkInButton.addActionListener(this);
		checkOutButton.addActionListener(this);
		servicesButton.addActionListener(this);

		contentContainer.add(homeButton);
		contentContainer.add(checkInButton);
		contentContainer.add(checkOutButton);
		contentContainer.add(servicesButton);

		guiLineBorder = BorderFactory.createEtchedBorder(fontColorYellow, fontColorYellow);

		databaseButtonGroup.add(roomInfoDatabase);
		databaseButtonGroup.add(customerInfoDatabase);
		databaseButtonGroup.add(servicesInfoDatabase);

		roomInfoDatabase.setBackground(backgroundColorGray);
		customerInfoDatabase.setBackground(backgroundColorGray);
		servicesInfoDatabase.setBackground(backgroundColorGray);

		roomInfoDatabase.setFont(customFont);
		customerInfoDatabase.setFont(customFont);
		servicesInfoDatabase.setFont(customFont);

		roomInfoDatabase.addActionListener(listener);
		customerInfoDatabase.addActionListener(listener);
		servicesInfoDatabase.addActionListener(listener);

		databasePanal = new JPanel();
		databasePanal.setLayout(new GridLayout(1, 4));
		databasePanal.setBackground(backgroundColorGray);
		databasePanal.setBorder(BorderFactory.createTitledBorder(guiLineBorder, "Choose Database"));
		databasePanal.add(roomInfoDatabase);
		databasePanal.add(customerInfoDatabase);
		databasePanal.add(servicesInfoDatabase);
		databasePanal.setSize(600, 50);
		databasePanal.setLocation(475, 33);
		databasePanal.setVisible(false);
		contentContainer.add(databasePanal);

		vacanciesRoomLabel.setFont(customFont);
		vacanciesResRoomLabel.setFont(customFont);
		vacanciesRoomTypeLabel.setFont(customFont);
		vacanciesResRoomTypeLabel.setFont(customFont);
		vacanciesRoomPriceLabel.setFont(customFont);
		vacanciesResRoomPriceLabel.setFont(customFont);

		vacanciesRoomLabel.setForeground(fontColorYellow);
		vacanciesResRoomLabel.setForeground(fontColorYellow);
		vacanciesRoomTypeLabel.setForeground(fontColorYellow);
		vacanciesRoomPriceLabel.setForeground(fontColorYellow);

		vacanciesRoomCB.setPrototypeDisplayValue("XXX");

		vacanciesPanel = new JPanel();
		vacanciesPanel.setLayout(new GridLayout(3, 2));
		vacanciesPanel.setBackground(backgroundColorGray);
		vacanciesPanel.setBorder(BorderFactory.createTitledBorder(guiLineBorder, "VACANCIES"));
		vacanciesPanel.add(vacanciesRoomLabel);
		vacanciesPanel.add(vacanciesRoomCB);
		vacanciesPanel.add(vacanciesRoomTypeLabel);
		vacanciesPanel.add(vacanciesResRoomTypeLabel);
		vacanciesPanel.add(vacanciesRoomPriceLabel);
		vacanciesPanel.add(vacanciesResRoomPriceLabel);
		vacanciesPanel.setSize(300, 200);
		vacanciesPanel.setLocation(450, 70);
		vacanciesPanel.setVisible(false);
		contentContainer.add(vacanciesPanel);

		vacanciesCheckButton.setFont(customFont);
		singleRoom.setFont(customFont);
		doubleRoom.setFont(customFont);
		masterRoom.setFont(customFont);
		vacanciesRoomPriceLabel.setFont(customFont);
		vacanciesResRoomPriceLabel.setFont(customFont);

		roomTypeButtonGroup.add(singleRoom);
		roomTypeButtonGroup.add(doubleRoom);
		roomTypeButtonGroup.add(masterRoom);

		singleRoom.setBackground(backgroundColorGray);
		doubleRoom.setBackground(backgroundColorGray);
		masterRoom.setBackground(backgroundColorGray);

		singleRoom.addActionListener(this);
		doubleRoom.addActionListener(this);
		masterRoom.addActionListener(this);

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		vacanciesCheckButton.setBackground(fontColorYellow);
		vacanciesCheckButton.setSize(200, 30);
		controlPanel.add(vacanciesCheckButton);
		controlPanel.add(singleRoom);
		controlPanel.add(doubleRoom);
		controlPanel.add(masterRoom);
		controlPanel.setBackground(backgroundColorGray);
		controlPanel.setBorder(BorderFactory.createTitledBorder(guiLineBorder, "Vacancies"));
		controlPanel.setSize(600, 100);
		controlPanel.setLocation(300, 300);
		controlPanel.setVisible(false);
		contentContainer.add(controlPanel);

		vacanciesCheckButton.addActionListener(this);

		yesOptions.setFont(customFont);
		noOptions.setFont(customFont);
		extraServiceTaxi.setFont(customFont);
		extraServiceIron.setFont(customFont);
		extraServiceWakeupCall.setFont(customFont);
		extraServiceBreakfast.setFont(customFont);
		vacanciesRoomCB.setFont(customFont);

		yesOptions.setForeground(fontColorYellow);
		noOptions.setForeground(fontColorYellow);
		extraServiceTaxi.setForeground(fontColorYellow);
		extraServiceIron.setForeground(fontColorYellow);
		extraServiceWakeupCall.setForeground(fontColorYellow);
		extraServiceBreakfast.setForeground(fontColorYellow);

		yesOptions.setBackground(backgroundColorGray);
		noOptions.setBackground(backgroundColorGray);
		extraServiceTaxi.setBackground(backgroundColorGray);
		extraServiceIron.setBackground(backgroundColorGray);
		extraServiceWakeupCall.setBackground(backgroundColorGray);
		extraServiceBreakfast.setBackground(backgroundColorGray);
		vacanciesRoomCB.setBackground(backgroundColorGray);

		servicesButtonGroup.add(yesOptions);
		servicesButtonGroup.add(noOptions);

		servicesPanel = new JPanel();
		servicesPanel.setLayout(new GridLayout(4, 1));
		servicesPanel.setBackground(backgroundColorGray);
		servicesPanel.setBorder(BorderFactory.createTitledBorder(guiLineBorder, "Extra Service"));
		servicesPanel.add(extraServiceTaxi);
		servicesPanel.add(extraServiceIron);
		servicesPanel.add(extraServiceWakeupCall);
		servicesPanel.add(extraServiceBreakfast);
		servicesPanel.setSize(170, 250);
		servicesPanel.setLocation(3, 35);
		servicesPanel.setVisible(false);
		contentContainer.add(servicesPanel);

		RoomLabel.setFont(customFont);
		FirstNameLabel.setFont(customFont);
		LastNameLabel.setFont(customFont);
		Address1Label.setFont(customFont);
		TownLabel.setFont(customFont);
		contactNoLabel.setFont(customFont);
		enterRoomNo.setFont(customFont);
		servicesLabel.setFont(customFont);
		confirmButton.setFont(customFont);
		updateButton.setFont(customFont);
		deleteButton.setFont(customFont);
		clearButton.setFont(customFont);
		editButton.setFont(customFont);

		RoomCB.setFont(customFont);
		FirstNameTF.setFont(customFont);
		LastNameTF.setFont(customFont);
		Address1TF.setFont(customFont);
		TownTF.setFont(customFont);
		contactNoTF.setFont(customFont);

		RoomLabel.setForeground(fontColorYellow);
		FirstNameLabel.setForeground(fontColorYellow);
		LastNameLabel.setForeground(fontColorYellow);
		Address1Label.setForeground(fontColorYellow);
		TownLabel.setForeground(fontColorYellow);
		contactNoLabel.setForeground(fontColorYellow);
		enterRoomNo.setForeground(fontColorYellow);
		servicesLabel.setForeground(fontColorYellow);

		confirmButton.setBackground(fontColorYellow);
		updateButton.setBackground(fontColorYellow);
		exportButton.setBackground(fontColorYellow);
		deleteButton.setBackground(fontColorYellow);
		clearButton.setBackground(fontColorYellow);
		editButton.setBackground(fontColorYellow);

		confirmButton.setSize(100, 30);
		updateButton.setSize(100, 30);
		exportButton.setSize(100, 30);
		deleteButton.setSize(100, 30);
		clearButton.setSize(100, 30);
		editButton.setSize(100, 30);

		detailsPanel = new JPanel();
		detailsPanel.setLayout(new GridLayout(13, 2));
		detailsPanel.setBackground(backgroundColorGray);
		detailsPanel.setBorder(BorderFactory.createTitledBorder(guiLineBorder, "Customer Data Entry"));
		detailsPanel.add(RoomLabel);
		detailsPanel.add(RoomCB);
		detailsPanel.add(FirstNameLabel);
		detailsPanel.add(FirstNameTF);
		detailsPanel.add(LastNameLabel);
		detailsPanel.add(LastNameTF);
		detailsPanel.add(Address1Label);
		detailsPanel.add(Address1TF);
		detailsPanel.add(TownLabel);
		detailsPanel.add(TownTF);
		detailsPanel.add(contactNoLabel);
		detailsPanel.add(contactNoTF);
		detailsPanel.add(blankLabel);
		detailsPanel.add(servicesLabel);

		detailsPanel.add(yesOptions);
		detailsPanel.add(noOptions);
		detailsPanel.add(blankLabel);
		detailsPanel.add(confirmButton);
		detailsPanel.add(updateButton);
		detailsPanel.add(deleteButton);
		detailsPanel.add(clearButton);
		detailsPanel.add(editButton);
		detailsPanel.setSize(300, 347);
		detailsPanel.setLocation(177, 35);
		detailsPanel.setVisible(false);
		contentContainer.add(detailsPanel);

		confirmButton.addActionListener(this);
		updateButton.addActionListener(this);
		exportButton.addActionListener(this);
		deleteButton.addActionListener(this);
		clearButton.addActionListener(this);
		editButton.addActionListener(this);

		yesOptions.addActionListener(listener);
		noOptions.addActionListener(listener);
		extraServiceTaxi.addActionListener(listener);
		extraServiceIron.addActionListener(listener);
		extraServiceWakeupCall.addActionListener(listener);
		extraServiceBreakfast.addActionListener(listener);

		TableofDBContentsCustomer.setPreferredScrollableViewportSize(new Dimension(300, 600));
		dbContentsPanel1 = new JScrollPane(TableofDBContentsCustomer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel1.setBackground(fontColorYellow);
		dbContentsPanel1.setBorder(BorderFactory.createTitledBorder(guiLineBorder, "Database Content"));
		dbContentsPanel1.setVisible(false);
		dbContentsPanel1.setSize(700, 300);
		dbContentsPanel1.setLocation(477, 80);
		contentContainer.add(dbContentsPanel1);

		TableofDBContentsAmine.setPreferredScrollableViewportSize(new Dimension(300, 600));
		dbContentsPanel3 = new JScrollPane(TableofDBContentsAmine, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel3.setBackground(fontColorYellow);
		dbContentsPanel3.setBorder(BorderFactory.createTitledBorder(guiLineBorder, "Database Content"));
		dbContentsPanel3.setVisible(false);
		dbContentsPanel3.setSize(700, 300);
		dbContentsPanel3.setLocation(477, 80);
		contentContainer.add(dbContentsPanel3);

		TableofDBContentsRoom.setPreferredScrollableViewportSize(new Dimension(300, 600));
		dbContentsPanel4 = new JScrollPane(TableofDBContentsRoom, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel4.setBackground(fontColorYellow);
		dbContentsPanel4.setBorder(BorderFactory.createTitledBorder(guiLineBorder, "Database Content"));
		dbContentsPanel4.setVisible(false);
		dbContentsPanel4.setSize(700, 300);
		dbContentsPanel4.setLocation(477, 80);
		contentContainer.add(dbContentsPanel4);

		checkRoomLabel.setFont(customFont);
		resRoomLabel.setFont(customFont);
		checkFirstNameLabel.setFont(customFont);
		resFirstNameLabel.setFont(customFont);
		checkLastNameLabel.setFont(customFont);
		resLastNameLabel.setFont(customFont);
		checkAddress1Label.setFont(customFont);
		resAddress1Label.setFont(customFont);
		checkTownLabel.setFont(customFont);
		resTownLabel.setFont(customFont);
		checkcontactNoLabel.setFont(customFont);
		rescontactNoLabel.setFont(customFont);
		checkRoomTypeLabel.setFont(customFont);
		resRoomTypeLabel.setFont(customFont);
		checkRoomPriceLabel.setFont(customFont);
		resRoomPriceLabel.setFont(customFont);
		enterRoomNo.setFont(customFont);
		myRoomCB.setFont(customFont);

		checkOutSearchButton.setFont(customFont);
		checkOutConfirmButton.setFont(customFont);

		RoomTF.setFont(customFont);
		FirstNameTF.setFont(customFont);
		LastNameTF.setFont(customFont);
		Address1TF.setFont(customFont);
		TownTF.setFont(customFont);
		contactNoTF.setFont(customFont);

		checkRoomLabel.setForeground(fontColorYellow);
		checkFirstNameLabel.setForeground(fontColorYellow);
		checkLastNameLabel.setForeground(fontColorYellow);
		checkAddress1Label.setForeground(fontColorYellow);
		checkTownLabel.setForeground(fontColorYellow);
		checkcontactNoLabel.setForeground(fontColorYellow);
		checkRoomTypeLabel.setForeground(fontColorYellow);
		checkRoomPriceLabel.setForeground(fontColorYellow);
		enterRoomNo.setForeground(fontColorYellow);

		checkOutConfirmButton.setSize(100, 30);
		checkOutSearchButton.setSize(100, 30);
		checkOutConfirmButton.setBackground(fontColorYellow);
		checkOutSearchButton.setBackground(fontColorYellow);

		checkOutPanel = new JPanel();
		checkOutPanel.setLayout(new GridLayout(10, 2));
		checkOutPanel.setBackground(backgroundColorGray);
		checkOutPanel.setBorder(BorderFactory.createTitledBorder(guiLineBorder, "CUSTOMER CHECKOUT DETAILS"));
		checkOutPanel.add(checkRoomLabel);
		checkOutPanel.add(resRoomLabel);
		checkOutPanel.add(checkFirstNameLabel);
		checkOutPanel.add(resFirstNameLabel);
		checkOutPanel.add(checkLastNameLabel);
		checkOutPanel.add(resLastNameLabel);
		checkOutPanel.add(checkAddress1Label);
		checkOutPanel.add(resAddress1Label);
		checkOutPanel.add(checkTownLabel);
		checkOutPanel.add(resTownLabel);
		checkOutPanel.add(checkcontactNoLabel);
		checkOutPanel.add(rescontactNoLabel);
		checkOutPanel.add(checkRoomTypeLabel);
		checkOutPanel.add(resRoomTypeLabel);
		checkOutPanel.add(checkRoomPriceLabel);
		checkOutPanel.add(resRoomPriceLabel);
		checkOutPanel.add(enterRoomNo);
		checkOutPanel.add(myRoomCB);
		checkOutPanel.add(checkOutSearchButton);
		checkOutPanel.add(checkOutConfirmButton);
		checkOutPanel.setSize(400, 400);
		checkOutPanel.setLocation(400, 50);
		checkOutPanel.setVisible(false);
		contentContainer.add(checkOutPanel);

		checkOutConfirmButton.addActionListener(this);
		checkOutSearchButton.addActionListener(this);

		checkServiceTaxi.setFont(customFont);
		checkServiceIron.setFont(customFont);
		checkServiceWakeupCall.setFont(customFont);
		checkServiceBreakfast.setFont(customFont);
		TownTF.setFont(customFont);
		contactNoTF.setFont(customFont);
		whatRoomDDown.setFont(customFont);
		howManyCount.setFont(customFont);
		resTempLabel.setFont(customFont);
		myServiceCB.setFont(customFont);

		checkServiceGroup.add(checkServiceTaxi);
		checkServiceGroup.add(checkServiceIron);
		checkServiceGroup.add(checkServiceWakeupCall);
		checkServiceGroup.add(checkServiceBreakfast);

		checkServiceTaxi.setBackground(backgroundColorGray);
		checkServiceIron.setBackground(backgroundColorGray);
		checkServiceWakeupCall.setBackground(backgroundColorGray);
		checkServiceBreakfast.setBackground(backgroundColorGray);
		myServiceCB.setBackground(backgroundColorGray);

		whatRoomDDown.setBackground(fontColorYellow);
		howManyCount.setBackground(fontColorYellow);

		checkServiceTaxi.addActionListener(this);
		checkServiceIron.addActionListener(this);
		checkServiceWakeupCall.addActionListener(this);
		checkServiceBreakfast.addActionListener(this);
		whatRoomDDown.addActionListener(this);
		howManyCount.addActionListener(this);

		custServPanel = new JPanel();
		custServPanel.setLayout(new FlowLayout());
		custServPanel.setBackground(backgroundColorGray);
		custServPanel.setBorder(BorderFactory.createTitledBorder(guiLineBorder, "Customer Required Services"));
		custServPanel.add(whatRoomDDown);
		custServPanel.add(howManyCount);
		custServPanel.add(resTempLabel);
		custServPanel.add(myServiceCB);

		custServPanel.setSize(700, 400);
		custServPanel.setLocation(250, 50);
		myServiceCB.setVisible(false);
		custServPanel.setVisible(false);

		custServGroupPanel = new JPanel();
		custServGroupPanel.setLayout(new GridLayout(1, 4));
		custServGroupPanel.setBackground(backgroundColorGray);
		custServGroupPanel.setBorder(BorderFactory.createTitledBorder(guiLineBorder, "Select From Services"));
		custServGroupPanel.add(checkServiceTaxi);
		custServGroupPanel.add(checkServiceIron);
		custServGroupPanel.add(checkServiceWakeupCall);
		custServGroupPanel.add(checkServiceBreakfast);
		custServGroupPanel.setSize(100, 400);
		custServGroupPanel.setLocation(280, 300);
		custServGroupPanel.setVisible(true);
		custServPanel.add(custServGroupPanel);

		contentContainer.add(custServPanel);
		setSize(1000, 845);
		setVisible(true);

		TableModelCustomer.refreshFromDB(sqlStatement);
	}

	public void initiate_db_conn() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3307/test2";
			databaseConnection = DriverManager.getConnection(url, "root", "admin");
			sqlStatement = databaseConnection.createStatement();
		} catch (Exception e) {
			System.out.println("Error: Failed to connect to database\n" + e.getMessage());
		}
	}

	ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (yesOptions.isSelected()) {
				servicesPanel.setVisible(true);

				if (extraServiceTaxi.isSelected()) {
					taxiString = "yes";
				} else
					taxiString = "no";

				if (extraServiceIron.isSelected()) {
					ironString = "yes";
				} else
					ironString = "no";

				if (extraServiceWakeupCall.isSelected()) {
					wakecallString = "yes";
				} else
					wakecallString = "no";
			}

			if (extraServiceBreakfast.isSelected()) {
				breakfastString = "yes";
			} else
				breakfastString = "no";

			if (noOptions.isSelected()) {
				servicesPanel.setVisible(false);
			}

			if (roomInfoDatabase.isSelected()) {
				dbContentsPanel4.setVisible(true);
				dbContentsPanel1.setVisible(false);
				dbContentsPanel3.setVisible(false);
				TableModelCustomer.refreshFromDB(sqlStatement);
			}

			if (customerInfoDatabase.isSelected()) {
				dbContentsPanel1.setVisible(true);
				dbContentsPanel4.setVisible(false);
				dbContentsPanel3.setVisible(false);
				TableModelCustomer.refreshFromDB(sqlStatement);
			}

			if (servicesInfoDatabase.isSelected()) {
				dbContentsPanel3.setVisible(true);
				dbContentsPanel1.setVisible(false);
				dbContentsPanel4.setVisible(false);
				TableModelServices.refreshFromDB(sqlStatement);
			}
		}
	};

	public void actionPerformed(ActionEvent e) {

		Object target = e.getSource();

		if (target == homeButton) {
			controlPanel.setVisible(true);
			detailsPanel.setVisible(false);
			checkOutPanel.setVisible(false);
			dbContentsPanel1.setVisible(false);
			dbContentsPanel3.setVisible(false);
			dbContentsPanel4.setVisible(false);
			vacanciesPanel.setVisible(true);
			custServPanel.setVisible(false);
			databasePanal.setVisible(false);
			servicesPanel.setVisible(false);

		}

		if (target == checkInButton) {
			controlPanel.setVisible(false);
			detailsPanel.setVisible(true);
			checkOutPanel.setVisible(false);
			vacanciesPanel.setVisible(false);
			custServPanel.setVisible(false);
			dbContentsPanel1.setVisible(false);
			dbContentsPanel3.setVisible(false);
			dbContentsPanel4.setVisible(false);
			databasePanal.setVisible(true);
			servicesPanel.setVisible(false);
			TableModelRoom.refreshFromDB(sqlStatement);

			servicesPanel.setVisible(false);
		}

		if (target == checkOutButton) {
			checkOutPanel.setVisible(true);
			controlPanel.setVisible(false);
			detailsPanel.setVisible(false);
			dbContentsPanel1.setVisible(false);
			dbContentsPanel3.setVisible(false);
			dbContentsPanel4.setVisible(false);
			vacanciesPanel.setVisible(false);
			custServPanel.setVisible(false);
			databasePanal.setVisible(false);
			servicesPanel.setVisible(false);
		}

		if (target == checkOutConfirmButton) {
			String updateTemp1 = "DELETE customer, service FROM customer INNER JOIN service WHERE customer.room="
					+ RoomCB.getSelectedItem() + " AND service.room=" + RoomCB.getSelectedItem() + ";";
			String updateTemp0 = "UPDATE room inner join customer inner join service SET room.roomVacant = 'yes', room.cust_id = null, room.service_id = null where room_id = "
					+ RoomCB.getSelectedItem() + ";";
			try {
				sqlStatement.executeUpdate(updateTemp1);
				sqlStatement.executeUpdate(updateTemp0);
			} catch (SQLException sqle) {
				System.err.println("Error with  insert:\n" + sqle.toString());
			}

		}

		if (target == servicesButton) {
			controlPanel.setVisible(false);
			detailsPanel.setVisible(false);
			checkOutPanel.setVisible(false);
			dbContentsPanel1.setVisible(false);
			dbContentsPanel3.setVisible(false);
			dbContentsPanel4.setVisible(false);
			vacanciesPanel.setVisible(false);
			custServPanel.setVisible(true);
			databasePanal.setVisible(false);
			servicesPanel.setVisible(false);
		}

		if (target == clearButton) {
			RoomTF.setText("");
			FirstNameTF.setText("");
			LastNameTF.setText("");
			Address1TF.setText("");
			TownTF.setText("");
			contactNoTF.setText("");
		}

		if (target == confirmButton) {

			try {

				String updateTemp0 = "UPDATE room inner join customer inner join service SET room.roomVacant = 'no', room.cust_id = customer.cust_id, room.service_id = service.service_id where room_id = "
						+ RoomCB.getSelectedItem() + ";";
				sqlStatement.executeUpdate(updateTemp0);
				databaseResultSet = sqlStatement.executeQuery("SELECT * from room ");
				databaseResultSet.next();
				databaseResultSet.close();
			}

			catch (SQLException sqle) {
				System.err.println("Error with delete:\n" + sqle.toString());
			}

			TableModelCustomer.refreshFromDB(sqlStatement);

			try {
				String updateTemp = "INSERT INTO customer VALUES(" + null + ",'" + RoomCB.getSelectedItem() + "','"
						+ FirstNameTF.getText() + "','" + LastNameTF.getText() + "','" + Address1TF.getText() + "','"
						+ TownTF.getText() + "','" + contactNoTF.getText() + "');";

				if (yesOptions.isSelected()) {
					String updateTemp3 = "INSERT INTO service VALUES(" + null + ",'" + taxiString + "','" + ironString
							+ "','" + wakecallString + "','" + breakfastString + "','" + RoomCB.getSelectedItem()
							+ "');";
					sqlStatement.executeUpdate(updateTemp3);
					TableModelServices.refreshFromDB(sqlStatement);
				}
				if (noOptions.isSelected()) {
					String updateTemp3 = "INSERT INTO service VALUES(" + null + ",'" + taxiString + "','" + ironString
							+ "','" + wakecallString + "','" + breakfastString + "','" + RoomCB.getSelectedItem()
							+ "');";
					sqlStatement.executeUpdate(updateTemp3);
					TableModelServices.refreshFromDB(sqlStatement);
				}

				sqlStatement.executeUpdate(updateTemp);

			} catch (SQLException sqle) {
				System.err.println("Error with  insert:\n" + sqle.toString());
			} finally {
				TableModelCustomer.refreshFromDB(sqlStatement);
				TableModelServices.refreshFromDB(sqlStatement);
				TableModelRoom.refreshFromDB(sqlStatement);
			}
		}
		if (target == deleteButton) {
			String updateTemp1 = "DELETE customer, service FROM customer INNER JOIN service WHERE customer.room="
					+ RoomCB.getSelectedItem() + " AND service.room=" + RoomCB.getSelectedItem() + ";";
			String updateTemp0 = "UPDATE room SET room.roomVacant = 'yes', room.cust_id = null, room.service_id = null where room_id = "
					+ RoomCB.getSelectedItem() + ";";
			try {
				sqlStatement.executeUpdate(updateTemp1);
				sqlStatement.executeUpdate(updateTemp0);
			} catch (SQLException sqle) {
				System.err.println("Error with  insert:\n" + sqle.toString());
			}

			finally {
				TableModelCustomer.refreshFromDB(sqlStatement);
				TableModelServices.refreshFromDB(sqlStatement);
				TableModelRoom.refreshFromDB(sqlStatement);
			}
		}
		if (target == updateButton) {
			try {

				if (yesOptions.isSelected()) {

					String updateTemp3 = "UPDATE service SET " + "Taxi = '" + taxiString + "'," + " Iron = '"
							+ ironString + "'," + " Breakfast = '" + breakfastString + "'," + " WakeUpCall = '"
							+ wakecallString + "',  room = '" + RoomCB.getSelectedItem() + "' where service.room = "
							+ RoomCB.getSelectedItem();
					sqlStatement.executeUpdate(updateTemp3);
					databaseResultSet = sqlStatement.executeQuery("SELECT * from service ");
					databaseResultSet.next();
					databaseResultSet.close();
					TableModelServices.refreshFromDB(sqlStatement);
				}

				String updateTemp1 = "UPDATE customer SET " + "room = '" + RoomCB.getSelectedItem() + "',firstName = '"
						+ FirstNameTF.getText() + "', lastName = '" + LastNameTF.getText() + "', address1 = '"
						+ Address1TF.getText() + "', town = '" + TownTF.getText() + "', contactNo = '"
						+ contactNoTF.getText() + "' where customer.room = " + RoomCB.getSelectedItem();

				sqlStatement.executeUpdate(updateTemp1);

				databaseResultSet = sqlStatement.executeQuery("SELECT * from customer ");
				databaseResultSet.next();
				databaseResultSet.close();
			} catch (SQLException sqle) {
				System.err.println("Error with  update:\n" + sqle.toString());
			} finally {
				TableModelCustomer.refreshFromDB(sqlStatement);
			}
		}

		if (target == this.checkOutSearchButton) {

			command = "select roomNo, roomType, roomPrice, firstName, lastName, address1, town, contactNo, roomVacant from room inner join customer where room.room_id="
					+ myRoomCB.getSelectedItem() + " AND customer.room=" + myRoomCB.getSelectedItem() + ";";

			try {
				databaseResultSet = sqlStatement.executeQuery(command);
				writeToFile(databaseResultSet);

			} catch (Exception e1) {
				e1.printStackTrace();
			}

			CSVReader reader = null;
			try {
				reader = new CSVReader(
						new FileReader("C:/Users/Keith Bentham/Desktop/JDBC Project/Main_JDBC_Project/Test.csv"), ',',
						'"', 1);
				String[] nextLine;

				while ((nextLine = reader.readNext()) != null) {

					resRoomLabel.setText(nextLine[0]);
					resFirstNameLabel.setText(nextLine[3]);
					resLastNameLabel.setText(nextLine[4]);
					resAddress1Label.setText(nextLine[5]);
					resTownLabel.setText(nextLine[6]);
					rescontactNoLabel.setText(nextLine[7]);
					resRoomTypeLabel.setText(nextLine[1]);
					resRoomPriceLabel.setText("€" + nextLine[2]);
					System.out.println(nextLine[8]);

				}
			} catch (Exception en) {
				en.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException en) {
					en.printStackTrace();
				}
			}
		}

		if (target == this.editButton) {
			command = "select firstName, lastName, address1, town, contactNo from customer where customer.room="
					+ RoomCB.getSelectedItem() + ";";

			try {
				databaseResultSet = sqlStatement.executeQuery(command);
				writeToFile(databaseResultSet);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			CSVReader reader = null;
			try {
				reader = new CSVReader(
						new FileReader("C:/Users/Keith Bentham/Desktop/JDBC Project/Main_JDBC_Project/Test.csv"), ',',
						'"', 1);
				String[] nextLine;

				while ((nextLine = reader.readNext()) != null) {

					FirstNameTF.setText(nextLine[0]);
					LastNameTF.setText(nextLine[1]);
					Address1TF.setText(nextLine[2]);
					TownTF.setText(nextLine[3]);
					contactNoTF.setText(nextLine[4]);

				}
			} catch (Exception en) {
				en.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException en) {
					en.printStackTrace();
				}
			}
		}

		if (target == this.vacanciesCheckButton) {

			command1 = "select roomNo from room where room.roomVacant='yes' AND room.roomType='Single';";
			command2 = "select roomNo from room where room.roomVacant='yes'AND room.roomType='Double';";
			command3 = "select roomNo from room where room.roomVacant='yes'AND room.roomType='Master';";

			if (singleRoom.isSelected()) {
				vacanciesResRoomTypeLabel.setText("Single");
				vacanciesResRoomPriceLabel.setText("€39.99");
				command = command1;
			}
			if (doubleRoom.isSelected()) {
				vacanciesResRoomTypeLabel.setText("Double");
				vacanciesResRoomPriceLabel.setText("€49.99");
				command = command2;
			}
			if (masterRoom.isSelected()) {
				vacanciesResRoomTypeLabel.setText("Master");
				vacanciesResRoomPriceLabel.setText("€59.99");
				command = command3;
			}

			try {

				databaseResultSet = sqlStatement.executeQuery(command);
				writeToFile(databaseResultSet);
				vacanciesRoomCB.removeAllItems();

			} catch (Exception e1) {
				e1.printStackTrace();
			}

			CSVReader reader = null;
			try {
				reader = new CSVReader(
						new FileReader("C:/Users/Keith Bentham/Desktop/JDBC Project/Main_JDBC_Project/Test.csv"), ',',
						'"', 1);
				String[] nextLine;

				while ((nextLine = reader.readNext()) != null) {

					vacanciesRoomCB.addItem(nextLine[0]);

					System.out.println(nextLine[0]);
				}
			} catch (Exception en) {
				en.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException en) {
					en.printStackTrace();
				}
			}

		}

		if (target == this.whatRoomDDown) {

			command2 = "select room from service  where service.Taxi='yes';";
			command3 = "select room from service  where service.Iron='yes';";
			command4 = "select room from service  where service.WakeUpCall='yes';";
			command5 = "select room from service  where service.Breakfast='yes';";

			if (checkServiceTaxi.isSelected()) {
				command = command2;
			}

			if (checkServiceIron.isSelected()) {
				command = command3;
			}

			if (checkServiceWakeupCall.isSelected()) {
				command = command4;
			}

			if (checkServiceBreakfast.isSelected()) {
				command = command5;
			}

			try {
				myServiceCB.removeAllItems();
				databaseResultSet = sqlStatement.executeQuery(command);
				writeToFile(databaseResultSet);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			try {
				databaseResultSet = sqlStatement.executeQuery(command);
				writeToFile(databaseResultSet);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			CSVReader reader = null;
			try {
				reader = new CSVReader(
						new FileReader("C:/Users/Keith Bentham/Desktop/JDBC Project/Main_JDBC_Project/Test.csv"), ',',
						'"', 1);
				String[] nextLine;

				while ((nextLine = reader.readNext()) != null) {

					myServiceCB.addItem(nextLine[0]);
					if (checkServiceTaxi.isSelected()) {
						resTempLabel
								.setText("The rooms that want a Taxi are:" + "										");
						myServiceCB.setVisible(true);
					}

					if (checkServiceIron.isSelected()) {
						resTempLabel
								.setText("The rooms that want an Iron are:" + "										");
						myServiceCB.setVisible(true);
					}

					if (checkServiceWakeupCall.isSelected()) {
						resTempLabel
								.setText("The rooms that want a Wakeup Call are:" + "								");
						myServiceCB.setVisible(true);
					}

					if (checkServiceBreakfast.isSelected()) {
						resTempLabel
								.setText("The rooms that want Breakfast are:" + "   								");
						myServiceCB.setVisible(true);
					}

					for (String token : nextLine) {
						System.out.println(token);
					}
				}
			}

			catch (Exception en) {
				en.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException en) {
					en.printStackTrace();
				}

			}

		}

		if (target == this.howManyCount) {

			myServiceCB.setVisible(false);
			command2 = "select count(*) from service  where service.Taxi='yes';";
			command3 = "select count(*) from service  where service.Iron='yes';";
			command4 = "select count(*) from service  where service.WakeUpCall='yes';";
			command5 = "select count(*) from service  where service.Breakfast='yes';";

			if (checkServiceTaxi.isSelected()) {
				command = command2;
			}

			if (checkServiceIron.isSelected()) {
				command = command3;
			}

			if (checkServiceWakeupCall.isSelected()) {
				command = command4;
			}

			if (checkServiceBreakfast.isSelected()) {
				command = command5;
			}

			try {
				databaseResultSet = sqlStatement.executeQuery(command);
				writeToFile(databaseResultSet);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			CSVReader reader = null;
			try {
				reader = new CSVReader(
						new FileReader("C:/Users/Keith Bentham/Desktop/JDBC Project/Main_JDBC_Project/Test.csv"), ',',
						'"', 1);
				String[] nextLine;

				while ((nextLine = reader.readNext()) != null) {

					if (checkServiceTaxi.isSelected()) {
						resTempLabel.setText("The number of rooms that want a Taxi : " + nextLine[0]);
					}

					if (checkServiceIron.isSelected()) {
						resTempLabel.setText("The number of rooms that want an Iron are: " + nextLine[0]);
					}

					if (checkServiceWakeupCall.isSelected()) {
						resTempLabel.setText("The number of rooms that want a Wakeup Call are: " + nextLine[0]);
					}
					if (checkServiceBreakfast.isSelected()) {
						resTempLabel.setText("The number of rooms that want breakfast are: " + nextLine[0]);
					}
				}
			}

			catch (Exception en) {
				en.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException en) {
					en.printStackTrace();
				}

			}

		}
	}

	private void writeToFile(ResultSet rs) {
		try {
			System.out.println("In writeToFile");
			FileWriter outputFile = new FileWriter("test.csv");
			PrintWriter printWriter = new PrintWriter(outputFile);
			HSSFWorkbook wb = new HSSFWorkbook();
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();

			for (int i = 0; i < numColumns; i++) {
				printWriter.print(rsmd.getColumnLabel(i + 1) + ",");
			}
			printWriter.print("\n");
			while (rs.next()) {
				for (int i = 0; i < numColumns; i++) {
					printWriter.print(rs.getString(i + 1) + ",");
				}
				printWriter.print("\n");
				printWriter.flush();
			}
			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
