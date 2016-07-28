import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ActionListener {
	private JMenuItem exitItem;
	private JMenuItem New;

	public MainWindow() {
		super(" ");

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("");

		exitItem = new JMenuItem("Exit");
		New = new JMenuItem("New");

		fileMenu.add(exitItem);
		fileMenu.add(New);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);

		exitItem.addActionListener(this);
		New.addActionListener(this);

		MainContent c = new MainContent("Keith's B&B");

		getContentPane().add(c);

		setSize(1200, 600);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(exitItem)) {
			this.dispose();
		}
	}
}
