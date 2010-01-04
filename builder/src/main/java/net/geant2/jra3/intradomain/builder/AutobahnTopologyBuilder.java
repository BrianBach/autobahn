package net.geant2.jra3.intradomain.builder;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JSplitPane;
import javax.swing.JMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import net.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel;
import net.geant2.jra3.intradomain.common.GenericInterface;
import net.geant2.jra3.intradomain.common.Node;
import net.geant2.jra3.intradomain.ethernet.EthLink;
import net.geant2.jra3.intradomain.ethernet.EthPhysicalPort;
import net.geant2.jra3.intradomain.sdh.SdhDevice;
import net.geant2.jra3.intradomain.sdh.SdhPort;
import net.geant2.jra3.intradomain.sdh.StmLink;
import net.geant2.jra3.intradomain.topology.Topology;
import org.jgraph.JGraph;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import java.awt.GridBagConstraints;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.filechooser.FileFilter;


/**
 * Autobahn Topology Builder is tool use for creating topologies for JRA3 Autobahn
 * Interdomain Manager
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 * 
 */
public class AutobahnTopologyBuilder extends JFrame implements ActionListener,
		GraphSelectionListener {

	private static final long serialVersionUID = -2974312593128077421L;

	/**
	 * Dialog box used for configuring spanning tree issues
	 */
	private SpanningTreeDialog spanningsDialog = new SpanningTreeDialog();

	/**
	 * Table model for property table use for not regonize object
	 */
	private EmptyPropertyModel emptyModel = new EmptyPropertyModel();
	/**
	 * Current edited project
	 */
	public static TopologyProject project;
	/**
	 * Fonts are use for all windows and components in application
	 */
	public static Font font = new Font("Tahoma", Font.PLAIN, 11);
	public static Font fontBolded = new Font("Tahoma", Font.BOLD, 11);
	/**
	 * Icon use for representing node on the graph view
	 */
	public static ImageIcon nodeIcon = new ImageIcon(
			AutobahnTopologyBuilder.class.getResource("/images/node.png"));
	/**
	 * Icon use for representing interface on the graph view
	 */
	public static ImageIcon interfaceIcon = new ImageIcon(
			AutobahnTopologyBuilder.class.getResource("/images/interface.png"));

	/**
	 * JGraph objects use for graph view manipulations
	 */
	public static GraphModel model;
	public static GraphLayoutCache view;
	public static JGraph graph;

	/**
	 * Swing commponents
	 */
	private JMenuBar mainMenuBar = null;
	private JMenu FileMenu = null;
	private JMenu helpMenu = null;
	private JPanel mainPanel = null;
	private JToolBar mainToolBar = null;
	private JPanel bottomPanel = null;
	private JSplitPane rightPanel = null;
	private JToolBar rightToolbar = null;
	private JScrollPane mapScrollPane = null;
	private JButton addElementButton = null;
	private JPanel propertiesPanel = null;
	private JScrollPane propertiesScrollPane = null;
	private JTable propertiesTable = null;
	private JMenuItem newMenuItem = null;
	private JMenuItem saveMenuItem = null;
	private JMenuItem openMenuItem = null;
	private JMenuItem saveAsMenuItem;
	private JMenuItem exportMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem openAsNewFromTopologyMenuItem;
	private JMenuItem aboutMenuItem;
	public JPanel mapAndToolbarPanel = null;
	private JButton newProjectButton = null;
	private JButton saveProjectButton = null;
	private JButton exportTopologyButton = null;
	private JButton addLinkButton = null;
	private JButton addInterface = null;
	private JButton zoomInButton;
	private JButton zoomOutButton;
	private JButton remove = null;
	private JButton snapshotButton;
	private JButton spanningButton;
	private JButton openProjectButton;
	private JButton switchButton;
	/**
	 * Selected Ethernet link
	 */
	public EthLink selectedLink;
	/**
	 * Last saving path
	 */
	public String lastPath;	
	
	/**
	 * Creats AuthobahnTopologyBuilder object
	 */
	public AutobahnTopologyBuilder() {
		model = new DefaultGraphModel();
		view = new GraphLayoutCache(model, new DefaultCellViewFactory());
		graph = new JGraph(model, view);
		graph.getSelectionModel().addGraphSelectionListener(this);
		graph.setAntiAliased(true);
		ProjectFactory.initialize();
		initialize();
	}

	/**
	 * Crates main menu bar
	 * 
	 * @return javax.swing.JMenuBar created menu
	 */
	private JMenuBar getMainMenuBar() {
		if (mainMenuBar == null) {
			mainMenuBar = new JMenuBar();
			mainMenuBar.setPreferredSize(new Dimension(0, 20));
			mainMenuBar.setName("Menu");
			mainMenuBar.add(getFileMenu());
			mainMenuBar.add(getHelpMenu());
			mainMenuBar.setFont(font);
		}
		return mainMenuBar;
	}

	/**
	 * Create FileMenu bar
	 * 
	 * @return javax.swing.JMenu created menu
	 */
	private JMenu getFileMenu() {
		if (FileMenu == null) {
			FileMenu = new JMenu();
			FileMenu.setMnemonic(KeyEvent.VK_F);
			FileMenu.setText(MessagesProvider.getMessage("menu.file"));
			FileMenu.setFont(font);
			FileMenu.add(getNewMenuItem());
			FileMenu.add(getOpenMenuItem());
			FileMenu.addSeparator();
			FileMenu.add(getSaveMenuItem());
			FileMenu.add(getSaveAsMenuItem());
			FileMenu.addSeparator();
			FileMenu.add(getOpenAsNewFromTopologyMenuItem());
			FileMenu.add(getExportMenuItem());
			FileMenu.addSeparator();
			FileMenu.add(getExitMenuItem());
		}
		return FileMenu;
	}

	/**
	 * Create HelpMenu bar
	 * 
	 * @return javax.swing.JMenu created menu
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setMnemonic(KeyEvent.VK_H);
			helpMenu.setText(MessagesProvider.getMessage("menu.help"));
			helpMenu.add(getAboutMenuItem());
			helpMenu.setFont(AutobahnTopologyBuilder.font);
		}
		return helpMenu;
	}

	/**
	 * Create main panel
	 * 
	 *  @return javax.swing.JPanel created panel
	 */
	private JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanel();
			mainPanel.setFont(font);
			mainPanel.setLayout(new BorderLayout());
			mainPanel.add(getMainToolBar(), BorderLayout.NORTH);
			mainPanel.add(getRightPanel(), BorderLayout.CENTER);
			mainPanel.add(getBottomPanel(), BorderLayout.SOUTH);
		}
		return mainPanel;
	}

	/**
	 * Creates main toolbar 
	 * 
	 * @return javax.swing.JToolBar created toolbar
	 */
	private JToolBar getMainToolBar() {
		if (mainToolBar == null) {
			mainToolBar = new JToolBar();
			mainToolBar.setFont(font);
			mainToolBar.setPreferredSize(new Dimension(18, 32));
			mainToolBar.add(getNewProjectButton());
			mainToolBar.add(getOpenProjectButton());
			mainToolBar.addSeparator();
			mainToolBar.add(getSaveProjectButton());
			mainToolBar.add(getExportTopologyButton());
		}
		return mainToolBar;
	}

	/**
	 * Creates bottom panel
	 * 
	 * @return javax.swing.JPanel create panel
	 */
	private JPanel getBottomPanel() {
		if (bottomPanel == null) {
			bottomPanel = new JPanel();
			bottomPanel.setLayout(new GridBagLayout());
			bottomPanel.setPreferredSize(new Dimension(0, 25));
			bottomPanel.setFont(font);
		}
		return bottomPanel;
	}

	/**
	 *Creates right panel
	 * 
	 * @return javax.swing.JSplitPane created panel
	 */
	private JSplitPane getRightPanel() {
		if (rightPanel == null) {
			rightPanel = new JSplitPane();
			// rightPanel.setLayout(new BorderLayout());
			rightPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
			rightPanel.setDividerLocation(400);
			rightPanel.setTopComponent(getMapAndToolbarPanel());
			rightPanel.setBottomComponent(getPropertiesPanel());
			rightPanel.setFont(font);
			// rightPanel.setLeftComponent(getRightToolbar());
		}
		return rightPanel;
	}

	/**
	 * Creates right toolbar
	 * 
	 * @return javax.swing.JToolBar created toolbar
	 */
	private JToolBar getRightToolbar() {
		if (rightToolbar == null) {
			rightToolbar = new JToolBar();
			rightToolbar.setPreferredSize(new Dimension(32, 32));
			rightToolbar.add(getAddElementButton());
			rightToolbar.setFont(font);
			rightToolbar.add(getAddInterface());
			rightToolbar.add(getAddLinkButton());
			rightToolbar.add(getSpanningTree());
			rightToolbar.addSeparator();
			rightToolbar.add(getSwitchDirection());
			rightToolbar.addSeparator();
			rightToolbar.add(getRemove());
			rightToolbar.addSeparator();
			rightToolbar.add(getZoomInButton());
			rightToolbar.add(getZoomOutButton());
			rightToolbar.addSeparator();
			rightToolbar.add(getSnapshotButton());
		}
		return rightToolbar;
	}

	/**
	 * Enable or disable buttons for project actions
	 * 
	 * @param activate identifies if enable or disable
	 */
	public void activateProject(boolean activate) {
		if (project != null) {
			getAddElementButton().setEnabled(activate);
			getZoomInButton().setEnabled(activate);
			getZoomOutButton().setEnabled(activate);
			getSnapshotButton().setEnabled(activate);
			getExportTopologyButton().setEnabled(activate);
		}
	}
	/**
	 * Create graph scroll pane
	 * 
	 * @return javax.swing.JScrollPane create scroll pane
	 */
	private JScrollPane getMapScrollPane() {
		if (mapScrollPane == null) {
			mapScrollPane = new JScrollPane(graph);
			mapScrollPane.setPreferredSize(new Dimension(200, 200));
			mapScrollPane.setToolTipText(MessagesProvider
					.getMessage("menu.file.save"));
			mapScrollPane.setFont(font);
		}
		return mapScrollPane;
	}

	/**
	 * Creates add element button
	 * 
	 * @return javax.swing.JButton created button
	 */
	private JButton getAddElementButton() {
		if (addElementButton == null) {
			addElementButton = new JButton();
			addElementButton.setIcon(new ImageIcon(getClass().getResource(
					"/images/addNode.png")));
			addElementButton.setActionCommand("GRAPH_ADD_NODE");
			addElementButton.addActionListener(this);
			addElementButton.setFont(font);
			addElementButton.setEnabled(false);
			addElementButton.setToolTipText(MessagesProvider.getMessage("button.add.node.tip"));
		}
		return addElementButton;
	}

	/**
	 * Creates properties panel
	 * 
	 * @return javax.swing.JPanel created panel
	 */
	private JPanel getPropertiesPanel() {
		if (propertiesPanel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			propertiesPanel = new JPanel();
			propertiesPanel.setLayout(new GridBagLayout());
			propertiesPanel.setPreferredSize(new Dimension(50, 100));
			propertiesPanel.add(getPropertiesScrollPane(), gridBagConstraints);
			propertiesPanel.setFont(font);
		}
		return propertiesPanel;
	}

	/**
	 * Creates properties scroll pane
	 * 
	 * @return javax.swing.JScrollPane created panel
	 */
	private JScrollPane getPropertiesScrollPane() {
		if (propertiesScrollPane == null) {
			propertiesScrollPane = new JScrollPane();
			propertiesScrollPane.setViewportView(getPropertiesTable());
			propertiesScrollPane.setFont(font);
		}
		return propertiesScrollPane;
	}

	/**
	 * Creates properties table
	 * 
	 * @return javax.swing.JTable created table
	 */
	private JTable getPropertiesTable() {
		if (propertiesTable == null) {
			propertiesTable = new JTable(new EmptyPropertyModel());
			propertiesTable.setFont(font);
			propertiesTable.getTableHeader().setFont(fontBolded);
			propertiesTable.getColumnModel().getColumn(0).setWidth(100);
			propertiesTable.getColumnModel().getColumn(0)
					.setPreferredWidth(100);
			propertiesTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		}
		return propertiesTable;
	}

	/**
	 * Creates new menu item
	 * 
	 * @return javax.swing.JMenuItem created menu item
	 */
	private JMenuItem getNewMenuItem() {
		if (newMenuItem == null) {
			newMenuItem = new JMenuItem();
			newMenuItem.setText("New Project");
			newMenuItem.setActionCommand("NEW_PROJECT");
			newMenuItem.setIcon(new ImageIcon(getClass().getResource(
					"/images/filenew.png")));
			newMenuItem.setMnemonic(KeyEvent.VK_N);
			newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
					ActionEvent.ALT_MASK));
			newMenuItem.addActionListener(this);
			newMenuItem.setFont(font);
		}
		return newMenuItem;
	}

	/**
	 * Create new from a topology menu item
	 * 
	 * @return javax.swing.JMenuItem created menu item
	 */
	private JMenuItem getOpenAsNewFromTopologyMenuItem() {
		if (openAsNewFromTopologyMenuItem == null) {
			openAsNewFromTopologyMenuItem = new JMenuItem();
			openAsNewFromTopologyMenuItem.setText("Import topology");
			openAsNewFromTopologyMenuItem.setActionCommand("IMPORT_TOPOLOGY");
			openAsNewFromTopologyMenuItem.setIcon(new ImageIcon(getClass()
					.getResource("/images/fileimport.png")));
			openAsNewFromTopologyMenuItem.setMnemonic(KeyEvent.VK_I);
			openAsNewFromTopologyMenuItem.setAccelerator(KeyStroke
					.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
			openAsNewFromTopologyMenuItem.addActionListener(this);
			openAsNewFromTopologyMenuItem.setFont(font);
		}
		return openAsNewFromTopologyMenuItem;
	}

	/**
	 * Creates  saveAs menu item
	 * 
	 * @return javax.swing.JMenuItem created menu item 
	 */
	private JMenuItem getSaveAsMenuItem() {
		if (saveAsMenuItem == null) {
			saveAsMenuItem = new JMenuItem();
			saveAsMenuItem.setText("Save As");
			saveAsMenuItem.setActionCommand("SAVE_AS_PROJECT");
			saveAsMenuItem.setIcon(new ImageIcon(getClass().getResource(
					"/images/filesaveas.png")));
			saveAsMenuItem.addActionListener(this);
			saveAsMenuItem.setFont(font);
		}
		return saveAsMenuItem;
	}

	/**
	 * Create export menu item
	 * 
	 * @return javax.swing.JMenuItem created menu item 
	 */
	private JMenuItem getExportMenuItem() {
		if (exportMenuItem == null) {
			exportMenuItem = new JMenuItem();
			exportMenuItem.setText("Export topology");
			exportMenuItem.setActionCommand("EXPORT_TOPOLOGY");
			exportMenuItem.setMnemonic(KeyEvent.VK_E);
			exportMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
					ActionEvent.ALT_MASK));
			exportMenuItem.setIcon(new ImageIcon(getClass().getResource(
					"/images/fileexport.png")));
			exportMenuItem.addActionListener(this);
			exportMenuItem.setFont(font);
		}
		return exportMenuItem;
	}
	
	/**
	 * Creates about menu item
	 * 
	 * @return javax.swing.JMenuItem created menu item
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.setActionCommand("PRINT_PROJECT");
			aboutMenuItem.setMnemonic(KeyEvent.VK_A);
			aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
					ActionEvent.ALT_MASK));
			aboutMenuItem.addActionListener(this);
			aboutMenuItem.setFont(font);
		}
		return aboutMenuItem;
	}

	/**
	 * Creates exit menu item
	 * 
	 * @return javax.swing.JMenuItem created menu item 
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.setActionCommand("EXIT");
			exitMenuItem.setIcon(new ImageIcon(getClass().getResource(
					"/images/fileclose.png")));
			exitMenuItem.addActionListener(this);
			exitMenuItem.setFont(font);
		}
		return exitMenuItem;
	}

	/**
	 * Creates  save menu item
	 * 
	 * @return javax.swing.JMenuItem created menu item
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Save Project");
			saveMenuItem.setActionCommand("SAVE_PROJECT");
			saveMenuItem.setIcon(new ImageIcon(getClass().getResource(
					"/images/filesave.png")));
			saveMenuItem.setMnemonic(KeyEvent.VK_S);
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					ActionEvent.ALT_MASK));
			saveMenuItem.setFont(font);
			saveMenuItem.addActionListener(this);
		}
		return saveMenuItem;
	}

	/**
	 * Creates new project button
	 * 
	 * @return javax.swing.JButton created button
	 */
	private JButton getNewProjectButton() {
		if (newProjectButton == null) {
			newProjectButton = new JButton();
			newProjectButton.setIcon(new ImageIcon(getClass().getResource(
					"/images/filenew.png")));
			newProjectButton.setActionCommand("NEW_PROJECT");
			newProjectButton.setToolTipText("Create new Project");
			newProjectButton.addActionListener(this);
			newProjectButton.setFont(font);
			newProjectButton.setToolTipText(MessagesProvider.getMessage("menu.file.new"));
		}
		return newProjectButton;
	}

	/**
	 * Creates open project button
	 * 
	 * @return javax.swing.JButton created button
	 */
	private JButton getOpenProjectButton() {
		if (openProjectButton == null) {
			openProjectButton = new JButton();
			openProjectButton.setIcon(new ImageIcon(getClass().getResource(
					"/images/fileopen.png")));
			openProjectButton.setActionCommand("OPEN_PROJECT");
			openProjectButton.setToolTipText("Open Project");
			openProjectButton.addActionListener(this);
			openProjectButton.setFont(font);
			openProjectButton.setToolTipText(MessagesProvider.getMessage("menu.file.open"));
		}
		return openProjectButton;
	}

	/**
	 * Creates save project button
	 * 
	 * @return javax.swing.JButton created button
	 */
	private JButton getSaveProjectButton() {
		if (saveProjectButton == null) {
			saveProjectButton = new JButton();
			saveProjectButton.setIcon(new ImageIcon(getClass().getResource(
					"/images/filesave.png")));
			saveProjectButton.setActionCommand("SAVE_PROJECT");
			saveProjectButton.addActionListener(this);
			saveProjectButton.setFont(font);
			saveProjectButton.setToolTipText(MessagesProvider.getMessage("menu.file.save"));
		}
		return saveProjectButton;
	}

	/**
	 * Creates export topology button
	 * 
	 * @return javax.swing.JButton created button
	 */
	private JButton getExportTopologyButton() {
		if (exportTopologyButton == null) {
			exportTopologyButton = new JButton();
			exportTopologyButton.setIcon(new ImageIcon(getClass().getResource(
					"/images/fileexport.png")));
			exportTopologyButton.setActionCommand("EXPORT_TOPOLOGY");
			exportTopologyButton.addActionListener(this);
			exportTopologyButton.setFont(font);
			exportTopologyButton.setToolTipText(MessagesProvider.getMessage("menu.file.export"));
		}
		return exportTopologyButton;
	}

	/**
	 * Creates open menu item
	 * 
	 * @return javax.swing.JMenuItem created menu item
	 */
	private JMenuItem getOpenMenuItem() {
		if (openMenuItem == null) {
			openMenuItem = new JMenuItem();
			openMenuItem.setActionCommand("OPEN_PROJECT");
			openMenuItem.setText("Open project");
			openMenuItem.setIcon(new ImageIcon(getClass().getResource(
					"/images/fileopen.png")));
			openMenuItem.addActionListener(this);
			openMenuItem.setFont(font);
			
		}
		return openMenuItem;
	}

	/**
	 * Creates graph toolbar panel
	 * 
	 * @return javax.swing.JPanel created panel
	 */
	private JPanel getMapAndToolbarPanel() {
		if (mapAndToolbarPanel == null) {
			mapAndToolbarPanel = new JPanel();
			mapAndToolbarPanel.setLayout(new BorderLayout());
			mapAndToolbarPanel.add(getRightToolbar(), BorderLayout.NORTH);
			mapAndToolbarPanel.add(getMapScrollPane(), BorderLayout.CENTER);
			mapAndToolbarPanel.setFont(font);
		}
		return mapAndToolbarPanel;
	}

	/**
	 * Creates add link button
	 * 
	 * @return javax.swing.JButton created button
	 */
	private JButton getAddLinkButton() {
		if (addLinkButton == null) {
			addLinkButton = new JButton();
			addLinkButton.setActionCommand("GRAPH_ADD_LINK");
			addLinkButton.setIcon(new ImageIcon(getClass().getResource(
					"/images/addLink.png")));
			addLinkButton.addActionListener(this);
			addLinkButton.setEnabled(false);
			addLinkButton.setToolTipText(MessagesProvider.getMessage("button.add.link.tip"));
		}
		return addLinkButton;
	}

	/**
	 * Creates zoomIn button
	 * 
	 * @return javax.swing.JButton created button
	 */
	private JButton getZoomInButton() {
		if (zoomInButton == null) {
			zoomInButton = new JButton();
			zoomInButton.setActionCommand("GRAPH_ZOOM_IN");
			zoomInButton.setIcon(new ImageIcon(getClass().getResource(
					"/images/zoom-in.png")));
			zoomInButton.addActionListener(this);
			zoomInButton.setEnabled(false);
			zoomInButton.setToolTipText(MessagesProvider.getMessage("button.zoom.in.tip"));
		}
		return zoomInButton;
	}

	/**
	 * Creates zoomOut button
	 * 
	 * @return javax.swing.JButton created button
	 */
	private JButton getZoomOutButton() {
		if (zoomOutButton == null) {
			zoomOutButton = new JButton();
			zoomOutButton.setActionCommand("GRAPH_ZOOM_OUT");
			zoomOutButton.setIcon(new ImageIcon(getClass().getResource(
					"/images/zoom-out.png")));
			zoomOutButton.addActionListener(this);
			zoomOutButton.setEnabled(false);
			zoomOutButton.setToolTipText(MessagesProvider.getMessage("button.zoom.out.tip"));
		}
		return zoomOutButton;
	}

	/**
	 * Creates zoomOut button
	 * 
	 * @return javax.swing.JButton created button
	 */
	private JButton getSnapshotButton() {
		if (snapshotButton == null) {
			snapshotButton = new JButton();
			snapshotButton.setActionCommand("SNAP_SHOT");
			snapshotButton.setIcon(new ImageIcon(getClass().getResource(
					"/images/snapShot.png")));
			snapshotButton.addActionListener(this);
			snapshotButton.setSize(new Dimension(32, 32));
			snapshotButton.setEnabled(false);
			snapshotButton.setToolTipText(MessagesProvider.getMessage("button.snapshot.tip"));
		}
		return snapshotButton;
	}

	/**
	 * Creates add interface button
	 * 
	 * @return javax.swing.JButton created button
	 */
	private JButton getAddInterface() {
		if (addInterface == null) {
			addInterface = new JButton();
			addInterface.setIcon(new ImageIcon(getClass().getResource(
					"/images/addInterface.png")));
			addInterface.setActionCommand("GRAPH_ADD_INTERFACE");
			addInterface.addActionListener(this);
			addInterface.setEnabled(false);
			addInterface.setToolTipText(MessagesProvider.getMessage("button.add.interface.tip"));
		}
		return addInterface;
	}

	/**
	 * Open spanning tree manager button
	 * 
	 * @return javax.swing.JButton created button
	 */
	private JButton getSpanningTree() {
		if (spanningButton == null) {
			spanningButton = new JButton();
			spanningButton.setIcon(new ImageIcon(getClass().getResource(
					"/images/spanning.png")));
			spanningButton.setActionCommand("SPANNING_TREE_MANAGING");
			spanningButton.addActionListener(this);
			spanningButton.setEnabled(false);
			spanningButton.setToolTipText(MessagesProvider.getMessage("button.manage.spanning.tip"));
		}
		return spanningButton;
	}

	/**
	 * Creates remove button
	 * 
	 * @return javax.swing.JButton created button
	 */
	private JButton getRemove() {
		if (remove == null) {
			remove = new JButton();
			remove.setActionCommand("GRAPH_REMOVE");
			remove.setIcon(new ImageIcon(getClass().getResource(
					"/images/remove.png")));
			remove.addActionListener(this);
			remove.setEnabled(false);
			remove.setToolTipText(MessagesProvider.getMessage("button.remove.tip"));
		}
		return remove;
	}
	
	/**
	 * This method switch direction of the link button
	 * 
	 * @return javax.swing.JButton created button
	 */
	private JButton getSwitchDirection() {
		if (switchButton == null) {
			switchButton = new JButton();
			switchButton.setActionCommand("SWITCH_DIRECTION");
			switchButton.setIcon(new ImageIcon(getClass().getResource(
					"/images/switch.png")));
			switchButton.addActionListener(this);
			switchButton.setEnabled(false);
			switchButton.setToolTipText(MessagesProvider.getMessage("button.switch.tip"));
		}
		return switchButton;
	}
	
	
	/**
	 * Starts application
	 * @param args no argument are needed
	 */
	public static void main(String[] args) {
		SplashScreen splash = new SplashScreen(5000);
		splash.showSplash();
		AutobahnTopologyBuilder frame = new AutobahnTopologyBuilder();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Initializes application
	 */
	private void initialize() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (screen.width - 800) / 2;
	    int y = (screen.height - 600) / 2;
	    setBounds(x, y, 800, 600);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/images/autobahnMarker.png")));
		this.setTitle("Autobahn topology builder");
		this.setFont(font);
		this.setContentPane(getMainPanel());
		this.setJMenuBar(getMainMenuBar());
		this.setPreferredSize(new Dimension(800, 600));
		activateProject(false);
	}
	/**
	 * Performs application actions
	 * @param e action event
	 */
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if (name.equals("GRAPH_ADD_NODE")) {
			graphCreateNewNode();
			return;
		}
		if (name.equals("GRAPH_ADD_LINK")) {
			graphCreateNewLink();
			return;
		}
		if (name.equals("GRAPH_ADD_INTERFACE")) {
			graphCreateNewInterface();
			return;
		}
		if (name.equals("GRAPH_ZOOM_IN")) {
			graph.setScale(graph.getScale() + 0.1);
			return;
		}
		if (name.equals("GRAPH_ZOOM_OUT")) {
			graph.setScale(graph.getScale() - 0.1);
			return;
		}
		if (name.equals("SWITCH_DIRECTION")){
			DefaultEdge edge = (DefaultEdge)graph.getSelectionCell();
			project.switchLinkDirection (edge.getUserObject());
		
			DefaultPort start = ((DefaultPort)edge.getSource());
			DefaultPort end = (DefaultPort)edge.getTarget();
			edge.setSource(end);
			edge.setTarget(start);
			view.refresh(view.getCellViews(), true);
			graph.updateUI();
			return;
		}
		if (name.equals("GRAPH_ADD_INTERFACE")) {
			graphCreateNewInterface();
			return;
		}
		if (name.equals("GRAPH_REMOVE")) {
			graphRemove();
			return;
		}
		if (name.equals("NEW_PROJECT")) {
			createNewProject();
			return;
		}
		if (name.equals("OPEN_PROJECT")) {
			openProject();
			return;
		}
		if (name.equals("SAVE_PROJECT")) {
			save();
			return;
		}
		if (name.equals("SAVE_AS_PROJECT")) {
			saveAsProject();
			return;
		}
		
		if (name.equals("EXPORT_TOPOLOGY")) {
			exportTopology();
			return;
		}
		if (name.equals("IMPORT_TOPOLOGY")) {
			importTopology();
			return;
		}
		if (name.equals("SNAP_SHOT")) {
			snapShot();
			return;
		}

		if (name.equals("SPANNING_TREE_MANAGING")) {
			openSpanningTreeDialog();
			return;
		}

		if (name.equals("EXIT")) {

			dispose();
			System.exit(1);
			return;
		}

	}
	
	/**
	 * Opens spanning tree dialog box
	 */
	private void openSpanningTreeDialog() {
		System.out.println("Openning spannnig tree");
		if (project.getTopology().getEthernetTopology() != null
				&& selectedLink != null) {
			spanningsDialog.openSpannnigTreeDialog(project.getTopology()
					.getEthernetTopology(), selectedLink);
		}
	}
	/**
	 * Imports topology, but opening file chooser dialog box and parsing loaded topology
	 */
	private void importTopology() {
		if (project ==null)
			return;
		JFileChooser chooser = new JFileChooser(project.getPath());
		chooser.setFileView(new AutobahnTopologyFileView());
		// FileFilter filter = new AutoBahnTopologyFileFilter();
		// chooser.setFileFilter(filter);
		if (!checkProjectSave())
			return;
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Topology topology = TopologyProject.importTopolgy(chooser
					.getSelectedFile().getAbsolutePath());

			view.remove(view.getCells(true, true, true, true));

			if (topology.getSdhTopology() != null) {
				sdhTopologyShownig(topology, null);
				project = ProjectFactory.createProject("SDH");

			} else if (topology.getEthernetTopology() != null) {
				ethTopologyShowing(topology, null);
				project = ProjectFactory.createProject("ETHERNET");
			}
			project.setTopology(topology);
			graph.updateUI();
			activateProject(true);
		}

	}
	/**
	 * Creates the JGraph representation of a ethernet topology use during  importing topology 
	 * 
	 * @param topology ethernet topology
	 * @param properties	showing properties
	 */
	private void ethTopologyShowing(Topology topology, Map properties) {
		int x = 50, y = 50;
		int countInRow = 4;
		List<Node> nodes = topology.getNodes();
		List<EthPhysicalPort> interfaces = topology.getEthernetTopology()
				.getPorts();
		List<EthLink> links = topology.getEthernetTopology().getLinks();

		int lenght = nodes.size();
		Map<String, DefaultGraphCell> temp = new HashMap<String, DefaultGraphCell>();
		int interfacesLength = interfaces.size();
		Rectangle2D screenRect = graph.getBounds(); 
			graph.getBounds();
		for (int i = 0; i < lenght; i++) {
			Node device = nodes.get(i);

			Rect rect = null;
			DefaultGraphCell cell = null;
			if (properties != null){
				rect = (Rect) properties.get(device.getName());
				rect.setX(rect.getX()-screenRect.getX());
				rect.setY(rect.getY()-screenRect.getY());
			}
				
			if (rect != null)
				cell = createCell(rect.getX(), rect.getY(), rect.getWidth(),
						rect.getHeigth(), false, true, Color.white, nodeIcon);
			else
				cell = createCell(x, y, 80, 30, false, true, Color.white,
						nodeIcon);
			cell.setUserObject(device);
			view.insert(cell);
			int x_interface = x - 50, y_interface = y + 30 + 10;
			for (int j = 0; j < interfacesLength; j++) {
				if ((interfaces.get(j).getGenericInterface().getNode() == device)) {
					rect = null;
					if (properties != null) {
						rect = (Rect) properties.get(device.getName());
					}
					DefaultGraphCell interfaceCell = null;
					if (rect != null)
						interfaceCell = createCell(rect.getX(), rect.getY(),
								rect.getWidth(), rect.getHeigth(), false,
								false, Color.white, interfaceIcon);
					else
						interfaceCell = createCell(x_interface, y_interface,
								80, 20, false, false, Color.white,
								interfaceIcon);
					if (interfaces.get(j).getGenericInterface().getName() != null)
						temp.put(interfaces.get(j).getGenericInterface()
								.getName(), interfaceCell);

					DefaultEdge edge = createEdge(cell, interfaceCell,
							(float) 0.1, false, false, Color.cyan, false);
					view.insert(interfaceCell);
					view.insert(edge);
					Object genericInterface = interfaces.get(j);
					interfaceCell.setUserObject(genericInterface);
					interfaceCell.addPort();
					if (interfaces.get(j).getGenericInterface().getName() != null)
						temp.put(interfaces.get(j).getGenericInterface()
								.getName(), interfaceCell);
					x_interface += 40;
					y_interface += 30;
				}
			}
			if (i != 0 && (i % countInRow) == 0) {
				y += 150;
				x = 150;
			} else
				x += 150;
		}
		lenght = links.size();
		EthLink link = null;
		List<EthLink> toRemoveLinks = new ArrayList<EthLink>();
		for (int i = 0; i < lenght; i++) {
			link = links.get(i);
			if (link.getGenericLink() == null
					|| link.getGenericLink().getStartInterface() == null
					|| link.getGenericLink().getEndInterface() == null) {
				toRemoveLinks.add(link);
				continue;
			}
			GenericInterface start = link.getGenericLink().getStartInterface();
			GenericInterface end = link.getGenericLink().getEndInterface();

			DefaultGraphCell cell1 = (DefaultGraphCell) temp.get(start
					.getName());
			DefaultGraphCell cell2 = (DefaultGraphCell) temp.get(end.getName());

			if (cell1 == null || cell2 == null) {
				toRemoveLinks.add(link);
				continue;
			}

			DefaultEdge edge = createEdge(cell1, cell2, (float) 2.0, true,
					false, Color.black, true);
			edge.setUserObject(link);
			view.insert(edge);
		}
		int length = toRemoveLinks.size();
		for (int i = 0; i < length; i++)
			links.remove(toRemoveLinks.get(i));

	}
	/**
	 * Creates the JGraph representation of a ethernet topology use during  opening project 
	 * 
	 * @param topology ethernet topology
	 * @param properties	showing properties
	 */
	private void ethernetTopologyShowing(Topology topology, Map properties) {
		int x = 50, y = 50;
		int countInRow = 4;
		ArrayList<Node> devices = topology.getNodes();
		ArrayList<EthPhysicalPort> interfaces = topology.getEthernetTopology()
				.getPorts();
		int lenght = devices.size();
		Map<String, DefaultGraphCell> temp = new HashMap<String, DefaultGraphCell>();
		
		Rectangle2D screenRect = graph.getBounds();
		for (int i = 0; i < lenght; i++) {
			Node node = devices.get(i);
			DefaultGraphCell cell = null;
			Rect rect = null;
			if (properties != null){
				rect = (Rect) properties.get(node.getName());
			}
			if (rect != null)
				cell = createCell(rect.getX(), rect.getY(), rect.getWidth(),
						rect.getHeigth(), false, true, Color.white, nodeIcon);
			else
				cell = createCell(x, y, 80, 30, false, true, Color.white,
						nodeIcon);
			cell.setUserObject(node);
			view.insert(cell);

			int interfacesLength = interfaces.size();

			int x_interface = x - 50, y_interface = y + 30 + 10;
			for (int j = 0; j < interfacesLength; j++) {
				if ((interfaces.get(j).getGenericInterface().getNode() == node)) {
					rect = null;
					if (properties != null) {
						rect = (Rect) properties.get(interfaces.get(j)
								.toString());
					}
					DefaultGraphCell interfaceCell = null;
					if (rect != null)
						interfaceCell = createCell(rect.getX(), rect.getY(),
								rect.getWidth(), rect.getHeigth(), false,
								false, Color.white, interfaceIcon);
					else
						interfaceCell = createCell(x_interface, y_interface,
								80, 20, false, false, Color.white,
								interfaceIcon);
					Object genericInterface = interfaces.get(j);
					interfaceCell.setUserObject(genericInterface);
					if (interfaces.get(j).getGenericInterface().getName() != null)
						temp.put(interfaces.get(j).getGenericInterface()
								.getName(), interfaceCell);
					DefaultEdge edge = createEdge(cell, interfaceCell,
							(float) 0.1, false, false, Color.cyan, false);
					GraphConstants.setSelectable(edge.getAttributes(), false);
					view.insert(interfaceCell);
					view.insert(edge);

					interfaceCell.addPort();
					if (interfaces.get(j).getGenericInterface().getName() != null)
						temp.put(interfaces.get(j).getGenericInterface()
								.getName(), interfaceCell);
					x_interface += 40;
					y_interface += 30;
				}
			}
			if (i != 0 && (i % countInRow) == 0) {
				y += 150;
				x = 150;
			} else
				x += 150;
		}
		ArrayList<EthLink> links = topology.getEthernetTopology().getLinks();
		lenght = links.size();
		EthLink link = null;
		List<EthLink> toRemoveLinks = new ArrayList<EthLink>();
		for (int i = 0; i < lenght; i++) {
			link = links.get(i);
			if (link.getGenericLink() == null
					|| link.getGenericLink().getStartInterface() == null
					|| link.getGenericLink().getEndInterface() == null) {
				toRemoveLinks.add(link);
				continue;
			}
			GenericInterface start = link.getGenericLink().getStartInterface();
			GenericInterface end = link.getGenericLink().getEndInterface();

			DefaultGraphCell cell1 = (DefaultGraphCell) temp.get(start
					.getName());
			DefaultGraphCell cell2 = (DefaultGraphCell) temp.get(end.getName());

			if (cell1 == null || cell2 == null) {
				toRemoveLinks.add(link);
				continue;
			}

			DefaultEdge edge = createEdge(cell1, cell2, (float) 2.0, true,
					false, Color.black, true);
			edge.setUserObject(link);
			view.insert(edge);
		}
		int length = toRemoveLinks.size();
		for (int i = 0; i < length; i++)
			links.remove(toRemoveLinks.get(i));
	}
	/**
	 * Creates the JGraph representation of a SDH topology use during  importing topology 
	 * 
	 * @param topology SDH topology
	 * @param properties showing properties
	 */
	private void sdhTopologyShownig(Topology topology, Map properties) {
		int x = 50, y = 50;
		int countInRow = 4;
		List<SdhDevice> devices = topology.getSdhTopology().getDevices();
		List<SdhPort> interfaces = topology.getSdhTopology().getPorts();
		int lenght = devices.size();
		Map<String, DefaultGraphCell> temp = new HashMap<String, DefaultGraphCell>();
		Rectangle2D screenRect = graph.getBounds();
		for (int i = 0; i < lenght; i++) {

			SdhDevice device = devices.get(i);
			DefaultGraphCell cell = null;
			Rect rect = null;
			if (properties != null){
				rect = (Rect) properties.get(device.getName());
				rect.setX(rect.getX()-screenRect.getX());
				rect.setY(rect.getY()-screenRect.getY());
			}
			if (rect != null)
				cell = createCell(rect.getX(), rect.getY(), rect.getWidth(),
						rect.getHeigth(), false, true, Color.white, nodeIcon);
			else
				cell = createCell(x, y, 80, 30, false, true, Color.white,
						nodeIcon);
			cell.setUserObject(device);
			view.insert(cell);

			int interfacesLength = interfaces.size();

			int x_interface = x, y_interface = y + 30 + 50;

			for (int j = 0; j < interfacesLength; j++) {
				if ((interfaces.get(j).getGenericInterface().getNode() == device
						.getNode())) {

					rect = null;
					if (properties != null) {
						rect = (Rect) properties.get(interfaces.get(j)
								.toString());
					}
					DefaultGraphCell interfaceCell = null;
					if (rect != null)
						interfaceCell = createCell(rect.getX(), rect.getY(),
								rect.getWidth(), rect.getHeigth(), false,
								false, Color.white, interfaceIcon);
					else
						interfaceCell = createCell(x_interface, y_interface,
								80, 20, false, false, Color.white,
								interfaceIcon);
					Object genericInterface = interfaces.get(j);
					interfaceCell.setUserObject(genericInterface);
					if (interfaces.get(j).getGenericInterface().getName() != null)
						temp.put(interfaces.get(j).getGenericInterface()
								.getName(), interfaceCell);
					DefaultEdge edge = createEdge(cell, interfaceCell,
							(float) 0.1, false, false, Color.cyan, false);
					GraphConstants.setSelectable(edge.getAttributes(), false);
					view.insert(interfaceCell);
					view.insert(edge);

					interfaceCell.addPort();
					if (interfaces.get(j).getGenericInterface().getName() != null)
						temp.put(interfaces.get(j).getGenericInterface()
								.getName(), interfaceCell);
					x_interface += 40;
					y_interface += 30;
				}
			}
			if (i != 0 && (i % countInRow) == 0) {
				y += 150;
				x = 150;
			} else
				x += 150;
		}
		List<StmLink> links = topology.getSdhTopology().getLinks();
		lenght = links.size();
		StmLink link = null;
		List<StmLink> toRemoveLinks = new ArrayList<StmLink>();
		for (int i = 0; i < lenght; i++) {
			link = links.get(i);
			if (link.getStmLink() == null
					|| link.getStmLink().getStartInterface() == null
					|| link.getStmLink().getEndInterface() == null) {
				toRemoveLinks.add(link);
				continue;
			}
			GenericInterface start = link.getStmLink().getStartInterface();
			GenericInterface end = link.getStmLink().getEndInterface();

			DefaultGraphCell cell1 = (DefaultGraphCell) temp.get(start
					.getName());
			DefaultGraphCell cell2 = (DefaultGraphCell) temp.get(end.getName());

			if (cell1 == null || cell2 == null) {
				toRemoveLinks.add(link);
				continue;
			}

			DefaultEdge edge = createEdge(cell1, cell2, (float) 2.0, true,
					false, Color.black, true);
			edge.setUserObject(link);
			view.insert(edge);
		}
		int length = toRemoveLinks.size();
		for (int i = 0; i < length; i++)
			links.remove(toRemoveLinks.get(i));
	}
	/**
	 * Asks user if project should be set before exiting
	 * 
	 * @return boolean return true if yes
	 * 
	 */
	private boolean checkProjectSave() {
		if (project != null) {
			int returnVal = JOptionPane.showConfirmDialog(this,
					"Would you save currently opened project");
			if (returnVal == JOptionPane.YES_OPTION) {
				exportProject();
				return true;
			} else if (returnVal == JOptionPane.NO_OPTION) {
				return true;
			} else {
				return false;
			}
		} else
			return true;
	}
	/**
	 * Creates and save the topology snapshot image
	 *
	 */
	private void snapShot() {
		JFileChooser chooser=null;
		
		chooser = new JFileChooser(project.getPath());
			
		chooser.setFileView(new AutobahnTopologyFileView());
		String[] fileExts = {"png"};
		GenericFileFilter filter = new GenericFileFilter(fileExts, "PNG Image Files");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			OutputStream out;
			try {
				out = new FileOutputStream(chooser.getSelectedFile());
				Color bg = null; 
				bg = graph.getBackground();
				BufferedImage img = graph.getImage(bg, 0);
				ImageIO.write(img, "png", out);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "There was problem with storring  snapshot image");
			} 
		}

	}
	/**
	 * Removes the graph elements 
	 */
	private void graphRemove() {
		Object[] cells = graph.getSelectionCells();
		if (cells != null && cells.length > 0) {
			int ret = JOptionPane.showConfirmDialog(this,
					"Would you remove all selected elements?", "Removing",
					JOptionPane.CANCEL_OPTION);
			if (ret == JOptionPane.OK_OPTION) {
				int length = cells.length;
				Object object;
				DefaultGraphCell cell;
				for (int i = 0; i < length; i++) {
					cell = (DefaultGraphCell) cells[i];
					object = ((DefaultGraphCell) cells[i]).getUserObject();
					if (object != null) {
						removeLink(cell);
						removeInterface(cell);
						removeNode(cell);
						project.removeElement(object);
						project.removeGraphPropertes(object.toString());
					}
				}
			
			Object[] cellsNew = graph.getSelectionCells();

			graph.getGraphLayoutCache().remove(cells);
			graph.getGraphLayoutCache().remove(cellsNew);
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Please select at least one element");
		}

	}
	/**
	 * Removes node from the graph and the topology 
	 * @param cell jgraph cell combined with node object
	 */
	private void removeNode(DefaultGraphCell cell) {
		int childCount;
		Object object = cell.getUserObject();
		if (project.isNode(object)) {
			childCount = cell.getChildCount();
			graph.getSelectionModel().addSelectionCell(cell);
			for (int j = 0; j < childCount; j++) {
				DefaultPort port = (DefaultPort) cell.getChildAt(j);
				graph.getSelectionModel().addSelectionCell(port);
				Iterator edges = port.getEdges().iterator();
				DefaultEdge edge = null;

				while (edges.hasNext()) {
					edge = (DefaultEdge) edges.next();

					DefaultGraphCell src = (DefaultGraphCell) ((DefaultPort) edge
							.getSource()).getParent();
					if (project.isInterface(src.getUserObject()))
						removeInterface(src);
					else
						graph.getSelectionModel().addSelectionCell(src);

					DefaultGraphCell target = (DefaultGraphCell) ((DefaultPort) edge
							.getTarget()).getParent();
					if (project.isInterface(target.getUserObject()))
						removeInterface(target);
					else
						graph.getSelectionModel().addSelectionCell(target);

					graph.getSelectionModel().addSelectionCell(edge);

				}
			}
		}
	}
	/**
	 * Checks and removes if possible the interface from the graph and topology 
	 * @param cell jgraph cell related with interface
	 * @return	boolean true if interface is removed
	 */
	private boolean removeInterface(DefaultGraphCell cell) {
		Object object = cell.getUserObject();
		if (project.isInterface(object)) {
			DefaultPort port = (DefaultPort) cell.getChildAt(0);
			graph.getSelectionModel().addSelectionCell(port);
			Iterator edges = port.getEdges().iterator();
			DefaultEdge edge = null;
			graph.getSelectionModel().addSelectionCell(cell);
			
			while (edges.hasNext()) {
				edge = (DefaultEdge) edges.next();
				System.out.println ("edge:"+edge);
				GraphConstants.setSelectable(edge.getAttributes(), true);
				graph.getSelectionModel().addSelectionCell(edge);
				if (edge.getUserObject() != null) {
					project.removeElement(edge.getUserObject());
					project.removeGraphPropertes(object.toString());
				}
			}
			
			project.removeElement(object);
			return true;
		}
		return false;
	}
	/**
	 * Checks and removes if possible the link from the graph and topology 
	 * @param cell jgraph cell related with link
	 * @return	boolean true if link is removed
	 */
	public boolean removeLink(DefaultGraphCell cell) {
		Object object = cell.getUserObject();
		if (project.isLink(object)) {
			graph.addSelectionCell(cell);
			if (project.getTopology().getEthernetTopology() != null) {
				project.getTopology().getEthernetTopology()
						.removeSpanningsTreeForLink(object);
				project.removeLink(object);
			}
			return true;
		}
		return false;
	}
	/**
	 * Creates new interface and add it to the graph and topology
	 */
	private void graphCreateNewInterface() {
		if (graph.getSelectionCell() != null) {
			DefaultGraphCell cell = (DefaultGraphCell) graph.getSelectionCell();
			if (!project.isNode(cell.getUserObject())) {
				return;
			}
			Rectangle2D rect = GraphConstants.getBounds(cell.getAttributes());
			DefaultGraphCell interfaceCell = createCell(rect.getX(), rect
					.getY()
					+ rect.getHeight() + 50, 80, 50, false, false, Color.white,
					interfaceIcon);
			DefaultEdge edge = createEdge(cell, interfaceCell, (float) 0.1,
					true, false, Color.cyan, false);

			GraphConstants.setSelectable(edge.getAttributes(), true);
			Object genericInterface = project.createInterface(cell
					.getUserObject());
			interfaceCell.setUserObject(genericInterface);
			view.insert(interfaceCell);
			view.insert(edge);

			graph.getSelectionCells(null);

			project.putGraphPropertes(genericInterface.toString(), new Rect(
					GraphConstants.getBounds(interfaceCell.getAttributes())
							.getBounds()));

			graph.updateUI();
			graph.getSelectionModel().setSelectionCell(interfaceCell);

		} else {
			JOptionPane.showMessageDialog(this,
					"Please mark exactly one objects");
		}
	}
	/**
	 * Crates edge on the graph
	 * 
	 * @param cell1 cell connected with edge	
	 * @param cell2 cell connected with edge
	 * @param width line width
	 * @param selectable specify if edge can be selected
	 * @param editable specify if edge can be edited
	 * @param color	specify if edge color
	 * @param direction specify the edge direction
	 * @return created edge
	 */
	public DefaultEdge createEdge(DefaultGraphCell cell1,
			DefaultGraphCell cell2, float width, boolean selectable,
			boolean editable, Color color, boolean direction) {
		DefaultPort startPort = (DefaultPort) cell1.getChildAt(0);
		DefaultPort endPort = (DefaultPort) cell2.getChildAt(0);
		DefaultEdge edge = new DefaultEdge();
		GraphConstants.setLineWidth(edge.getAttributes(), width);
		GraphConstants.setFont(edge.getAttributes(),
				AutobahnTopologyBuilder.font);
		edge.setSource(startPort);
		edge.setTarget(endPort);
		GraphConstants.setSelectable(edge.getAttributes(), selectable);
		GraphConstants.setEditable(edge.getAttributes(), editable);
		GraphConstants.setLineColor(edge.getAttributes(), color);
		GraphConstants.setDisconnectable(edge.getAttributes(), false);
		if (direction){
			GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_TECHNICAL);
		}
		return edge;
	}
	/**
	 * Creates and adds new link to the graph and topology 
	 */
	private void graphCreateNewLink() {
		Object[] cells = graph.getSelectionCells();
		if (cells != null && cells.length == 2) {
			DefaultGraphCell cell1 = (DefaultGraphCell) cells[0];
			DefaultGraphCell cell2 = (DefaultGraphCell) cells[1];

			if (cell1 == null || cell2 == null
					|| !project.isInterface(cell1.getUserObject())
					|| !project.isInterface(cell2.getUserObject())) {
				JOptionPane.showMessageDialog(this,
						"Please mark two interfaces");
				return;
			}
			DefaultEdge edge = createEdge(cell1, cell2, (float) 2.0, true,
					false, Color.black, true);
			Object link = project.createLink(cell1.getUserObject(), cell2
					.getUserObject());
			edge.setUserObject(link);
			getAddLinkButton().setEnabled(false);
			graph.getSelectionModel().setSelectionCell(edge);
			view.insert(edge);

		} else {
			JOptionPane.showMessageDialog(this,
					"Please mark exactly two objects");
		}

	}
	/**
	 * Creates and adds new node to the graph and topology 
	 */
	private void graphCreateNewNode() {
		Rectangle2D rect = graph.getBounds();
		DefaultGraphCell cell = createCell(rect.getCenterX(),
				rect.getCenterY(), 80, 30, false, false, Color.white, nodeIcon);
		cell.setUserObject(project.createNode());
		view.insert(cell);
		graph.getSelectionModel().setSelectionCell(cell);
		project.putGraphPropertes(cell.getUserObject().toString(), new Rect(
				GraphConstants.getBounds(cell.getAttributes()).getBounds()));

	}
	/**
	 * Creates new cell
	 * 
	 * @param x x position
	 * @param y y position
	 * @param width	cell width
	 * @param height cell height
	 * @param editable specify if cell can be edited
	 * @param sizeable specify if cell can be resized
	 * @param color specify color
	 * @param icon icon for the cell
	 * @return	created cell
	 */
	public DefaultGraphCell createCell(double x, double y, double width,
			double height, boolean editable, boolean sizeable, Color color,
			ImageIcon icon) {
		DefaultGraphCell cell = new DefaultGraphCell();
		GraphConstants.setFont(cell.getAttributes(),
				AutobahnTopologyBuilder.font);
		GraphConstants.setEditable(cell.getAttributes(), editable);
		GraphConstants.setSizeable(cell.getAttributes(), sizeable);
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(
				x, y, width, height));
		GraphConstants.setGradientColor(cell.getAttributes(), color);
		GraphConstants.setOpaque(cell.getAttributes(), true);
		GraphConstants.setIcon(cell.getAttributes(), icon);
		GraphConstants.setAutoSize(cell.getAttributes(), true);
		DefaultPort port = new DefaultPort();
		cell.add(port);
		return cell;
	}
	/**
	 * Saves project with new location
	 */
	private void saveAsProject() {
		if (project==null)
			return;
		JFileChooser chooser=null;
		if( lastPath== null)
			chooser= new JFileChooser("./");
		else
			chooser= new JFileChooser(lastPath);
			
		chooser.setFileView(new AutobahnTopologyFileView());
		FileFilter filter = new AutoBahnTopologyFileFilter();
		chooser.setFileFilter(filter);
		Object[] cells = view.getCells(false, true, false, false);
		int length = cells.length;
		for (int i = 0; i < length; i++) {
			DefaultGraphCell cell = (DefaultGraphCell) cells[i];
			Object object = cell.getUserObject();
			if (object != null) {
				if (project.isNode(object) || project.isInterface(object))
					project.putGraphPropertes(object.toString(), new Rect(
							GraphConstants.getBounds(cell.getAttributes())
									.getBounds()));

			}
		}
		int returnVal = chooser.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path =chooser.getSelectedFile().getPath();
			System.out.println (path);
			
			int index =path.lastIndexOf('\\');
			if (index==-1){
				index =path.lastIndexOf('/');
			}
			System.out.println (index);
			String directory=null;
			String file=null;
		
			if (index != -1){
				directory = path.substring(0, index);
				file= path.substring(index+1, path.length());
				index = file.lastIndexOf('.');
				if (index != -1)
					file = file.substring(0,index);
			}
			lastPath= directory;
			if (directory!= null)
			project.setPath(directory);
			if (file !=null){
				project.setPath(directory);
				project.setName(file);
				setTitle("AutoBahn Topology Builder: " + file);
			}
			
			TopologyProject.exportProject(project, chooser.getSelectedFile()
					.getName());
			
		}
	}
	/**
	 * Saves project
	 */
	public void save (){
		if (project == null)
			return;
		Object[] cells = view.getCells(false, true, false, false);
		int length = cells.length;
		for (int i = 0; i < length; i++) {
			DefaultGraphCell cell = (DefaultGraphCell) cells[i];
			Object object = cell.getUserObject();
			if (object != null) {
				if (project.isNode(object) || project.isInterface(object))
					project.putGraphPropertes(object.toString(), new Rect(
							graph.getCellBounds(cell).getBounds()));
				}
		}
		String fileName = project.getPath()+"/"+project.getName()+".xml";
		File file = new File (fileName);
		if (file.exists())
			file.renameTo(new File(fileName+"bak"));
		file.delete();
		TopologyProject.exportProject(project, project.getPath()+"/"+project.getName()+".xml");
	}
	/**
	 * Opens project for editing
	 */
	private void openProject() {
		if (!checkProjectSave())
			return;
		JFileChooser chooser;
		if (lastPath==null)
			chooser = new JFileChooser("./");
		else
			chooser = new JFileChooser(lastPath);
		chooser.setFileView(new AutobahnTopologyFileView());
		FileFilter filter = new AutoBahnTopologyFileFilter();
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			project = TopologyProject.importProject(chooser.getSelectedFile().getAbsolutePath());
			lastPath = project.getPath();
			System.out.println (lastPath);
			view.remove(view.getCells(true, true, true, true));
			
			if (project != null) {
				setTitle("AutoBahn Topology Builder: " + project.getName());
				if (project.getTopology().getSdhTopology() != null) {
					sdhTopologyShownig(project.getTopology(), project
							.getGraphProperties());
				}
				if (project.getTopology().getEthernetTopology() != null) {
					ethernetTopologyShowing(project.getTopology(), project
							.getGraphProperties());
				}
				ProjectFactory.setProjectTools(project);
				activateProject(true);
				setTitle("AutoBahn Topology Builder: " + project.getName());
			}

		}else
		{
			activateProject(false);
		}
	}
	/**
	 * Creates new project
	 */
	private void createNewProject() {

		if (!checkProjectSave())
			return;
		NewProjectDialog dialog = new NewProjectDialog();
		project = dialog.openDialog();
		view.remove(view.getCells(true, true, true, true));
		if (project != null) {
			setTitle("AutoBahn Topology Builder: " + project.getName());
			activateProject(true);
		}

	}
	/**
	 * Export topology to xml file
	 */
	private void exportTopology() {
		if (project==null)
			return;
		JFileChooser chooser = new JFileChooser(project.getPath());
		chooser.setFileView(new AutobahnTopologyFileView());
		FileFilter filter = new AutoBahnTopologyFileFilter();
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			TopologyProject.exportTopolgy(project.getTopology(), chooser
					.getSelectedFile().getAbsolutePath());
		}
	}
	/**
	 * Export project
	 */
	private void exportProject() {
		if (project!= null)
			return;
		JFileChooser chooser = new JFileChooser(project.getPath());
		chooser.setFileView(new AutobahnTopologyFileView());
		FileFilter filter = new AutoBahnTopologyFileFilter();
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			TopologyProject.exportProject(project, chooser.getSelectedFile()
					.getAbsolutePath());
		}
	}
	/*
	 * (non-Javadoc)
	 * @see org.jgraph.event.GraphSelectionListener#valueChanged(org.jgraph.event.GraphSelectionEvent)
	 */
	public void valueChanged(GraphSelectionEvent e) {
		Object[] selected = graph.getSelectionCells();
		getRemove().setEnabled(false);
		getAddInterface().setEnabled(false);
		getAddLinkButton().setEnabled(false);
		getSpanningTree().setEnabled(false);
		getSwitchDirection().setEnabled(false);
		if (selected == null || selected.length < 1) {
			propertiesTable.setModel(emptyModel);
			return;
		}

		if (selected.length == 1) {
			getRemove().setEnabled(true);
			Object object = ((DefaultGraphCell) selected[0]).getUserObject();
			if (object == null)
				return;
			if (project == null)
				return;
			if (project.isLink(object)){
					if (project.getTopology().getEthernetTopology() != null){
						selectedLink = (EthLink) object;
						getSpanningTree().setEnabled(true);
					}
				getSwitchDirection().setEnabled(true);
				
			} else {
				getSpanningTree().setEnabled(false);
				getSwitchDirection().setEnabled(false);
				selectedLink = null;
				
			}
			if (project.isNode(object))
				getAddInterface().setEnabled(true);
			else
				getAddInterface().setEnabled(false);
		}
		if (selected.length == 2){
			getRemove().setEnabled(true);
			if (project.isInterface(((DefaultGraphCell) selected[0])
					.getUserObject())
					&& project.isInterface(((DefaultGraphCell) selected[1])
							.getUserObject()))
				if (!project.checkIfLinkExist(((DefaultGraphCell) selected[0])
						.getUserObject(), ((DefaultGraphCell) selected[1])
						.getUserObject()))
					getAddLinkButton().setEnabled(true);

				else
					getAddLinkButton().setEnabled(false);
		}

		Object object = ((DefaultGraphCell) selected[0]).getUserObject();
		if (object == null) {
			propertiesTable.setModel(emptyModel);
			propertiesTable.getColumnModel().getColumn(0).setWidth(100);
			propertiesTable.getColumnModel().getColumn(0)
					.setPreferredWidth(100);
			propertiesTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
			return;
		}

		ObjectPropertiesModel model = project.getPropertiesModel(object
				.getClass().getName());

		if (model != null) {
			model.setUserObject(object);
			propertiesTable.setModel(model.getTableModel());
			propertiesTable.updateUI();
			BooleanClassRanderer editor = new BooleanClassRanderer(
					new JCheckBox());
			propertiesTable.setDefaultRenderer(Boolean.class, editor);
			propertiesTable.setDefaultEditor(Boolean.class, editor);
		} else {
			propertiesTable.setModel(emptyModel);

		}
	}
	/**
	 * Changes the properities table model to valid for edited type
	 * 
	 * @param model	model
	 * @param value edited object
	 */
	public void changeMode(ObjectPropertiesModel model, Object value) {
		model.setUserObject(value);
		propertiesTable.setModel(model.getTableModel());
	}

} // @jve:decl-index=0:visual-constraint="10,10"

