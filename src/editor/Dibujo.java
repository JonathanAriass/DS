package editor;
import java.util.*;

import figuras.Historial;

public class Dibujo {
	public void AddFigura(Figura figura) {
		figuras.add(figura);
	}
	
	public void dibuja() {
		for (Figura figura : figuras)
			figura.dibujar();
	}

	public Figura getFigura(int x, int y) {
		for (Figura figura : figuras)
			if (figura.contiene(x, y))
				return figura;
		return null;
	}
	
	// metodo borrar para borrar en la lista
	public void removeFigura(Figura figura) {
		figuras.remove(figura);
	}
	
	public void undo() {
		cambios.undo();
	}

	public void redo() {
		cambios.redo();
	}
	
	// hacer un undo y redo aqui que es el que controla que hacer

	List<Figura> figuras = new ArrayList<Figura>();
	Historial cambios = new Historial();
}
