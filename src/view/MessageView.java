package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MessageView extends JPanel {
	private JTabbedPane tabbedPane;

	// inbox tab
	private JTable inboxTable;
	private DefaultTableModel inboxTableModel;
	private JButton markReadBtn, replyBtn;

	// details panel
	private JLabel lblSender, lblRecipient, lblSubject, lblTime, lblStatus, lblCode;
	private JTextArea detailMessageArea;

	// compose tab
	private JPanel composePanel;
	private JTextField recipientIdField, courseCodeField, subjectField;
	private JTextArea messageArea;
	private JButton sendBtn;

	public MessageView() {
		
		//construct our panels and add them to tabbed pane
		setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();

		tabbedPane.addTab("Inbox", buildInboxSplitPane());
		tabbedPane.addTab("Compose", buildComposePanel());

		add(tabbedPane, BorderLayout.CENTER);
	}

	private JSplitPane buildInboxSplitPane() {
		JPanel inboxPanel = new JPanel(new BorderLayout());
		inboxPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		//construct our table
		inboxTableModel = new DefaultTableModel(new String[] { "ID", "From", "To", "Course", "Subject", "Date", "Status" },
				0);
		inboxTable = new JTable(inboxTableModel);
		inboxTable.setAutoCreateRowSorter(true);
		inboxPanel.add(new JScrollPane(inboxTable), BorderLayout.CENTER);

		//construct our btns for interaction
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		markReadBtn = new JButton("Mark as Read");
		replyBtn = new JButton("Reply");
		btnPanel.add(markReadBtn);
		btnPanel.add(replyBtn);
		inboxPanel.add(btnPanel, BorderLayout.SOUTH);

		//message details panel
		JPanel detailPanel = new JPanel(new BorderLayout());
		detailPanel.setBorder(new EmptyBorder(0, 10, 10, 10));

		
		//message headers
		JPanel labels = new JPanel(new GridLayout(6, 1, 5, 5));
		lblSender = new JLabel("Sender: ");
		lblRecipient = new JLabel("Recipient: ");
		lblSubject = new JLabel("Subject: ");
		lblTime = new JLabel("Time: ");
		lblStatus = new JLabel("Status: ");
		lblCode = new JLabel("Course: ");
		labels.add(lblSender);
		labels.add(lblRecipient);
		labels.add(lblSubject);
		labels.add(lblTime);
		labels.add(lblStatus);
		labels.add(lblCode);

		//message description
		detailMessageArea = new JTextArea();
		detailMessageArea.setLineWrap(true);
		detailMessageArea.setWrapStyleWord(true);
		detailMessageArea.setEditable(false);
		JScrollPane detailScroll = new JScrollPane(detailMessageArea);
		detailScroll.setPreferredSize(new Dimension(400, 100));

		detailPanel.add(labels, BorderLayout.NORTH);
		detailPanel.add(detailScroll, BorderLayout.CENTER);

		//use a split pane for adjustability between message and inbox
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inboxPanel, detailPanel);
		splitPane.setResizeWeight(0.4); // 40% to top
		splitPane.setDividerSize(5);
		return splitPane;
	}

	//build our message panel
	private JPanel buildComposePanel() {
		composePanel = new JPanel(new BorderLayout());
		composePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		//add fields
		JPanel fields = new JPanel(new GridLayout(4, 2, 5, 5));
		fields.add(new JLabel("Recipient Email:"));
		recipientIdField = new JTextField();
		fields.add(recipientIdField);
		fields.add(new JLabel("Course Code:"));
		courseCodeField = new JTextField();
		fields.add(courseCodeField);
		fields.add(new JLabel("Subject:"));
		subjectField = new JTextField();
		fields.add(subjectField);
		fields.add(new JLabel("Message:"));
		composePanel.add(fields, BorderLayout.NORTH);

		//set our message area properties and add components
		messageArea = new JTextArea();
		messageArea.setLineWrap(true);
		messageArea.setWrapStyleWord(true);
		JScrollPane scrollMsg = new JScrollPane(messageArea);
		scrollMsg.setPreferredSize(new Dimension(400, 300));
		composePanel.add(scrollMsg, BorderLayout.CENTER);

		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		sendBtn = new JButton("Send");
		btnPanel.add(sendBtn);
		composePanel.add(btnPanel, BorderLayout.SOUTH);

		return composePanel;
	}

	public JTable getInboxTable() {
		return inboxTable;
	}

	public DefaultTableModel getInboxTableModel() {
		return inboxTableModel;
	}

	public JButton getMarkReadBtn() {
		return markReadBtn;
	}

	public JButton getReplyBtn() {
		return replyBtn;
	}

	public JLabel getLblSender() {
		return lblSender;
	}

	public JLabel getLblRecipient() {
		return lblRecipient;
	}

	public JLabel getLblSubject() {
		return lblSubject;
	}

	public JLabel getLblTime() {
		return lblTime;
	}

	public JLabel getLblStatus() {
		return lblStatus;
	}

	public JLabel getLblCode() {
		return lblCode;
	}

	public JTextArea getDetailMessageArea() {
		return detailMessageArea;
	}

	public JTextField getRecipientIdField() {
		return recipientIdField;
	}

	public JTextField getCourseCodeField() {
		return courseCodeField;
	}

	public JTextField getSubjectField() {
		return subjectField;
	}

	public JTextArea getMessageArea() {
		return messageArea;
	}

	public JButton getSendBtn() {
		return sendBtn;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	//view testing
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Messaging");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(new MessageView());
			frame.setSize(600, 600);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
