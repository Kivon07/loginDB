package logReg;
import java.awt.*;
import java.sql.*;
import java.time.Year;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentManagementForm extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Components para sa Pending Applicants Tab
    private JTable pendingTable;
    private DefaultTableModel pendingModel;
    private JButton btnApprove, btnReject, btnRefreshPending;

    // Components para sa Student Information Management Tab
    private JTextField txtManageStudentID, txtManageFirstName, txtManageLastName, txtManageEmail, txtManageSearch;
    private JComboBox<String> cbManageCourse, cbManageYearLevel, cbManageStatus;
    private JButton btnAddRecord, btnModifyInfo, btnDeleteRecord, btnClearForm;
    private JTable manageTable;
    private DefaultTableModel manageModel;
    private static Connection conn;
    private int selectedStudentDbId = -1; // Database Primary Key ID

    public StudentManagementForm() {
        initComponentsCustom();
        loadPendingApplications();
        loadManageStudentData("");
        generateNextStudentID();
    }

    private void initComponentsCustom() {
        setTitle("Student Information Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Title
        JLabel lblTitle = new JLabel("  Student Information Management System", JLabel.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setPreferredSize(new Dimension(1000, 40));
        add(lblTitle, BorderLayout.NORTH);

        // Tabbed Pane (2 Tabs na lang matatira)
        JTabbedPane tabbedPane = new JTabbedPane();

        // ==========================================
        // TAB 1: PENDING ENROLLMENT APPLICANTS
        // ==========================================
        JPanel panelPending = new JPanel(null);

        JLabel lblPendingHeader = new JLabel("Applicants Waiting for Approval:");
        lblPendingHeader.setBounds(20, 15, 300, 25);
        lblPendingHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelPending.add(lblPendingHeader);

        String[] pendingColumns = {"App ID", "First Name", "Last Name", "Email", "Course", "Year Level", "Status"};
        pendingModel = new DefaultTableModel(pendingColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        pendingTable = new JTable(pendingModel);
        JScrollPane scrollPending = new JScrollPane(pendingTable);
        scrollPending.setBounds(20, 50, 720, 430);
        panelPending.add(scrollPending);

        btnApprove = new JButton("Approve Application");
        btnApprove.setBounds(760, 50, 180, 40);
        btnApprove.setBackground(new Color(46, 139, 87));
        btnApprove.setForeground(Color.WHITE);
        btnApprove.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelPending.add(btnApprove);

        btnReject = new JButton("Reject Application");
        btnReject.setBounds(760, 105, 180, 40);
        btnReject.setBackground(new Color(178, 34, 34));
        btnReject.setForeground(Color.WHITE);
        btnReject.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelPending.add(btnReject);

        btnRefreshPending = new JButton("Refresh List");
        btnRefreshPending.setBounds(760, 160, 180, 35);
        panelPending.add(btnRefreshPending);

        tabbedPane.addTab("Pending Applications", panelPending);

        // ==========================================
        // TAB 2: STUDENT INFORMATION MANAGEMENT (BASED ON PHOTO)
        // ==========================================
        JPanel panelManage = new JPanel(null);

        JLabel lblManageHeader = new JLabel("Student Information Management");
        lblManageHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblManageHeader.setBounds(20, 10, 350, 30);
        panelManage.add(lblManageHeader);

        // Left Side Form Labels and Fields
        JLabel lblStudentID = new JLabel("Student ID:");
        lblStudentID.setBounds(20, 50, 100, 25);
        panelManage.add(lblStudentID);

        txtManageStudentID = new JTextField();
        txtManageStudentID.setBounds(120, 50, 220, 25);
        txtManageStudentID.setEditable(false);
        panelManage.add(txtManageStudentID);

        JLabel lblFirstName = new JLabel("First Name:");
        lblFirstName.setBounds(20, 90, 100, 25);
        panelManage.add(lblFirstName);

        txtManageFirstName = new JTextField();
        txtManageFirstName.setBounds(120, 90, 220, 25);
        panelManage.add(txtManageFirstName);

        JLabel lblLastName = new JLabel("Last Name:");
        lblLastName.setBounds(20, 130, 100, 25);
        panelManage.add(lblLastName);

        txtManageLastName = new JTextField();
        txtManageLastName.setBounds(120, 130, 220, 25);
        panelManage.add(txtManageLastName);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 170, 100, 25);
        panelManage.add(lblEmail);

        txtManageEmail = new JTextField();
        txtManageEmail.setBounds(120, 170, 220, 25);
        panelManage.add(txtManageEmail);

        JLabel lblCourse = new JLabel("Course:");
        lblCourse.setBounds(20, 210, 100, 25);
        panelManage.add(lblCourse);

        String[] courses = {"BS Computer Science", "BS Information Technology", "BS Information Systems"};
        cbManageCourse = new JComboBox<>(courses);
        cbManageCourse.setBounds(120, 210, 220, 25);
        panelManage.add(cbManageCourse);

        JLabel lblYearLevel = new JLabel("Year Level:");
        lblYearLevel.setBounds(20, 250, 100, 25);
        panelManage.add(lblYearLevel);

        String[] yearLevels = {"1st Year", "2nd Year", "3rd Year", "4th Year"};
        cbManageYearLevel = new JComboBox<>(yearLevels);
        cbManageYearLevel.setBounds(120, 250, 220, 25);
        panelManage.add(cbManageYearLevel);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(20, 290, 100, 25);
        panelManage.add(lblStatus);

        String[] statuses = {"Pending", "Enrolled", "Dropped", "Graduated"};
        cbManageStatus = new JComboBox<>(statuses);
        cbManageStatus.setBounds(120, 290, 220, 25);
        panelManage.add(cbManageStatus);

        // Action Buttons
        btnAddRecord = new JButton("Add Record");
        btnAddRecord.setBounds(20, 335, 140, 30);
        panelManage.add(btnAddRecord);

        btnModifyInfo = new JButton("Modify Info");
        btnModifyInfo.setBounds(180, 335, 160, 30);
        panelManage.add(btnModifyInfo);

        btnDeleteRecord = new JButton("Delete Record");
        btnDeleteRecord.setBounds(20, 375, 140, 30);
        panelManage.add(btnDeleteRecord);

        btnClearForm = new JButton("Clear Form");
        btnClearForm.setBounds(180, 375, 160, 30);
        panelManage.add(btnClearForm);

        // Right Side Table & Search
        JLabel lblSearchRecord = new JLabel("Search Record:");
        lblSearchRecord.setBounds(370, 50, 100, 25);
        panelManage.add(lblSearchRecord);

        txtManageSearch = new JTextField();
        txtManageSearch.setBounds(470, 50, 480, 25);
        panelManage.add(txtManageSearch);

        String[] manageColumns = {"ID", "Student ID", "First Name", "Last Name", "Email", "Course", "Year Level", "Status"};
        manageModel = new DefaultTableModel(manageColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        manageTable = new JTable(manageModel);
        JScrollPane scrollManage = new JScrollPane(manageTable);
        scrollManage.setBounds(370, 85, 580, 380);
        panelManage.add(scrollManage);

        tabbedPane.addTab("Student Information Management", panelManage);

        add(tabbedPane, BorderLayout.CENTER);

        // ==========================================
        // EVENT LISTENERS
        // ==========================================
        btnApprove.addActionListener(e -> approveSelectedApplicant());
        btnReject.addActionListener(e -> rejectSelectedApplicant());
        btnRefreshPending.addActionListener(e -> loadPendingApplications());

        btnAddRecord.addActionListener(e -> addStudentRecord());
        btnModifyInfo.addActionListener(e -> modifyStudentRecord());
        btnDeleteRecord.addActionListener(e -> deleteStudentRecord());
        btnClearForm.addActionListener(e -> clearManageForm());

        txtManageSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                loadManageStudentData(txtManageSearch.getText().trim());
            }
        });

        manageTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                populateFormFromTable();
            }
        });
    }

    // 1. Load pending applications
    private void loadPendingApplications() {
        pendingModel.setRowCount(0);
        String sql = "SELECT * FROM enrollment_applications WHERE status = 'Pending' ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                pendingModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("course"),
                    rs.getString("year_level"),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading applicants: " + e.getMessage());
        }
    }

    // 2. APPROVAL LOGIC
    private void approveSelectedApplicant() {
        int selectedRow = pendingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an applicant from the table to approve.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int appId = (int) pendingModel.getValueAt(selectedRow, 0);
        String firstName = pendingModel.getValueAt(selectedRow, 1).toString();
        String lastName = pendingModel.getValueAt(selectedRow, 2).toString();
        String email = pendingModel.getValueAt(selectedRow, 3).toString();
        String course = pendingModel.getValueAt(selectedRow, 4).toString();
        String yearLevel = pendingModel.getValueAt(selectedRow, 5).toString();

        int confirm = JOptionPane.showConfirmDialog(this, "Approve enrollment for " + firstName + " " + lastName + "?", "Confirm Approval", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int currentYear = Year.now().getValue();
        String getLastIdSql = "SELECT id FROM students ORDER BY id DESC LIMIT 1";
        String insertStudentSql = "INSERT INTO students (student_id, username, password, first_name, last_name, email, course, year_level, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Enrolled')";
        String updateAppSql = "UPDATE enrollment_applications SET status = 'Approved' WHERE id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int nextId = 1;
            try (PreparedStatement pstGet = conn.prepareStatement(getLastIdSql);
                 ResultSet rs = pstGet.executeQuery()) {
                if (rs.next()) {
                    nextId = rs.getInt("id") + 1;
                }
            }
            String generatedStudentId = "STU-" + currentYear + "-" + String.format("%04d", nextId);
            String username = generatedStudentId; 
            String defaultPassword = lastName.toLowerCase() + "2026";

            try (PreparedStatement pstInsert = conn.prepareStatement(insertStudentSql)) {
                pstInsert.setString(1, generatedStudentId);
                pstInsert.setString(2, username);
                pstInsert.setString(3, defaultPassword);
                pstInsert.setString(4, firstName);
                pstInsert.setString(5, lastName);
                pstInsert.setString(6, email);
                pstInsert.setString(7, course);
                pstInsert.setString(8, yearLevel);
                pstInsert.executeUpdate();
            }

            try (PreparedStatement pstUpdate = conn.prepareStatement(updateAppSql)) {
                pstUpdate.setInt(1, appId);
                pstUpdate.executeUpdate();
            }

            conn.commit();
            JOptionPane.showMessageDialog(this, "Applicant Successfully Approved!\n\nGenerated Student ID: " + generatedStudentId + "\nUsername: " + username + "\nDefault Password: " + defaultPassword, "Success", JOptionPane.INFORMATION_MESSAGE);

            loadPendingApplications();
            loadManageStudentData("");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error processing approval: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 3. REJECT LOGIC
    private void rejectSelectedApplicant() {
        int selectedRow = pendingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an applicant to reject.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int appId = (int) pendingModel.getValueAt(selectedRow, 0);
        String sql = "UPDATE enrollment_applications SET status = 'Rejected' WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, appId);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Application status set to Rejected.", "Info", JOptionPane.INFORMATION_MESSAGE);
            loadPendingApplications();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error rejecting application: " + e.getMessage());
        }
    }

    // 4. MANAGEMENT TAB METHODS
    private void generateNextStudentID() {
        int currentYear = Year.now().getValue();
        String sql = "SELECT id FROM students ORDER BY id DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            int nextId = 1;
            if (rs.next()) {
                nextId = rs.getInt("id") + 1;
            }
            txtManageStudentID.setText("STU-" + currentYear + "-" + String.format("%04d", nextId));

        } catch (SQLException e) {
            txtManageStudentID.setText("STU-" + currentYear + "-0001");
        }
    }

    private void loadManageStudentData(String searchQuery) {
        manageModel.setRowCount(0);
        String sql = "SELECT * FROM students WHERE student_id LIKE ? OR first_name LIKE ? OR last_name LIKE ? OR course LIKE ? ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            String queryParam = "%" + searchQuery + "%";
            pst.setString(1, queryParam);
            pst.setString(2, queryParam);
            pst.setString(3, queryParam);
            pst.setString(4, queryParam);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                manageModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("student_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("course"),
                    rs.getString("year_level"),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading management data: " + e.getMessage());
        }
    }

    private void populateFormFromTable() {
        int selectedRow = manageTable.getSelectedRow();
        if (selectedRow != -1) {
            selectedStudentDbId = Integer.parseInt(manageModel.getValueAt(selectedRow, 0).toString());
            txtManageStudentID.setText(manageModel.getValueAt(selectedRow, 1).toString());
            txtManageFirstName.setText(manageModel.getValueAt(selectedRow, 2).toString());
            txtManageLastName.setText(manageModel.getValueAt(selectedRow, 3).toString());
            txtManageEmail.setText(manageModel.getValueAt(selectedRow, 4).toString());
            cbManageCourse.setSelectedItem(manageModel.getValueAt(selectedRow, 5).toString());
            cbManageYearLevel.setSelectedItem(manageModel.getValueAt(selectedRow, 6).toString());
            cbManageStatus.setSelectedItem(manageModel.getValueAt(selectedRow, 7).toString());
        }
    }

    private void addStudentRecord() {
        String studentId = txtManageStudentID.getText().trim();
        String firstName = txtManageFirstName.getText().trim();
        String lastName = txtManageLastName.getText().trim();
        String email = txtManageEmail.getText().trim();
        String course = cbManageCourse.getSelectedItem().toString();
        String yearLevel = cbManageYearLevel.getSelectedItem().toString();
        String status = cbManageStatus.getSelectedItem().toString();
        
        
        
        
        
        

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = studentId;
        String defaultPassword = lastName.toLowerCase() + "2026";
        String sql = "INSERT INTO students (student_id, username, password, first_name, last_name, email, course, year_level, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, studentId);
            pst.setString(2, username);
            pst.setString(3, defaultPassword);
            pst.setString(4, firstName);
            pst.setString(5, lastName);
            pst.setString(6, email);
            pst.setString(7, course);
            pst.setString(8, yearLevel);
            pst.setString(9, status);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            clearManageForm();
            loadManageStudentData("");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding record: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
    
    }

    private void modifyStudentRecord() {
        if (selectedStudentDbId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student from the table to modify.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String firstName = txtManageFirstName.getText().trim();
        String lastName = txtManageLastName.getText().trim();
        String email = txtManageEmail.getText().trim();
        String course = cbManageCourse.getSelectedItem().toString();
        String yearLevel = cbManageYearLevel.getSelectedItem().toString();
        String status = cbManageStatus.getSelectedItem().toString();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "UPDATE students SET first_name = ?, last_name = ?, email = ?, course = ?, year_level = ?, status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, firstName);
            pst.setString(2, lastName);
            pst.setString(3, email);
            pst.setString(4, course);
            pst.setString(5, yearLevel);
            pst.setString(6, status);
            pst.setInt(7, selectedStudentDbId);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            clearManageForm();
            loadManageStudentData("");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error modifying record: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudentRecord() {
        if (selectedStudentDbId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student record to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, selectedStudentDbId);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            clearManageForm();
            loadManageStudentData("");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting record: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearManageForm() {
        selectedStudentDbId = -1;
        txtManageFirstName.setText("");
        txtManageLastName.setText("");
        txtManageEmail.setText("");
        txtManageSearch.setText("");
        cbManageCourse.setSelectedIndex(0);
        cbManageYearLevel.setSelectedIndex(0);
        cbManageStatus.setSelectedIndex(0);
        manageTable.clearSelection();
        generateNextStudentID();
    }

    public static void main(String args[]) {
    	
    	
    	Statement stmt = null;
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(StudentManagementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(() -> new StudentManagementForm().setVisible(true));
        
        
        try{
        	stmt = conn.createStatement();
			
			String createTable = "CREATE TABLE IF NOT EXISTS userCreds(userID int(3) auto_increment not null primary key,"
					+ "userName varchar(50) unique not null,"
					+ "password varchar(64) not null,"
					+ "role VARCHAR(20) not null"
					+ ");";
			stmt.executeUpdate(createTable);
        }
        
      
			catch(SQLException e) {
			System.out.print("Not Created");
		}
    }
}

