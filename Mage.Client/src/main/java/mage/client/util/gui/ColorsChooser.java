package mage.client.util.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import mage.client.constants.Constants;
import org.mage.card.arcane.ManaSymbols;

public class ColorsChooser extends JComboBox implements ListCellRenderer {

    private static final long serialVersionUID = 1L;

    public ColorsChooser(final String colors) {

        this.setRenderer(this);

        final DefaultComboBoxModel model = new DefaultComboBoxModel();

        model.addElement("u");
        model.addElement("r");
        model.addElement("b");
        model.addElement("g");
        model.addElement("w");
        model.addElement("x");

        model.addElement("bu");
        model.addElement("bg");
        model.addElement("br");
        model.addElement("bw");
        model.addElement("ug");
        model.addElement("ur");
        model.addElement("uw");
        model.addElement("gr");
        model.addElement("gw");
        model.addElement("rw");
        model.addElement("xx");

        model.addElement("bur");
        model.addElement("buw");
        model.addElement("bug");
        model.addElement("brg");
        model.addElement("brw");
        model.addElement("bgw");
        model.addElement("wur");
        model.addElement("wug");
        model.addElement("wrg");
        model.addElement("rgu");
        model.addElement("xxx");

        setModel(model);
        setSelectedItem(colors);
        this.setFocusable(false);
    }

    @Override
    public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected,
            final boolean cellHasFocus) {

        final JPanel panel = new JPanel(new FlowLayout());
        drawOn(panel, (String) value);
        panel.setBorder(Constants.EMPTY_BORDER);
        if (isSelected) {
            panel.setBackground(Color.LIGHT_GRAY);
        }
        return panel;
    }

    private void drawOn(JPanel panel, String value) {
        List<Image> images = new ArrayList<>();
        value = value.toUpperCase(Locale.ENGLISH);
        for (int i = 0; i < value.length(); i++) {
            char symbol = value.charAt(i);
            Image image = ManaSymbols.getSizedManaSymbol(String.valueOf(symbol));
            if (image != null) {
                images.add(image);
            }
        }

        if (images.size() == value.length()) {
            int dx = 0;
            for (Image image : images) {
                ImageIcon icon = new ImageIcon(image);
                JLabel imageLabel = new JLabel();
                imageLabel.setSize(11, 11);
                imageLabel.setLocation(dx, 0);
                imageLabel.setIcon(icon);
                panel.add(imageLabel);
                dx += 13;
            }
        } else {
            String s = value.replace("B", "{B}").replace("R", "{R}").replace("G", "{G}").replace("W", "{W}").replace("U", "{U}").replace("X", "{X}");
            panel.add(new JLabel(s));
        }
    }
}
