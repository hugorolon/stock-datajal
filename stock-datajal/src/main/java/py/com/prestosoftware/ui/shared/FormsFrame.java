package py.com.prestosoftware.ui.shared;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.util.Borders;
import py.com.prestosoftware.util.ConstMessagesEN;
import py.com.prestosoftware.util.LookAndFeelUtils;

@Component
public class FormsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton addressesBtn;
    private JButton clientBtn;
    private JButton reservationStatusBtn;
    private JButton paymentMethodBtn;
    private JButton paymentBtn;
    private JButton reservationBtn;
    private JButton roomStatusBtn;
    private JButton roomTypeBtn;
    private JButton roomBtn;
    private JButton rateBtn;
    private JButton roomXReservationBtn;

    public FormsFrame() {
        setFrameUp();
        initComponents();
        pack();
    }

    private void setFrameUp() {
        getRootPane().setBorder(Borders.createEmptyBorder());
        setTitle(ConstMessagesEN.Labels.FORMS);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        LookAndFeelUtils.setWindowsLookAndFeel();
        setLayout(new GridLayout(6, 2, 20, 20));
    }

    private void initComponents() {
        addressesBtn = new JButton(ConstMessagesEN.Labels.ADDRESSES);
        clientBtn = new JButton(ConstMessagesEN.Labels.CLIENTS);
        reservationStatusBtn = new JButton(ConstMessagesEN.Labels.RESERVATION_STATUSES);
        paymentMethodBtn = new JButton(ConstMessagesEN.Labels.PAYMENT_METHODS);
        paymentBtn = new JButton(ConstMessagesEN.Labels.PAYMENTS);
        reservationBtn = new JButton(ConstMessagesEN.Labels.RESERVATIONS);
        roomStatusBtn = new JButton(ConstMessagesEN.Labels.ROOM_STATUSES);
        roomTypeBtn = new JButton(ConstMessagesEN.Labels.ROOM_TYPES);
        roomBtn = new JButton(ConstMessagesEN.Labels.ROOMS);
        rateBtn = new JButton(ConstMessagesEN.Labels.RATES);
        roomXReservationBtn = new JButton(ConstMessagesEN.Labels.ROOM_X_RESERVATIONS);

        add(addressesBtn);
        add(clientBtn);
        add(reservationStatusBtn);
        add(paymentMethodBtn);
        add(paymentBtn);
        add(reservationBtn);
        add(roomStatusBtn);
        add(roomTypeBtn);
        add(roomBtn);
        add(rateBtn);
        add(roomXReservationBtn);
    }

    public JButton getAddressesBtn() {
        return addressesBtn;
    }

    public JButton getClientBtn() {
        return clientBtn;
    }

    public JButton getReservationStatusBtn() {
        return reservationStatusBtn;
    }

    public JButton getReservationBtn() {
        return reservationBtn;
    }

    public JButton getPaymentBtn() {
        return paymentBtn;
    }

    public JButton getPaymentMethodBtn() {
        return paymentMethodBtn;
    }

    public JButton getRoomStatusBtn() {
        return roomStatusBtn;
    }

    public JButton getRoomTypeBtn() {
        return roomTypeBtn;
    }

    public JButton getRoomBtn() {
        return roomBtn;
    }

    public JButton getRateBtn() {
        return rateBtn;
    }

    public JButton getRoomXReservationBtn() {
        return roomXReservationBtn;
    }
}
