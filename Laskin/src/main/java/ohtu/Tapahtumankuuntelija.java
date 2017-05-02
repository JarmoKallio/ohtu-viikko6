package ohtu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JTextField;

public class Tapahtumankuuntelija implements ActionListener {

    private JButton nollaa;
    private JButton undo;
    private Sovelluslogiikka sovellus;
    private Map<JButton, Komento> komennot;
    private Komento edellinen;

    public Tapahtumankuuntelija(JButton plus, JButton miinus, JButton nollaa, JButton undo, JTextField tuloskentta, JTextField syotekentta) {
        this.nollaa = nollaa;
        this.undo = undo;
        this.sovellus = new Sovelluslogiikka();
        komennot = new HashMap<>();
        komennot.put(plus, new Summa(sovellus, tuloskentta, syotekentta));
        komennot.put(miinus, new Erotus(sovellus, tuloskentta, syotekentta));
        komennot.put(nollaa, new Nollaa(sovellus, tuloskentta, syotekentta));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        Komento komento = komennot.get(ae.getSource());
        if (komento != null) {
            komento.suorita();
            edellinen = komento;
        } else {
            // toiminto oli undo
            edellinen.peru();
            edellinen = null;
        }

        nollaa.setEnabled(sovellus.tulos() != 0);
        undo.setEnabled(edellinen != null);
    }

    private class Summa extends Komento {

        private int vanhaLukema = 0;
        int arvo;
        private JTextField tuloskentta;
        private Sovelluslogiikka sovellus;
        private JTextField syotekentta;

        public Summa(Sovelluslogiikka sovellus, JTextField tuloskentta, JTextField syotekentta) {
            this.tuloskentta = tuloskentta;
            this.syotekentta = syotekentta;
            this.sovellus = sovellus;

        }

        @Override
        public void suorita() {
            try {
                this.arvo = Integer.parseInt(syotekentta.getText());
            } catch (Exception e) {
            }
            vanhaLukema = sovellus.tulos();
            sovellus.plus(arvo);
            int laskunTulos = sovellus.tulos();
            syotekentta.setText("");
            tuloskentta.setText("" + laskunTulos);
            checkEnabled(laskunTulos);

        }

        @Override
        public void peru() {
            sovellus.nollaa();
            sovellus.plus(vanhaLukema);
            tuloskentta.setText("" + vanhaLukema);
            checkEnabled(vanhaLukema);
        }

        private void checkEnabled(int tulos) {
            if (tulos == 0) {
                nollaa.setEnabled(false);
            } else {
                nollaa.setEnabled(true);
            }
            undo.setEnabled(true);
        }

    }

    private class Erotus extends Komento {

        private int vanhaLukema = 0;
        int arvo;
        private JTextField tuloskentta;
        private Sovelluslogiikka sovellus;
        private JTextField syotekentta;

        public Erotus(Sovelluslogiikka sovellus, JTextField tuloskentta, JTextField syotekentta) {
            this.tuloskentta = tuloskentta;
            this.syotekentta = syotekentta;
            this.sovellus = sovellus;

        }

        @Override
        public void suorita() {
            try {
                this.arvo = Integer.parseInt(syotekentta.getText());
            } catch (Exception e) {
            }
            vanhaLukema = sovellus.tulos();
            sovellus.miinus(arvo);
            int laskunTulos = sovellus.tulos();
            syotekentta.setText("");
            tuloskentta.setText("" + laskunTulos);
            checkEnabled(laskunTulos);
        }

        @Override
        public void peru() {
            sovellus.nollaa();
            sovellus.plus(vanhaLukema);
            tuloskentta.setText("" + vanhaLukema);
            checkEnabled(vanhaLukema);
        }

        private void checkEnabled(int tulos) {
            if (tulos == 0) {
                nollaa.setEnabled(false);
            } else {
                nollaa.setEnabled(true);
            }
            undo.setEnabled(true);
        }

    }

    private class Nollaa extends Komento {

        private int vanhaLukema = 0;
        private JTextField tuloskentta;
        private Sovelluslogiikka sovellus;
        private JTextField syotekentta;

        public Nollaa(Sovelluslogiikka sovellus, JTextField tuloskentta, JTextField syotekentta) {
            this.tuloskentta = tuloskentta;
            this.syotekentta = syotekentta;
            this.sovellus = sovellus;

        }

        @Override
        public void suorita() {
            vanhaLukema = sovellus.tulos();
            sovellus.nollaa();
            int laskunTulos = sovellus.tulos();
            syotekentta.setText("");
            tuloskentta.setText("" + laskunTulos);
            checkEnabled(laskunTulos);

        }

        @Override
        public void peru() {
            sovellus.nollaa();
            sovellus.plus(vanhaLukema);
            tuloskentta.setText("" + vanhaLukema);
            checkEnabled(vanhaLukema);
        }

        private void checkEnabled(int tulos) {
            if (tulos == 0) {
                nollaa.setEnabled(false);
            } else {
                nollaa.setEnabled(true);
            }
            undo.setEnabled(true);
        }
    }

}
