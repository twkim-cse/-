import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Position;

public class Subject_Application extends JFrame {
	JTextField subject;
	JTextField name;
	JTextField profession;

	JTextField search;

	JRadioButton male = new JRadioButton("Male");
	JRadioButton female = new JRadioButton("Female");

	JButton bSearch; // ���� ������ ���� ��ư
	JButton bSave; // ���� ������ ���� ��ư
	JButton bDelete; // ���� ������ ���� ��ư
	JButton bNew;
	JButton bPrint; // ����� ���� ��ư
	JButton bPreview; // �̸����⸦ ���� ��ư

	JList names = new JList();
	JList subjects = new JList();
	JList professions = new JList();


	Connection conn;
	//JPanel backgroundPanel;
	MainPanel mainPanel;
	
	// DefaultListModel<String> nameListModel = new DefaultListModel<>();
	// JList<String> names = new JList<>(nameListModel);
	
	public class MainPanel extends JPanel {
        private ImageIcon backgroundIcon;

        public MainPanel(ImageIcon backgroundIcon) {
            this.backgroundIcon = backgroundIcon;

            // ���� �г��� ũ�⸦ ��� �̹����� ũ��� ����
            setPreferredSize(new Dimension(backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight()));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            System.out.println("paintComponent ȣ���");
            if (backgroundIcon != null) {
                // ��� �̹��� �׸���
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

	public Subject_Application() {
		
		ImageIcon backgroundIcon = new ImageIcon("C:\\Users\\kty13\\OneDrive\\���� ȭ��\\20225531java\\Winter_project_my\\src\\���.jpg");
		if (backgroundIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
		    System.out.println("�̹����� ���������� �ε�Ǿ����ϴ�.");
		} else {
		    System.out.println("�̹��� �ε忡 �����߽��ϴ�.");
		}
	    // ���� �г� ����
		mainPanel = new MainPanel(backgroundIcon);
		mainPanel.setLayout(new BorderLayout());
		
	    // ���� ��� �г� ����
	    JPanel leftTopPanel = new JPanel(new RiverLayout());
	    JScrollPane name_Scroller = new JScrollPane(names);
	    name_Scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    name_Scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    names.setVisibleRowCount(7);
	    names.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    names.setFixedCellWidth(100);
	    leftTopPanel.add("br center", new JLabel("�л� �� ���� ����"));
	    leftTopPanel.add("p center", name_Scroller);

	    // ������ ��� �г� ����
	    JPanel rightTopPanel = new JPanel(new RiverLayout());
	    subject = new JTextField(20);
	    name = new JTextField(10);
	    profession = new JTextField(20);
	    ButtonGroup gender = new ButtonGroup();
	    gender.add(male);
	    gender.add(female);

	    rightTopPanel.add("br center", new JLabel("������û ����"));
	    rightTopPanel.add("p left", new JLabel("�� ��  ��"));
	    rightTopPanel.add("tab", subject);
	    rightTopPanel.add("br", new JLabel());
	    rightTopPanel.add("br", new JLabel("��  ��"));
	    rightTopPanel.add("tab", name);
	    rightTopPanel.add("br", new JLabel());
	    rightTopPanel.add("br", new JLabel("��  ��"));
	    rightTopPanel.add("tab", profession);
	    rightTopPanel.add("br", new JLabel());
	    rightTopPanel.add("br", new JLabel("��  ��"));
	    rightTopPanel.add("tab", male);
	    rightTopPanel.add("tab", female);

	    // ���� �ϴ� �г� ����
	    JPanel leftBottomPanel = new JPanel(new RiverLayout());
	    JPanel tmpPanel = new JPanel(new RiverLayout());
	    JPanel tmpPanel1 = new JPanel(new RiverLayout());
	    JPanel tmpPanel2 = new JPanel(new RiverLayout());

	    search = new JTextField(20);
	    bSearch = new JButton("�˻�");
	    bPrint = new JButton("���");
	    bPreview = new JButton("�̸�����");
	    tmpPanel1.add(search);
	    tmpPanel2.add(bSearch);
	    tmpPanel2.add(bPrint);
	    tmpPanel2.add(bPreview);
	    tmpPanel.add("center", tmpPanel1);
	    tmpPanel.add("br center", tmpPanel2);
	    leftBottomPanel.add("center", tmpPanel);

	    // ������ �ϴ� �г� ����
	    JPanel rightBottomPanel = new JPanel(new RiverLayout());
	    tmpPanel = new JPanel(new RiverLayout());
	    bSave = new JButton("����");
	    bDelete = new JButton("����");
	    bNew = new JButton("���");
	    tmpPanel.add(bSave);
	    tmpPanel.add("tab", bDelete);
	    tmpPanel.add("tab", bNew);
	    rightBottomPanel.add("center", tmpPanel);
	    rightBottomPanel.add("br", Box.createRigidArea(new Dimension(0, 5)));

	 // ��� �г� ����
	    JPanel topPanel = new JPanel(new GridLayout(1, 2));
	    topPanel.add(leftTopPanel);
	    topPanel.add(rightTopPanel);

	    // �ϴ� �г� ����
	    JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
	    bottomPanel.add(leftBottomPanel);
	    bottomPanel.add(rightBottomPanel);
	    
	    // ��� �гΰ� �ϴ� �г��� �����ϴ� ���� �г� ����
	    mainPanel.add(topPanel, BorderLayout.CENTER);
	    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

	    SearchListener searchListener = new SearchListener();
		bSearch.addActionListener(searchListener);
		bDelete.addActionListener(new DButtonListener());
		bNew.addActionListener(new ButtonListener());
		bSave.addActionListener(new SButtonListener());
		sbjList listener = new sbjList();
		names.addListSelectionListener(listener);
		bPrint.addActionListener(new PPButtonListener());
		bPreview.addActionListener(new PPButtonListener());

	    // ��� �̹��� �г��� �����ӿ� �߰�
	    getContentPane().add(mainPanel, BorderLayout.CENTER);

	    setTitle("������û �����ͺ��̽� �ý���");
	    setSize(700, 500);
	    setVisible(true);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		Subject_Application sa = new Subject_Application();
		sa.dbConnection();
		sa.dbRecord();
		sa.setVisible(true);
	}

	private void dbConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/���¿�proj", "root", "mite");
			//			dbRecord();
		} catch (Exception ex) {
			System.out.println("DB ���� ����: " + ex.getMessage());
		}
	}

