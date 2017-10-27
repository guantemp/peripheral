/*
 * Created by JFormDesigner on Fri Nov 29 15:44:29 CST 2013
 */

package cc.foxtail.peripheral.swing;

import javax.swing.*;
import java.awt.*;

/**
 * @author rtyh
 */
public class Wer extends JDialog {
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JToolBar toolBar1;
    private JButton button2;
    private JToolBar toolBar2;
    private JButton button1;

    public Wer(Frame owner) {
        super(owner);
        initComponents();
    }

    public Wer(Dialog owner) {
        super(owner);
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        toolBar1 = new JToolBar();
        button2 = new JButton();
        toolBar2 = new JToolBar();
        button1 = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setForeground(Color.lightGray);
        setResizable(false);
        setTitle("\u5c0f\u7968\u8bbe\u8ba1\u5668");
        setMinimumSize(new Dimension(400, 300));
        setLocationByPlatform(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== toolBar1 ========
        {
            toolBar1.setFloatable(false);

            //---- button2 ----
            button2.setText("text");
            toolBar1.add(button2);
        }
        contentPane.add(toolBar1, BorderLayout.NORTH);

        //======== toolBar2 ========
        {
            toolBar2.setFloatable(false);
            toolBar2.setOrientation(SwingConstants.VERTICAL);

            //---- button1 ----
            button1.setText("text");
            toolBar2.add(button1);
        }
        contentPane.add(toolBar2, BorderLayout.WEST);
        setSize(400, 300);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
