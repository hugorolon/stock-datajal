package py.com.prestosoftware.ui.helpers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * @web http://jc-mouse.blogspot.com/
 * @author Mouse
 */
public class ImagenPanel extends JPanel {

	private static final long serialVersionUID = -1705720631384479273L;
	private Image imagenOriginal;
    private Image imagenTemporal;    
    private BufferedImage imagenMemoria;
    private Graphics2D g2D;
    private Boolean tieneImagen = false;   
    
    private int valEscalaX=0;
    private int valEscalaY=0;

    /* al crear el objeto se crea con una imagen pasada como parametro*/
    public ImagenPanel(BufferedImage archivo){
        this.imagenOriginal = archivo;
        this.imagenTemporal = archivo;
        this.setSize(archivo.getWidth(),archivo.getHeight());
        this.setVisible(true);
        this.tieneImagen=true;
    }

    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      if(this.tieneImagen){
        imagenMemoria = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        g2D = imagenMemoria.createGraphics();
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //se anhade la foto
        g2D.drawImage(imagenTemporal,0, 0, imagenTemporal.getWidth(this), imagenTemporal.getHeight(this), this);
        g2.drawImage(imagenMemoria, 0, 0, this);
      }
    }

    public void Aumentar(int Valor_Zoom){
        //se calcula el incremento
        valEscalaX =  (int) (imagenTemporal.getWidth(this) * escala(Valor_Zoom) );
        valEscalaY =  (int) (imagenTemporal.getHeight(this) * escala(Valor_Zoom) );
        //se escala la imagen sumado el nuevo incremento
        this.imagenTemporal = imagenTemporal.getScaledInstance((int) (imagenTemporal.getWidth(this) + valEscalaX), (int) (imagenTemporal.getHeight(this) + valEscalaY), Image.SCALE_AREA_AVERAGING);
        resize();
    }

    public void Disminuir(int valorZoom){
        valEscalaX =  (int) (imagenTemporal.getWidth(this) * escala(valorZoom) );
        valEscalaY =  (int) (imagenTemporal.getHeight(this) * escala(valorZoom) );
        this.imagenTemporal = imagenTemporal.getScaledInstance((int) (imagenTemporal.getWidth(this) - valEscalaX), (int) (imagenTemporal.getHeight(this) - valEscalaY), Image.SCALE_AREA_AVERAGING);
        resize();
     }

    private float escala(int valor){
        return  valor/100f;
    }

    public void Restaurar(){
        this.imagenTemporal = this.imagenOriginal;
        resize();
    }

    private void resize(){
        this.setSize(imagenTemporal.getWidth(this),imagenTemporal.getHeight(this));
    }
}
