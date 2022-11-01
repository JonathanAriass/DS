package figuras;

import editor.Dibujo;
import editor.Figura;

public class CambioCreacion implements Cambio {

	private Figura figura;
	private Dibujo dibujo;
	
	public CambioCreacion(Dibujo d, Figura f) {
		figura = f;
		dibujo = d;
	}

	@Override
	public void undo() {
		dibujo.removeFigura(figura);
	}

	@Override
	public void redo() {
		dibujo.AddFigura(figura);
	}

}
