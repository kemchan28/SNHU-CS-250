import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.*;

public class TopFiveDestiationList {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TopDestinationListFrame topDestinationListFrame = new TopDestinationListFrame();
				topDestinationListFrame.setTitle("Top 5 Dstination List");
				topDestinationListFrame.setVisible(true);
			}
		});
	}
}

class Destinations {
	static final String canadaURL = "http://travel.quarkexpeditions.com/artic/canadian-artic/";
	static final String canadaDesc = "Experience the mountains.";
	static final ImageIcon canadaImg = new ImageIcon(Objects.requireNonNull(Destinations.class.getResource("/resources/canadian_artic.png")));
	
	static final String irelandURL = "http://www.gocolette.com/en/tours/europe/ireland/shades-of-ireland";
	static final String irelandDesc = "Experience Ireland charms.";
	static final ImageIcon irelandImg = new ImageIcon(Objects.requireNonNull(Destinations.class.getResource("/resources/ireland.png")));
	
	static final String spainURL = "http://www.gocolette.com/en/tours/europe/spain/spain-classics";
	static final String spainDesc = "Embark on a Spanish adventure.";
	static final ImageIcon spainImg = new ImageIcon(Objects.requireNonNull(Destinations.class.getResource("/resources/spain.png")));
	
	static final String beachURL = "http://www.imdb.com/title/tt0163978/";
	static final String beachDesc = "The beaches.";
	static final ImageIcon beachImg = new ImageIcon(Objects.requireNonNull(Destinations.class.getResource("/resources/beach.png")));
	
	static final String puntaURL = "http://www.cheapcaribbean.com/destinations/dominican-republic/punta-cana-test";
	static final String puntaDesc = "Punta Cana has got you covered.";
	static final ImageIcon puntaImg = new ImageIcon(Objects.requireNonNull(Destinations.class.getResource("/resources/punta.png")));
	
}

class TopDestinationListFrame extends JFrame {
	private DefaultListModel listModel;
	
	public TopDestinationListFrame() {
		super("Top Five Destination List");
		setDefaultCloseOperation(WindowConstants.EXIR_ON_CLOSE);
		setSize(900,750);
		
		listModel = new DefaultListModel();
		
		//updates to top 5 lists below, import new image files to resource directory
		addDestinationNameAndPicture(Destinations.canadaDesc, Destinations.canadaImg, Destinations.canadaURL);
        addDestinationNameAndPicture(Destinations.irelandDesc, Destinations.irelandImg, Destinations.irelandURL);
        addDestinationNameAndPicture(Destinations.spainDesc, Destinations.spainImg, Destinations.spainURL);
        addDestinationNameAndPicture(Destinations.beachDesc, Destinations.beachImg, Destinations.beachURL);
        addDestinationNameAndPicture(Destinations.puntaDesc, Destinations.puntaImg, Destinations.puntaURL);
        
        JList list = new JList(listModel);
        JScrollPane scrollPane = new JScrollPane(list);

        // Call selectPackageLister() method to enable package selection.
        selectPackageListener(list);

        TextAndIconListCellRenderer renderer = new TextAndIconListCellRenderer(10);

        list.setCellRenderer(renderer);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void addDestinationNameAndPicture(String text, Icon icon, String url) {
        TextAndIcon tai = new TextAndIcon(text, icon, url);
        listModel.addElement(tai);
    }

    // Helper method to implement MouseListener for selecting a package.
    public static void selectPackageListener(JList list) {
        list.addMouseListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        final TextAndIcon selectedCell = (TextAndIcon) list.getSelectedValue();
                        try {
                            Desktop.getDesktop().browse(new URI(selectedCell.getUrl()));
                        } catch (IOException | URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                    }

                    // Other MouseListener methods must be Overridden
                    // unless the class is abstracted. (according to my IDE)
                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                }
        );
    };
}


class TextAndIcon {
    private String text;
    private Icon icon;
    private String url;

    public TextAndIcon(String text, Icon icon, String url) {
        this.text = text;
        this.icon = icon;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


class TextAndIconListCellRenderer extends JLabel implements ListCellRenderer {
    private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

    private Border insideBorder;

    public TextAndIconListCellRenderer() {
        this(0, 0, 0, 0);
    }

    public TextAndIconListCellRenderer(int padding) {
        this(padding, padding, padding, padding);
    }

    public TextAndIconListCellRenderer(int topPadding, int rightPadding, int bottomPadding, int leftPadding) {
        insideBorder = BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding);
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean hasFocus) {
        // The object from the combo box model MUST be a TextAndIcon.
        TextAndIcon tai = (TextAndIcon) value;

        // Sets text and icon on 'this' JLabel.
        setText(tai.getText());
        setIcon(tai.getIcon());

        // Sets different foreground and background colors for selected cells
        list.setSelectionForeground(Color.white);
        list.setSelectionBackground(Color.darkGray);

        // Sets different foreground and background colors for unselected cells
        list.setBackground(Color.black);
        list.setForeground(Color.gray);


        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        Border outsideBorder;

        if (hasFocus) {
            outsideBorder = UIManager.getBorder("List.focusCellHighlightBorder");
        } else {
            outsideBorder = UIManager.getBorder("");
        }

        setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
        setComponentOrientation(list.getComponentOrientation());
        setEnabled(list.isEnabled());
        setFont(list.getFont());

        return this;
    }

    // The following methods are overridden to be empty for performance
    // reasons. If you want to understand better why, please read:
    //
    // http://java.sun.com/javase/6/docs/api/javax/swing/DefaultListCellRenderer.html#override

    public void validate() {
    }

    public void invalidate() {
    }

    public void repaint() {
    }

    public void revalidate() {
    }

    public void repaint(long tm, int x, int y, int width, int height) {
    }

    public void repaint(Rectangle r) { 
        
	}
}