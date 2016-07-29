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

	private Border lineBorder;

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
	private JLabel Address2Label = new JLabel("Address2:");
	private JLabel TownLabel = new JLabel("Town:");
	private JLabel contactNoLabel = new JLabel("Contact Num:");
	private JLabel enterRoomNo = new JLabel("Select Room Number:");
	private JLabel servicesLabel = new JLabel("Extra Service:");
	private JLabel blankLabel = new JLabel(" 			");
	
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
	private JTextField Address2TF = new JTextField(10);
	private JTextField TownTF = new JTextField(10);
	private JTextField contactNoTF = new JTextField(10);

	private static QueryTableModelCustomer TableModelCustomer = new QueryTableModelCustomer();
	private static QueryTableModelAmine TableModelAmine = new QueryTableModelAmine();
	private static QueryTableModelRoom TableModelRoom = new QueryTableModelRoom();
	private JTable TableofDBContentsCustomer = new JTable(TableModelCustomer);
	private JTable TableofDBContentsAmine = new JTable(TableModelAmine);
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

	private JButton checkService1 = new JButton("What Room's");
	private JButton checkService2 = new JButton("How Many");

	private JRadioButton taxi = new JRadioButton("Taxi");
	private JRadioButton iron = new JRadioButton("Iron");
	private JRadioButton wakeupCall = new JRadioButton("Wakeup Call");
	private JRadioButton brecky = new JRadioButton("Breakfast");

	private ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton X = new JRadioButton("X");
	private JRadioButton A = new JRadioButton("Single");
	private JRadioButton B = new JRadioButton("Double");
	private JRadioButton C = new JRadioButton("Master");

	private ButtonGroup checkServiceGroup = new ButtonGroup();
	private JRadioButton Q = new JRadioButton("Q");
	private JRadioButton bf = new JRadioButton("Breakfast");
	private JRadioButton tax = new JRadioButton("Taxi");
	private JRadioButton ior = new JRadioButton("Iron");
	private JRadioButton wakup = new JRadioButton("Wakeup Call");
	private JRadioButton breck = new JRadioButton("Brecky");

	private ButtonGroup dbGroup = new ButtonGroup();
	private JRadioButton Room = new JRadioButton("Room DB");
	private JRadioButton Customer = new JRadioButton("Customer DB");
	private JRadioButton Breakfast = new JRadioButton("Breakfast DB");
	private JRadioButton Amine = new JRadioButton("Service DB");

	String[] list1 = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", };
	String[] list0 = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", };
	List[] list2 = {};

	private JComboBox<?> myRoomCB = new JComboBox<Object>(list1);
	private JComboBox<?> RoomCB = new JComboBox<Object>(list0);
	private JComboBox<String> vacanciesRoomCB = new JComboBox<String>();
	private JComboBox myServiceCB = new JComboBox();

	JTextPane custDetPane = new JTextPane();

	ButtonGroup breakfastGroup = new ButtonGroup();
	JRadioButton Z = new JRadioButton("Z");
	JRadioButton yes = new JRadioButton("Yes");
	JRadioButton no = new JRadioButton("No");

	ButtonGroup amenitiesGroup = new ButtonGroup();
	JRadioButton y = new JRadioButton("Y");
	JRadioButton yes1 = new JRadioButton("Yes");
	JRadioButton no1 = new JRadioButton("No");

	ButtonGroup bigButtonGroup = new ButtonGroup();

	private Font f1 = new Font("Sans Bold", Font.BOLD, 16);

	private Color backgroundColorGray = new Color(167,173,186);

	private Color fontColorYellow = new Color(255,220,115);

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

		homeButton.setFont(f1);
		checkInButton.setFont(f1);
		checkOutButton.setFont(f1);
		servicesButton.setFont(f1);

		homeButton.addActionListener(this);
		checkInButton.addActionListener(this);
		checkOutButton.addActionListener(this);
		servicesButton.addActionListener(this);

		contentContainer.add(homeButton);
		contentContainer.add(checkInButton);
		contentContainer.add(checkOutButton);
		contentContainer.add(servicesButton);

		lineBorder = BorderFactory.createEtchedBorder(fontColorYellow, fontColorYellow);

		dbGroup.add(Room);
		dbGroup.add(Customer);
		dbGroup.add(Amine);

		Room.setBackground(backgroundColorGray);
		Customer.setBackground(backgroundColorGray);
		Amine.setBackground(backgroundColorGray);

		Room.setFont(f1);
		Customer.setFont(f1);
		Amine.setFont(f1);

		Room.addActionListener(listener);
		Customer.addActionListener(listener);
		Amine.addActionListener(listener);

		databasePanal = new JPanel();
		databasePanal.setLayout(new GridLayout(1, 4));
		databasePanal.setBackground(backgroundColorGray);
		databasePanal.setBorder(BorderFactory.createTitledBorder(lineBorder, "Choose Database"));
		databasePanal.add(Room);
		databasePanal.add(Customer);
		databasePanal.add(Amine);
		databasePanal.setSize(600, 50);
		databasePanal.setLocation(475, 33);
		databasePanal.setVisible(false);
		contentContainer.add(databasePanal);

		vacanciesRoomLabel.setFont(f1);
		vacanciesResRoomLabel.setFont(f1);
		vacanciesRoomTypeLabel.setFont(f1);
		vacanciesResRoomTypeLabel.setFont(f1);
		vacanciesRoomPriceLabel.setFont(f1);
		vacanciesResRoomPriceLabel.setFont(f1);

		vacanciesRoomLabel.setForeground(fontColorYellow);
		vacanciesResRoomLabel.setForeground(fontColorYellow);
		vacanciesRoomTypeLabel.setForeground(fontColorYellow);
		vacanciesRoomPriceLabel.setForeground(fontColorYellow);

		vacanciesRoomCB.setPrototypeDisplayValue("XXX");

		vacanciesPanel = new JPanel();
		vacanciesPanel.setLayout(new GridLayout(3, 2));
		vacanciesPanel.setBackground(backgroundColorGray);
		vacanciesPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "VACANCIES"));
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

		vacanciesCheckButton.setFont(f1);
		A.setFont(f1);
		B.setFont(f1);
		C.setFont(f1);
		vacanciesRoomPriceLabel.setFont(f1);
		vacanciesResRoomPriceLabel.setFont(f1);

		buttonGroup.add(X);
		X.setVisible(false);
		buttonGroup.add(A);
		buttonGroup.add(B);
		buttonGroup.add(C);

		X.setBackground(backgroundColorGray);
		A.setBackground(backgroundColorGray);
		B.setBackground(backgroundColorGray);
		C.setBackground(backgroundColorGray);

		A.addActionListener(this);
		B.addActionListener(this);
		C.addActionListener(this);

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		vacanciesCheckButton.setBackground(fontColorYellow);
		vacanciesCheckButton.setSize(200, 30);
		controlPanel.add(vacanciesCheckButton);
		controlPanel.add(X);
		controlPanel.add(A);
		controlPanel.add(B);
		controlPanel.add(C);
		controlPanel.setBackground(backgroundColorGray);
		controlPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Vacancies"));
		controlPanel.setSize(600, 100);
		controlPanel.setLocation(300, 300);
		controlPanel.setVisible(false);
		contentContainer.add(controlPanel);

		vacanciesCheckButton.addActionListener(this);
		
		yes1.setFont(f1);
		no1.setFont(f1);
		taxi.setFont(f1);
		iron.setFont(f1);
		wakeupCall.setFont(f1);
		brecky.setFont(f1);
		vacanciesRoomCB.setFont(f1);

		yes1.setForeground(fontColorYellow);
		no1.setForeground(fontColorYellow);
		taxi.setForeground(fontColorYellow);
		iron.setForeground(fontColorYellow);
		wakeupCall.setForeground(fontColorYellow);
		brecky.setForeground(fontColorYellow);

		yes1.setBackground(backgroundColorGray);
		no1.setBackground(backgroundColorGray);
		y.setBackground(backgroundColorGray);
		taxi.setBackground(backgroundColorGray);
		iron.setBackground(backgroundColorGray);
		wakeupCall.setBackground(backgroundColorGray);
		brecky.setBackground(backgroundColorGray);
		vacanciesRoomCB.setBackground(backgroundColorGray);

		amenitiesGroup.add(y);
		y.setVisible(false);
		amenitiesGroup.add(yes1);
		amenitiesGroup.add(no1);

		servicesPanel = new JPanel();
		servicesPanel.setLayout(new GridLayout(4, 1));
		servicesPanel.setBackground(backgroundColorGray);
		servicesPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Extra Service"));
		servicesPanel.add(taxi);
		servicesPanel.add(iron);
		servicesPanel.add(wakeupCall);
		servicesPanel.add(brecky);
		servicesPanel.setSize(170, 250);
		servicesPanel.setLocation(3, 35);
		servicesPanel.setVisible(false);
		contentContainer.add(servicesPanel);
		
		RoomLabel.setFont(f1);
		FirstNameLabel.setFont(f1);
		LastNameLabel.setFont(f1);
		Address1Label.setFont(f1);
		Address2Label.setFont(f1);
		TownLabel.setFont(f1);
		contactNoLabel.setFont(f1);
		enterRoomNo.setFont(f1);
		servicesLabel.setFont(f1);
		confirmButton.setFont(f1);
		updateButton.setFont(f1);
		deleteButton.setFont(f1);
		clearButton.setFont(f1);
		editButton.setFont(f1);

		RoomCB.setFont(f1);
		FirstNameTF.setFont(f1);
		LastNameTF.setFont(f1);
		Address1TF.setFont(f1);
		TownTF.setFont(f1);
		contactNoTF.setFont(f1);

		RoomLabel.setForeground(fontColorYellow);
		FirstNameLabel.setForeground(fontColorYellow);
		LastNameLabel.setForeground(fontColorYellow);
		Address1Label.setForeground(fontColorYellow);
		Address2Label.setForeground(fontColorYellow);
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
		detailsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Customer Data Entry"));
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
		
		
		detailsPanel.add(yes1); 
		detailsPanel.add(no1);
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
		X.setVisible(false); 

		X.addActionListener(listener); 
		X.setSelected(true);
		y.addActionListener(listener);
		yes1.addActionListener(listener);
		no1.addActionListener(listener);
		taxi.addActionListener(listener);
		iron.addActionListener(listener);
		wakeupCall.addActionListener(listener);
		brecky.addActionListener(listener);
 

		TableofDBContentsCustomer.setPreferredScrollableViewportSize(new Dimension(300, 600));
		dbContentsPanel1 = new JScrollPane(TableofDBContentsCustomer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel1.setBackground(fontColorYellow);
		dbContentsPanel1.setBorder(BorderFactory.createTitledBorder(lineBorder, "Database Content"));
		dbContentsPanel1.setVisible(false);
		dbContentsPanel1.setSize(700, 300);
		dbContentsPanel1.setLocation(477, 80);
		contentContainer.add(dbContentsPanel1);

		TableofDBContentsAmine.setPreferredScrollableViewportSize(new Dimension(300, 600));
		dbContentsPanel3 = new JScrollPane(TableofDBContentsAmine, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel3.setBackground(fontColorYellow);
		dbContentsPanel3.setBorder(BorderFactory.createTitledBorder(lineBorder, "Database Content"));
		dbContentsPanel3.setVisible(false);
		dbContentsPanel3.setSize(700, 300);
		dbContentsPanel3.setLocation(477, 80);
		contentContainer.add(dbContentsPanel3);

		TableofDBContentsRoom.setPreferredScrollableViewportSize(new Dimension(300, 600));
		dbContentsPanel4 = new JScrollPane(TableofDBContentsRoom, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel4.setBackground(fontColorYellow);
		dbContentsPanel4.setBorder(BorderFactory.createTitledBorder(lineBorder, "Database Content"));
		dbContentsPanel4.setVisible(false);
		dbContentsPanel4.setSize(700, 300);
		dbContentsPanel4.setLocation(477, 80);
		contentContainer.add(dbContentsPanel4);
		
		checkRoomLabel.setFont(f1);
		resRoomLabel.setFont(f1);
		checkFirstNameLabel.setFont(f1);
		resFirstNameLabel.setFont(f1);
		checkLastNameLabel.setFont(f1);
		resLastNameLabel.setFont(f1);
		checkAddress1Label.setFont(f1);
		resAddress1Label.setFont(f1);
		checkTownLabel.setFont(f1);
		resTownLabel.setFont(f1);
		checkcontactNoLabel.setFont(f1);
		rescontactNoLabel.setFont(f1);
		checkRoomTypeLabel.setFont(f1);
		resRoomTypeLabel.setFont(f1);
		checkRoomPriceLabel.setFont(f1);
		resRoomPriceLabel.setFont(f1);
		enterRoomNo.setFont(f1);
		myRoomCB.setFont(f1);

		checkOutSearchButton.setFont(f1);
		checkOutConfirmButton.setFont(f1);

		RoomTF.setFont(f1);
		FirstNameTF.setFont(f1);
		LastNameTF.setFont(f1);
		Address1TF.setFont(f1);
		TownTF.setFont(f1);
		contactNoTF.setFont(f1);

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
		checkOutPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "CUSTOMER CHECKOUT DETAILS"));
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
		
		bf.setFont(f1);
		tax.setFont(f1);
		ior.setFont(f1);
		wakup.setFont(f1);
		breck.setFont(f1);
		TownTF.setFont(f1);
		contactNoTF.setFont(f1);
		checkService1.setFont(f1);
		checkService2.setFont(f1);
		resTempLabel.setFont(f1);
		myServiceCB.setFont(f1);

		checkServiceGroup.add(Q);
		Q.setVisible(false);
		checkServiceGroup.add(tax);
		checkServiceGroup.add(ior);
		checkServiceGroup.add(wakup);
		checkServiceGroup.add(breck);

		Q.setBackground(backgroundColorGray);
		bf.setBackground(backgroundColorGray);
		tax.setBackground(backgroundColorGray);
		ior.setBackground(backgroundColorGray);
		wakup.setBackground(backgroundColorGray);
		breck.setBackground(backgroundColorGray);
		myServiceCB.setBackground(backgroundColorGray);

		checkService1.setBackground(fontColorYellow);
		checkService2.setBackground(fontColorYellow);

		Q.addActionListener(this);
		bf.addActionListener(this);
		tax.addActionListener(this);
		ior.addActionListener(this);
		wakup.addActionListener(this);
		breck.addActionListener(this);
		checkService1.addActionListener(this);
		checkService2.addActionListener(this);

		custServPanel = new JPanel();
		custServPanel.setLayout(new FlowLayout());
		custServPanel.setBackground(backgroundColorGray);
		custServPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "CUSTOMER SERVICES"));
		custServPanel.add(checkService1);
		custServPanel.add(checkService2);
		custServPanel.add(resTempLabel);
		custServPanel.add(myServiceCB);

		custServPanel.setSize(700, 400);
		custServPanel.setLocation(250, 50);
		myServiceCB.setVisible(false);
		custServPanel.setVisible(false);

		custServGroupPanel = new JPanel();
		custServGroupPanel.setLayout(new GridLayout(1, 4));
		custServGroupPanel.setBackground(backgroundColorGray);
		custServGroupPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "CUSTOMER SERVICES OPTIONS"));
		custServGroupPanel.add(Q);
		custServGroupPanel.add(tax);
		custServGroupPanel.add(ior);
		custServGroupPanel.add(wakup);
		custServGroupPanel.add(breck);
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

			if (yes1.isSelected()) {
				servicesPanel.setVisible(true);

				if (taxi.isSelected()) {
					taxiString = "yes";
				} else
					taxiString = "no";

				if (iron.isSelected()) {
					ironString = "yes";
				} else
					ironString = "no";

				if (wakeupCall.isSelected()) {
					wakecallString = "yes";
				} else
					wakecallString = "no";
			}

			if (brecky.isSelected()) {
				breakfastString = "yes";
			} else
				breakfastString = "no";

			if (no1.isSelected()) {
				servicesPanel.setVisible(false);
			}

			if (Room.isSelected()) {
				dbContentsPanel4.setVisible(true);
				dbContentsPanel1.setVisible(false);
				dbContentsPanel3.setVisible(false);
				TableModelCustomer.refreshFromDB(sqlStatement);
			}

			if (Customer.isSelected()) {
				dbContentsPanel1.setVisible(true);
				dbContentsPanel4.setVisible(false);
				dbContentsPanel3.setVisible(false);
				TableModelCustomer.refreshFromDB(sqlStatement);
			}

			if (Breakfast.isSelected()) {
				dbContentsPanel1.setVisible(false);
				dbContentsPanel3.setVisible(false);
				dbContentsPanel4.setVisible(false);

			}

			if (Amine.isSelected()) {
				dbContentsPanel3.setVisible(true);
				dbContentsPanel1.setVisible(false);
				dbContentsPanel4.setVisible(false);

				TableModelAmine.refreshFromDB(sqlStatement);

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
			String updateTemp1 = "DELETE customer, amine FROM customer INNER JOIN amine WHERE customer.room="
					+ RoomCB.getSelectedItem() + " AND amine.room=" + RoomCB.getSelectedItem() + ";";
			String updateTemp0 = "UPDATE room inner join customer inner join amine SET room.roomVacant = 'yes', room.cust_id = null, room.am_id = null where room_id = "
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
			Address2TF.setText("");
			TownTF.setText("");
			contactNoTF.setText("");
		}

		if (target == confirmButton) {

			try {

				String updateTemp0 = "UPDATE room inner join customer inner join amine SET room.roomVacant = 'no', room.cust_id = customer.cust_id, room.am_id = amine.am_id where room_id = " + RoomCB.getSelectedItem() + ";";
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
				String updateTemp = "INSERT INTO customer VALUES(" + null + ",'" + RoomCB.getSelectedItem() + "','"	+ FirstNameTF.getText() + "','" + LastNameTF.getText() + "','" + Address1TF.getText() + "','" + TownTF.getText() + "','" + contactNoTF.getText() + "');";
 

				if (yes1.isSelected()) {
					String updateTemp3 = "INSERT INTO amine VALUES(" + null + ",'" + taxiString + "','" + ironString + "','" + wakecallString	+ "','" + breakfastString + "','" + RoomCB.getSelectedItem() + "');";
					sqlStatement.executeUpdate(updateTemp3);
					TableModelAmine.refreshFromDB(sqlStatement);
				}
				if (no1.isSelected()) {
					String updateTemp3 = "INSERT INTO amine VALUES(" + null + ",'" + taxiString + "','" + ironString + "','" + wakecallString	+ "','" + breakfastString + "','" + RoomCB.getSelectedItem() + "');";
					sqlStatement.executeUpdate(updateTemp3);
					TableModelAmine.refreshFromDB(sqlStatement);
				}

				sqlStatement.executeUpdate(updateTemp);

			} catch (SQLException sqle) {
				System.err.println("Error with  insert:\n" + sqle.toString());
			} finally {
				TableModelCustomer.refreshFromDB(sqlStatement);
				TableModelAmine.refreshFromDB(sqlStatement);
				TableModelRoom.refreshFromDB(sqlStatement);
			}
		}
		if (target == deleteButton) {
			String updateTemp1 = "DELETE customer, amine FROM customer INNER JOIN amine WHERE customer.room="
					+ RoomCB.getSelectedItem() + " AND amine.room=" + RoomCB.getSelectedItem() + ";";
			String updateTemp0 = "UPDATE room SET room.roomVacant = 'yes', room.cust_id = null, room.am_id = null where room_id = "
					+ RoomCB.getSelectedItem() + ";";
			try {
				sqlStatement.executeUpdate(updateTemp1);
				sqlStatement.executeUpdate(updateTemp0);
			} catch (SQLException sqle) {
				System.err.println("Error with  insert:\n" + sqle.toString());
			}

			finally {
				TableModelCustomer.refreshFromDB(sqlStatement);
				TableModelAmine.refreshFromDB(sqlStatement);
				TableModelRoom.refreshFromDB(sqlStatement);
			}
		}
		if (target == updateButton) {
			try {
 
				if (yes1.isSelected()) {

	String updateTemp3 = "UPDATE amine SET " + "Taxi = '" + taxiString + "'," + " Iron = '" + ironString + "'," + " Brecky = '" + breakfastString + "'," + " WakeUpCall = '" + wakecallString + "',  room = '"	+ RoomCB.getSelectedItem() + "' where amine.room = " + RoomCB.getSelectedItem();
					sqlStatement.executeUpdate(updateTemp3);
					databaseResultSet = sqlStatement.executeQuery("SELECT * from amine ");
					databaseResultSet.next();
					databaseResultSet.close();
					TableModelAmine.refreshFromDB(sqlStatement);
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
						new FileReader("C:/Users/Keith Bentham/Desktop/JDBC Project/Main_JDBC_Project/Test.csv"),
						',', '"', 1);
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
						new FileReader("C:/Users/Keith Bentham/Desktop/JDBC Project/Main_JDBC_Project/Test.csv"),
						',', '"', 1);
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

			if (A.isSelected()) {
				vacanciesResRoomTypeLabel.setText("Single");
				vacanciesResRoomPriceLabel.setText("€39.99");
				command = command1;
			}
			if (B.isSelected()) {
				vacanciesResRoomTypeLabel.setText("Double");
				vacanciesResRoomPriceLabel.setText("€49.99");
				command = command2;
			}
			if (C.isSelected()) {
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
						new FileReader("C:/Users/Keith Bentham/Desktop/JDBC Project/Main_JDBC_Project/Test.csv"),
						',', '"', 1);
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
		
		if (target == this.checkService1) {

			command2 = "select room from amine  where amine.Taxi='yes';";
			command3 = "select room from amine  where amine.Iron='yes';";
			command4 = "select room from amine  where amine.WakeUpCall='yes';";
			command5 = "select room from amine  where amine.Breakfast='yes';";


			if (tax.isSelected()) {
				command = command2;
			}

			if (ior.isSelected()) {
				command = command3;
			}

			if (wakup.isSelected()) {
				command = command4;
			}

			if (breck.isSelected()) {
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
						new FileReader("C:/Users/Keith Bentham/Desktop/JDBC Project/Main_JDBC_Project/Test.csv"),
						',', '"', 1);
				String[] nextLine;

				while ((nextLine = reader.readNext()) != null) {

					myServiceCB.addItem(nextLine[0]);
					if (tax.isSelected()) {
						resTempLabel.setText("The rooms that want a Taxi are:"+"										");
						myServiceCB.setVisible(true);
					}

					if (ior.isSelected()) {
						resTempLabel.setText("The rooms that want an Iron are:"+"										");
						myServiceCB.setVisible(true);
					}

					if (wakup.isSelected()) {
						resTempLabel.setText("The rooms that want a Wakeup Call are:"+"								");
						myServiceCB.setVisible(true);
					}

					if (breck.isSelected()) {
						resTempLabel.setText("The rooms that want Breakfast are:"+"   								");
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

		if (target == this.checkService2) {

			myServiceCB.setVisible(false);
			command2 = "select count(*) from amine  where amine.Taxi='yes';";
			command3 = "select count(*) from amine  where amine.Iron='yes';";
			command4 = "select count(*) from amine  where amine.WakeUpCall='yes';";
			command5 = "select count(*) from amine  where amine.Breakfast='yes';";

			if (tax.isSelected()) {
				command = command2;
			}

			if (ior.isSelected()) {
				command = command3;
			}

			if (wakup.isSelected()) {
				command = command4;
			}

			if (breck.isSelected()) {
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
						new FileReader("C:/Users/Keith Bentham/Desktop/JDBC Project/Main_JDBC_Project/Test.csv"),
						',', '"', 1);
				String[] nextLine;

				while ((nextLine = reader.readNext()) != null) {

					if (tax.isSelected()) {
						resTempLabel.setText("The number of rooms that want a Taxi : " + nextLine[0] );
					}

					if (ior.isSelected()) {
						resTempLabel.setText("The number of rooms that want an Iron are: " + nextLine[0] );
					}

					if (wakup.isSelected()) {
						resTempLabel.setText("The number of rooms that want a Wakeup Call are: " + nextLine[0] );
					}
					if (breck.isSelected()) {
						resTempLabel.setText("The number of rooms that want breakfast are: " + nextLine[0] );
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