	public void dbRecord() {
		try {
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT s.subject, name, gender, p.profession FROM class "
					+ "NATURAL JOIN subject_app sa NATURAL JOIN subject s NATURAL JOIN profession p");
			Vector<String> list = new Vector<String>();
			while (rs.next()) {
				String studentName = rs.getString("name");
				System.out.println("Retrieved student: " + studentName);
				list.add(studentName);
			}
			stmt.close();
			Collections.sort(list);
			names.setListData(list);

			if (!list.isEmpty()) {
				names.setSelectedIndex(0);
			}
			// repaint();
			// names.revalidate();

		} catch (SQLException sqlex) {
			System.out.println("SQL ����: " + sqlex.getMessage());
			sqlex.printStackTrace();
		}
	}

	public class sbjList implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting() && !names.isSelectionEmpty()) {
				try {
					Statement stmt = conn.createStatement();
					String selectedName = (String) names.getSelectedValue();
					ResultSet rs = stmt.executeQuery("SELECT s.subject, name, gender, p.profession FROM class "
							+ "NATURAL JOIN subject_app sa NATURAL JOIN subject s NATURAL JOIN profession p WHERE name = '"
							+ selectedName + "'");

					if (rs.next()) {
						// ������ ó�� �ڵ�...
						subject.setText(rs.getString("subject"));
						name.setText(rs.getString("name"));
						profession.setText(rs.getString("profession"));
						if (rs.getString("gender").equals("M"))
							male.setSelected(true);
						else
							female.setSelected(true);
					}
					stmt.close();

				} catch (SQLException sqlex) {
					System.out.println("sql���� : " + sqlex.getMessage());

				} catch (Exception ex) {
					System.out.println("DB Handling ���� (����Ʈ ������): " + ex.getMessage());
				}
			}
		}
	}

	public class SearchListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = names.getNextMatch(search.getText().trim(), 0, Position.Bias.Forward);
			if (index != -1)
				names.setSelectedIndex(index);
			search.setText("");
		}
	}

	public class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			subject.setText("");
			name.setText("");
			profession.setText("");
			male.setSelected(true);
			female.setSelected(false);
			names.clearSelection();
		}

	}

	public class DButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				Statement stmt = conn.createStatement();
				String selectedName = (String) names.getSelectedValue();

				// �̸����� contact_id�� ã��
				ResultSet rs = stmt.executeQuery("SELECT contact_id FROM class WHERE name = '" + selectedName + "'");
				if (rs.next()) {
					int contactId = rs.getInt("contact_id");

					// subject_app ���̺��� subject_id�� �ش��ϴ� ���ڵ� ����
					stmt.executeUpdate("DELETE FROM subject_app WHERE contact_id = " + contactId);

					// class ���̺��� contact_id�� �ش��ϴ� ���ڵ� ����
					stmt.executeUpdate("DELETE FROM class WHERE contact_id = " + contactId);
				}

				stmt.close();

				dbRecord(); // ����� ������ �ٽ� �ε�
			} catch (SQLException sqlex) {
				System.out.println("���� �߻�: " + sqlex.getMessage());
			} catch (Exception ex) {
				System.out.println("DELETE ������ ����");
			}
		}
	}
	// �̸� �ٲ� �� prof_id�� �״�ο��� �ϴµ� ����ؼ� �ϳ��� �����ϴ� ���� �ذ��ؾ� �� ����.
	// ���� profession�� �ٲ� �� ���� �̸� ���� �ٲ��� ��
	// profession�� �ٲܽ� contact_id�� �״�ο�����
	// -> �ذ������δ� ���� ������ �ߺ����� �Լ��� ������ ���� ������ ����.

	public class SButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				Statement stmt = conn.createStatement();

				// �Է¹��� �л� ����
				String oldStudentName = (String) names.getSelectedValue();
				String newStudentName = name.getText().trim();
				String gender = male.isSelected() ? "M" : "F";
				// ���� ������ profession �ؽ�Ʈ �ʵ忡�� �Է¹޾ƾ� �մϴ�.
				String professionName = profession.getText().trim();
				// ���� ������ subject �ؽ�Ʈ �ʵ忡�� �Է¹޾ƾ� �մϴ�.
				String subjectName = subject.getText().trim();

				// �̸��� ����Ǿ��� ��, ���� ���ڵ带 ������Ʈ
				stmt.executeUpdate("UPDATE class SET name = '" + newStudentName + "', gender = '" + gender
						+ "' WHERE name = '" + oldStudentName + "'");

				// ���� ���ڵ尡 ������ ���� �߰�
				int updatedRows = stmt.getUpdateCount();
				if (updatedRows == 0) {
					// ������ �̹� �����ϴ��� Ȯ��
					ResultSet profIdResult = stmt.executeQuery(
							"SELECT prof_id FROM profession WHERE profession = '" + professionName + "'");
					int profId = -1;
					if (profIdResult.next()) {
						// �̹� �����ϴ� ��� �ش� ������ prof_id�� ������
						profId = profIdResult.getInt("prof_id");
					} else {
						// ������ ���� ��� ������ �߰��ϰ� prof_id�� ������
						stmt.executeUpdate("INSERT INTO profession (profession) VALUES ('" + professionName + "')",
								Statement.RETURN_GENERATED_KEYS);
						ResultSet generatedKeys = stmt.getGeneratedKeys();
						if (generatedKeys.next()) {
							profId = generatedKeys.getInt(1);
						}
					}

					// �л��� class ���̺� �߰�
					stmt.executeUpdate("INSERT INTO class (name, gender, prof_id) VALUES ('" + newStudentName + "', '"
							+ gender + "', " + profId + ")", Statement.RETURN_GENERATED_KEYS);
					ResultSet classGeneratedKeys = stmt.getGeneratedKeys();
					int contactId = -1;
					if (classGeneratedKeys.next()) {
						contactId = classGeneratedKeys.getInt(1);

						// �̹� �����ϴ��� Ȯ��
						ResultSet subjectIdResult = stmt.executeQuery(
								"SELECT subject_id FROM subject WHERE subject = '" + subjectName + "'");
						int subjectId = -1;
						if (subjectIdResult.next()) {
							// �̹� �����ϴ� ��� �ش� ������ subject_id�� ������
							subjectId = subjectIdResult.getInt("subject_id");
						} else {
							// ������ ���� ��� ������ �߰��ϰ� subject_id�� ������
							stmt.executeUpdate("INSERT INTO subject (subject) VALUES ('" + subjectName + "')",
									Statement.RETURN_GENERATED_KEYS);
							ResultSet subjectGeneratedKeys = stmt.getGeneratedKeys();
							if (subjectGeneratedKeys.next()) {
								subjectId = subjectGeneratedKeys.getInt(1);
							}
						}

						// �л��� �����ϴ� ������ subject_app ���̺� �߰�
						stmt.executeUpdate("INSERT INTO subject_app (contact_id, subject_id) VALUES (" + contactId
								+ ", " + subjectId + ")");
						// ...
					}
				}

				stmt.close();
				dbRecord(); // ����� ������ �ٽ� �ε�
			} catch (SQLException sqlex) {
				System.out.println("SQL ����: " + sqlex.getMessage());
			} catch (Exception ex) {
				System.out.println("DB ����: " + ex.getMessage());
			}
		}
	}

	public class PPButtonListener implements ActionListener {
		private JFrame previewFrame = new JFrame("�̸�����");
		private JPanel previewPanel = new JPanel();
		private JButton nextPageButton = new JButton("���� ������");
		private static final int RECORDS_PER_PAGE = 10; // �������� ���ڵ� ��

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == bPreview) {
				showPrintPreview(); // �̸����� ��ư�� ������ �� �̸����� �г��� ��Ÿ��
			} else if (e.getSource() == bPrint) {
				printPanel(); // ����Ʈ ��ư�� ������ �� ����Ʈ �۾��� �����
			}
		}

		// �̸����� ��� ����
		private void showPrintPreview() {
			// ���� �������� ��Ÿ���� ����
			int currentPage = 1;

			// �̸����� �г� ����
			previewPanel.setLayout(new BoxLayout(previewPanel, BoxLayout.Y_AXIS));

			// ù ������ ǥ��
			displayPage(createBook(currentPage));

			// ���� ������ ��ư�� ���� �׼� ������ �߰�
			nextPageButton.addActionListener(new ActionListener() {
				private AtomicInteger currentPage = new AtomicInteger(1); // AtomicInteger�� ����

				@Override
				public void actionPerformed(ActionEvent e) {
					currentPage.incrementAndGet(); // ���� �������� ������Ŵ
					Book nextPageBook = createBook(currentPage.get());
					if (nextPageBook != null) {
						displayPage(nextPageBook);
						previewFrame.revalidate(); // �������� �ٽ� �׸����� ��û�Ͽ� ���� ������ �ݿ�
					} else {
						nextPageButton.setEnabled(false); // �� �̻� �������� ������ ��ư ��Ȱ��ȭ
					}
				}
			});

			// �̸����� ������ ����
			previewFrame.setLayout(new BorderLayout());
			previewFrame.add(new JScrollPane(previewPanel), BorderLayout.CENTER);
			previewFrame.add(nextPageButton, BorderLayout.SOUTH);
			previewFrame.setSize(400, 600);
			previewFrame.setLocationRelativeTo(null);
			previewFrame.setVisible(true);      
		}

		// ����Ʈ �г��� �μ�
		private void printPanel() {
			// Book ��ü�� �����ϰ�, ����� ������ �����մϴ�.
			Book book = createBook(1); // ù ��° �������� �μ��մϴ�.

			// PrintablePanel�� Book ��ü�� �ʱ�ȭ�մϴ�.
			PrintablePanel printablePanel = new PrintablePanel(book);

			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(printablePanel);

			if (job.printDialog()) {
				try {
					job.print();
				} catch (PrinterException ex) {
					ex.printStackTrace();
				}
			}
		}

		// ������ ������ �̸����� �гο� ǥ���ϴ� �޼ҵ�
		private void displayPage(Book book) {
			previewPanel.removeAll(); // ���� �������� ������ ��� ����
			for (String record : book.getContent().split("\n")) {
				JLabel label = new JLabel(record);
				previewPanel.add(label); // �̸����� �гο� ���ڵ� �߰�
			}
		}


		// Book ��ü�� �����ϰ�, ����� ������ �����մϴ�.
		private Book createBook(int currentPage) {
			StringBuilder content = new StringBuilder();
			try {
				Statement stmt = conn.createStatement();
				int startIndex = (currentPage - 1) * RECORDS_PER_PAGE;
				ResultSet rs = stmt.executeQuery("SELECT s.subject, name, gender, p.profession FROM class "
						+ "NATURAL JOIN subject_app sa NATURAL JOIN subject s NATURAL JOIN profession p "
						+ "LIMIT " + startIndex + ", " + RECORDS_PER_PAGE);

				while (rs.next()) {
					// �л� ������ �̸����� �гο� �߰�
					String studentInfo = "�̸�: " + rs.getString("name") + ", "
							+ "����: " + rs.getString("subject") + ", "
							+ "����: " + rs.getString("gender") + ", "
							+ "����: " + rs.getString("profession");
					content.append(studentInfo).append("\n");
				}
				stmt.close();

			} catch (SQLException sqlex) {
				System.out.println("�̸����� ����: " + sqlex.getMessage());
			} catch (Exception ex) {
				System.out.println("�̸����� ����: " + ex.getMessage());
			}
			return new Book(content.toString());
		}
	}

}



