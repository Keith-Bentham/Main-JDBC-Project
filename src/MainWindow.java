import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ActionListener {
	private JMenuItem exitItem; 
	
	public MainWindow() {
		super("");
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Exit");
		exitItem = new JMenuItem("Exit"); 
		fileMenu.add(exitItem); 
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		exitItem.addActionListener(this); 
		MainContent c = new MainContent("Keiths B&B");
		getContentPane().add(c);
		setSize(1202, 600);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(exitItem)) {
			this.dispose();
		}
	}
}
