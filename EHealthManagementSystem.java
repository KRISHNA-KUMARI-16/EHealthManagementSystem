import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Patient {
    String name;
    int age;
    String contact;
    String medicalHistory;

    Patient(String name, int age, String contact, String medicalHistory) {
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.medicalHistory = medicalHistory;
    }

    public String toString() {
        return name + " | Age: " + age + " | Contact: " + contact;
    }
}

class Appointment {
    String patientName;
    String doctorName;
    String date;
    String time;

    Appointment(String patientName, String doctorName, String date, String time) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
    }

    public String toString() {
        return patientName + " with Dr. " + doctorName + " on " + date + " at " + time;
    }
}

public class EHealthManagementSystem extends JFrame {
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<Appointment> appointments = new ArrayList<>();

    private JTabbedPane tabbedPane;

    public EHealthManagementSystem() {
        setTitle("E-Health Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize tabs
        tabbedPane = new JTabbedPane();

        JPanel patientPanel = createPatientPanel();
        JPanel appointmentPanel = createAppointmentPanel();
        JPanel communicationPanel = createCommunicationPanel();

        tabbedPane.addTab("Patients", patientPanel);
        tabbedPane.addTab("Appointments", appointmentPanel);
        tabbedPane.addTab("Communication", communicationPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Set font for tabs
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private JPanel createPatientPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 255));

        DefaultListModel<Patient> patientListModel = new DefaultListModel<>();
        JList<Patient> patientList = new JList<>(patientListModel);
        patientList.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField contactField = new JTextField();
        JTextArea medicalHistoryField = new JTextArea(3, 10);
        JButton addPatientButton = new JButton("Add Patient");

        // Set button styles
        addPatientButton.setBackground(new Color(150, 200, 255));
        addPatientButton.setFont(new Font("Arial", Font.BOLD, 14));
        addPatientButton.setIcon(new ImageIcon("patien.jpeg")); // Add an icon here (update path as needed)

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);
        formPanel.add(new JLabel("Contact:"));
        formPanel.add(contactField);
        formPanel.add(new JLabel("Medical History:"));
        formPanel.add(new JScrollPane(medicalHistoryField));
        formPanel.add(new JLabel());
        formPanel.add(addPatientButton);

        addPatientButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String contact = contactField.getText();
                String medicalHistory = medicalHistoryField.getText();

                Patient patient = new Patient(name, age, contact, medicalHistory);
                patients.add(patient);
                patientListModel.addElement(patient);

                nameField.setText("");
                ageField.setText("");
                contactField.setText("");
                medicalHistoryField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Please enter valid data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JScrollPane(patientList), BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createAppointmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 240, 240));

        DefaultListModel<Appointment> appointmentListModel = new DefaultListModel<>();
        JList<Appointment> appointmentList = new JList<>(appointmentListModel);
        appointmentList.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        JTextField patientNameField = new JTextField();
        JTextField doctorNameField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();
        JButton addAppointmentButton = new JButton("Add Appointment");

        // Set button styles
        addAppointmentButton.setBackground(new Color(255, 150, 150));
        addAppointmentButton.setFont(new Font("Arial", Font.BOLD, 14));
        addAppointmentButton.setIcon(new ImageIcon("wp11040022.jpg")); // Add an icon here (update path as needed)

        formPanel.add(new JLabel("Patient Name:"));
        formPanel.add(patientNameField);
        formPanel.add(new JLabel("Doctor Name:"));
        formPanel.add(doctorNameField);
        formPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("Time (HH:MM):"));
        formPanel.add(timeField);
        formPanel.add(new JLabel());
        formPanel.add(addAppointmentButton);

        addAppointmentButton.addActionListener(e -> {
            try {
                String patientName = patientNameField.getText();
                String doctorName = doctorNameField.getText();
                String date = dateField.getText();
                String time = timeField.getText();

                // Check for conflicts
                boolean conflict = appointments.stream().anyMatch(appointment ->
                        appointment.doctorName.equals(doctorName) &&
                                appointment.date.equals(date) &&
                                appointment.time.equals(time));

                if (conflict) {
                    JOptionPane.showMessageDialog(panel, "This slot is already booked with Dr. " + doctorName,
                            "Conflict", JOptionPane.WARNING_MESSAGE);
                } else {
                    Appointment appointment = new Appointment(patientName, doctorName, date, time);
                    appointments.add(appointment);
                    appointmentListModel.addElement(appointment);

                    patientNameField.setText("");
                    doctorNameField.setText("");
                    dateField.setText("");
                    timeField.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Please enter valid data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JScrollPane(appointmentList), BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCommunicationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 255, 240));

        JTextArea communicationArea = new JTextArea();
        communicationArea.setEditable(false);
        communicationArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JTextField messageField = new JTextField();
        JComboBox<String> senderDropdown = new JComboBox<>(new String[]{"Patient", "Doctor"});
        JButton sendButton = new JButton("Send Message");

        // Set button styles
        sendButton.setBackground(new Color(150, 255, 150));
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setIcon(new ImageIcon("\"C:\\Users\\navin\\OneDrive\\send icon.jpeg\"")); // Add an icon here (update path as needed)

        sendButton.addActionListener(e -> {
            String sender = (String) senderDropdown.getSelectedItem();
            String message = messageField.getText();
            if (!message.isEmpty()) {
                communicationArea.append(sender + ": " + message + "\n");
                messageField.setText("");
            } else {
                JOptionPane.showMessageDialog(panel, "Please enter a message!", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(senderDropdown, BorderLayout.WEST);
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        panel.add(new JScrollPane(communicationArea), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EHealthManagementSystem frame = new EHealthManagementSystem();
            frame.setVisible(true);
        });
    }
}
